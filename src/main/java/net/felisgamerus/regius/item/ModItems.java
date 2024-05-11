package net.felisgamerus.regius.item;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.DriedSphagnumMossBlock;
import net.felisgamerus.regius.block.DriedSphagnumPlant;
import net.felisgamerus.regius.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Regius.MOD_ID);

    //Sphagnum plants
    public static final RegistryObject<Item> SPHAGNUM_MOSS = ITEMS.register("sphagnum_moss",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DRIED_SPHAGNUM_MOSS = ITEMS.register("dried_sphagnum_moss",
            () -> new BlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS.get(), new Item.Properties()));

    //Sphagnum blocks
    public static final RegistryObject<Item> SPHAGNUM_MOSS_BLOCK = ITEMS.register("sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DRIED_SPHAGNUM_MOSS_BLOCK = ITEMS.register("dried_sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
