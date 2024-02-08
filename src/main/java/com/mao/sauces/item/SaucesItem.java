package com.mao.sauces.item;

import com.mao.sauces.registry.ItemsRegistry;
import com.mao.sauces.registry.SoundsRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class SaucesItem extends Item {
    private final String sauces;
    private static final Map<String, StatusEffect> map = new HashMap<>();

    public SaucesItem(String sauces, StatusEffect effect) {
        super(new Item.Settings().maxDamage(64).recipeRemainder(ItemsRegistry.EMPTY_SAUCE_BOTTLE));
        this.sauces = sauces;
        map.put(sauces, effect);
    }

    public void spreadSauces(ItemStack stack, PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        World world = player.getWorld();

        nbt.putString("sauces", sauces);
        stack.setNbt(nbt);

        world.playSound(player.getX(), player.getY(), player.getZ(), SoundsRegistry.SPREAD, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
    }

    public static StatusEffect getEffect(ItemStack stack) {
        assert stack.getNbt() != null;
        return map.get(stack.getNbt().getString("sauces"));
    }

    public static String getSauces(ItemStack stack) {
        assert stack.getNbt() != null;
        return stack.getNbt().getString("sauces");
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.packRgb(255, 250, 250);
    }
}
