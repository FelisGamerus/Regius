package net.felisgamerus.regius.util;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.item.ModItems;
import net.felisgamerus.regius.item.custom.BallPythonBucketItem;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.BALL_PYTHON_BUCKET.get(), ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, "bucket_phenotype"),
                (stack, level, entity, seed) -> BallPythonBucketItem.getBucketPhenotype(stack));
    }
}
