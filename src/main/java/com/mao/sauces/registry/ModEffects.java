package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.common.effect.CravingEffect;
import com.mao.sauces.common.effect.EmptyEffect;
import com.mao.sauces.common.effect.FlavorfulEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEffects {
    public static final StatusEffect SILKY = register("silky", new EmptyEffect(StatusEffectCategory.NEUTRAL));
    public static final StatusEffect FLAVORFUL = register("flavorful", new FlavorfulEffect());
    public static final StatusEffect CRAVING = register("craving", new CravingEffect());

    private static StatusEffect register(String id, StatusEffect effect){
        return Registry.register(Registries.STATUS_EFFECT, Sauces.id(id), effect);
    }

    public static void registerEffect(){
        Sauces.LOGGER.debug("Register Mod Effects");
    }
}
