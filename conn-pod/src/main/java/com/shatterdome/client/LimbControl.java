package com.shatterdome.client;

import com.shatterdome.content.limb.LimbJointBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/**
 * Client-side foot target for the limb the local pilot controls.
 * Limb space: x = right, y = up, z = forward, origin = hip.
 * Mouse X/Y move the foot right/forward, the scroll wheel moves it up/down.
 */
public class LimbControl {

    public static final Vec3 REST = new Vec3(0, -3.0, 0.6);

    private static final double BLOCKS_PER_PIXEL = 0.01;
    private static final double BLOCKS_PER_SCROLL = 0.25;

    public static Vec3 target = REST;

    /** Called once per tick while limb mode is held. dx/dy are mouse pixels, scroll is wheel notches. */
    public static void update(float dx, float dy, double scroll) {
        Vec3 t = target.add(dx * BLOCKS_PER_PIXEL, scroll * BLOCKS_PER_SCROLL, -dy * BLOCKS_PER_PIXEL);
        double max = LimbJointBlockEntity.REACH * 0.98;
        if (t.length() > max)
            t = t.scale(max / t.length());
        target = t;
    }

    /** The target a given limb should use right now (its pilot's target, or the rest pose). */
    public static Vec3 targetFor(LimbJointBlockEntity limb) {
        BlockPos pod = limb.getLinkedPod();
        if (pod != null && ConnPodInput.seated && pod.equals(ConnPodInput.podPos))
            return target;
        return REST;
    }
}
