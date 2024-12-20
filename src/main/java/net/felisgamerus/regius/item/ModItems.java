package net.felisgamerus.regius.item;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.ModBlocks;
import net.felisgamerus.regius.entity.ModEntities;
import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.felisgamerus.regius.item.custom.FuelBlockItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.common.returnsreceiver.qual.This;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Regius.MOD_ID);

    //Misc
    public static final RegistryObject<Item> BALL_PYTHON_SPAWN_EGG = ITEMS.register("ball_python_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BALL_PYTHON, 0x5c3b23, 0xbd9a5b, new Item.Properties()));
    public static final RegistryObject<Item> BALL_PYTHON_BUCKET = ITEMS.register("ball_python_bucket",
            () -> new BallPythonBucketItem(() -> ModEntities.BALL_PYTHON.get(),() -> Fluids.EMPTY,() -> SoundEvents.BUCKET_EMPTY_AXOLOTL, (new Item.Properties()).stacksTo(1)));

    /*public static final RegistryObject<Item> BALL_PYTHON_BUCKET = ITEMS.register("ball_python_bucket",
            () -> new MobBucketItem(ModEntities.BALL_PYTHON.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, (new Item.Properties()).stacksTo(1)));
    */ //This is broken and idk why

    //Sphagnum plants
    public static final RegistryObject<Item> SPHAGNUM_MOSS = ITEMS.register("sphagnum_moss",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DRIED_SPHAGNUM_MOSS = ITEMS.register("dried_sphagnum_moss",
            () -> new FuelBlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS.get(), new Item.Properties(), 100));

    //Sphagnum blocks
    public static final RegistryObject<Item> SPHAGNUM_MOSS_BLOCK = ITEMS.register("sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DRIED_SPHAGNUM_MOSS_BLOCK = ITEMS.register("dried_sphagnum_moss_block",
            () -> new FuelBlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties(), 1000));

    //Hollow logs
    public static final RegistryObject<Item> HOLLOW_STRIPPED_OAK_LOG = ITEMS.register("hollow_stripped_oak_log",
            () -> new FuelBlockItem(ModBlocks.HOLLOW_STRIPPED_OAK_LOG.get(), new Item.Properties(), 300));
    public static final RegistryObject<Item> HOLLOW_OAK_LOG = ITEMS.register("hollow_oak_log",
            () -> new FuelBlockItem(ModBlocks.HOLLOW_OAK_LOG.get(), new Item.Properties(), 300));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
