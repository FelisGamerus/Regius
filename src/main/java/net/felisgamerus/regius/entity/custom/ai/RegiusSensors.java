package net.felisgamerus.regius.entity.custom.ai;

import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.SBLConstants;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.function.Supplier;

public class RegiusSensors {
    public static void init() {}

    public static final Supplier<SensorType<NearbyPreySensor<?>>> NEARBY_PREY = register("nearby_prey", NearbyPreySensor::new);

    private static <T extends ExtendedSensor<?>> Supplier<SensorType<T>> register(String id, Supplier<T> sensor) {
        return SBLConstants.SBL_LOADER.registerSensorType(id, sensor);
    }
}
