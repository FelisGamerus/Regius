package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.RegiusBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RegiusBlockTagProvider extends BlockTagsProvider {
    public RegiusBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Regius.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(RegiusBlocks.HOLLOW_OAK_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_OAK_LOG.get())
                .add(RegiusBlocks.HOLLOW_SPRUCE_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get())
                .add(RegiusBlocks.HOLLOW_BIRCH_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get())
                .add(RegiusBlocks.HOLLOW_JUNGLE_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get())
                .add(RegiusBlocks.HOLLOW_ACACIA_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get())
                .add(RegiusBlocks.HOLLOW_DARK_OAK_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get())
                .add(RegiusBlocks.HOLLOW_MANGROVE_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get())
                .add(RegiusBlocks.HOLLOW_CHERRY_LOG.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get())
                .add(RegiusBlocks.HOLLOW_CRIMSON_STEM.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_CRIMSON_STEM.get())
                .add(RegiusBlocks.HOLLOW_WARPED_STEM.get())
                .add(RegiusBlocks.HOLLOW_STRIPPED_WARPED_STEM.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(RegiusBlocks.SPHAGNUM_MOSS.get())
                .add(RegiusBlocks.DRIED_SPHAGNUM_MOSS.get())
                .add(RegiusBlocks.SPHAGNUM_MOSS_BLOCK.get())
                .add(RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
        
        tag(BlockTags.DIRT)
                .add(RegiusBlocks.SPHAGNUM_MOSS_BLOCK.get())
                .add(RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
    }
}
