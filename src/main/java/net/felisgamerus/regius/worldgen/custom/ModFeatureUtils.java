package net.felisgamerus.regius.worldgen.custom;


import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.*;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModFeatureUtils extends FeatureUtils {
    //Why is everything hardcoded whyyyyyy ;~;

    private static BlockPredicate sphagnumPatchPredicate(List<Block> blocks) {
        BlockPredicate blockpredicate;
        if (!blocks.isEmpty()) {
            blockpredicate = BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), blocks));
        } else {
            blockpredicate = BlockPredicate.ONLY_IN_AIR_PREDICATE;
        }

        return blockpredicate;
    }

    public static RandomPatchConfiguration sphagnumPatchConfigurationFinal(int tries, Holder<PlacedFeature> feature) {
        return new RandomPatchConfiguration(tries, 3, 3, feature);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration sphagnumPatchConfigurationPart2(F feature, FC config, List<Block> blocks, int tries) {
        return sphagnumPatchConfigurationFinal(tries, PlacementUtils.filtered(feature, config, sphagnumPatchPredicate(blocks)));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration sphagnumPatchConfigurationPart1(F feature, FC config, List<Block> blocks) {
        return sphagnumPatchConfigurationPart2(feature, config, blocks, 96);
    }
}
