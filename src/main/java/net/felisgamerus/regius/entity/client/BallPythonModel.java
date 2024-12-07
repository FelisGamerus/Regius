package net.felisgamerus.regius.entity.client;

import net.felisgamerus.regius.Regius;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class BallPythonModel extends GeoModel {
    private final ResourceLocation model = new ResourceLocation(Regius.MOD_ID, "geo/ballpython.geo.json");
    private final ResourceLocation texture = new ResourceLocation(Regius.MOD_ID, "textures/entity/ballpython/normal.png");
    private final ResourceLocation animations = new ResourceLocation(Regius.MOD_ID, "animations/ballpython.animation.json");

    @Override
    public ResourceLocation getModelResource(GeoAnimatable animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable animatable) {
        return this.animations;
    }
}
