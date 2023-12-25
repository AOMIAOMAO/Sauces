package com.mao.sauces.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import static net.minecraft.recipe.ShapedRecipe.outputFromJson;

public class SauceMakingMachineRecipeSerializer implements RecipeSerializer<SauceMakingMachineRecipe> {
    private SauceMakingMachineRecipeSerializer() {
    }

    public static final SauceMakingMachineRecipeSerializer INSTANCE = new SauceMakingMachineRecipeSerializer();


    @Override
    public SauceMakingMachineRecipe read(Identifier id, JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));

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

        int processtime = json.get("processtime").getAsInt();
        int inputCount = json.get("inputCount").getAsInt();

        return new SauceMakingMachineRecipe(ingredient, container, output, id, processtime, inputCount);
    }

    @Override
    public SauceMakingMachineRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient ingredient = Ingredient.fromPacket(buf);
        ItemStack bottle = buf.readItemStack();
        ItemStack output = buf.readItemStack();
        int processtime = buf.readInt();
        int inputCount = buf.readInt();

        return new SauceMakingMachineRecipe(ingredient, bottle, output, id, processtime, inputCount);
    }

    @Override
    public void write(PacketByteBuf buf, SauceMakingMachineRecipe recipe) {
        recipe.ingredient.write(buf);
        buf.writeItemStack(recipe.getBottle());
        buf.writeItemStack(recipe.getOutput(null));
        buf.writeInt(recipe.getProcesstime());
        buf.writeInt(recipe.getInputCount());
    }
}
