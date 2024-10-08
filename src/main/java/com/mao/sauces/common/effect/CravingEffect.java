package com.mao.sauces.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class CravingEffect extends StatusEffect {
    public CravingEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        ItemStack item = entity.getMainHandStack();
        if (item.isFood() && entity.isUsingItem()){
            if (entity.getItemUseTime() == entity.getItemUseTimeLeft()){
                entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0f, 1.0f);
                entity.stopUsingItem();
                entity.eatFood(entity.getWorld(), item);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
