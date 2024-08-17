package com.mao.sauces.registry;

import com.google.common.collect.ImmutableSet;
import com.mao.sauces.Sauces;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> SAUCE_CHEF_KEY = of("sauce_chef_poi");
    public static final VillagerProfession SAUCE_CHEF = register("sauce_chef", SAUCE_CHEF_KEY, SoundEvents.ITEM_BOTTLE_EMPTY);
    public static final PointOfInterestType SAUCE_CHEF_POI = register("sauce_chef_poi", ModBlocks.SAUCE_MAKER);

    private static RegistryKey<PointOfInterestType> of(String id) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Sauces.id(id));
    }

    private static VillagerProfession register(String id, RegistryKey<PointOfInterestType> heldWorkstation, @Nullable SoundEvent workSound){
        return Registry.register(Registries.VILLAGER_PROFESSION, Sauces.id(id),
                new VillagerProfession(id, entry -> entry.matchesKey(heldWorkstation),
                        entry-> entry.matchesKey(heldWorkstation), ImmutableSet.of(), ImmutableSet.of(), workSound));
    }

    private static PointOfInterestType register(String id, Block block){
        return PointOfInterestHelper.register(Sauces.id(id), 1, 1, block);
    }

    public static void registerVillage(){
        Sauces.LOGGER.debug("Register Mod Village");
    }
}
