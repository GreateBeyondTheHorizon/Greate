package electrolyte.greate.compat.jei.category;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.item.ItemHelper;
import electrolyte.greate.compat.jei.category.TieredBlockCuttingCategory.TieredCondensedBlockCuttingRecipe;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedSaw;
import electrolyte.greate.registry.Saws;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.getResultItem;

public class TieredBlockCuttingCategory extends GreateRecipeCategory<TieredCondensedBlockCuttingRecipe> {

    public TieredBlockCuttingCategory(Info<TieredCondensedBlockCuttingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TieredCondensedBlockCuttingRecipe recipe, IFocusGroup focuses) {
        List<List<ItemStack>> results = recipe.getCondensedOutputs();
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 5)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStacks(Arrays.asList(recipe.getIngredients().get(0).getItems()));

        int i = 0;
        for(List<ItemStack> itemStacks : results) {
            int xPos = 78 + (i % 5) * 19;
            int yPos = 48 + (i / 5) * -19;
            builder.addSlot(RecipeIngredientRole.OUTPUT, xPos, yPos)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addItemStacks(itemStacks);
            i++;
        }
    }

    @Override
    public void draw(TieredCondensedBlockCuttingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 31, 6);
        AllGuiTextures.JEI_SHADOW.render(graphics, 33 - 17, 37 + 13);
        new TieredAnimatedSaw(Saws.SAWS[0].get()).draw(graphics, 33, 37);
    }

    public static class TieredCondensedBlockCuttingRecipe extends StonecutterRecipe {

        List<ItemStack> outputs = new ArrayList<>();

        public TieredCondensedBlockCuttingRecipe(Ingredient ingredient) {
            super(new ResourceLocation(""), "", ingredient, ItemStack.EMPTY);
        }

        public void addOutput(ItemStack stack) {
            outputs.add(stack);
        }

        public List<ItemStack> getOutputs() {
            return outputs;
        }

        public List<List<ItemStack>> getCondensedOutputs() {
            List<List<ItemStack>> result = new ArrayList<>();
            int index = 0;
            boolean firstPass = true;
            for(ItemStack stack : outputs) {
                if(firstPass) {
                    result.add(new ArrayList<>());
                }
                result.get(index).add(stack);
                index++;
                if(index >= 15) {
                    index = 0;
                    firstPass = false;
                }
            }
            return result;
        }

        @Override
        public boolean isSpecial() {
            return true;
        }
    }

    public static List<TieredCondensedBlockCuttingRecipe> condenseRecipes(List<Recipe<?>> stoneCuttingRecipes) {
        List<TieredCondensedBlockCuttingRecipe> condensedRecipes = new ArrayList<>();
        Recipes: for(Recipe<?> recipe : stoneCuttingRecipes) {
            Ingredient ingredient = recipe.getIngredients().get(0);
            for(TieredCondensedBlockCuttingRecipe tieredCondensedRecipe : condensedRecipes) {
                if(ItemHelper.matchIngredients(ingredient, tieredCondensedRecipe.getIngredients().get(0))) {
                    tieredCondensedRecipe.addOutput(getResultItem(recipe));
                    continue Recipes;
                }
            }

            TieredCondensedBlockCuttingRecipe tcbcr = new TieredCondensedBlockCuttingRecipe(ingredient);
            tcbcr.addOutput(getResultItem(recipe));
            condensedRecipes.add(tcbcr);
        }
        return condensedRecipes;
    }
}
