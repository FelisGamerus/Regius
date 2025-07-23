package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.SPHAGNUM_MOSS.get());
        dropSelf(ModBlocks.DRIED_SPHAGNUM_MOSS.get());
        dropSelf(ModBlocks.SPHAGNUM_MOSS_BLOCK.get());
        dropSelf(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
        dropSelf(ModBlocks.HOLLOW_OAK_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_OAK_LOG.get());
        dropSelf(ModBlocks.HOLLOW_SPRUCE_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get());
        dropSelf(ModBlocks.HOLLOW_BIRCH_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get());
        dropSelf(ModBlocks.HOLLOW_JUNGLE_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get());
        dropSelf(ModBlocks.HOLLOW_ACACIA_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get());
        dropSelf(ModBlocks.HOLLOW_DARK_OAK_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get());
        dropSelf(ModBlocks.HOLLOW_MANGROVE_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get());
        dropSelf(ModBlocks.HOLLOW_CHERRY_LOG.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get());
        dropSelf(ModBlocks.HOLLOW_CRIMSON_STEM.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_CRIMSON_STEM.get());
        dropSelf(ModBlocks.HOLLOW_WARPED_STEM.get());
        dropSelf(ModBlocks.HOLLOW_STRIPPED_WARPED_STEM.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
