package com.shatterdome.content.weapon;

import com.shatterdome.AllBlockEntityTypes;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Stress-powered plasma cannon. Phase 1: draws stress only; firing logic comes later. */
public class PlasmaCannonBlock extends RotatedPillarKineticBlock implements IBE<PlasmaCannonBlockEntity> {

    public PlasmaCannonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(AXIS);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public Class<PlasmaCannonBlockEntity> getBlockEntityClass() {
        return PlasmaCannonBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PlasmaCannonBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.PLASMA_CANNON.get();
    }
}
