package com.shatterdome.content.limb;

import net.minecraft.world.phys.Vec3;

/**
 * Pure-math FABRIK solver for a chain of rigid bones. No Minecraft world access, so the same
 * class can later drive real Create block segments instead of a rendered model.
 * Coordinates are relative to the root (joints[0] = hip = origin).
 */
public class IkChain {

    public final double[] lengths;
    public final Vec3[] joints;
    private final double reach;

    public IkChain(double... lengths) {
        this.lengths = lengths;
        this.joints = new Vec3[lengths.length + 1];
        double sum = 0;
        for (double l : lengths)
            sum += l;
        this.reach = sum;
        reset();
    }

    public double reach() {
        return reach;
    }

    /** Hangs down with a slight forward knee bend (+Z is forward). */
    public void reset() {
        joints[0] = Vec3.ZERO;
        Vec3[] dirs = {
                new Vec3(0, -1, 0.5).normalize(),
                new Vec3(0, -1, -0.35).normalize(),
                new Vec3(0, -0.3, 1).normalize()
        };
        for (int i = 0; i < lengths.length; i++)
            joints[i + 1] = joints[i].add(dirs[Math.min(i, dirs.length - 1)].scale(lengths[i]));
    }

    private static Vec3 norm(Vec3 v, Vec3 fallback) {
        return v.lengthSqr() < 1e-9 ? fallback : v.normalize();
    }

    /** Moves the chain tip toward target, keeping the root fixed and every bone its original length. */
    public void solve(Vec3 target, int iterations) {
        int n = lengths.length;
        Vec3 root = joints[0];
        Vec3 down = new Vec3(0, -1, 0);

        if (target.distanceTo(root) >= reach - 1e-4) {
            Vec3 dir = norm(target.subtract(root), down);
            for (int i = 0; i < n; i++)
                joints[i + 1] = joints[i].add(dir.scale(lengths[i]));
            return;
        }

        for (int iter = 0; iter < iterations; iter++) {
            // backward pass: tip to target
            joints[n] = target;
            for (int i = n - 1; i >= 0; i--)
                joints[i] = joints[i + 1].add(norm(joints[i].subtract(joints[i + 1]), down).scale(lengths[i]));
            // forward pass: root back to origin
            joints[0] = root;
            for (int i = 0; i < n; i++)
                joints[i + 1] = joints[i].add(norm(joints[i + 1].subtract(joints[i]), down).scale(lengths[i]));
            if (joints[n].distanceToSqr(target) < 1e-6)
                break;
        }
    }
}
