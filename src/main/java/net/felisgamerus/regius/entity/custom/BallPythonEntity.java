package net.felisgamerus.regius.entity.custom;

import net.felisgamerus.regius.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Predicate;

public class BallPythonEntity extends Animal implements GeoEntity {

    public final BallPythonEntityPart[] ballPythonParts;
    public final int MULTIPART_COUNT = 2;
    public int ringBufferIndex = -1;
    public final double[][] ringBuffer = new double[64][3];

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.ballpython.idle");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.ballpython.walk");
    protected static final RawAnimation TONGUE = RawAnimation.begin().thenPlay("animation.ballpython.tongue");

    public BallPythonEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1.0F, 1.0F, false);
        this.ballPythonParts = new BallPythonEntityPart[this.MULTIPART_COUNT];
        for (int i = 0; i < this.MULTIPART_COUNT; i++) {
            this.ballPythonParts[i] = new BallPythonEntityPart(this, this.getBbWidth(), this.getBbHeight());
        }
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
        //Temp until genetics
        return ModEntities.BALL_PYTHON.get().create(level);
        /*BallPythonEntity babySnake = ModEntities.BALL_PYTHON.get().create(pLevel);
        if ((babySnake != null) && (pOtherParent instanceof BallPythonEntity)) {
            if (pOtherParent instanceof BallPythonEntity) {
                //TODO: Figure out why parent1 isn't passing its genes down properly
                BallPythonEntity parent1 = (BallPythonEntity) pOtherParent;
                LocusMap babyGenes = getBabyGenes(this, parent1);
                babySnake.setGenes(babyGenes);
                babySnake.setGenotype(getGenotypeString(babyGenes));
            }
        }
        return babySnake;*/
    }
}
