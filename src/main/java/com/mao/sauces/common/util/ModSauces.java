package com.mao.sauces.common.util;


import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum ModSauces {
    JAM(StatusEffects.REGENERATION, Formatting.RED);

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
