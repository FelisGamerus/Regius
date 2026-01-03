package net.felisgamerus.regius.worldgen;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.RegiusBlocks;
import net.felisgamerus.regius.worldgen.custom.ModFeatureUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class RegiusConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> SPHAGNUM_MOSS_KEY = registerKey("sphagnum_moss");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, SPHAGNUM_MOSS_KEY, Feature.RANDOM_PATCH,
                ModFeatureUtils.sphagnumPatchConfigurationPart1(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(RegiusBlocks.SPHAGNUM_MOSS.get().defaultBlockState())
                        ), List.of(Blocks.GRASS_BLOCK)));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
