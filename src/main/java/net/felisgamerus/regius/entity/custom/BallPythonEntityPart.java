package net.felisgamerus.regius.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.entity.PartEntity;

import javax.annotation.Nullable;
import java.util.List;

//Code pulled and modified from Untamed Wilds and Alex's Mobs
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

    //This method currently does not work and I cannot figure out how to get it to work
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.BUCKET) {
            return InteractionResult.PASS; //BPEntityParts can be bucketed, but create nothing when the resulting ball python bucket is used. Temporary until I can work out a fix
        } else return this.getParent().mobInteract(player, hand);
    }

    //This method currently does not work and I cannot figure out how to get it to work
    @Override
    public boolean hurt(DamageSource source, float amount) {
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

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public void push(Entity entityIn) {
        entityIn.push(this);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

}
