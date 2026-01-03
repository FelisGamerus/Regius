package net.felisgamerus.regius.item;

import net.felisgamerus.regius.Regius;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegiusCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Regius.MOD_ID);

    public static final Supplier<CreativeModeTab> REGIUS_TAB = CREATIVE_MODE_TAB.register("regius_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(RegiusItems.BALL_PYTHON_BUCKET.get()))
                    .title(Component.translatable("creativetab.regius.regius"))
                    .displayItems((itemDisplayerParameters, output) -> {
                        output.accept(RegiusItems.BALL_PYTHON_SPAWN_EGG);
                        output.accept(RegiusItems.BALL_PYTHON_BUCKET);

                        output.accept(RegiusItems.SPHAGNUM_MOSS);
                        output.accept(RegiusItems.DRIED_SPHAGNUM_MOSS);

                        output.accept(RegiusItems.SPHAGNUM_MOSS_BLOCK);
                        output.accept(RegiusItems.DRIED_SPHAGNUM_MOSS_BLOCK);

                        output.accept(RegiusItems.HOLLOW_OAK_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_OAK_LOG);
                        output.accept(RegiusItems.HOLLOW_SPRUCE_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_SPRUCE_LOG);
                        output.accept(RegiusItems.HOLLOW_BIRCH_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_BIRCH_LOG);
                        output.accept(RegiusItems.HOLLOW_JUNGLE_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_JUNGLE_LOG);
                        output.accept(RegiusItems.HOLLOW_ACACIA_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_ACACIA_LOG);
                        output.accept(RegiusItems.HOLLOW_DARK_OAK_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_DARK_OAK_LOG);
                        output.accept(RegiusItems.HOLLOW_MANGROVE_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_MANGROVE_LOG);
                        output.accept(RegiusItems.HOLLOW_CHERRY_LOG);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_CHERRY_LOG);
                        output.accept(RegiusItems.HOLLOW_CRIMSON_STEM);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_CRIMSON_STEM);
                        output.accept(RegiusItems.HOLLOW_WARPED_STEM);
                        output.accept(RegiusItems.HOLLOW_STRIPPED_WARPED_STEM);

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
