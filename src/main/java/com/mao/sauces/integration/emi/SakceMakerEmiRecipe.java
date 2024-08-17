package com.mao.sauces.integration.emi;

import com.mao.sauces.Sauces;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.AnimatedTextureWidget;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SakceMakerEmiRecipe implements EmiRecipe {
    public static final Identifier BG = new Identifier(Sauces.MODID, "textures/gui/sauce_maker.png");

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final EmiStack output;
    private final int makingTime;
    private final EmiStack container;

    public SakceMakerEmiRecipe(Identifier id, List<EmiIngredient> input, EmiStack output, int makingTime, EmiStack container) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.makingTime = makingTime;
        this.container = container;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return SaucesEmiPlugin.SAUCE_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 110;
    }

    @Override
    public int getDisplayHeight() {
        return 50;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BG, 0, 0, 105, 50, 6, 6);
        addSlot(widgets, input.get(0), 6, 5);
        addSlot(widgets, input.get(1), 25, 5);
        addSlot(widgets, input.get(2), 6, 24);
        addSlot(widgets, container, 25, 24);

        AnimatedTextureWidget widget = widgets.addAnimatedTexture(BG, 49, 17, 22, 16, 0, 58, 1000 * 10, true, false, false);
        widgets.addText(Text.translatable("sauces.emi.makingtime", makingTime / 20), widget.getBounds().x(), widget.getBounds().y() - 8, Formatting.WHITE.getColorValue(), true);
        addSlot(widgets, output, 76, 15).recipeContext(this);
    }

    private SlotWidget addSlot(WidgetHolder widgets, EmiIngredient ingredient, int x, int y) {
        return widgets.addSlot(ingredient, x, y).drawBack(false);
    }
}
