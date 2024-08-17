package com.mao.sauces.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class FlavorfulEffect extends StatusEffect {
    public FlavorfulEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        ItemStack stack = entity.getActiveItem();
        if (entity instanceof PlayerEntity player) {
            if (stack.isFood()) {
                if (entity.getItemUseTime() == entity.getItemUseTimeLeft()) {
                    player.getHungerManager().add(2, 0.8f);
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
