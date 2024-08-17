package com.mao.sauces.common.util;


import com.mao.sauces.registry.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Random;

public enum ModSauces {
    JAM(StatusEffects.REGENERATION, Formatting.LIGHT_PURPLE),
    KETCHUP(ModEffects.CRAVING , Formatting.DARK_RED),
    MAYONNAISE(StatusEffects.SLOW_FALLING, Formatting.YELLOW),
    CHOCOLATE_SAUCE(ModEffects.SILKY, Formatting.YELLOW),
    CHILLI_SAUCE(StatusEffects.HASTE, Formatting.RED),
    CREAMY_PEPPERCORN_SAUCE(ModEffects.FLAVORFUL, Formatting.YELLOW),
    CREAM(StatusEffects.NIGHT_VISION, Formatting.WHITE),
    PEANUT_BUTTER(StatusEffects.JUMP_BOOST, Formatting.YELLOW);

    public final String name;
    public final StatusEffect effect;
    public final Formatting color;

    ModSauces(StatusEffect effect, Formatting color){
        this.name = name().toLowerCase(Locale.ROOT);
        this.color = color;
        this.effect = effect;
    }

    public StatusEffect getEffect() {
        return effect;
    }

    public Formatting getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void eatFood(LivingEntity le){
        Random random = new Random();
        int duration = random.nextInt(20 * 90, 20 * 120);
        int amplifier = random.nextInt(0, 1);
        le.addStatusEffect(new StatusEffectInstance(getEffect(), duration, amplifier));
        if (le instanceof PlayerEntity player) player.getHungerManager().add(2, 0.5f);
    }

    @Nullable
    public static ModSauces matching(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && !nbt.getString("sauces").isEmpty()) {
            String str = nbt.getString("sauces");
            try {
                return Enum.valueOf(ModSauces.class, str);
            } catch (Exception ignored) {}
        }
        return null;
    }
}
