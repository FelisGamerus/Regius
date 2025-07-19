package net.felisgamerus.regius.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BallPythonRenderer extends GeoEntityRenderer<BallPythonEntity>{

    public BallPythonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BallPythonModel());
    }

    @Override
    public void render(BallPythonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
