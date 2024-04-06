package net.felisgamerus.regius.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

//This might be needed depending on what I do with SphagnumPlant
public class DriedSphagnumPlant extends Block {
    public DriedSphagnumPlant(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 5, 16);

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    //Borrowed from the AbstractGlassBlock class
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}