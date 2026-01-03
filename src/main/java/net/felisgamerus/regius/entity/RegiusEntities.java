package net.felisgamerus.regius.entity;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegiusEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Regius.MOD_ID);

    //Change CREATURE to AMBIENT? Tropical-fish like spawning for finding new morphs
    public static final Supplier<EntityType<BallPythonEntity>> BALL_PYTHON =
            ENTITY_TYPES.register("ball_python", () -> EntityType.Builder.of(BallPythonEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 0.25f).build("ball_python"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
