package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.RegiusBlocks;
import net.felisgamerus.regius.item.RegiusItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class RegiusItemModelProvider extends ItemModelProvider {
    public RegiusItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Regius.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(RegiusItems.BALL_PYTHON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        basicItem(RegiusItems.SPHAGNUM_MOSS.get());
        basicItem(RegiusItems.DRIED_SPHAGNUM_MOSS.get());

        simpleBlockItem(RegiusBlocks.SPHAGNUM_MOSS_BLOCK.get());
        simpleBlockItem(RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
    }
}
