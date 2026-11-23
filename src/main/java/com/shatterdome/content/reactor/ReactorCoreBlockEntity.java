package com.shatterdome.content.reactor;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorCoreBlockEntity extends GeneratingKineticBlockEntity {

    /** RPM this reactor adds to the network. Capacity (SU per RPM) is set in AllBlocks. */
    public static final int GENERATED_RPM = 64;

    public ReactorCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) {
            updateGeneratedRotation();
        }
    }

    @Override
    public float getGeneratedSpeed() {
        return GENERATED_RPM;
    }
}
