package com.mao.sauces.mixin;

import com.mao.sauces.item.SaucesItem;
import com.nhoryzon.mc.farmersdelight.block.CuttingBoardBlock;
import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.nhoryzon.mc.farmersdelight.block.CuttingBoardBlock.spawnCuttingParticles;

@Mixin(CuttingBoardBlock.class)
public class CuttingBoardBlockMixin {

    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
    private void spreadSauces(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir ) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CuttingBoardBlockEntity cuttingBoardBlockEntity) {
            ItemStack stack = player.getStackInHand(player.getActiveHand());
            ItemStack stack1 = cuttingBoardBlockEntity.getStack(0);
            if (stack.getNbt() != null && !stack1.isEmpty() && stack.getItem() instanceof SaucesItem sauce && stack1.isFood() && !stack.getNbt().getString("sauces").isEmpty()) {
                sauce.spreadSauces(stack1, player);
                spawnCuttingParticles(world, pos, stack1, 10);
                stack.damage(1, player, p -> p.sendToolBreakStatus(p.getActiveHand()));
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }else {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
