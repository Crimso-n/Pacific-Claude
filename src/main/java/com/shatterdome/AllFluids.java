package com.shatterdome;

import com.tterrag.registrate.util.entry.FluidEntry;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class AllFluids {

    /** Kaiju Blue. Textures: assets/shatterdome/textures/fluid/kaiju_blue_still.png / _flow.png */
    public static final FluidEntry<BaseFlowingFluid.Flowing> KAIJU_BLUE = Shatterdome.REGISTRATE
            .standardFluid("kaiju_blue")
            .lang("Kaiju Blue")
            .fluidProperties(p -> p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .slopeFindDistance(3)
                    .explosionResistance(100f))
            .source(BaseFlowingFluid.Source::new)
            .block()
            .build()
            .bucket()
            .model((c, p) -> p.generated(c::getEntry, Shatterdome.asResource("item/kaiju_blue_bucket")))
            .build()
            .register();

    public static void register() {
        // Force class loading
    }
}