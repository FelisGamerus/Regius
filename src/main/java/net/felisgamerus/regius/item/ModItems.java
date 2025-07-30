package net.felisgamerus.regius.item;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.ModBlocks;
import net.felisgamerus.regius.entity.ModEntities;
import net.felisgamerus.regius.item.custom.BallPythonBucketItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Regius.MOD_ID);

    //MISC
    public static final DeferredItem<Item> BALL_PYTHON_SPAWN_EGG = ITEMS.register("ball_python_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.BALL_PYTHON, 0x5c3b23, 0xbd9a5b,
                new Item.Properties()));
    public static final DeferredItem<Item> BALL_PYTHON_BUCKET = ITEMS.register("ball_python_bucket",
            () -> new BallPythonBucketItem(ModEntities.BALL_PYTHON.get(), Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_AXOLOTL, (new Item.Properties()).stacksTo(1)));

    //SPHAGNUM PLANTS
    public static final DeferredItem<Item> SPHAGNUM_MOSS = ITEMS.register("sphagnum_moss",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS.get(), new Item.Properties()));
    public static final DeferredItem<Item> DRIED_SPHAGNUM_MOSS = ITEMS.register("dried_sphagnum_moss",
            () -> new BlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS.get(), new Item.Properties()));

    //SPHAGNUM BLOCKS
    public static final DeferredItem<Item> SPHAGNUM_MOSS_BLOCK = ITEMS.register("sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> DRIED_SPHAGNUM_MOSS_BLOCK = ITEMS.register("dried_sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));

    //HOLLOW LOGS
    public static final DeferredItem<Item> HOLLOW_STRIPPED_OAK_LOG = ITEMS.register("hollow_stripped_oak_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_OAK_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_OAK_LOG = ITEMS.register("hollow_oak_log",
            () -> new BlockItem(ModBlocks.HOLLOW_OAK_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_SPRUCE_LOG = ITEMS.register("hollow_stripped_spruce_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_SPRUCE_LOG = ITEMS.register("hollow_spruce_log",
            () -> new BlockItem(ModBlocks.HOLLOW_SPRUCE_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_BIRCH_LOG = ITEMS.register("hollow_stripped_birch_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_BIRCH_LOG = ITEMS.register("hollow_birch_log",
            () -> new BlockItem(ModBlocks.HOLLOW_BIRCH_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_JUNGLE_LOG = ITEMS.register("hollow_stripped_jungle_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_JUNGLE_LOG = ITEMS.register("hollow_jungle_log",
            () -> new BlockItem(ModBlocks.HOLLOW_JUNGLE_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_ACACIA_LOG = ITEMS.register("hollow_stripped_acacia_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_ACACIA_LOG = ITEMS.register("hollow_acacia_log",
            () -> new BlockItem(ModBlocks.HOLLOW_ACACIA_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_DARK_OAK_LOG = ITEMS.register("hollow_stripped_dark_oak_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_DARK_OAK_LOG = ITEMS.register("hollow_dark_oak_log",
            () -> new BlockItem(ModBlocks.HOLLOW_DARK_OAK_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_MANGROVE_LOG = ITEMS.register("hollow_stripped_mangrove_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_MANGROVE_LOG = ITEMS.register("hollow_mangrove_log",
            () -> new BlockItem(ModBlocks.HOLLOW_MANGROVE_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_CHERRY_LOG = ITEMS.register("hollow_stripped_cherry_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_CHERRY_LOG = ITEMS.register("hollow_cherry_log",
            () -> new BlockItem(ModBlocks.HOLLOW_CHERRY_LOG.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_CRIMSON_STEM = ITEMS.register("hollow_stripped_crimson_stem",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_CRIMSON_STEM.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_CRIMSON_STEM = ITEMS.register("hollow_crimson_stem",
            () -> new BlockItem(ModBlocks.HOLLOW_CRIMSON_STEM.get(), new Item.Properties()));

    public static final DeferredItem<Item> HOLLOW_STRIPPED_WARPED_STEM = ITEMS.register("hollow_stripped_warped_stem",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_WARPED_STEM.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_WARPED_STEM = ITEMS.register("hollow_warped_stem",
            () -> new BlockItem(ModBlocks.HOLLOW_WARPED_STEM.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
