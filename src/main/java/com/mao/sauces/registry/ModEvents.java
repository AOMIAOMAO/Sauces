package com.mao.sauces.registry;

import com.mao.sauces.common.event.FinishUsingItemCallback;
import com.mao.sauces.common.util.ModSauces;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class ModEvents {

    public static void registerEvents(){
        FinishUsingItemCallback.EVENT.register((world, stack, entity) -> {
            ModSauces sauces = ModSauces.matching(stack);
            if (sauces != null){
               sauces.eatFood(entity);
            }
            return stack;
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getStackInHand(hand);
            if (world.getBlockState(pos).isOf(Blocks.WATER_CAULDRON) || world.getBlockState(pos).isOf(Blocks.WATER)){
                if (stack.getNbt() != null && stack.getNbt().contains("sauces")){
                    stack.getNbt().remove("sauces");
                    world.playSound(null, pos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.BLOCKS);
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
                MutableText text = Text.translatable("item.sauces.food.sauces.tooltip").formatted(Formatting.GOLD).append(Text.translatable("item.sauces." + sauces.getName()).formatted(sauces.getColor()));
                MutableText effectText = Text.translatable("item.sauces.food.effect.tooltip").formatted(Formatting.BLUE).append(Text.translatable(sauces.getEffect().getTranslationKey())).formatted(Formatting.BLUE);
                MutableText hungerText = Text.translatable("item.sauces.food.hunger.tooltip").formatted(Formatting.BLUE);
                lines.add(1, text);
                lines.add(2, effectText);
                lines.add(3, hungerText);
            }
        });
    }
}
