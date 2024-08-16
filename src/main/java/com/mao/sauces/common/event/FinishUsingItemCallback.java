package com.mao.sauces.common.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface FinishUsingItemCallback {
    Event<FinishUsingItemCallback> EVENT = EventFactory.createArrayBacked(FinishUsingItemCallback.class, listeners -> (world, stack, entity) -> {
        for (FinishUsingItemCallback listener : listeners) {
            listener.finishUsing(world, stack, entity);
        }

        return stack;
    });

    ItemStack finishUsing(World world, ItemStack stack, LivingEntity entity);
}
