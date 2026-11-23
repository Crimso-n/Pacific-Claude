package com.shatterdome;

import com.tterrag.registrate.util.entry.RegistryEntry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AllCreativeModeTabs {
    public static final String TAB_KEY = "itemGroup." + Shatterdome.ID;

    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            Shatterdome.REGISTRATE.defaultCreativeTab("main_tab", builder ->
                    builder
                            .title(Component.translatable(TAB_KEY))
                            .icon(() -> new ItemStack(AllBlocks.JAEGER_CONTROLLER.get()))
                            .build()
            ).register();

    static {
        Shatterdome.REGISTRATE.addRawLang(TAB_KEY, "Create: Shatterdome");
    }

    public static void register() {
        // Force class loading
    }
}
