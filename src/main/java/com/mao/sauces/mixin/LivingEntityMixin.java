package com.mao.sauces.mixin;

import com.mao.sauces.item.SaucesItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "applyFoodEffects", at = @At("TAIL"))
    private void eatFoodsWithSauces(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci) {
        if (stack.getNbt() != null) {
            StatusEffect effect = SaucesItem.getEffect(stack);

            Random random = new Random();
            int randomDuration = random.nextInt(20 * 90, 120 * 20);
            int randomAmplifier = random.nextInt(2);

            if (stack.isFood() && !stack.getNbt().getString("sauces").isEmpty() && effect != null) {
                targetEntity.addStatusEffect(new StatusEffectInstance(effect, randomDuration, randomAmplifier));
            }
        }
    }
}
