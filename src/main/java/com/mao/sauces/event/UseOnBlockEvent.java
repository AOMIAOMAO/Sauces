package com.mao.sauces.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class UseOnBlockEvent implements UseBlockCallback {

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getStackInHand(hand);

        if (world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.WATER_CAULDRON)) {
            if (stack.isFood() && stack.hasNbt() && !(stack.getItem() instanceof SuspiciousStewItem)) {
                world.playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                stack.setNbt(null);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
