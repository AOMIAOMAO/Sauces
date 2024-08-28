package com.mao.sauces.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.recipe.ShapedRecipe.outputFromJson;

public class SauceMakerRecipe implements Recipe<Inventory> {
    public static final int INPUT_SIZE = 3;

    private final Identifier id;
    private final DefaultedList<Ingredient> input;
    private final ItemStack container;
    private final int makingTime;
    private final ItemStack result;

    public SauceMakerRecipe(Identifier id, DefaultedList<Ingredient> input, ItemStack container, int cookingTime, ItemStack result) {
        this.id = id;
        this.input = input;

        if (!container.isEmpty()) {
            this.container = container;
        } else if (result.getItem().getRecipeRemainder() != null) {
            this.container = new ItemStack(result.getItem().getRecipeRemainder());
        } else {
            this.container = ItemStack.EMPTY;
        }
        this.makingTime = cookingTime;
        this.result = result;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for (int j = 0; j < INPUT_SIZE; ++j) {
            ItemStack itemstack = inventory.getStack(j);
            if (!itemstack.isEmpty()) {
                ++i;
                recipeMatcher.addInput(itemstack);
            }
        }
        return i == this.input.size() && recipeMatcher.match(this, null) && ItemStack.areItemsEqual(inventory.getStack(3), getContainer());
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> result = DefaultedList.of();
        result.addAll(input);
        return result;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.result.copy();
    }


    public ItemStack getContainer() {
        return container.copy();
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public int getMakingTime() {
        return makingTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SauceMakerRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<SauceMakerRecipe> {
        public static final Serializer INSTANCE = new Serializer();


        @Override
        public SauceMakerRecipe read(Identifier id, JsonObject json) {
            DefaultedList<Ingredient> ingredients = itemsFromJson(JsonHelper.getArray(json, "ingredients"));
            int cookingTime = json.get("makingtime").getAsInt();

            ItemStack output;
            JsonElement element = json.get("result");
            if (element.isJsonObject())
                output = outputFromJson((JsonObject) element);
            else {
                String string = element.getAsString();
                Item item = Registries.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
                output = new ItemStack(item);
            }

            ItemStack container = ItemStack.EMPTY;
            if (JsonHelper.hasElement(json, "container")) {
                JsonObject jsonContainer = JsonHelper.getObject(json, "container");
                container = new ItemStack(JsonHelper.getItem(jsonContainer, "item"), JsonHelper.getInt(jsonContainer, "count", 1));
            }

            return new SauceMakerRecipe(id, ingredients, container, cookingTime, output);
        }

        private static DefaultedList<Ingredient> itemsFromJson(JsonArray jsonArray) {
            DefaultedList<Ingredient> ingredients = DefaultedList.of();
            for (int i = 0; i < jsonArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(jsonArray.get(i));
                ingredients.add(ingredient);
            }
            return ingredients;
        }

        @Override
        public SauceMakerRecipe read(Identifier id, PacketByteBuf buf) {
            int i = buf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(i, Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromPacket(buf));
            int cookingTime = buf.readInt();
            ItemStack result = buf.readItemStack();
            ItemStack container = buf.readItemStack();
            return new SauceMakerRecipe(id, ingredients, container, cookingTime, result);
        }

        @Override
        public void write(PacketByteBuf buf, SauceMakerRecipe recipe) {
            buf.writeVarInt(recipe.input.size());
            for (Ingredient ingredient : recipe.input) {
                ingredient.write(buf);
            }
            buf.writeInt(recipe.getMakingTime());
            buf.writeItemStack(recipe.getOutput(null));
            buf.writeItemStack(recipe.getContainer());
        }
    }
}
