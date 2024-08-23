package net.felisgamerus.regius.event;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.entity.ModEntities;
import net.felisgamerus.regius.entity.custom.BallPythonEntitiy;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Regius.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BALL_PYTHON.get(), BallPythonEntitiy.createAttributes().build());
    }
}
