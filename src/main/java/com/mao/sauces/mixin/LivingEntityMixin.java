package com.mao.sauces.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mao.sauces.common.util.ModSauces;
import com.mao.sauces.registry.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
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

    @Inject(at = @At("TAIL"), method = "applyFoodEffects")
    private void effect(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci){
        Item item = stack.getItem();
        ModSauces sauces = ModSauces.matching(stack);
        if (item.isFood() && sauces != null){
            Random random = new Random();
            int duration = random.nextInt(20 * 90, 20 * 120);
            int amplifier = random.nextInt(0, 1);
            if (targetEntity instanceof PlayerEntity player) player.getHungerManager().add(1, 0.5f);
            targetEntity.addStatusEffect(new StatusEffectInstance(sauces.getEffect(), duration, amplifier));
        }
    }
}
