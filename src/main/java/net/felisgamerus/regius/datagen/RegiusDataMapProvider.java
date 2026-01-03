package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.block.RegiusBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class RegiusDataMapProvider extends DataMapProvider {
    protected RegiusDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.DRIED_SPHAGNUM_MOSS.getId(), new FurnaceFuel(100), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.getId(), new FurnaceFuel(300), false);

        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_OAK_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_OAK_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_SPRUCE_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_BIRCH_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_BIRCH_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_JUNGLE_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_ACACIA_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_ACACIA_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_DARK_OAK_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_MANGROVE_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_CHERRY_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(RegiusBlocks.HOLLOW_STRIPPED_CHERRY_LOG.getId(), new FurnaceFuel(300), false);
    }
}
