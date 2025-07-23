package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Regius.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.HOLLOW_OAK_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_OAK_LOG.get())
                .add(ModBlocks.HOLLOW_SPRUCE_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get())
                .add(ModBlocks.HOLLOW_BIRCH_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get())
                .add(ModBlocks.HOLLOW_JUNGLE_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get())
                .add(ModBlocks.HOLLOW_ACACIA_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get())
                .add(ModBlocks.HOLLOW_DARK_OAK_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get())
                .add(ModBlocks.HOLLOW_MANGROVE_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get())
                .add(ModBlocks.HOLLOW_CHERRY_LOG.get())
                .add(ModBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get())
                .add(ModBlocks.HOLLOW_CRIMSON_STEM.get())
                .add(ModBlocks.HOLLOW_STRIPPED_CRIMSON_STEM.get())
                .add(ModBlocks.HOLLOW_WARPED_STEM.get())
                .add(ModBlocks.HOLLOW_STRIPPED_WARPED_STEM.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.SPHAGNUM_MOSS.get())
                .add(ModBlocks.DRIED_SPHAGNUM_MOSS.get())
                .add(ModBlocks.SPHAGNUM_MOSS_BLOCK.get())
                .add(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
        
        tag(BlockTags.DIRT)
                .add(ModBlocks.SPHAGNUM_MOSS_BLOCK.get())
                .add(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
    }
}
