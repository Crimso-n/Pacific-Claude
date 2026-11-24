package com.shatterdome;

import com.shatterdome.client.LimbJointRenderer;
import com.shatterdome.content.limb.LimbJointBlockEntity;
import com.shatterdome.content.reactor.ReactorCoreBlockEntity;
import com.shatterdome.content.util.ShatterdomeShaftRenderer;
import com.shatterdome.content.weapon.PlasmaCannonBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {

    public static final BlockEntityEntry<ReactorCoreBlockEntity> REACTOR_CORE = Shatterdome.REGISTRATE
            .blockEntity("reactor_core", ReactorCoreBlockEntity::new)
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.REACTOR_CORE)
            .renderer(() -> ShatterdomeShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<PlasmaCannonBlockEntity> PLASMA_CANNON = Shatterdome.REGISTRATE
            .blockEntity("plasma_cannon", PlasmaCannonBlockEntity::new)
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.PLASMA_CANNON)
            .renderer(() -> ShatterdomeShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<LimbJointBlockEntity> LIMB_JOINT = Shatterdome.REGISTRATE
            .blockEntity("limb_joint", LimbJointBlockEntity::new)
            .validBlock(AllBlocks.LIMB_JOINT)
            .renderer(() -> LimbJointRenderer::new)
            .register();

    public static void register() {
        // Force class loading
    }
}
