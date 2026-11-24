package com.shatterdome.content.link;

import com.shatterdome.content.conn.ConnPodBlock;
import com.shatterdome.content.limb.LimbJointBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * Click a Conn Pod to select it, then click a Limb Joint to link them.
 * Shift-click a Limb Joint to unlink it.
 */
public class LinkWandItem extends Item {

    private static final String POD_KEY = "Pod";

    public LinkWandItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    private static void say(Player player, String text) {
        if (player != null)
            player.displayClientMessage(Component.literal(text), true);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (level.getBlockState(pos).getBlock() instanceof ConnPodBlock) {
            if (!level.isClientSide) {
                stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY,
                        data -> data.update(tag -> tag.putLong(POD_KEY, pos.asLong())));
                say(player, "Conn Pod selected. Now click a Limb Joint.");
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (level.getBlockEntity(pos) instanceof LimbJointBlockEntity limb) {
            if (!level.isClientSide) {
                if (player != null && player.isShiftKeyDown()) {
                    limb.setLinkedPod(null);
                    say(player, "Limb Joint unlinked.");
                } else {
                    CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                    if (!tag.contains(POD_KEY)) {
                        say(player, "Click a Conn Pod first.");
                    } else {
                        BlockPos pod = BlockPos.of(tag.getLong(POD_KEY));
                        if (level.getBlockState(pod).getBlock() instanceof ConnPodBlock) {
                            limb.setLinkedPod(pod);
                            say(player, "Linked Limb Joint to Conn Pod at " + pod.toShortString() + ".");
                        } else {
                            say(player, "That Conn Pod is gone. Select a Conn Pod again.");
                        }
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
