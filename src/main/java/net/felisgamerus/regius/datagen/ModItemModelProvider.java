package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.ModBlocks;
import net.felisgamerus.regius.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Regius.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.BALL_PYTHON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        basicItem(ModItems.SPHAGNUM_MOSS.get());
        basicItem(ModItems.DRIED_SPHAGNUM_MOSS.get());

        simpleBlockItem(ModBlocks.SPHAGNUM_MOSS_BLOCK.get());
        simpleBlockItem(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
    }
}
