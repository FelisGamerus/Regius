package net.felisgamerus.regius;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Regius.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue SPAWN_WEIGHT = BUILDER
            .comment("How often ball pythons spawn. Higher weight = more pythons and less other mobs.")
            .defineInRange("spawnWeight", 20, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue MORPH_CHANCE_ON_SPAWN = BUILDER
            .comment("1 in n chance for a ball python to have a morph when it spawns.")
            .defineInRange("morphChanceOnSpawn", 10, 1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int spawnWeight;
    public static int morphChanceOnSpawn;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        spawnWeight = SPAWN_WEIGHT.get();
        morphChanceOnSpawn = MORPH_CHANCE_ON_SPAWN.get();
    }
}
