package com.shatterdome.client;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shatterdome.AllBlocks;
import com.shatterdome.content.limb.IkChain;
import com.shatterdome.content.limb.LimbJointBlock;
import com.shatterdome.content.limb.LimbJointBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/** Draws the 3 limb bones as stretched plating blocks, solved by IK toward the pilot's foot target. */
public class LimbJointRenderer implements BlockEntityRenderer<LimbJointBlockEntity> {

    private static final float THICKNESS = 0.4f;

    public LimbJointRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(LimbJointBlockEntity limb, float partialTick, PoseStack ps, MultiBufferSource buffer,
                       int light, int overlay) {
        BlockState hip = limb.getBlockState();
        if (!(hip.getBlock() instanceof LimbJointBlock))
            return;
        Direction facing = hip.getValue(LimbJointBlock.FACING);

        // Limb space (x right) -> model space (forward is +Z, so right is -X).
        Vec3 t = LimbControl.targetFor(limb);
        IkChain chain = limb.chain;
        chain.solve(new Vec3(-t.x, t.y, t.z), 6);

        BlockState bone = AllBlocks.JAEGER_ALLOY_PLATING.getDefaultState();

        ps.pushPose();
        ps.translate(0.5, 0.5, 0.5);
        ps.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        for (int i = 0; i < chain.lengths.length; i++)
            drawBone(ps, buffer, light, overlay, bone, chain.joints[i], chain.joints[i + 1]);
        ps.popPose();
    }

    private static void drawBone(PoseStack ps, MultiBufferSource buffer, int light, int overlay,
                                 BlockState bone, Vec3 a, Vec3 b) {
        Vec3 d = b.subtract(a);
        double len = d.length();
        if (len < 1e-4)
            return;
        Vector3f dir = new Vector3f((float) (d.x / len), (float) (d.y / len), (float) (d.z / len));

        ps.pushPose();
        ps.translate(a.x, a.y, a.z);
        ps.mulPose(new Quaternionf().rotationTo(new Vector3f(0, 1, 0), dir));
        ps.scale(THICKNESS, (float) len, THICKNESS);
        ps.translate(-0.5, 0, -0.5);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(bone, ps, buffer, light, overlay);
        ps.popPose();
    }
}
