package net.felisgamerus.regius.block;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.custom.DriedSphagnumMossBlock;
import net.felisgamerus.regius.block.custom.DriedSphagnumPlant;
import net.felisgamerus.regius.block.custom.FlammableHollowLog;
import net.felisgamerus.regius.block.custom.SphagnumPlant;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Regius.MOD_ID);

    //Sphagnum plants
    public static final DeferredBlock<Block> SPHAGNUM_MOSS = BLOCKS.register("sphagnum_moss",
            () -> new SphagnumPlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WET_GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY).noOcclusion().noCollission()));
    public static final DeferredBlock<Block> DRIED_SPHAGNUM_MOSS = BLOCKS.register("dried_sphagnum_moss",
            () -> new DriedSphagnumPlant(SPHAGNUM_MOSS.get(), BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY).noOcclusion().noCollission()));

    //Sphagnum blocks
    public static final DeferredBlock<Block> SPHAGNUM_MOSS_BLOCK = BLOCKS.register("sphagnum_moss_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WET_GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> DRIED_SPHAGNUM_MOSS_BLOCK = BLOCKS.register("dried_sphagnum_moss_block",
            () -> new DriedSphagnumMossBlock(SPHAGNUM_MOSS_BLOCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY)));

    //Hollow logs
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_OAK_LOG = BLOCKS.register("hollow_stripped_oak_log",
            () -> new FlammableHollowLog(BlockBehaviour.Properties.of()
                    .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));
    public static final DeferredBlock<Block> HOLLOW_OAK_LOG = BLOCKS.register("hollow_oak_log",
            () -> new FlammableHollowLog(BlockBehaviour.Properties.of()
                    .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava(),
                    false, HOLLOW_STRIPPED_OAK_LOG.get()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
