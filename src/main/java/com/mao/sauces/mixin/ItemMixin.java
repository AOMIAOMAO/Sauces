package com.mao.sauces.mixin;

import com.mao.sauces.item.SaucesItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendTooltipForFood(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (stack.isFood() && stack.hasNbt() && !(stack.getItem() instanceof SuspiciousStewItem)) {
            String sauces = SaucesItem.getSauces(stack);
            tooltip.add(Text.translatable("item.sauces.sauces." + sauces).formatted(Formatting.BLUE));
        }
    }

    @Inject(method = "hasGlint", at = @At("TAIL"), cancellable = true)
    private void setGlintForFood(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(stack.isFood() && stack.hasNbt() && !(stack.getItem() instanceof SuspiciousStewItem)){
            cir.setReturnValue(true);
        }
    }
}
