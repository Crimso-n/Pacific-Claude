package com.shatterdome;

import com.shatterdome.content.link.LinkWandItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

/** Items use simple generated textures; replace the PNGs in assets/shatterdome/textures/item for real art. */
public class AllItems {

    public static final ItemEntry<Item> JAEGER_ALLOY_INGOT = Shatterdome.REGISTRATE
            .item("jaeger_alloy_ingot", Item::new)
            .model((c, p) -> p.generated(c::getEntry, Shatterdome.asResource("item/jaeger_alloy_ingot")))
            .register();

    public static final ItemEntry<Item> KAIJU_BLUE_VIAL = Shatterdome.REGISTRATE
            .item("kaiju_blue_vial", Item::new)
            .model((c, p) -> p.generated(c::getEntry, Shatterdome.asResource("item/kaiju_blue_vial")))
            .register();

    /** Click a Conn Pod, then a Limb Joint, to link them. Shift-click a Limb Joint to unlink. */
    public static final ItemEntry<LinkWandItem> LINK_WAND = Shatterdome.REGISTRATE
            .item("link_wand", LinkWandItem::new)
            .model((c, p) -> p.generated(c::getEntry, Shatterdome.asResource("item/link_wand")))
            .register();

    public static final ItemEntry<DeferredSpawnEggItem> KAIJU_SPAWN_EGG = Shatterdome.REGISTRATE
            .item("kaiju_spawn_egg", p -> new DeferredSpawnEggItem(AllEntityTypes.KAIJU::get, 0x1B3A6B, 0x3DE0FF, p))
            .model((c, p) -> p.withExistingParent(c.getName(), ResourceLocation.withDefaultNamespace("item/template_spawn_egg")))
            .register();

    public static void register() {
        // Force class loading
    }
}
