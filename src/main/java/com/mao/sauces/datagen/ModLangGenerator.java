package com.mao.sauces.datagen;

import com.mao.sauces.registry.ItemsRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLangGenerator extends FabricLanguageProvider {
    protected ModLangGenerator(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemsRegistry.EMPTY_SAUCE_BOTTLE, "Empty Sauce Bottle");
        translationBuilder.add(ItemsRegistry.KETCHUP, "Ketchup");
    }
}
