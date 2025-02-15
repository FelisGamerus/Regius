package net.felisgamerus.regius.item;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.ModBlocks;
import net.felisgamerus.regius.item.custom.FuelBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Regius.MOD_ID);

    //Sphagnum plants
    public static final DeferredItem<Item> SPHAGNUM_MOSS = ITEMS.register("sphagnum_moss",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS.get(), new Item.Properties()));
    public static final DeferredItem<Item> DRIED_SPHAGNUM_MOSS = ITEMS.register("dried_sphagnum_moss",
            () -> new FuelBlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS.get(), new Item.Properties(), 100));

    //Sphagnum blocks
    public static final DeferredItem<Item> SPHAGNUM_MOSS_BLOCK = ITEMS.register("sphagnum_moss_block",
            () -> new BlockItem(ModBlocks.SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> DRIED_SPHAGNUM_MOSS_BLOCK = ITEMS.register("dried_sphagnum_moss_block",
            () -> new FuelBlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get(), new Item.Properties(), 300));

    //Hollow logs
    public static final DeferredItem<Item> HOLLOW_STRIPPED_OAK_LOG = ITEMS.register("hollow_stripped_oak_log",
            () -> new FuelBlockItem(ModBlocks.HOLLOW_STRIPPED_OAK_LOG.get(), new Item.Properties(), 300));
    public static final DeferredItem<Item> HOLLOW_OAK_LOG = ITEMS.register("hollow_oak_log",
            () -> new FuelBlockItem(ModBlocks.HOLLOW_OAK_LOG.get(), new Item.Properties(), 300));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
