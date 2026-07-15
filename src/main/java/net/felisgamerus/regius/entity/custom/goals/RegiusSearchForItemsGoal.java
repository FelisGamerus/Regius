package net.felisgamerus.regius.entity.custom.goals;

import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class RegiusSearchForItemsGoal extends Goal { //Copied from FoxSearchForItemsGoal
    private BallPythonEntity snake;
    private static final Predicate<ItemEntity> ALLOWED_ITEMS = BallPythonEntity.getAllowedItems();

    public RegiusSearchForItemsGoal(BallPythonEntity snake) {
        this.snake = snake;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (!snake.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else if (snake.getTarget() == null && snake.getLastHurtByMob() == null) {
            if (!snake.canMove()) {
                return false;
            } else if (snake.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                return false;
            } else {
                List<ItemEntity> list = snake.level().getEntitiesOfClass(ItemEntity.class, snake.getBoundingBox().inflate((double)8.0F, (double)8.0F, (double)8.0F), ALLOWED_ITEMS);
                return !list.isEmpty() && snake.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    public void tick() {
        List<ItemEntity> list = snake.level().getEntitiesOfClass(ItemEntity.class, snake.getBoundingBox().inflate((double)8.0F, (double)8.0F, (double)8.0F), ALLOWED_ITEMS);
        ItemStack itemstack = snake.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemstack.isEmpty() && !list.isEmpty()) {
            snake.getNavigation().moveTo((Entity)list.get(0), (double)1.2F);
        }

    }

    public void start() {
        List<ItemEntity> list = snake.level().getEntitiesOfClass(ItemEntity.class, snake.getBoundingBox().inflate((double)8.0F, (double)8.0F, (double)8.0F), ALLOWED_ITEMS);
        if (!list.isEmpty()) {
            snake.getNavigation().moveTo((Entity)list.get(0), (double)1.2F);
        }

    }
}
