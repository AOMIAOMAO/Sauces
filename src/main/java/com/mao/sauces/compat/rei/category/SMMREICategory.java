package com.mao.sauces.compat.rei.category;

import com.mao.sauces.Sauces;
import com.mao.sauces.compat.rei.SaucesREIPlugin;
import com.mao.sauces.compat.rei.display.SMMREIDisplay;
import com.mao.sauces.registry.BlocksRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SMMREICategory implements DisplayCategory<SMMREIDisplay> {
    private static final Identifier GUI_TEXTURE = new Identifier(Sauces.MOD_ID, "textures/gui/sauce_maker_rei.png");


    @Override
    public CategoryIdentifier<? extends SMMREIDisplay> getCategoryIdentifier() {
        return SaucesREIPlugin.SMMREI_DISPLAY_CATEGORY;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(BlocksRegistry.SAUCE_MAKER_BLOCK.getTranslationKey());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(BlocksRegistry.SAUCE_MAKER_BLOCK);
    }

    @Override
    public List<Widget> setupDisplay(SMMREIDisplay display, Rectangle bounds) {
        Point origin = bounds.getLocation();
        final List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));
        Rectangle bgBounds = centeredIntoRecipeBase(new Point(origin.x, origin.y), 96, 57);

        widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, new Rectangle(bgBounds.x, bgBounds.y, 96, 57), 6, 8));

        widgets.add(Widgets.createSlot(new Point(bgBounds.x + 8, bgBounds.y + 2))
                .entries(display.getInputEntries().get(1)).markInput().disableBackground());
        widgets.add(Widgets.createSlot(new Point(bgBounds.x + 78, bgBounds.y + 19))
                .entries(display.getOutputEntries().get(0)).markOutput().disableBackground());
        Slot slot = Widgets.createSlot(new Point(bgBounds.x + 29, bgBounds.y + 22))
                .entries(display.getInputEntries().get(0)).markInput().disableBackground();
        widgets.add(slot);

        Arrow arrow = Widgets.createArrow(new Point(bgBounds.x + 63 - 15, bgBounds.y + 19)).animationDurationTicks(display.getProcesstime());
        widgets.add(arrow);

        widgets.add(Widgets.withTooltip(arrow, Text.translatable("tip.sauces.sauce_making_machine.processtime", display.getProcesstime() / 20)));
        widgets.add(Widgets.withTooltip(slot, Text.translatable("tip.sauces.sauce_making_machine.input_count", display.getInputCount())));

        return widgets;
    }

    public static Rectangle centeredIntoRecipeBase(Point origin, int width, int height) {
        return centeredInto(new Rectangle(origin.x, origin.y, 150, 74), width, height);
    }

    public static Rectangle centeredInto(Rectangle origin, int width, int height) {
        return new Rectangle(origin.x + (origin.width - width) / 2, origin.y + (origin.height - height) / 2, width, height);
    }

    @Override
    public int getDisplayHeight() {
        return 73;
    }
}
