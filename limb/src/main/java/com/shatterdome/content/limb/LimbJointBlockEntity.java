package com.shatterdome.content.limb;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/** Hip of a 3-bone limb. Stores which Conn Pod controls it (set with the Link Wand). */
public class LimbJointBlockEntity extends BlockEntity {

    /** Thigh, shin, foot lengths in blocks. */
    public static final double[] BONES = {1.6, 1.6, 1.0};
    public static final double REACH = BONES[0] + BONES[1] + BONES[2];

    /** Client-side pose, warm-started every frame. Not saved. */
    public final IkChain chain = new IkChain(BONES);

    @Nullable
    private BlockPos linkedPod;

    public LimbJointBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Nullable
    public BlockPos getLinkedPod() {
        return linkedPod;
    }

    public void setLinkedPod(@Nullable BlockPos pod) {
        this.linkedPod = pod;
        setChanged();
        if (level != null)
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (linkedPod != null)
            tag.putLong("LinkedPod", linkedPod.asLong());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        linkedPod = tag.contains("LinkedPod") ? BlockPos.of(tag.getLong("LinkedPod")) : null;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /** The limb reaches well outside its own block, so don't cull it with the block's bounds. */
    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition).inflate(REACH + 1);
    }
}
