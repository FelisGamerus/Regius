package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.block.RegiusBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class RegiusBlockLootTableProvider extends BlockLootSubProvider {
    protected RegiusBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(RegiusBlocks.SPHAGNUM_MOSS.get());
        dropSelf(RegiusBlocks.DRIED_SPHAGNUM_MOSS.get());
        dropSelf(RegiusBlocks.SPHAGNUM_MOSS_BLOCK.get());
        dropSelf(RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
        dropSelf(RegiusBlocks.HOLLOW_OAK_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_OAK_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_SPRUCE_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_BIRCH_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_JUNGLE_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_ACACIA_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_DARK_OAK_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_MANGROVE_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_CHERRY_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get());
        dropSelf(RegiusBlocks.HOLLOW_CRIMSON_STEM.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_CRIMSON_STEM.get());
        dropSelf(RegiusBlocks.HOLLOW_WARPED_STEM.get());
        dropSelf(RegiusBlocks.HOLLOW_STRIPPED_WARPED_STEM.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return RegiusBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
