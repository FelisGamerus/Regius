package net.felisgamerus.regius.entity.custom;

import net.felisgamerus.regius.entity.ModEntities;
import net.felisgamerus.regius.entity.custom.genetics.LocusMap;
import net.felisgamerus.regius.item.ModItems;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.function.Predicate;

public class BallPythonEntity extends Animal implements GeoEntity, DryBucketable {

    public final BallPythonEntityPart[] ballPythonParts;
    public final int MULTIPART_COUNT = 2;
    public int ringBufferIndex = -1;
    public final double[][] ringBuffer = new double[64][3];

    LocusMap ballPythonGenes = new LocusMap();
    ArrayList<String> MORPH_REFERENCE = ballPythonGenes.getLociArray();

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
    protected static final RawAnimation TONGUE = RawAnimation.begin().thenPlay("animation.ballpython.tongue");

    @Override
    public float getAgeScale() {
        return 1.0f; //Because babies being half-scale messes the hitboxes up
    }

    public BallPythonEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1.0F, 1.0F, false);
        this.ballPythonParts = new BallPythonEntityPart[this.MULTIPART_COUNT];
        for (int i = 0; i < this.MULTIPART_COUNT; i++) {
            this.ballPythonParts[i] = new BallPythonEntityPart(this, this.getBbWidth(), this.getBbHeight());
        }
    }

    //SPAWNING
    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {
        //1 in CHANCE_FOR_NOT_NORMAL chance for a spawned ball python to have a special morph
        //TODO: Make CHANCE_FOR_NOT_NORMAL configurable
        final int CHANCE_FOR_NOT_NORMAL = 10;
        Random notNormalSeed = new Random();
        if (notNormalSeed.nextInt(CHANCE_FOR_NOT_NORMAL) == 0) {
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
    //Thanks to Wyrmroost for helping me figure out string-based variants
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(BallPythonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> GENOTYPE = SynchedEntityData.defineId(BallPythonEntity.class, EntityDataSerializers.STRING);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
        builder.define(GENOTYPE, "normal");
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

    //ANIMATIONS - Many thanks to Naturalist for the source code
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
                .add(Attributes.MOVEMENT_SPEED, 0.151D)
                .add(Attributes.FOLLOW_RANGE, 5f)
                .add(Attributes.ATTACK_DAMAGE, 4.0D) //Change attack-related attributes once constriction is implemented
                .add(Attributes.ATTACK_KNOCKBACK, 0.0D)
                .add(Attributes.ATTACK_SPEED, 4.0D)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    //GOALS
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreedGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0D, 10));

        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Rabbit.class, 10, true, true, (Predicate<LivingEntity>)null)); //Make these not hardcoded later
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Chicken.class, 10, true, true, (Predicate<LivingEntity>)null));
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
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean pFromBucket) {
        this.entityData.set(FROM_BUCKET, pFromBucket);
    }

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
        return new ItemStack(ModItems.BALL_PYTHON_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_AXOLOTL;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return DryBucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    //MULTIPARTS - mostly pulled from Untamed Wilds

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.neoforged.neoforge.entity.PartEntity<?>[] getParts() {
        return this.ballPythonParts;
    }

    public boolean attackEntityPartFrom(BallPythonEntityPart ballPythonPart, DamageSource source, float amount) {
        //System.out.println("Parent hurt from child by " + source.toString() + " for " + amount + " at " + System.currentTimeMillis());
        return this.hurt(source, amount);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        //System.out.println("Ball python hurt by " + pSource.toString() + " for " + pAmount + " at " + System.currentTimeMillis());
        return super.hurt(pSource, pAmount);
    }

    private void setPartPosition(BallPythonEntityPart part, double offsetX, double offsetY, double offsetZ) {
        part.setPos(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);
    }

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
        return pStack.is(Items.RABBIT);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        BallPythonEntity babySnake = ModEntities.BALL_PYTHON.get().create(level);
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

    //Getting both genes from parent0??
    public LocusMap getBabyGenes (BallPythonEntity parent0, BallPythonEntity parent1) {
        //Gets the genes of the baby ball python
        LocusMap babyGenes = new LocusMap();
        LocusMap parent0Genes = createGenesFromGenotype(parent0.getGenotype());
        LocusMap parent1Genes = createGenesFromGenotype(parent1.getGenotype());

        for (int i = 0; i < MORPH_REFERENCE.size(); i++) {
            String locusName = MORPH_REFERENCE.get(i);
            if (this.random.nextBoolean()) {
                babyGenes.genes.get(locusName).setAllele0(parent0Genes.genes.get(locusName).getAllele0());
            } else {
                babyGenes.genes.get(locusName).setAllele0(parent0Genes.genes.get(locusName).getAllele1());
            }
        }

        for (int i = 0; i < MORPH_REFERENCE.size(); i++) {
            String locusName = MORPH_REFERENCE.get(i);
            if (this.random.nextBoolean()) {
                babyGenes.genes.get(locusName).setAllele1(parent1Genes.genes.get(locusName).getAllele0());
            } else {
                babyGenes.genes.get(locusName).setAllele1(parent1Genes.genes.get(locusName).getAllele1());
            }
        }
        return babyGenes;
    }

    //GENETICS
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

    public void setGenes (LocusMap givenLocusMap) {
        this.ballPythonGenes = givenLocusMap;
    }

    //Converts a ball python's genotype to its visible phenotype
    public String convertGenotypeToPhenotype (String genotype) {
        String phenotype = "normal";
        ArrayList<String> traitList = new ArrayList<>(Arrays.asList(genotype.split("_")));
        for (int i = 0; i < traitList.size(); i++) {
            String trait = traitList.get(i);
            if (trait.endsWith(".het")) { //Checks if the trait is het recessive. True = skip
                trait = null;
            } else if (trait.endsWith(".super")) { //Checks if the trait is homo dominant. True = no longer super
                String locus = trait.substring(0, (trait.length() - 6));
                if (this.ballPythonGenes.getLocusType(locus).equals("dominant")) {
                    trait = locus;
                }
            }
            if (trait != null) {
                if (phenotype.equals("normal")) {
                    phenotype = trait;
                } else {
                    phenotype += "_" + trait;
                }
            }
        } return phenotype;
    }

    public String getGenotypeString(LocusMap genes) {
        //aka the advanced (adv) reader
        //Returns a string of every trait of a Snake, visible or not (So yes het albinos). This one's mainly for debug purposes
        ArrayList<String> allTraits = new ArrayList<>();
        String genotype = "normal";

        for (int i = 0; i < MORPH_REFERENCE.size(); i++) {
            String locusName = MORPH_REFERENCE.get(i);
            int allele0Value = genes.getAllele0(locusName);
            int allele1Value = genes.getAllele1(locusName);
            boolean notNormal = false;
            //boolean dominantGeneExpressed = false;
            boolean isCodominantHeterozygous = false;
            boolean isHomozygous = false;

            if ((allele0Value != allele1Value) || (allele0Value != 0)) {notNormal = true;}
            //if ((allele0Value == 1) || (allele1Value == 1)) {dominantGeneExpressed = true;}
            if ((allele0Value == 1) ^ (allele1Value == 1)) {isCodominantHeterozygous = true;}
            if ((allele0Value == 1) && (allele1Value == 1)) {isHomozygous = true;}

            if (notNormal) {
                switch (genes.getLocusType(locusName)) {
                    case "dominant":
                    case "codominant":
                        if (isCodominantHeterozygous) {
                            allTraits.add(locusName);
                        } else if (isHomozygous) {
                            locusName = locusName + ".super";
                            allTraits.add(locusName);
                        }
                        break;
                    case "recessive":
                        if (isCodominantHeterozygous) {
                            locusName = locusName + ".het";
                            allTraits.add(locusName);
                        } else if (isHomozygous) {
                            allTraits.add(locusName);
                        }
                        break;
                }
            }
        }

        Collections.sort(allTraits);
        for (int i = 0; i < allTraits.size(); i++) {
            String trait = allTraits.get(i);
            if (i == 0) {
                genotype = trait;
            } else {
                genotype += "_" + trait;
            }
        }
        return genotype;
    }

    public LocusMap createGenesFromGenotype(String genotype) {
        //Returns a LocusMap created from a given genotype
        LocusMap createdGenes = new LocusMap();
        ArrayList<String> traitList = new ArrayList<>(Arrays.asList(genotype.split("_")));
        Collections.sort(traitList);
        for (int i = 0; i < traitList.size(); i++) {
            Boolean isHomozygous = false;
            String trait = traitList.get(i);
            if (trait.equals("normal")) {
                break;
            }
            if (trait != null) {
                if (trait.endsWith(".het")) { //Checks if it's het recessive
                    trait = trait.substring(0, (trait.length() - 4));
                } else if (trait.endsWith(".super")) { //Checks if it's homo dominant/co-dominant
                    trait = trait.substring(0, (trait.length() - 6));
                    isHomozygous = true;
                } else if (this.ballPythonGenes.getLocusType(trait).equals("recessive")) { //Checks if it's homo recessive
                    isHomozygous = true;
                }

                createdGenes.genes.get(trait).setAllele0(1); //Adds the trait to the new genes
                if (isHomozygous) {
                    createdGenes.genes.get(trait).setAllele1(1); //Adds it again if homo
                }
            }
        }
        return createdGenes;
    }
}
