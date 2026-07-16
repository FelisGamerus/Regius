package net.felisgamerus.regius.entity.custom.goals;

import net.felisgamerus.regius.block.RegiusBlocks;
import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class RegiusSeekShelterGoal extends MoveToBlockGoal {
    private int interval = reducedTickDelay(100);
    BallPythonEntity snake;

    public RegiusSeekShelterGoal(BallPythonEntity snake, double speedModifier, int searchRange) {
        super(snake, speedModifier, searchRange);
        this.snake = snake;
    }

    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 100 == 0;
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        BlockState blockstate = levelReader.getBlockState(blockPos);
        if(blockstate.is(RegiusBlocks.SPHAGNUM_MOSS)) {
            System.out.println("### ball python " + snake.getUUID() + " found shelter at " + blockPos.getX() + " " +  blockPos.getY() + " " + blockPos.getZ());
        }
        return blockstate.is(RegiusBlocks.SPHAGNUM_MOSS);
    }

    //Checks if snake is already in shelter
    private boolean alreadyHasShelter() {
        BlockPos blockpos = BlockPos.containing(snake.getX(), snake.getBoundingBox().maxY, snake.getZ());
        Predicate<BlockState> isSphagnumPlant = (state) -> state.is(RegiusBlocks.SPHAGNUM_MOSS);
        return snake.level().isStateAtPosition(blockpos, isSphagnumPlant) && snake.getWalkTargetValue(blockpos) >= 0.0F;
    }

    @Override
    public boolean canUse() {
        if (interval > 0) {
            interval--;
            return false;
        } else {
            return !snake.isSleeping() && !alreadyHasShelter() && this.mob.getTarget() == null;
        }
    }

    @Override
    public void start() {
        System.out.println("### Ball python " + snake.getUUID() + " is searching for shelter");
        snake.clearStates();
        super.start();
    }
}
