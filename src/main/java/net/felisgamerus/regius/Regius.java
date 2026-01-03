package net.felisgamerus.regius;

import net.felisgamerus.regius.block.RegiusBlocks;
import net.felisgamerus.regius.entity.RegiusEntities;
import net.felisgamerus.regius.entity.client.BallPythonRenderer;
import net.felisgamerus.regius.entity.custom.ai.RegiusSensors;
import net.felisgamerus.regius.item.RegiusCreativeModeTabs;
import net.felisgamerus.regius.item.RegiusItems;
import net.felisgamerus.regius.util.RegiusItemProperties;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.ComposterBlock;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Regius.MOD_ID)
public class Regius {
    public static final String MOD_ID = "regius";
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Regius(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        RegiusCreativeModeTabs.register(modEventBus);
        RegiusItems.register(modEventBus);
        RegiusBlocks.register(modEventBus);
        RegiusEntities.register(modEventBus);

        RegiusSensors.init();

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(RegiusBlocks.SPHAGNUM_MOSS.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(RegiusBlocks.DRIED_SPHAGNUM_MOSS.get(), RenderType.cutoutMipped());
        registerCompostables();
    }

    public static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(RegiusItems.SPHAGNUM_MOSS.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(RegiusItems.DRIED_SPHAGNUM_MOSS.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(RegiusItems.SPHAGNUM_MOSS_BLOCK.get(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(RegiusItems.DRIED_SPHAGNUM_MOSS_BLOCK.get(), 0.85F);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            //event.accept(ModItems.SPHAGNUM_MOSS);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            RegiusItemProperties.addCustomItemProperties();
            EntityRenderers.register(RegiusEntities.BALL_PYTHON.get(), BallPythonRenderer::new);
        }
    }
}
