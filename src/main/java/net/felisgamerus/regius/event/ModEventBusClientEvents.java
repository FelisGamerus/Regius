package net.felisgamerus.regius.event;

import net.felisgamerus.regius.Regius;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Regius.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    /*@SubscribeEvent
    public static void RegisterLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.BALL_PYTHON_LAYER, BallPythonModel::createBodyLayer);
    }*/
}
