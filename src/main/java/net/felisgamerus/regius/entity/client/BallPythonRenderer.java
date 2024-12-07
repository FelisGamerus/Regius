package net.felisgamerus.regius.entity.client;

import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BallPythonRenderer extends GeoEntityRenderer<BallPythonEntity>{
    public BallPythonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BallPythonModel());
    }
}
