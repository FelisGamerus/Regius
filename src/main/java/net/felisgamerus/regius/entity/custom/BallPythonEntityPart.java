package net.felisgamerus.regius.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;

import javax.annotation.Nullable;
import java.util.List;

public class BallPythonEntityPart extends PartEntity<BallPythonEntity> {

    private final EntityDimensions size;

    public BallPythonEntityPart(BallPythonEntity parent, float sizeX, float sizeY) {
        super(parent);
        this.size = EntityDimensions.scalable(sizeX, sizeY);
        this.refreshDimensions();
    }
    protected void collideWithNearbyEntities() {
        final List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
        Entity parent = this.getParent();
        if (parent != null) {
            entities.stream().filter(entity -> entity != parent && !(entity instanceof BallPythonEntityPart && ((BallPythonEntityPart) entity).getParent() == parent) && entity.isPushable()).forEach(entity -> entity.push(parent));
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        //System.out.println("Passing interaction by " + pPlayer.toString() + " using " + pHand.toString() + " at " + System.currentTimeMillis() + "to parent...");
        return this.getParent().mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        //System.out.println("Child hitbox hurt by " + source.toString() + " for " + amount + " at " + System.currentTimeMillis());
        return !this.isInvulnerableTo(source) && this.getParent().attackEntityPartFrom(this, source, amount);
    }

    @Nullable
    public ItemStack getPickResult() {
        Entity parent = this.getParent();
        return parent != null ? parent.getPickResult() : ItemStack.EMPTY;
    }

    public boolean isPickable() {
        return true;
    }

    public void push(Entity entityIn) {
        entityIn.push(this);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

}