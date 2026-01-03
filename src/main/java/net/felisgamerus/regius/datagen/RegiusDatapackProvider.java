package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.worldgen.RegiusBiomeModifiers;
import net.felisgamerus.regius.worldgen.RegiusConfiguredFeatures;
import net.felisgamerus.regius.worldgen.RegiusPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegiusDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, RegiusConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, RegiusPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, RegiusBiomeModifiers::bootstrap)
            ;

    public RegiusDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Regius.MOD_ID));
    }
}