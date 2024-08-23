// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

package net.felisgamerus.regius.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class BallPythonModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart pivotPoint;

	public BallPythonModel(ModelPart root) {
		this.pivotPoint = root.getChild("pivotPoint");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition pivotPoint = partdefinition.addOrReplaceChild("pivotPoint", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 18.0F));

		PartDefinition head = pivotPoint.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, 3.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.01F))
		.texOffs(0, 9).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -22.5F));

		PartDefinition tongue = head.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(13, 9).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 3.0F));

		PartDefinition body1 = head.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -1.75F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 8.0F));

		PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -0.25F, 7.0F));

		PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(31, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 11.0F));

		PartDefinition body4 = body3.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition tail = body4.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(31, 17).addBox(-1.0F, -1.5F, 0.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.5F, 11.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		pivotPoint.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return pivotPoint;
	}

}