package net.felisgamerus.regius.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.entity.custom.BallPythonEntitiy;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BallPythonRenderer extends MobRenderer<BallPythonEntitiy, BallPythonModel<BallPythonEntitiy>> {
    public BallPythonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BallPythonModel<>(pContext.bakeLayer(ModModelLayers.BALL_PYTHON_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BallPythonEntitiy pEntity) {
        return new ResourceLocation(Regius.MOD_ID, "textures/entity/ballpython/normal.png");
    }

    @Override
    public void render(BallPythonEntitiy pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
