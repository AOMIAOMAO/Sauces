package com.mao.sauces;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.concurrent.CompletableFuture;

public class SauceDataGen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(Tag::new);
    }

    protected static class ItemModels extends FabricModelProvider {
        public ItemModels(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        }
    }

    protected static class Lang extends FabricLanguageProvider {
        protected Lang(FabricDataOutput dataOutput) {
            super(dataOutput, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {

        }
    }

    protected static class Tag extends TagProvider<PointOfInterestType>{

        protected Tag(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
            super(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registryLookupFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
                    .addOptional(Sauces.id("sauce_chef_poi"));
        }
    }
}
