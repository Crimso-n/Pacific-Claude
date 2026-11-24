package com.shatterdome.client;

import org.lwjgl.glfw.GLFW;

import com.shatterdome.Shatterdome;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

/** Rebindable Shatterdome keys (Options > Controls > Create: Shatterdome). */
@EventBusSubscriber(modid = Shatterdome.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShatterdomeKeys {

    public static final String CATEGORY = "key.categories.shatterdome";

    /** Hold while seated in a Conn Pod: the mouse drives the limb instead of the camera. */
    public static final KeyMapping LIMB_MODE =
            new KeyMapping("key.shatterdome.limb_mode", GLFW.GLFW_KEY_R, CATEGORY);

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(LIMB_MODE);
    }
}
