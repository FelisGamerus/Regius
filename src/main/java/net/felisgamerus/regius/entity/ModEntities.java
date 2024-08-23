package net.felisgamerus.regius.entity;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.entity.custom.BallPythonEntitiy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Regius.MOD_ID);

    public static final RegistryObject<EntityType<BallPythonEntitiy>> BALL_PYTHON =
            ENTITY_TYPES.register("ball_python", () -> EntityType.Builder.of(BallPythonEntitiy::new, MobCategory.CREATURE)
                .sized(0.5f, 0.25f).build("ball_python"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
