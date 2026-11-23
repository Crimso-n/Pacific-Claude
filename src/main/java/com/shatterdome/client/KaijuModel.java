package com.shatterdome.client;

import com.shatterdome.Shatterdome;
import com.shatterdome.content.kaiju.Kaiju;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * Kaiju model: hunched bipedal beast with a horned head, hinged jaw, spined back,
 * two-joint clawed arms and a three-segment tail. Sized to roughly match the 0.6 x 1.95
 * hitbox before the SCALE attribute enlarges it. Texture: textures/entity/kaiju/kaiju.png
 * (128x128), glow pass: kaiju_glow.png.
 */
public class KaijuModel extends EntityModel<Kaiju> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Shatterdome.asResource("kaiju"), "main");

    private static final float DEG = (float) Math.PI / 180.0F;
    private static final float BODY_LEAN = 0.2F;

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart hornR;
    private final ModelPart hornL;
    private final ModelPart armR;
    private final ModelPart foreR;
    private final ModelPart armL;
    private final ModelPart foreL;
    private final ModelPart legR;
    private final ModelPart legL;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;

    public KaijuModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("body").getChild("head");
        this.jaw = root.getChild("body").getChild("head").getChild("jaw");
        this.hornR = root.getChild("body").getChild("head").getChild("horn_r");
        this.hornL = root.getChild("body").getChild("head").getChild("horn_l");
        this.armR = root.getChild("body").getChild("arm_r");
        this.foreR = root.getChild("body").getChild("arm_r").getChild("fore_r");
        this.armL = root.getChild("body").getChild("arm_l");
        this.foreL = root.getChild("body").getChild("arm_l").getChild("fore_l");
        this.legR = root.getChild("leg_r");
        this.legL = root.getChild("leg_l");
        this.tail1 = root.getChild("body").getChild("tail1");
        this.tail2 = root.getChild("body").getChild("tail1").getChild("tail2");
        this.tail3 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5F, -14F, -3F, 10F, 14F, 6F)
                .texOffs(44, 32).addBox(-1F, -13F, 3F, 2F, 4F, 2F)
                .texOffs(44, 32).addBox(-1F, -9F, 3F, 2F, 4F, 2F)
                .texOffs(52, 32).addBox(-1F, -5F, 3F, 2F, 3F, 2F)
                .texOffs(60, 32).addBox(-8F, -15F, -1F, 2F, 3F, 2F)
                .texOffs(60, 32).addBox(6F, -15F, -1F, 2F, 3F, 2F),
                PartPose.offsetAndRotation(0F, 13F, 0F, 0.2F, 0F, 0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(52, 0).addBox(-3.5F, -7F, -4F, 7F, 7F, 7F)
                .texOffs(0, 32).addBox(-2.5F, -4F, -8F, 5F, 3F, 4F)
                .texOffs(86, 20).addBox(-0.5F, -9F, -2F, 1F, 2F, 6F),
                PartPose.offset(0F, -13F, -1F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(18, 32).addBox(-2F, 0F, -5F, 4F, 2F, 5F),
                PartPose.offset(0F, -1F, -3F));

        PartDefinition hornR = head.addOrReplaceChild("horn_r", CubeListBuilder.create()
                .texOffs(36, 32).addBox(-1F, -5F, -1F, 2F, 5F, 2F),
                PartPose.offsetAndRotation(-3F, -6F, 0F, -0.4F, 0F, -0.5F));

        PartDefinition hornL = head.addOrReplaceChild("horn_l", CubeListBuilder.create().mirror()
                .texOffs(36, 32).addBox(-1F, -5F, -1F, 2F, 5F, 2F),
                PartPose.offsetAndRotation(3F, -6F, 0F, -0.4F, 0F, 0.5F));

        PartDefinition armR = body.addOrReplaceChild("arm_r", CubeListBuilder.create()
                .texOffs(106, 0).addBox(-2F, -1F, -2F, 4F, 8F, 4F),
                PartPose.offset(-7F, -12F, 0F));

        PartDefinition foreR = armR.addOrReplaceChild("fore_r", CubeListBuilder.create()
                .texOffs(0, 20).addBox(-2F, 0F, -2F, 4F, 8F, 4F)
                .texOffs(100, 20).addBox(-2.5F, 8F, -2.5F, 5F, 3F, 5F)
                .texOffs(76, 32).addBox(-2F, 9F, -3.5F, 1F, 3F, 1F)
                .texOffs(76, 32).addBox(-0.5F, 9F, -3.5F, 1F, 3F, 1F)
                .texOffs(76, 32).addBox(1F, 9F, -3.5F, 1F, 3F, 1F),
                PartPose.offset(0F, 7F, 0F));

        PartDefinition armL = body.addOrReplaceChild("arm_l", CubeListBuilder.create().mirror()
                .texOffs(106, 0).addBox(-2F, -1F, -2F, 4F, 8F, 4F),
                PartPose.offset(7F, -12F, 0F));

        PartDefinition foreL = armL.addOrReplaceChild("fore_l", CubeListBuilder.create().mirror()
                .texOffs(0, 20).addBox(-2F, 0F, -2F, 4F, 8F, 4F)
                .texOffs(100, 20).addBox(-2.5F, 8F, -2.5F, 5F, 3F, 5F)
                .texOffs(76, 32).addBox(-2F, 9F, -3.5F, 1F, 3F, 1F)
                .texOffs(76, 32).addBox(-0.5F, 9F, -3.5F, 1F, 3F, 1F)
                .texOffs(76, 32).addBox(1F, 9F, -3.5F, 1F, 3F, 1F),
                PartPose.offset(0F, 7F, 0F));

        PartDefinition legR = root.addOrReplaceChild("leg_r", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-2.5F, 0F, -2.5F, 5F, 10F, 5F)
                .texOffs(40, 20).addBox(-3F, 9F, -6F, 6F, 2F, 8F)
                .texOffs(80, 32).addBox(-2.5F, 10F, -8F, 1F, 1F, 2F)
                .texOffs(80, 32).addBox(-0.5F, 10F, -8F, 1F, 1F, 2F)
                .texOffs(80, 32).addBox(1.5F, 10F, -8F, 1F, 1F, 2F),
                PartPose.offset(-3F, 13F, 0F));

        PartDefinition legL = root.addOrReplaceChild("leg_l", CubeListBuilder.create().mirror()
                .texOffs(32, 0).addBox(-2.5F, 0F, -2.5F, 5F, 10F, 5F)
                .texOffs(40, 20).addBox(-3F, 9F, -6F, 6F, 2F, 8F)
                .texOffs(80, 32).addBox(-2.5F, 10F, -8F, 1F, 1F, 2F)
                .texOffs(80, 32).addBox(-0.5F, 10F, -8F, 1F, 1F, 2F)
                .texOffs(80, 32).addBox(1.5F, 10F, -8F, 1F, 1F, 2F),
                PartPose.offset(3F, 13F, 0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create()
                .texOffs(80, 0).addBox(-2.5F, -2.5F, 0F, 5F, 5F, 8F),
                PartPose.offsetAndRotation(0F, -3F, 3F, -0.35F, 0F, 0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create()
                .texOffs(16, 20).addBox(-2F, -2F, 0F, 4F, 4F, 8F),
                PartPose.offsetAndRotation(0F, 0F, 8F, -0.15F, 0F, 0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create()
                .texOffs(68, 20).addBox(-1F, -1F, 0F, 2F, 2F, 7F)
                .texOffs(68, 32).addBox(-0.5F, -3F, 2F, 1F, 2F, 3F),
                PartPose.offsetAndRotation(0F, 0F, 8F, -0.1F, 0F, 0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(Kaiju entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);

        float walk = limbSwing * 0.45F; // slow cadence: it is a giant
        float amp = Math.min(limbSwingAmount, 1.0F);
        float breathe = Mth.sin(ageInTicks * 0.08F) * 0.02F;
        float sway = Mth.sin(ageInTicks * 0.05F);

        // legs and arms swing in opposition
        this.legR.xRot += Mth.cos(walk) * 1.0F * amp;
        this.legL.xRot += Mth.cos(walk + Mth.PI) * 1.0F * amp;
        this.armR.xRot += Mth.cos(walk + Mth.PI) * 0.7F * amp - 0.15F;
        this.armL.xRot += Mth.cos(walk) * 0.7F * amp - 0.15F;
        this.armR.zRot += 0.08F + breathe;
        this.armL.zRot -= 0.08F + breathe;
        this.foreR.xRot -= 0.35F;
        this.foreL.xRot -= 0.35F;

        // heavy torso roll while walking, slow breathing at rest
        this.body.yRot += Mth.cos(walk) * 0.08F * amp;
        this.body.xRot += breathe;

        // head follows look direction, jaw idles open a touch
        this.head.yRot += netHeadYaw * DEG;
        this.head.xRot += headPitch * DEG - BODY_LEAN * 0.8F - breathe;
        this.jaw.xRot += 0.05F + (Mth.sin(ageInTicks * 0.1F) + 1.0F) * 0.03F;

        // tail whips side to side, each segment lagging the one before it
        this.tail1.yRot += Mth.cos(walk * 0.5F) * 0.30F * amp + sway * 0.05F;
        this.tail2.yRot += Mth.cos(walk * 0.5F - 0.8F) * 0.30F * amp + Mth.sin(ageInTicks * 0.05F - 0.6F) * 0.10F;
        this.tail3.yRot += Mth.cos(walk * 0.5F - 1.6F) * 0.35F * amp + Mth.sin(ageInTicks * 0.05F - 1.2F) * 0.14F;

        // melee swing: lunge, both claws raised and slammed down, jaws wide
        if (this.attackTime > 0.0F) {
            float swing = Mth.sin(this.attackTime * Mth.PI);
            this.armR.xRot -= swing * 2.0F;
            this.armL.xRot -= swing * 2.0F;
            this.foreR.xRot -= swing * 0.6F;
            this.foreL.xRot -= swing * 0.6F;
            this.body.xRot += swing * 0.3F;
            this.head.xRot -= swing * 0.3F;
            this.jaw.xRot += swing * 0.6F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
