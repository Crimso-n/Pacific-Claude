package com.shatterdome;

import com.shatterdome.content.kaiju.Kaiju;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Shatterdome.ID)
public class Shatterdome {
    public static final String ID = "shatterdome";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public Shatterdome(IEventBus modBus) {
        REGISTRATE.registerEventListeners(modBus);

        // Order matters: tab first so everything after lands in it.
        AllCreativeModeTabs.register();
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.MAIN_TAB);

        REGISTRATE.addRawLang("key.categories.shatterdome", "Create: Shatterdome");
        REGISTRATE.addRawLang("key.shatterdome.limb_mode", "Conn Pod: Limb Mode (hold)");

        AllEntityTypes.register();
        AllItems.register();
        AllFluids.register();
        AllBlocks.register();
        AllBlockEntityTypes.register();

        modBus.addListener(this::onEntityAttributes);
    }

    private void onEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AllEntityTypes.KAIJU.get(), Kaiju.createAttributes().build());
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}