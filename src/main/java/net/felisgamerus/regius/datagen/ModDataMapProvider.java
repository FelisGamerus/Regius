package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModBlocks.DRIED_SPHAGNUM_MOSS.getId(), new FurnaceFuel(100), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModBlocks.HOLLOW_OAK_LOG.getId(), new FurnaceFuel(300), false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModBlocks.HOLLOW_STRIPPED_OAK_LOG.getId(), new FurnaceFuel(300), false);
    }
}
