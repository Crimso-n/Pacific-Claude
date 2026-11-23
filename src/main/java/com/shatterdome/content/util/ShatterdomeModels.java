package com.shatterdome.content.util;

import com.shatterdome.Shatterdome;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;

/**
 * Hand-built block models made of simple boxes (pixel coordinates 0-16).
 * Kinetic models (reactor, cannon) are authored along the Y axis; Create's
 * BlockStateGen.axisBlock rotates them to the block's axis.
 */
public class ShatterdomeModels {

    public static ResourceLocation vanilla(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    public static ResourceLocation mod(String path) {
        return Shatterdome.asResource(path);
    }

    /** Adds a box where every face uses the same texture variable, e.g. "#plating". */
    public static void box(BlockModelBuilder m, float x1, float y1, float z1,
                           float x2, float y2, float z2, String tex) {
        m.element()
                .from(x1, y1, z1)
                .to(x2, y2, z2)
                .allFaces((dir, face) -> face.texture(tex))
                .end();
    }

    private static void palette(BlockModelBuilder m, String particle) {
        m.texture("plating", mod("block/jaeger_plating"));
        m.texture("dark", mod("block/jaeger_plating_dark"));
        m.texture("orange", mod("block/jaeger_trim_orange"));
        m.texture("hazard", mod("block/jaeger_hazard"));
        m.texture("glow", mod("block/jaeger_glow"));
        m.texture("amber", mod("block/jaeger_amber_glass"));
        m.texture("gun", mod("block/jaeger_gunmetal"));
        m.texture("particle", mod(particle));
    }

    public static void controller(BlockModelBuilder m) {
        palette(m, "block/jaeger_plating_dark");
        box(m, 0, 0, 0, 16, 14, 16, "#dark");
        box(m, -0.3f, 5.5f, -0.3f, 16.3f, 9.5f, 16.3f, "#hazard");
        box(m, 0, 14, 0, 16, 16, 16, "#orange");
        box(m, 5, 16, 5, 11, 17.5f, 11, "#glow");
    }

    public static void limbJoint(BlockModelBuilder m) {
        palette(m, "block/jaeger_plating");
        box(m, 2, 0, 2, 14, 3, 14, "#plating");
        box(m, 2, 13, 2, 14, 16, 14, "#plating");
        box(m, 3, 3, 3, 13, 13, 13, "#orange");
        box(m, 0, 6.5f, 6.5f, 16, 9.5f, 9.5f, "#dark");
    }

    public static void connPod(BlockModelBuilder m) {
        palette(m, "block/jaeger_plating");
        box(m, 0, 0, 0, 16, 5, 16, "#plating");
        box(m, 1, 5, 1, 15, 10, 15, "#amber");
        box(m, 0, 10, 0, 16, 14, 16, "#orange");
        box(m, 7, 14, 7, 9, 16, 9, "#dark");
    }

    public static void weaponMount(BlockModelBuilder m) {
        palette(m, "block/jaeger_plating");
        box(m, 0, 0, 0, 16, 4, 16, "#plating");
        box(m, 4, 4, 4, 12, 10, 12, "#dark");
        box(m, 2, 10, 2, 14, 13, 14, "#orange");
        box(m, 2, 13, 2, 5, 16, 14, "#plating");
        box(m, 11, 13, 2, 14, 16, 14, "#plating");
    }

    /** Authored along Y: shaft pokes out of y<3 and y>13. */
    public static void reactor(BlockModelBuilder m) {
        palette(m, "block/jaeger_plating_dark");
        box(m, 2, 3, 2, 14, 13, 14, "#dark");
        box(m, 0, 3, 0, 16, 5, 16, "#orange");
        box(m, 0, 11, 0, 16, 13, 16, "#orange");
        box(m, 1, 6.5f, 1, 15, 9.5f, 15, "#glow");
    }

    /** Authored along Y: barrel points up (+Y) before rotation. */
    public static void plasmaCannon(BlockModelBuilder m) {
        palette(m, "block/jaeger_gunmetal");
        box(m, 1, 3, 1, 15, 8, 15, "#dark");
        box(m, 0, 8, 0, 16, 10, 16, "#orange");
        box(m, 3.5f, 10, 3.5f, 12.5f, 12, 12.5f, "#gun");
        box(m, 2.5f, 12, 2.5f, 13.5f, 13, 13.5f, "#hazard");
    }
}
