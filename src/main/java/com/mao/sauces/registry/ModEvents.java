package com.mao.sauces.registry;

import com.mao.sauces.common.event.FinishUsingItemCallback;
import com.mao.sauces.common.util.ModSauces;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class ModEvents {

    public static void registerEvents(){
        FinishUsingItemCallback.EVENT.register((world, stack, entity) -> {
            ModSauces sauces = ModSauces.matching(stack);
            if (sauces != null){
                Random random = new Random();
                entity.addStatusEffect(new StatusEffectInstance(sauces.getEffect(), random.nextInt(20 * 90, 20 * 120), random.nextInt(0 ,1)));
            }
            return stack;
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getNbt() == null) return ActionResult.PASS;
            if (world.getBlockState(pos).isOf(Blocks.WATER_CAULDRON)){
                if (stack.getNbt().contains("sauces")){
                    stack.getNbt().put("sauces", null);
                    return ActionResult.success(world.isClient());
                }
            }
            return ActionResult.PASS;
        });
    }

    public static void registerClientEvents(){
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            ModSauces sauces = ModSauces.matching(stack);
            if (sauces != null){
                MutableText text = Text.translatable("item.sauces.food.tooltip").formatted(Formatting.GOLD).append(Text.translatable("item.sauces." + sauces.getName()).formatted(sauces.getColor()));
                MutableText effectText = Text.translatable("item.sauces.food.effect.tooltip").formatted(Formatting.BLUE).append(sauces.getEffect().getTranslationKey()).formatted(Formatting.BLUE);
                lines.add(1, text);
                lines.add(2, effectText);
            }
        });
    }
}
