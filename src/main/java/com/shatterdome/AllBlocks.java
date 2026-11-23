package com.shatterdome;

import static com.shatterdome.content.util.ShatterdomeModels.vanilla;

import java.util.function.Consumer;

import com.shatterdome.content.reactor.ReactorCoreBlock;
import com.shatterdome.content.util.ShatterdomeModels;
import com.shatterdome.content.weapon.PlasmaCannonBlock;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;

public class AllBlocks {

    // --- Structural blocks (behavior comes in later phases) ---

    /** Central Jaeger brain. Phase 2: assembles limbs into a contraption. */
    public static final BlockEntry<Block> JAEGER_CONTROLLER =
            shaped("jaeger_controller", () -> Blocks.OBSIDIAN, ShatterdomeModels::controller);

    /** Chainable limb joint (hip/knee/ankle). Phase 2: inverse kinematics. */
    public static final BlockEntry<Block> LIMB_JOINT =
            shaped("limb_joint", () -> Blocks.POLISHED_BLACKSTONE, ShatterdomeModels::limbJoint);

    /** Pilot cockpit. Phase 2: seat + control handling. */
    public static final BlockEntry<Block> CONN_POD =
            shaped("conn_pod", () -> Blocks.IRON_BLOCK, ShatterdomeModels::connPod);

    /** Weapon attachment point for the plasma cannon. */
    public static final BlockEntry<Block> WEAPON_MOUNT =
            shaped("weapon_mount", () -> Blocks.IRON_BLOCK, ShatterdomeModels::weaponMount);

    /** Plain full-cube armor plating. */
    public static final BlockEntry<Block> JAEGER_ALLOY_PLATING = Shatterdome.REGISTRATE
            .block("jaeger_alloy_plating", Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(),
                    prov.models().cubeAll(ctx.getName(), ShatterdomeModels.mod("block/jaeger_plating"))))
            .simpleItem()
            .register();

    // --- Kinetic blocks ---

    /** Stress source: 64 RPM x 512 SU = 32,768 SU. */
    public static final BlockEntry<ReactorCoreBlock> REACTOR_CORE = Shatterdome.REGISTRATE
            .block("reactor_core", ReactorCoreBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.noOcclusion())
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> {
                BlockModelBuilder model = prov.models().withExistingParent(ctx.getName(), vanilla("block/block"));
                ShatterdomeModels.reactor(model);
                BlockStateGen.axisBlock(ctx, prov, state -> model);
            })
            .onRegister(b -> BlockStressValues.CAPACITIES.register(b, () -> 512))
            .simpleItem()
            .register();

    /** Stress consumer: 256 SU per RPM (16,384 SU at 64 RPM). */
    public static final BlockEntry<PlasmaCannonBlock> PLASMA_CANNON = Shatterdome.REGISTRATE
            .block("plasma_cannon", PlasmaCannonBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.noOcclusion())
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> {
                BlockModelBuilder model = prov.models().withExistingParent(ctx.getName(), vanilla("block/block"));
                ShatterdomeModels.plasmaCannon(model);
                BlockStateGen.axisBlock(ctx, prov, state -> model);
            })
            .onRegister(b -> BlockStressValues.IMPACTS.register(b, () -> 256))
            .simpleItem()
            .register();

    /** A block with a hand-built multi-box model and no rotation. */
    private static BlockEntry<Block> shaped(String name, NonNullSupplier<? extends Block> base,
                                            Consumer<BlockModelBuilder> modelBuilder) {
        return Shatterdome.REGISTRATE
                .block(name, Block::new)
                .initialProperties(base)
                .properties(p -> p.noOcclusion())
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate((ctx, prov) -> {
                    BlockModelBuilder model = prov.models().withExistingParent(ctx.getName(), vanilla("block/block"));
                    modelBuilder.accept(model);
                    prov.simpleBlock(ctx.get(), model);
                })
                .simpleItem()
                .register();
    }

    public static void register() {
        // Force class loading
    }
}
