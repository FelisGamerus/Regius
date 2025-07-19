package net.felisgamerus.regius.event;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.entity.ModEntities;
import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Regius.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BALL_PYTHON.get(), BallPythonEntity.createAttributes().build());
    }
}
