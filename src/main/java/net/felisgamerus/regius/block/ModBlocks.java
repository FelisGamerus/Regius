package net.felisgamerus.regius.block;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.custom.DriedSphagnumMossBlock;
import net.felisgamerus.regius.block.custom.DriedSphagnumPlant;
import net.felisgamerus.regius.block.custom.FlammableHollowLog;
import net.felisgamerus.regius.block.custom.SphagnumPlant;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Regius.MOD_ID);

    //Sphagnum plants
    public static final RegistryObject<Block> SPHAGNUM_MOSS = BLOCKS.register("sphagnum_moss",
            () -> new SphagnumPlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WET_GRASS).strength(0.1F).noOcclusion().noCollission()));
    public static final RegistryObject<Block> DRIED_SPHAGNUM_MOSS = BLOCKS.register("dried_sphagnum_moss",
            () -> new DriedSphagnumPlant(SPHAGNUM_MOSS, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.GRASS).strength(0.1F).noOcclusion().noCollission()));

    //Sphagnum blocks
    public static final RegistryObject<Block> SPHAGNUM_MOSS_BLOCK = BLOCKS.register("sphagnum_moss_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(MapColor.PLANT).sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> DRIED_SPHAGNUM_MOSS_BLOCK = BLOCKS.register("dried_sphagnum_moss_block",
            () -> new DriedSphagnumMossBlock(SPHAGNUM_MOSS_BLOCK, BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(MapColor.WOOD).sound(SoundType.GRASS)));

    //Hollow logs
    public static final RegistryObject<Block> HOLLOW_STRIPPED_OAK_LOG = BLOCKS.register("hollow_stripped_oak_log",
            () -> new FlammableHollowLog(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> HOLLOW_OAK_LOG = BLOCKS.register("hollow_oak_log",
            () -> new FlammableHollowLog(BlockBehaviour.Properties.copy(Blocks.OAK_LOG), false, HOLLOW_STRIPPED_OAK_LOG));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
