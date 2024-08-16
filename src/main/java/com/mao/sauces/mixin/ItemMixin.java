package com.mao.sauces.mixin;

import com.mao.sauces.common.event.FinishUsingItemCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void event(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir){
        FinishUsingItemCallback.EVENT.invoker().finishUsing(world, stack, user);
    }
}
