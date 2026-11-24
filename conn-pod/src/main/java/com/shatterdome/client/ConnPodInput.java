package com.shatterdome.client;

import com.shatterdome.Shatterdome;
import com.shatterdome.content.conn.ConnPodBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

/**
 * Client-side input capture while the local player sits in a Conn Pod.
 *
 * In limb mode (LIMB_MODE held) the camera is frozen and the mouse movement is
 * measured as a per-tick delta in degrees instead. This is v1: nothing consumes
 * the values yet except the HUD readout; limbs/IK read them later.
 */
@EventBusSubscriber(modid = Shatterdome.ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ConnPodInput {

    // --- state read by the HUD (and later by limb control) ---
    public static boolean seated;
    public static boolean limbMode;
    /** Mouse movement this tick, in degrees of would-be camera rotation (only non-zero in limb mode). */
    public static float mouseDX, mouseDY;
    public static boolean forward, back, left, right, jump;

    private static float frozenYaw, frozenPitch;

    private static boolean isInConnPod(LocalPlayer player) {
        return player.getVehicle() instanceof SeatEntity seat
                && player.level().getBlockState(seat.blockPosition()).getBlock() instanceof ConnPodBlock;
    }

    private static void reset() {
        seated = false;
        limbMode = false;
        mouseDX = mouseDY = 0;
        forward = back = left = right = jump = false;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || !isInConnPod(player)) {
            reset();
            return;
        }
        seated = true;

        forward = mc.options.keyUp.isDown();
        back = mc.options.keyDown.isDown();
        left = mc.options.keyLeft.isDown();
        right = mc.options.keyRight.isDown();
        jump = mc.options.keyJump.isDown();

        boolean want = mc.screen == null && ShatterdomeKeys.LIMB_MODE.isDown();
        if (want && !limbMode) {
            frozenYaw = player.getYRot();
            frozenPitch = player.getXRot();
        }
        if (want) {
            mouseDX = player.getYRot() - frozenYaw;
            mouseDY = player.getXRot() - frozenPitch;
            // Put the player's rotation back so the real look direction never changes.
            player.setYRot(frozenYaw);
            player.setXRot(frozenPitch);
            player.yRotO = frozenYaw;
            player.xRotO = frozenPitch;
        } else {
            mouseDX = mouseDY = 0;
        }
        limbMode = want;
    }

    /** Between ticks the mouse still turns the player; cancel that visually so the camera stays frozen. */
    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        if (!limbMode)
            return;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null)
            return;
        float pt = (float) event.getPartialTick();
        event.setYaw(event.getYaw() - (Mth.lerp(pt, player.yRotO, player.getYRot()) - frozenYaw));
        event.setPitch(event.getPitch() - (Mth.lerp(pt, player.xRotO, player.getXRot()) - frozenPitch));
    }
}
