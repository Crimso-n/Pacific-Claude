package com.shatterdome.client;

import com.shatterdome.Shatterdome;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

/** Debug-style readout of what the Conn Pod is capturing. */
@EventBusSubscriber(modid = Shatterdome.ID, value = Dist.CLIENT)
public class ConnPodHud {

    @SubscribeEvent
    public static void registerLayers(RegisterGuiLayersEvent event) {
        try {
            event.registerAboveAll(Shatterdome.asResource("pilot_limb_readout"), ConnPodHud::render);
        } catch (IllegalArgumentException alreadyRegistered) {
            // Registered twice by a stray copy of this class; ignore the repeat.
        }
    }

    private static void render(GuiGraphics g, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (!ConnPodInput.seated || mc.options.hideGui)
            return;

        int x = 6, y = 6;
        g.drawString(mc.font, "CONN POD", x, y, 0xFFE08A00, true);
        y += 12;
        g.drawString(mc.font, "Limb mode: " + (ConnPodInput.limbMode ? "ON" : "off")
                        + "  (hold " + ShatterdomeKeys.LIMB_MODE.getTranslatedKeyMessage().getString() + ")",
                x, y, ConnPodInput.limbMode ? 0xFF55FF55 : 0xFFFFFFFF, true);
        y += 12;
        g.drawString(mc.font, "Mouse: move = foot right/forward, wheel = up/down", x, y, 0xFFAAAAAA, true);
        y += 12;
        Vec3 t = LimbControl.target;
        g.drawString(mc.font, String.format("Foot target  right %.2f  up %.2f  forward %.2f", t.x, t.y, t.z),
                x, y, 0xFFFFFFFF, true);
        y += 12;
        g.drawString(mc.font, String.format("Mouse dX %.0f  dY %.0f px", ConnPodInput.mouseDX, ConnPodInput.mouseDY),
                x, y, 0xFFFFFFFF, true);
        y += 12;
        g.drawString(mc.font, "Keys: "
                + (ConnPodInput.forward ? "W " : "") + (ConnPodInput.left ? "A " : "")
                + (ConnPodInput.back ? "S " : "") + (ConnPodInput.right ? "D " : "")
                + (ConnPodInput.jump ? "SPACE" : ""), x, y, 0xFFFFFFFF, true);
    }
}