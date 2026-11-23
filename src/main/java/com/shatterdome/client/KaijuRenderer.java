package com.shatterdome.client;

import com.shatterdome.Shatterdome;
import com.shatterdome.content.kaiju.Kaiju;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Renders the Kaiju with its custom model; the size comes from the SCALE attribute. */
public class KaijuRenderer extends MobRenderer<Kaiju, KaijuModel> {

    private static final ResourceLocation TEXTURE =
            Shatterdome.asResource("textures/entity/kaiju/kaiju.png");

    public KaijuRenderer(EntityRendererProvider.Context context) {
        super(context, new KaijuModel(context.bakeLayer(KaijuModel.LAYER)), 0.6F);
        this.addLayer(new KaijuGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Kaiju entity) {
        return TEXTURE;
    }
}
