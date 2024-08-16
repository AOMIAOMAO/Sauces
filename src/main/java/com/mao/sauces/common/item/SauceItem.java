package com.mao.sauces.common.item;

import com.mao.sauces.common.util.ModSauces;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SauceItem extends Item {
    public ModSauces sauces;

    public SauceItem(ModSauces sauces) {
        super(new Settings().recipeRemainder(Items.GLASS_BOTTLE).maxDamage(64));
        this.sauces = sauces;
    }

    public ModSauces getSauces() {
        return sauces;
    }

    public void spread(ItemStack stack, PlayerEntity player, ItemStack stackInHand, BlockPos pos){
        stack.getOrCreateNbt().putString("sauces", getSauces().name());
        player.playSound(SoundEvents.ENTITY_SLIME_JUMP, 0.7f, 1.0f);
        stackInHand.damage(1, player, p-> p.sendToolBreakStatus(player.getActiveHand()));
        spawnItemParticles(player.getWorld(), pos, stack, 8);
    }

    public boolean canSpread(ItemStack storedStack){
        if (storedStack.isEmpty()) return false;
        if (!storedStack.isFood()) return false;
        return storedStack.getNbt() == null || !storedStack.getNbt().contains("sauces");
    }

    public static void spawnItemParticles(World worldIn, BlockPos pos, ItemStack stack, int count) {
        for (int i = 0; i < count; ++i) {
            Vec3d vec3d = new Vec3d(((double) worldIn.getRandom().nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double) worldIn.getRandom().nextFloat() - 0.5) * 0.1);
            if (worldIn instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), (float) pos.getX() + 0.5F, (float) pos.getY() + 0.1F, (float) pos.getZ() + 0.5F, 1, vec3d.x, vec3d.y + 0.05, vec3d.z, 0.0);
            } else {
                worldIn.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), (float) pos.getX() + 0.5F, (float) pos.getY() + 0.1F, (float) pos.getZ() + 0.5F, vec3d.x, vec3d.y + 0.05, vec3d.z);
            }
        }
    }
}
