package electrolyte.greate.content.kinetics.crafter;

import com.google.gson.JsonObject;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class TieredMechanicalCrafterRecipe extends MechanicalCraftingRecipe {

    private int recipeTier; //TODO: this doesn't get checked anywhere yet.

    public TieredMechanicalCrafterRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn, boolean acceptMirrored, int recipeTier) {
        super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn, acceptMirrored);
        this.recipeTier = recipeTier;
    }

    private static TieredMechanicalCrafterRecipe fromShaped(ShapedRecipe recipe, boolean acceptsMirrored, int tier) {
        return new TieredMechanicalCrafterRecipe(recipe.getId(), recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem(null), acceptsMirrored, tier);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.MECHANICAL_CRAFTING.getSerializer();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.MECHANICAL_CRAFTING.getType();
    }

    public int getRecipeTier() {
        return recipeTier;
    }

    public static class Serializer extends ShapedRecipe.Serializer {
        @Override
        public ShapedRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            return fromShaped(super.fromJson(pRecipeId, pJson), GsonHelper.getAsBoolean(pJson,"acceptMirrored", true), GsonHelper.getAsInt(pJson, "recipeTier", 0));
        }

        @Override
        public ShapedRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return fromShaped(super.fromNetwork(pRecipeId, pBuffer), pBuffer.readBoolean() && pBuffer.readBoolean(), pBuffer.readInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ShapedRecipe pRecipe) {
            super.toNetwork(pBuffer, pRecipe);
            if(pRecipe instanceof MechanicalCraftingRecipe mcr) {
                pBuffer.writeBoolean(true);
                pBuffer.writeBoolean(mcr.acceptsMirrored());
                if(pRecipe instanceof TieredMechanicalCrafterRecipe tmcr) {
                    pBuffer.writeInt(tmcr.getRecipeTier());
                }
            } else {
                pBuffer.writeBoolean(false);
            }
        }
    }
}
