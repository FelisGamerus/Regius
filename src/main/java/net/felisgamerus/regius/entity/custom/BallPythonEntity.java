package net.felisgamerus.regius.entity.custom;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.felisgamerus.regius.Config;
import net.felisgamerus.regius.entity.RegiusEntities;
import net.felisgamerus.regius.entity.custom.ai.NearbyPreySensor;
import net.felisgamerus.regius.entity.custom.genetics.LocusMap;
import net.felisgamerus.regius.item.RegiusItems;
import net.felisgamerus.regius.util.ModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.*;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class BallPythonEntity extends Animal implements GeoEntity, DryBucketable, SmartBrainOwner<BallPythonEntity> {
    LocusMap ballPythonGenes = new LocusMap();
    static ArrayList<String> MORPH_REFERENCE = LocusMap.getLociArray();

    public boolean isPushedByFluid() {
        return false;
    }
    protected PathNavigation createNavigation(Level pLevel) {
        return new AmphibiousPathNavigation(this, pLevel);
    }

    public boolean isPickable() {
        return true;
    }
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.ballpython.idle");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.ballpython.walk");
    protected static final RawAnimation BALL = RawAnimation.begin().thenLoop("animation.ballpython.ball");
    protected static final RawAnimation TONGUE = RawAnimation.begin().thenPlay("animation.ballpython.tongue");

    @Override
    public float getAgeScale() {
        return 1.0f; //Because babies being half-scale messes the hitboxes up
    }

    public BallPythonEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1.0F, 1.0F, false);

        //The following two lines of code have been copied from Untamed Wilds. See the "MULTIPARTS" section for more details.
        this.ballPythonParts = new BallPythonEntityPart[this.MULTIPART_COUNT];
        for (int i = 0; i < this.MULTIPART_COUNT; i++) {this.ballPythonParts[i] = new BallPythonEntityPart(this, this.getBbWidth(), this.getBbHeight());
        }
    }

    //SPAWNING
    //A survey of 100 wild-spawned ball pythons says that the chance for a morph is actually closer to 6%
    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {
        //1 in MORPH_CHANCE_ON_SPAWN chance for a spawned ball python to have a special morph
        final int MORPH_CHANCE_ON_SPAWN = Config.morphChanceOnSpawn;
        Random notNormalSeed = new Random();
        if (notNormalSeed.nextInt(MORPH_CHANCE_ON_SPAWN) == 0) {
            //Selects a morph randomly out of all the morphs
            Random morphSeed = new Random();
            String morph = MORPH_REFERENCE.get(morphSeed.nextInt(MORPH_REFERENCE.size()));
            this.setGenotype(morph);
        }
        if (spawnGroupData == null) {
            spawnGroupData = new AgeableMob.AgeableMobGroupData(0.2F);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    //DATA
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(BallPythonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> GENOTYPE = SynchedEntityData.defineId(BallPythonEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> PHENOTYPE = SynchedEntityData.defineId(BallPythonEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
        builder.define(GENOTYPE, "normal");
        builder.define(PHENOTYPE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("FromBucket", this.fromBucket());
        compoundTag.putString("Genotype", this.getGenotype());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFromBucket(compoundTag.getBoolean("FromBucket"));

        String genotype = compoundTag.contains("Genotype") ? compoundTag.getString("Genotype") : "normal";
        setGenotype(genotype);
    }

    //FromBucket
    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean pFromBucket) {
        this.entityData.set(FROM_BUCKET, pFromBucket);
    }

    //Genotype
    public void setGenotype(String genotype) {
        this.setGenes(createGenesFromGenotype(genotype));
        if (!(getGenotypeString(this.ballPythonGenes).equals("normal"))) {
            this.entityData.set(GENOTYPE, genotype);
        } else {
            this.entityData.set(GENOTYPE, "normal");
        }
    }

    public String getGenotype () {
        return this.entityData.get(GENOTYPE);
    }

    //Needed for BallPythonBucketItem
    public static record GenotypeRecord(String genotype) {
        public static final Codec<GenotypeRecord> CODEC;
        static {
            CODEC = Codec.STRING.xmap(GenotypeRecord::new, GenotypeRecord::getGenotype);
        }

        public GenotypeRecord(String genotype) {
            this.genotype = genotype;
        }

        public String getGenotype() {
            return this.genotype;
        }

        public String getPhenotype(String genotype) {
            String phenotype = convertGenotypeToPhenotype(genotype);
            return phenotype;
        }
    }

    //ANIMATIONS
    /*predicate and tonguePredicate methods copied from Naturalist
    Copyright (c) 2022 Starfish Studios, Inc.

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.*/

    private <E extends BallPythonEntity> PlayState predicate(final AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(WALK);
            event.getController().setAnimationSpeed(0.75D);
        } else {
            event.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    private <E extends BallPythonEntity> PlayState tonguePredicate(final AnimationState<E> event) {
        if (this.random.nextInt(1000) < this.ambientSoundTime && event.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            event.getController().forceAnimationReset();

            event.getController().setAnimation(TONGUE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main_controller", 5, this::predicate));
        AnimationController<BallPythonEntity> tongueController = new AnimationController<>(this, "tongue_controller", 0, this::tonguePredicate);
        controllers.add(tongueController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    //ATTRIBUTES
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.165D)
                .add(Attributes.FOLLOW_RANGE, 5f)
                .add(Attributes.ATTACK_DAMAGE, 4.0D) //Change attack-related attributes once constriction is implemented
                .add(Attributes.ATTACK_KNOCKBACK, 0.0D)
                .add(Attributes.ATTACK_SPEED, 4.0D)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    //BRAIN
    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<BallPythonEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new NearbyPreySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<BallPythonEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<BallPythonEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<BallPythonEntity>(
                        new BreedWithPartner<>(),
                        new SetAttackTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    @Override
    public BrainActivityGroup<BallPythonEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new AnimatableMeleeAttack<>(0));
    }

    //MOVEMENT
    public void travel(Vec3 pTravelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }

    }

    //BUCKETS
    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, compoundTag -> {
            compoundTag.putInt("Age", this.getAge());
            compoundTag.putString("Genotype", this.getGenotype());
        });
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
        if (tag.contains("Genotype")) {
            this.setGenotype(tag.getString("Genotype"));
        }
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(RegiusItems.BALL_PYTHON_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_AXOLOTL;
    }

    //INTERACTIONS
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return DryBucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    //MULTIPARTS
    //All code in the MULTIPARTS section has been copied and modified from Untamed Wilds
    //https://github.com/RayTrace082/untamedwilds/tree/1.18.2
    /*Copyright (C) 2020 RayTrace082
    Modifications Copyright (C) 2025 FelisGamerus
    Dec. 8, 2024: Changed parameters to work with Regius ball pythons

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.*/

    public final BallPythonEntityPart[] ballPythonParts;
    public final int MULTIPART_COUNT = 2;
    public int ringBufferIndex = -1;
    public final double[][] ringBuffer = new double[64][3];

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.neoforged.neoforge.entity.PartEntity<?>[] getParts() {
        return this.ballPythonParts;
    }

    //This method currently does not work and I cannot figure out how to get it to work
    public boolean attackEntityPartFrom(BallPythonEntityPart ballPythonPart, DamageSource source, float amount) {
        return this.hurt(source, amount);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    private void setPartPosition(BallPythonEntityPart part, double offsetX, double offsetY, double offsetZ) {
        part.setPos(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    //Haven't figured out how to remove this without causing a StackOverflowError yet
    public void aiStep() {
        if (!this.isNoAi()) {
            if (!isSleeping()) {
                if (this.ringBufferIndex < 0) {
                    for (int i = 0; i < this.ringBuffer.length; ++i) {
                        this.ringBuffer[i][0] = this.getYRot();
                        this.ringBuffer[i][1] = this.getY();
                    }
                }
                this.ringBufferIndex++;
                if (this.ringBufferIndex == this.ringBuffer.length) {
                    this.ringBufferIndex = 0;
                }
                this.ringBuffer[this.ringBufferIndex][0] = this.getYRot();
                this.ringBuffer[ringBufferIndex][1] = this.getY();
                Vector3d[] multipartVectorArray = new Vector3d[this.ballPythonParts.length];

                for (int j = 0; j < this.ballPythonParts.length; ++j) {
                    this.ballPythonParts[j].collideWithNearbyEntities();
                    multipartVectorArray[j] = new Vector3d(this.ballPythonParts[j].getX(), this.ballPythonParts[j].getY(), this.ballPythonParts[j].getZ());
                }
                float f15 = (float) (this.getMovementOffsets(5)[1] - this.getMovementOffsets(10)[1]) * 10.0F * ((float) Math.PI / 180F);
                float f16 = Mth.cos(f15);
                float yaw = this.getYRot() * ((float) Math.PI / 180F);
                float f3 = Mth.sin(yaw);
                float f18 = Mth.cos(yaw);

                for (int k = 0; k < this.MULTIPART_COUNT; ++k) {
                    BallPythonEntityPart ball_python_part = this.ballPythonParts[k];

                    float f7 = yaw;
                    float f20 = Mth.sin(f7);
                    float f21 = Mth.cos(f7);
                    //Controls hitbox order
                    float f23 = (k + 1f) * -1;
                    //Numbers after f3 and f18 control how far children are from parent -- Assuming hitbox width of 1.0, 0.0 is flush, 1.0 is inside parent, pos. numbers move towards front and neg. towards back
                    this.setPartPosition(ball_python_part, -(f3 * 0.0F + f20 * f23) * f16, 0, (f18 * 0.0F + f21 * f23) * f16);

                    this.ballPythonParts[k].xo = multipartVectorArray[k].x;
                    this.ballPythonParts[k].yo = multipartVectorArray[k].y;
                    this.ballPythonParts[k].zo = multipartVectorArray[k].z;
                    this.ballPythonParts[k].xOld = multipartVectorArray[k].x;
                    this.ballPythonParts[k].yOld = multipartVectorArray[k].y;
                    this.ballPythonParts[k].zOld = multipartVectorArray[k].z;
                }
            }
            else{
                for (int k = 0; k < this.MULTIPART_COUNT; ++k) {
                    BallPythonEntityPart ball_python_part = this.ballPythonParts[k];
                    this.setPartPosition(ball_python_part, 0, 0, 0);
                    this.ballPythonParts[k].xo = this.getX();
                    this.ballPythonParts[k].yo = this.getY();
                    this.ballPythonParts[k].zo = this.getZ();
                    this.ballPythonParts[k].xOld = this.getX();
                    this.ballPythonParts[k].yOld = this.getY();
                    this.ballPythonParts[k].zOld = this.getZ();
                }
            }
        }
        super.aiStep();
    }

    public double[] getMovementOffsets(int p_70974_1_) {
        int i = this.ringBufferIndex - p_70974_1_ & 63;
        int j = this.ringBufferIndex - p_70974_1_ - 1 & 63;
        double[] offsetDoubleArray = new double[3];
        double d0 = this.ringBuffer[i][0];
        offsetDoubleArray[0] = d0;
        d0 = this.ringBuffer[i][1];
        offsetDoubleArray[1] = d0;
        return offsetDoubleArray;
    }

    //BREEDING
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ModTags.Items.BALL_PYTHON_BREEDABLE_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        BallPythonEntity babySnake = RegiusEntities.BALL_PYTHON.get().create(level);
        if (babySnake != null) {
            if (otherParent instanceof BallPythonEntity) {
                BallPythonEntity parent1 = (BallPythonEntity) otherParent;
                LocusMap babyGenes = getBabyGenes(this, parent1);
                babySnake.setGenes(babyGenes);
                babySnake.setGenotype(getGenotypeString(babyGenes));
            }
        }
        return babySnake;
    }

    public static LocusMap getBabyGenes (BallPythonEntity parent0, BallPythonEntity parent1) {
        //Gets the genes of the baby ball python
        LocusMap babyGenes = new LocusMap();
        LocusMap parent0Genes = createGenesFromGenotype(parent0.getGenotype());
        LocusMap parent1Genes = createGenesFromGenotype(parent1.getGenotype());

        for (int i = 0; i < MORPH_REFERENCE.size(); i++) {
            String locusName = MORPH_REFERENCE.get(i);
            if (parent0.random.nextBoolean()) {
                babyGenes.getGenes().get(locusName).setAllele0(parent0Genes.getGenes().get(locusName).getAllele0());
            } else {
                babyGenes.getGenes().get(locusName).setAllele0(parent0Genes.getGenes().get(locusName).getAllele1());
            }
        }

        for (int i = 0; i < MORPH_REFERENCE.size(); i++) {
            String locusName = MORPH_REFERENCE.get(i);
            if (parent0.random.nextBoolean()) {
                babyGenes.getGenes().get(locusName).setAllele1(parent1Genes.getGenes().get(locusName).getAllele0());
            } else {
                babyGenes.getGenes().get(locusName).setAllele1(parent1Genes.getGenes().get(locusName).getAllele1());
            }
        }
        return babyGenes;
    }

    //GENETICS
    public void setGenes (LocusMap givenLocusMap) {
        this.ballPythonGenes = givenLocusMap;
    }

    //Converts a ball python's genotype to its visible phenotype
    public static String convertGenotypeToPhenotype (String genotype) {
        String phenotype = "normal";
        ArrayList<String> morphList = new ArrayList<>(Arrays.asList(genotype.split("_")));
        for (int i = 0; i < morphList.size(); i++) {
            String morph = morphList.get(i);
            if (morph.endsWith("-het")) { //Checks if the morph is het recessive. True = skip
                morph = null;
            } else if (morph.endsWith("-super")) { //Checks if the morph is homo dominant. True = no longer super
                String morphNoSuper = morph.substring(0, (morph.length() - 6));
                if (LocusMap.checkType(morphNoSuper, "dominant")) {
                    morph = morphNoSuper;
                }
            }
            if (morph != null) {
                if (phenotype.equals("normal")) {
                    phenotype = morph;
                } else {
                    phenotype += "_" + morph;
                }
            }
        } return phenotype;
    }

    //Returns a string of every trait of a Snake, visible or not (So yes het albinos)
    public static String getGenotypeString(LocusMap genes) {
        ArrayList<String> allMorphs = new ArrayList<>();
        String genotype = "normal";

        for (int i = 0; i < MORPH_REFERENCE.size(); i++) {
            String locusName = MORPH_REFERENCE.get(i);
            int allele0Value = genes.getAllele0(locusName);
            int allele1Value = genes.getAllele1(locusName);
            boolean notNormal = false;
            boolean isCodominantHeterozygous = false;
            boolean isHomozygous = false;

            if ((allele0Value != allele1Value) || (allele0Value != 0)) {notNormal = true;}
            if ((allele0Value == 1) ^ (allele1Value == 1)) {isCodominantHeterozygous = true;}
            if ((allele0Value == 1) && (allele1Value == 1)) {isHomozygous = true;}

            if (notNormal) {
                switch (genes.getLocusType(locusName)) {
                    case "dominant":
                    case "incompleteDominant":
                        if (isCodominantHeterozygous) {
                            allMorphs.add(locusName);
                        } else if (isHomozygous) {
                            locusName = locusName + "-super";
                            allMorphs.add(locusName);
                        }
                        break;
                    case "recessive":
                        if (isCodominantHeterozygous) {
                            locusName = locusName + "-het";
                            allMorphs.add(locusName);
                        } else if (isHomozygous) {
                            allMorphs.add(locusName);
                        }
                        break;
                }
            }
        }

        Collections.sort(allMorphs);
        for (int i = 0; i < allMorphs.size(); i++) {
            String morph = allMorphs.get(i);
            if (i == 0) {
                genotype = morph;
            } else {
                genotype += "_" + morph;
            }
        }
        return genotype;
    }

    //Returns a LocusMap created from a given genotype
    public static LocusMap createGenesFromGenotype(String genotype) {
        LocusMap createdGenes = new LocusMap();
        ArrayList<String> morphList = new ArrayList<>(Arrays.asList(genotype.split("_")));
        Collections.sort(morphList);
        for (int i = 0; i < morphList.size(); i++) {
            Boolean isHomozygous = false;
            String morph = morphList.get(i);
            if (morph.equals("normal")) {
                break;
            }
            if (morph != null) {
                if (morph.endsWith("-het")) { //Checks if it's het recessive
                    morph = morph.substring(0, (morph.length() - 4));
                } else if (morph.endsWith("-super")) { //Checks if it's homo dominant/incomplete dominant
                    morph = morph.substring(0, (morph.length() - 6));
                    isHomozygous = true;
                } else if (LocusMap.checkType(morph, "recessive")) { //Checks if it's homo recessive
                    isHomozygous = true;
                }

                createdGenes.getGenes().get(morph).setAllele0(1); //Adds the morph to the new genes
                if (isHomozygous) {
                    createdGenes.getGenes().get(morph).setAllele1(1); //Adds it again if homo
                }
            }
        }
        return createdGenes;
    }
}
