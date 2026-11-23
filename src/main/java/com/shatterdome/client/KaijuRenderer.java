package com.shatterdome.client;

import com.shatterdome.content.kaiju.Kaiju;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Placeholder renderer: vanilla zombie model/texture (size comes from the SCALE attribute). */
public class KaijuRenderer extends MobRenderer<Kaiju, HumanoidModel<Kaiju>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/zombie/zombie.png");

    public KaijuRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Kaiju entity) {
        return TEXTURE;
    }
}
