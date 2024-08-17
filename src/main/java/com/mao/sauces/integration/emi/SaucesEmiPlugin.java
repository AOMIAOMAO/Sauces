package com.mao.sauces.integration.emi;

import com.mao.sauces.Sauces;
import com.mao.sauces.common.recipe.SauceMakerRecipe;
import com.mao.sauces.registry.ModItems;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.util.Identifier;

public class SaucesEmiPlugin implements EmiPlugin {
    public static final Identifier MY_SPRITE_SHEET = Sauces.id("textures/gui/simplified_textures.png");
    public static final EmiStack MY_WORKSTATION = EmiStack.of(ModItems.SAUCE_MAKER);
    public static final EmiRecipeCategory SAUCE_CATEGORY
            = new EmiRecipeCategory(Sauces.id("sauce_maker"), MY_WORKSTATION, simplifiedRenderer(0, 0));


    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(SAUCE_CATEGORY);
        registry.addWorkstation(SAUCE_CATEGORY, MY_WORKSTATION);

        for (SauceMakerRecipe recipe : registry.getRecipeManager().listAllOfType(SauceMakerRecipe.Type.INSTANCE)) {
            registry.addRecipe(new SakceMakerEmiRecipe(recipe.getId(), recipe.getIngredients().stream().map(EmiIngredient::of).toList(), EmiStack.of(recipe.getOutput(null)), recipe.getMakingTime(), EmiStack.of(recipe.getContainer())));
        }
    }

    private static EmiRenderable simplifiedRenderer(int u, int v) {
        return (draw, x, y, delta) -> draw.drawTexture(MY_SPRITE_SHEET, x, y, u, v, 16, 16, 16, 16);
    }
}
