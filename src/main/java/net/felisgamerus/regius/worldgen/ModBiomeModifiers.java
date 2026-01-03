package net.felisgamerus.regius.worldgen;

import net.felisgamerus.regius.Config;
import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> SPAWN_BALL_PYTHON = registerKey("spawn_ball_python");

    public static final ResourceKey<BiomeModifier> ADD_SPHAGNUM_MOSS = registerKey("add_sphagnum_moss");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(SPAWN_BALL_PYTHON, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SAVANNA)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BALL_PYTHON.get(), 20, 1, 1))));

        context.register(ADD_SPHAGNUM_MOSS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SWAMP)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SPHAGNUM_MOSS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, name));
    }
}
