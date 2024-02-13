package electrolyte.greate.compat.jei.category;

import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedMechanicalPress;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.registry.MechanicalPresses;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.getResultItem;

@ParametersAreNonnullByDefault
public class TieredPackingCategory extends TieredBasinCategory {

    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();
    private final PackingType packingType;

    protected enum PackingType {
        COMPACTING, AUTO_SQUARE
    }

    public static TieredPackingCategory standard(Info<TieredBasinRecipe> info) {
        return new TieredPackingCategory(info, PackingType.COMPACTING);
    }

    public static TieredPackingCategory autoSquare(Info<TieredBasinRecipe> info) {
        return new TieredPackingCategory(info, PackingType.AUTO_SQUARE);
    }

    protected TieredPackingCategory(Info<TieredBasinRecipe> info, PackingType packingType) {
        super(info, packingType != PackingType.AUTO_SQUARE);
        this.packingType = packingType;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TieredBasinRecipe recipe, IFocusGroup focuses) {
        if(packingType == PackingType.COMPACTING) {
            super.setRecipe(builder, recipe, focuses);
            return;
        }

        int i = 0;
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int size = ingredients.size();
        int rows = size == 4 ? 2 : 3;
        while(i < size) {
            Ingredient ingredient = ingredients.get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, (rows == 2 ? 27 : 18) + (i % rows) * 19, 51 - (i / rows) * 19)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addIngredients(ingredient);
            i++;
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 142, 51)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStack(getResultItem(recipe));
    }

    @Override
    public void draw(TieredBasinRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        boolean type = packingType == PackingType.AUTO_SQUARE;
        double yOffset = type ? 90 : 108;
        super.draw(recipe, recipeSlotsView, graphics, x, yOffset);
        if(this.packingType != PackingType.COMPACTING) {
            AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 136, 32);
            AllGuiTextures.JEI_SHADOW.render(graphics, 81, 68);
        }

        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if(requiredHeat != HeatCondition.NONE) {
            heater.withHeat(requiredHeat.visualizeAsBlazeBurner()).draw(graphics, getBackground().getWidth() / 2 + 3, 55);
        }
        new TieredAnimatedMechanicalPress(MechanicalPresses.MECHANICAL_PRESSES[recipe.getRecipeTier()].get(), true).draw(graphics, getBackground().getWidth() / 2 + 3, 34);
    }
}
