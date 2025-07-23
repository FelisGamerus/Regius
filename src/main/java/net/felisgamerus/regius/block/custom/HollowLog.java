package net.felisgamerus.regius.block.custom;

import net.felisgamerus.regius.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class HollowLog extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    private Block strippedLog; // = ModBlocks.STRIPPED_OAK_HOLLOW_LOG.get().defaultBlockState(); <- When this is uncommented it crashes for some reason
    private boolean isStripped = true;

    public HollowLog(Properties properties, Boolean pIsStripped, Block pStrippedLog) {
        super(properties);
        this.strippedLog = pStrippedLog;
        this.isStripped = pIsStripped;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(AXIS, Direction.Axis.Y));
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape OUTSIDE = Shapes.block();
    private static final VoxelShape X_INSIDE = box(0, 2, 2, 16, 14, 14);
    private static final VoxelShape Z_INSIDE = box(2, 2, 0, 14, 14, 16);
    private static final VoxelShape Y_INSIDE = box(2, 0, 2, 14, 16, 14);
    private static final VoxelShape X_AXIS = Shapes.join(OUTSIDE, X_INSIDE, BooleanOp.ONLY_FIRST);
    private static final VoxelShape Z_AXIS = Shapes.join(OUTSIDE, Z_INSIDE, BooleanOp.ONLY_FIRST);
    private static final VoxelShape Y_AXIS = Shapes.join(OUTSIDE, Y_INSIDE, BooleanOp.ONLY_FIRST);

    public VoxelShape getShape(BlockState p_51470_, BlockGetter p_51471_, BlockPos p_51472_, CollisionContext p_51473_) {
        switch ((Direction.Axis) p_51470_.getValue(AXIS)) {
            case X:
            default:
                return X_AXIS;
            case Z:
                return Z_AXIS;
            case Y:
                return Y_AXIS;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_51454_) {
        FluidState fluidstate = p_51454_.getLevel().getFluidState(p_51454_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(p_51454_).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }

    public BlockState updateShape(BlockState p_51461_, Direction p_51462_, BlockState p_51463_, LevelAccessor p_51464_, BlockPos p_51465_, BlockPos p_51466_) {
        if (p_51461_.getValue(WATERLOGGED)) {
            p_51464_.scheduleTick(p_51465_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51464_));
        }

        return super.updateShape(p_51461_, p_51462_, p_51463_, p_51464_, p_51465_, p_51466_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51468_) {
        p_51468_.add(WATERLOGGED).add(AXIS);
    }

    public FluidState getFluidState(BlockState p_51475_) {
        return p_51475_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51475_);
    }

    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context,
                                                     ItemAbility itemAbility, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            if (!isStripped) {
                return strippedLog.defaultBlockState()
                        .setValue(AXIS, state.getValue(AXIS))
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            }
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}

