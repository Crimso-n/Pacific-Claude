package com.shatterdome.client;

import com.shatterdome.Shatterdome;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Client-only mod bus hooks: entity model layer registration. */
@EventBusSubscriber(modid = Shatterdome.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShatterdomeClient {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KaijuModel.LAYER, KaijuModel::createBodyLayer);
    }
}
