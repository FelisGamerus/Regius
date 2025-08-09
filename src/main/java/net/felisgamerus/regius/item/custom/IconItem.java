package net.felisgamerus.regius.item.custom;

import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;
import java.util.Optional;

//Used when I need an icon for something and I can only use items
public class IconItem extends Item {
    public IconItem(Properties properties) {
        super(properties);
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ChatFormatting[] formatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        MutableComponent hoverText = Component.translatable("regius.icon_item_warning");

        tooltipComponents.add(hoverText.withStyle(formatting));
    }
}
