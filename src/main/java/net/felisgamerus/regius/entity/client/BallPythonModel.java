package net.felisgamerus.regius.entity.client;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BallPythonModel extends GeoModel<BallPythonEntity> {
    private final ResourceLocation animations = ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, "animations/ballpython.animation.json");
    private final ResourceLocation modelAdult = ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, "geo/ballpython.geo.json");
    private final ResourceLocation modelBaby = ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, "geo/ballpython_baby.geo.json");

    @Override
    public ResourceLocation getAnimationResource(BallPythonEntity entity) {
        return this.animations;
    }

    @Override
    public ResourceLocation getModelResource(BallPythonEntity entity) {
        return entity.isBaby() ? modelBaby : modelAdult;
    }

    @Override
    public ResourceLocation getTextureResource(BallPythonEntity entity) {
        //ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, "textures/entity/ballpython/normal.png");
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, "textures/entity/ballpython/" + entity.convertGenotypeToPhenotype(entity.getGenotype()) + ".png");
        return texture;
    }

}
