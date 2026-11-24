package com.shatterdome.client;

import org.jetbrains.annotations.Nullable;

import com.shatterdome.Shatterdome;
import com.shatterdome.content.conn.ConnPodBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

/**
 * Client-side input capture while the local player sits in a Conn Pod.
 *
 * Limb mode (LIMB_MODE held): the camera is pinned, the scroll wheel stops changing the hotbar,
 * and mouse movement is measured from the raw cursor position (pixels per tick). Those values
 * drive {@link LimbControl}. When limb mode ends the player's real look direction is restored.
 */
@EventBusSubscriber(modid = Shatterdome.ID, value = Dist.CLIENT)
public class ConnPodInput {

    // --- state read by the HUD and limb control ---
    public static boolean seated;
    public static boolean limbMode;
    /** Position of the pod the local player is sitting in (null when not seated). */
    @Nullable
    public static BlockPos podPos;
    /** Mouse movement this tick in pixels (only non-zero in limb mode). */
    public static float mouseDX, mouseDY;
    public static boolean forward, back, left, right, jump;

    private static float frozenYaw, frozenPitch;
    private static float camYaw, camPitch;
    private static boolean haveCam;
    private static float heldCamYaw, heldCamPitch;
    private static double lastCursorX, lastCursorY;
    private static double scrollAccum;

    @Nullable
    private static BlockPos connPodOf(LocalPlayer player) {
        if (player.getVehicle() instanceof SeatEntity seat) {
            BlockPos pos = seat.blockPosition();
            if (player.level().getBlockState(pos).getBlock() instanceof ConnPodBlock)
                return pos;
        }
        return null;
    }

    private static void reset() {
        seated = false;
        limbMode = false;
        podPos = null;
        mouseDX = mouseDY = 0;
        scrollAccum = 0;
        forward = back = left = right = jump = false;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        BlockPos pod = player == null ? null : connPodOf(player);
        if (player == null || pod == null) {
            if (limbMode && player != null)
                restoreLook(player);
            reset();
            return;
        }
        seated = true;
        podPos = pod;

        forward = mc.options.keyUp.isDown();
        back = mc.options.keyDown.isDown();
        left = mc.options.keyLeft.isDown();
        right = mc.options.keyRight.isDown();
        jump = mc.options.keyJump.isDown();

        double cx = mc.mouseHandler.xpos();
        double cy = mc.mouseHandler.ypos();

        boolean want = mc.screen == null && ShatterdomeKeys.LIMB_MODE.isDown();
        if (want && !limbMode) {
            frozenYaw = player.getYRot();
            frozenPitch = player.getXRot();
            heldCamYaw = camYaw;
            heldCamPitch = camPitch;
            mouseDX = mouseDY = 0;
            scrollAccum = 0;
        } else if (want) {
            mouseDX = (float) (cx - lastCursorX);
            mouseDY = (float) (cy - lastCursorY);
            LimbControl.update(mouseDX, mouseDY, scrollAccum);
            scrollAccum = 0;
        } else {
            if (limbMode)
                restoreLook(player);
            mouseDX = mouseDY = 0;
            scrollAccum = 0;
        }
        lastCursorX = cx;
        lastCursorY = cy;
        limbMode = want;
    }

    private static void restoreLook(LocalPlayer player) {
        player.setYRot(frozenYaw);
        player.setXRot(frozenPitch);
        player.yRotO = frozenYaw;
        player.xRotO = frozenPitch;
    }

    /** In limb mode the wheel moves the foot up/down instead of changing the hotbar slot. */
    @SubscribeEvent
    public static void onScroll(InputEvent.MouseScrollingEvent event) {
        if (seated && limbMode) {
            scrollAccum += event.getScrollDeltaY();
            event.setCanceled(true);
        }
    }

    /** Pin the camera while in limb mode; otherwise remember where it points. */
    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        if (limbMode && haveCam) {
            event.setYaw(heldCamYaw);
            event.setPitch(heldCamPitch);
        } else {
            camYaw = event.getYaw();
            camPitch = event.getPitch();
            haveCam = true;
        }
    }
}
