package net.felisgamerus.regius.entity.custom.ai;

import net.felisgamerus.regius.util.ModTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class NearbyPreySensor<E extends LivingEntity> extends EntityFilteringSensor<LivingEntity, E> {
    @Override
    public MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of(getMemory(), MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return ModSensors.NEARBY_PREY.get();
    }

    //Checks if the target is a valid prey mob
    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, entity) -> {
            if (!target.getType().is(ModTags.Entities.BALL_PYTHON_PREY)) {
                return false;
            }

            return Sensor.isEntityAttackable(entity, target);
        };
    }

    @Nullable
    @Override
    protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
        return matcher.findClosest(target -> predicate().test(target, entity)).orElse(null);
    }
}
