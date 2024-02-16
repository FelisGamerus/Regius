package net.felisgamerus.regius.item;

import net.felisgamerus.regius.Regius;
import net.felisgamerus.regius.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Regius.MOD_ID);

    public static final RegistryObject<CreativeModeTab> REGIUS_TAB = CREATIVE_MODE_TABS.register("regius_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SPHAGNUM_MOSS_BLOCK.get()))
                    .title(Component.translatable("creativetab.regius_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.SPHAGNUM_MOSS_BLOCK.get());
                        pOutput.accept(ModBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
