package com.mao.sauces.item;

import com.mao.sauces.registry.SoundsRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SaucesItem extends Item {
    private final String sauces;

    public SaucesItem(String sauces) {
        super(new Item.Settings().maxDamage(25).recipeRemainder(Items.GLASS_BOTTLE));
        this.sauces = sauces;
    }

    public void spreadSauces(ItemStack stack, PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        World world = player.getWorld();

        nbt.putString("sauces", sauces);
        stack.setNbt(nbt);

        world.playSound(player.getX(), player.getY(), player.getZ(), SoundsRegistry.SPREAD, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
    }

    public static String getSauces(ItemStack stack) {
        assert stack.getNbt() != null;
        return stack.getNbt().getString("sauces");
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.packRgb(210, 105, 30);
    }
}
