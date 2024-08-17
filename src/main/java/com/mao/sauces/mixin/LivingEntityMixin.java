package com.mao.sauces.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mao.sauces.registry.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F", remap = false), method = "travel")
    public float effect(Block instance, Operation<Float> original) {
        float ans = original.call(instance);
        if(hasStatusEffect(ModEffects.SILKY)){
            return 0.98F;
        }
        return ans;
    }
}
