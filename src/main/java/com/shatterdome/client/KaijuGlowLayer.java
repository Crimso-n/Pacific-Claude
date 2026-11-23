package com.shatterdome.client;

import com.shatterdome.Shatterdome;
import com.shatterdome.content.kaiju.Kaiju;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

/** Full-bright bioluminescent pass: draws textures/entity/kaiju/kaiju_glow.png regardless of light level. */
public class KaijuGlowLayer extends EyesLayer<Kaiju, KaijuModel> {

    private static final RenderType GLOW =
            RenderType.eyes(Shatterdome.asResource("textures/entity/kaiju/kaiju_glow.png"));

    public KaijuGlowLayer(RenderLayerParent<Kaiju, KaijuModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return GLOW;
    }
}
