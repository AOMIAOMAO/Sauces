package com.mao.sauces.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
    private void eatFoods(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci) {
        if (stack.getNbt() != null) {
            String code = stack.getNbt().getString("sauces");

            Random random = new Random();
            int randomDuration = random.nextInt(30 * 20, 45 * 20);
            int randomAmplifier = random.nextInt(2);

            if (stack.isFood() && stack.hasNbt()) {
                switch (code) {
                    case "salad_dressing" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, randomDuration, randomAmplifier));
                    case "jam" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, randomDuration, randomAmplifier));
                    case "chocolate_sauce" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, randomDuration, randomAmplifier));
                    case "cream" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, randomDuration, 1));
                    case "mushroom_paste" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, randomDuration, randomAmplifier));
                    case "black_pepper_sauce" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, randomDuration, randomAmplifier));
                    case "chilli_sauce" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, randomDuration, randomAmplifier));
                    case "peanut_butter" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, randomDuration, randomAmplifier));
                    case "ketchup" -> targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 20 * 25, 0));
                }
            }
        }
    }
}
