package com.mao.sauces.recipe;

import com.mao.sauces.block.blockentity.SauceMakerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SauceMakerRecipe implements Recipe<SauceMakerBlockEntity> {
    protected final Ingredient ingredient;
    protected final ItemStack bottle;
    protected final ItemStack output;
    protected final Identifier id;
    protected final int processtime;
    protected final int inputCount;

    public SauceMakerRecipe(Ingredient ingredient, ItemStack bottle, ItemStack output, Identifier id, int processtime, int inputCount) {
        this.ingredient = ingredient;
        this.output = output;
        this.id = id;
        this.processtime = processtime;
        this.inputCount = inputCount;
        if (!bottle.isEmpty()) {
            this.bottle = bottle;
        } else if (output.getItem().getRecipeRemainder() != null) {
            this.bottle = new ItemStack(output.getItem().getRecipeRemainder());
        } else {
            this.bottle = ItemStack.EMPTY;
        }
    }

    public int getProcesstime() {
        return processtime;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getInputCount() {
        return inputCount;
    }

    public ItemStack getBottle() {
        return bottle;
    }

    @Override
    public boolean matches(SauceMakerBlockEntity inventory, World world) {
        return this.ingredient.test(inventory.getStack(0)) && bottle.isOf(inventory.getBottleItem().getItem()) && inventory.getStack(0).getCount() == inputCount;
    }

    @Override
    public ItemStack craft(SauceMakerBlockEntity inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(registryManager).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.ingredient);
        return list;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SauceMakerRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SauceMakerRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
    }
}
