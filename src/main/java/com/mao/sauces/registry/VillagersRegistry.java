package com.mao.sauces.registry;

import com.google.common.collect.ImmutableSet;
import com.mao.sauces.Sauces;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class VillagersRegistry {
    public static final RegistryKey<PointOfInterestType> SAUCES_POI_KEY = poiKey("saucepoi");
    public static final PointOfInterestType SAUCES_POI = registerPoi("saucepoi", BlocksRegistry.SAUCE_MAKER_BLOCK);
    public static final VillagerProfession SAUCE_CHEF = registerProfession("sauce_chef", SAUCES_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Sauces.asID(name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_FISHERMAN));
    }

    private static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(Sauces.asID(name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Sauces.asID(name));
    }

    public static void registerVillagers() {
        Sauces.LOGGER.info("Registering Villagers " + Sauces.MOD_ID);
    }
}
