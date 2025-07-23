package net.felisgamerus.regius.block;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.custom.*;
import net.minecraft.client.resources.sounds.Sound;
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

    //SPHAGNUM PLANTS
    public static final DeferredBlock<Block> SPHAGNUM_MOSS = BLOCKS.register("sphagnum_moss",
            () -> new SphagnumPlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WET_GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY).noOcclusion().noCollission()));
    public static final DeferredBlock<Block> DRIED_SPHAGNUM_MOSS = BLOCKS.register("dried_sphagnum_moss",
            () -> new DriedSphagnumPlant(SPHAGNUM_MOSS.get(), BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY).noOcclusion().noCollission()));

    //SPHAGNUM BLOCKS
    public static final DeferredBlock<Block> SPHAGNUM_MOSS_BLOCK = BLOCKS.register("sphagnum_moss_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WET_GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> DRIED_SPHAGNUM_MOSS_BLOCK = BLOCKS.register("dried_sphagnum_moss_block",
            () -> new DriedSphagnumMossBlock(SPHAGNUM_MOSS_BLOCK.get(), BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.GRASS).strength(0.1F).pushReaction(PushReaction.DESTROY)));

    //HOLLOW LOGS
    //Stripped, flammable
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_OAK_LOG = BLOCKS.register("hollow_stripped_oak_log", () -> flammableHollowLog(MapColor.WOOD, MapColor.WOOD, SoundType.WOOD, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_SPRUCE_LOG = BLOCKS.register("hollow_stripped_spruce_log", () -> flammableHollowLog(MapColor.PODZOL, MapColor.PODZOL, SoundType.WOOD, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_BIRCH_LOG = BLOCKS.register("hollow_stripped_birch_log", () -> flammableHollowLog(MapColor.SAND, MapColor.SAND, SoundType.WOOD, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_JUNGLE_LOG = BLOCKS.register("hollow_stripped_jungle_log", () -> flammableHollowLog(MapColor.DIRT, MapColor.DIRT, SoundType.WOOD, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_ACACIA_LOG = BLOCKS.register("hollow_stripped_acacia_log", () -> flammableHollowLog(MapColor.COLOR_ORANGE, MapColor.COLOR_ORANGE, SoundType.WOOD, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_DARK_OAK_LOG = BLOCKS.register("hollow_stripped_dark_oak_log", () -> flammableHollowLog(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN, SoundType.WOOD, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_MANGROVE_LOG = BLOCKS.register("hollow_stripped_mangrove_log", () -> flammableHollowLog(MapColor.COLOR_RED, MapColor.COLOR_RED, SoundType.WOOD, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_CHERRY_LOG = BLOCKS.register("hollow_stripped_cherry_log", () -> flammableHollowLog(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_PINK, SoundType.CHERRY_WOOD, true, Blocks.AIR));

    //Flammable
    public static final DeferredBlock<Block> HOLLOW_OAK_LOG = BLOCKS.register("hollow_oak_log", () -> flammableHollowLog(MapColor.WOOD, MapColor.PODZOL, SoundType.WOOD, false, ModBlocks.HOLLOW_STRIPPED_OAK_LOG.get()));
    public static final DeferredBlock<Block> HOLLOW_SPRUCE_LOG = BLOCKS.register("hollow_spruce_log", () -> flammableHollowLog(MapColor.PODZOL, MapColor.COLOR_BROWN, SoundType.WOOD, false, ModBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get()));
    public static final DeferredBlock<Block> HOLLOW_BIRCH_LOG = BLOCKS.register("hollow_birch_log", () -> flammableHollowLog(MapColor.SAND, MapColor.QUARTZ, SoundType.WOOD, false, ModBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get()));
    public static final DeferredBlock<Block> HOLLOW_JUNGLE_LOG = BLOCKS.register("hollow_jungle_log", () -> flammableHollowLog(MapColor.DIRT, MapColor.PODZOL, SoundType.WOOD, false, ModBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get()));
    public static final DeferredBlock<Block> HOLLOW_ACACIA_LOG = BLOCKS.register("hollow_acacia_log", () -> flammableHollowLog(MapColor.COLOR_ORANGE, MapColor.STONE, SoundType.WOOD, false, ModBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get()));
    public static final DeferredBlock<Block> HOLLOW_DARK_OAK_LOG = BLOCKS.register("hollow_dark_oak_log", () -> flammableHollowLog(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN, SoundType.WOOD, false, ModBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get()));
    public static final DeferredBlock<Block> HOLLOW_MANGROVE_LOG = BLOCKS.register("hollow_mangrove_log", () -> flammableHollowLog(MapColor.COLOR_RED, MapColor.PODZOL, SoundType.WOOD, false, ModBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get()));
    public static final DeferredBlock<Block> HOLLOW_CHERRY_LOG = BLOCKS.register("hollow_cherry_log", () -> flammableHollowLog(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, SoundType.CHERRY_WOOD, false, ModBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get()));

    //Stripped, non-flammable
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_CRIMSON_STEM = BLOCKS.register("hollow_stripped_crimson_stem", () -> hollowLog(MapColor.CRIMSON_STEM, MapColor.CRIMSON_STEM, SoundType.STEM, true, Blocks.AIR));
    public static final DeferredBlock<Block> HOLLOW_STRIPPED_WARPED_STEM = BLOCKS.register("hollow_stripped_warped_stem", () -> hollowLog(MapColor.WARPED_STEM, MapColor.WARPED_STEM, SoundType.STEM, true, Blocks.AIR));

    //Non-flammable
    public static final DeferredBlock<Block> HOLLOW_CRIMSON_STEM = BLOCKS.register("hollow_crimson_stem", () -> hollowLog(MapColor.CRIMSON_STEM, MapColor.CRIMSON_STEM, SoundType.STEM, false, HOLLOW_STRIPPED_CRIMSON_STEM.get()));
    public static final DeferredBlock<Block> HOLLOW_WARPED_STEM = BLOCKS.register("hollow_warped_stem", () -> hollowLog(MapColor.WARPED_STEM, MapColor.WARPED_STEM, SoundType.STEM, false, HOLLOW_STRIPPED_WARPED_STEM.get()));

    //HELPER METHODS
    private static Block flammableHollowLog(MapColor topMapColor, MapColor sideMapColor, SoundType soundType, Boolean isStripped, Block strippedLog) {
        return new FlammableHollowLog(BlockBehaviour.Properties.of()
                .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava(),
                isStripped, strippedLog);
    }

    private static Block hollowLog(MapColor topMapColor, MapColor sideMapColor, SoundType soundType, Boolean isStripped, Block strippedLog) {
        return new HollowLog(BlockBehaviour.Properties.of()
                .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType),
                isStripped, strippedLog);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
