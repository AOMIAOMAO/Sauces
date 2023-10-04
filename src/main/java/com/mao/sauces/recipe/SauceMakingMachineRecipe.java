package com.mao.sauces.recipe;

import com.mao.sauces.block.blockentity.SauceMakingMachineBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SauceMakingMachineRecipe implements Recipe<SauceMakingMachineBlockEntity> {
    protected final Ingredient ingredient;
    protected final Ingredient bottle;
    protected final ItemStack output;
    protected final Identifier id;
    protected final int processtime;
    protected final int inputCount;

    public SauceMakingMachineRecipe(Ingredient ingredient, Ingredient bottle, ItemStack output, Identifier id, int processtime, int inputCount) {
        this.ingredient = ingredient;
        this.bottle = bottle;
        this.output = output;
        this.id = id;
        this.processtime = processtime;
        this.inputCount = inputCount;
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

    @Override
    public boolean matches(SauceMakingMachineBlockEntity inventory, World world) {
        return this.ingredient.test(inventory.getStack(0)) && this.bottle.test(inventory.getBottleItem()) && inventory.getStack(0).getCount() == inputCount;
    }

    @Override
    public ItemStack craft(SauceMakingMachineBlockEntity inventory, DynamicRegistryManager registryManager) {
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
        list.add(this.bottle);
        return list;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SauceMakingMachineRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SauceMakingMachineRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
    }
}
