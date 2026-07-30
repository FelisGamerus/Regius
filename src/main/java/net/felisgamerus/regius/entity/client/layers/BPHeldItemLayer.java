package net.felisgamerus.regius.entity.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

//For rendering the item in the mouth
public class BPHeldItemLayer<T extends GeoAnimatable> extends BlockAndItemGeoLayer<T> {

    public BPHeldItemLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    //Returns the held item of the ball python
    @Override
    protected @Nullable ItemStack getStackForBone(GeoBone bone, T animatable) {
        if(bone.getName().equals("item")) { //For "item" bone specifically
            return ((LivingEntity) animatable).getItemBySlot(EquipmentSlot.MAINHAND); //Returns held item
        }
        else return null;
    }

    //Rotates item 90 degrees so it's held sideways
    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

        super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
    }

    //Same as fox
    @Override
    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
        return ItemDisplayContext.GROUND;
    }
}
