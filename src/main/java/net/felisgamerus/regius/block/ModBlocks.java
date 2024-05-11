package net.felisgamerus.regius.block;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.item.ModItems;
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

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Regius.MOD_ID);

    public static final RegistryObject<Block> SPHAGNUM_MOSS = registerBlock("sphagnum_moss",
            () -> new SphagnumPlant(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.WET_GRASS).strength(0.1F).noOcclusion().noCollission()));
    public static final RegistryObject<Block> DRIED_SPHAGNUM_MOSS = registerBlock("dried_sphagnum_moss",
            () -> new DriedSphagnumPlant(SPHAGNUM_MOSS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS).strength(0.1F).noOcclusion().noCollission()));

    public static final RegistryObject<Block> SPHAGNUM_MOSS_BLOCK = registerBlock("sphagnum_moss_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).sound(SoundType.WET_GRASS)));
    //Gonna need a separate ConcretePowderBlock-like class for each dried moss later
    public static final RegistryObject<Block> DRIED_SPHAGNUM_MOSS_BLOCK = registerBlock("dried_sphagnum_moss_block",
            () -> new DriedSphagnumMossBlock(SPHAGNUM_MOSS_BLOCK, BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
