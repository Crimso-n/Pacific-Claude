package com.shatterdome;

import com.shatterdome.client.KaijuRenderer;
import com.shatterdome.content.kaiju.Kaiju;
import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.LootTable;

public class AllEntityTypes {

    public static final EntityEntry<Kaiju> KAIJU = Shatterdome.REGISTRATE
            .entity("kaiju", Kaiju::new, MobCategory.MONSTER)
            .properties(b -> b.sized(0.6F, 1.95F).clientTrackingRange(24).fireImmune())
            .renderer(() -> KaijuRenderer::new)
            .loot((tables, type) -> tables.add(type, LootTable.lootTable()))
            .register();

    public static void register() {
        // Force class loading
    }
}