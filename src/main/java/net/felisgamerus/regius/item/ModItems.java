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

    //Misc
    public static final DeferredItem<Item> BALL_PYTHON_SPAWN_EGG = ITEMS.register("ball_python_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.BALL_PYTHON, 0x5c3b23, 0xbd9a5b,
                new Item.Properties()));
    public static final DeferredItem<Item> BALL_PYTHON_BUCKET = ITEMS.register("ball_python_bucket",
            () -> new BallPythonBucketItem(ModEntities.BALL_PYTHON.get(),Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_AXOLOTL, (new Item.Properties()).stacksTo(1)));

    //Sphagnum plants
    public static final DeferredItem<Item> SPHAGNUM_MOSS = ITEMS.register("sphagnum_moss",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS.get(), new Item.Properties()));
    public static final DeferredItem<Item> DRIED_SPHAGNUM_MOSS = ITEMS.register("dried_sphagnum_moss",
            () -> new BlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS.get(), new Item.Properties()));

    //Sphagnum blocks
    public static final DeferredItem<Item> SPHAGNUM_MOSS_BLOCK = ITEMS.register("sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> DRIED_SPHAGNUM_MOSS_BLOCK = ITEMS.register("dried_sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));

    //Hollow logs
    public static final DeferredItem<Item> HOLLOW_STRIPPED_OAK_LOG = ITEMS.register("hollow_stripped_oak_log",
            () -> new BlockItem(ModBlocks.HOLLOW_STRIPPED_OAK_LOG.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOLLOW_OAK_LOG = ITEMS.register("hollow_oak_log",
            () -> new BlockItem(ModBlocks.HOLLOW_OAK_LOG.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
