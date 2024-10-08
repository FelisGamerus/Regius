package net.felisgamerus.regius;

import com.mojang.logging.LogUtils;
import net.felisgamerus.regius.block.ModBlocks;
import net.felisgamerus.regius.entity.ModEntities;
import net.felisgamerus.regius.entity.client.BallPythonRenderer;
import net.felisgamerus.regius.item.ModCreativeModeTab;
import net.felisgamerus.regius.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(Regius.MOD_ID)
public class Regius {
    public static final String MOD_ID = "regius";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Regius() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTab.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPHAGNUM_MOSS.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_SPHAGNUM_MOSS.get(), RenderType.cutoutMipped());
        registerCompostables();
    }

    public static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(ModItems.SPHAGNUM_MOSS.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.DRIED_SPHAGNUM_MOSS.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.SPHAGNUM_MOSS_BLOCK.get(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(ModItems.DRIED_SPHAGNUM_MOSS_BLOCK.get(), 0.85F);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.BALL_PYTHON.get(), BallPythonRenderer::new);
        }
    }
}
