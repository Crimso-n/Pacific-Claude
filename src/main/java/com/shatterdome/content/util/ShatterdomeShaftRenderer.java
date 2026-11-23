package com.shatterdome.content.util;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

/** Spins a Create shaft through the (inset) casing model so rotation is visible. */
public class ShatterdomeShaftRenderer<T extends KineticBlockEntity> extends KineticBlockEntityRenderer<T> {

    public ShatterdomeShaftRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected BlockState getRenderedBlockState(T be) {
        return shaft(getRotationAxisOf(be));
    }

    @Override
    protected SuperByteBuffer getRotatedModel(T be, BlockState state) {
        return CachedBuffers.partial(AllPartialModels.SHAFT, state);
    }
}
