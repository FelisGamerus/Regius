package net.felisgamerus.regius.entity.custom.goals;

import net.felisgamerus.regius.block.RegiusBlocks;
import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

public class RegiusSleepGoal extends Goal { //Used to make the python curl up
    private BallPythonEntity snake;
    private static final int WAIT_TIME_BEFORE_SLEEP = reducedTickDelay(500);
    private int wakingTimer;
    private int sleepingTimer;

    public RegiusSleepGoal(BallPythonEntity snake) {
        this.snake = snake;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        wakingTimer = snake.getRandom().nextInt(WAIT_TIME_BEFORE_SLEEP);
    }

    @Override
    public void tick() {
        super.tick();
        sleepingTimer--;
    }

    @Override
    public boolean canUse() {
        if(Math.random() < 0.5) { //50% chance to fail
            return false;
        } else return snake.xxa == 0.0F && snake.yya == 0.0F && snake.zza == 0.0F ? this.canSleep() || snake.isSleeping() : false;
    }

    @Override
    public boolean canContinueToUse() {
        if(this.canSleep() && sleepingTimer > 0) {
            return true;
        } else return (Math.random() < 0.5); //50% chance to wake up
    }

    private boolean canSleep() {
        if(snake.isInWater() || !snake.getMainHandItem().isEmpty()) {
            return false;
        } else if(this.wakingTimer > 0) {
            this.wakingTimer--;
            return false;
        } else {
            return true;
        }
    }

    //Might use later idk
    //Checks if snake is in sphagnum moss
    /*private boolean hasShelter() {
        BlockPos blockpos = BlockPos.containing(snake.getX(), snake.getBoundingBox().maxY, snake.getZ());
        Predicate<BlockState> isSphagnumPlant = (state) -> state.is(RegiusBlocks.SPHAGNUM_MOSS);
        return snake.level().isStateAtPosition(blockpos, isSphagnumPlant) && snake.getWalkTargetValue(blockpos) >= 0.0F;
    }*/

    @Override
    public void stop() {
        this.wakingTimer = snake.getRandom().nextInt(WAIT_TIME_BEFORE_SLEEP);
        snake.clearStates();
    }

    @Override
    public void start() {
        this.sleepingTimer = snake.getRandom().nextInt(WAIT_TIME_BEFORE_SLEEP);
        snake.setSleeping(true);
        snake.getNavigation().stop();
        snake.getMoveControl().setWantedPosition(snake.getX(), snake.getY(), snake.getZ(), 0.0);
    }
}
