package electrolyte.greate.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedMillstone;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.registry.Millstones;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredMillingCategory extends GreateRecipeCategory<TieredAbstractCrushingRecipe> {

    public TieredMillingCategory(Info<TieredAbstractCrushingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TieredAbstractCrushingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 9)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));

        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        int i = 0;
        for(ProcessingOutput output : results) {
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2)  * -19;
            builder.addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 133 + xOffset, 27 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack())
                    .addTooltipCallback(addStochasticTooltip(output));
            i++;
        }
    }

    @Override
    public void draw(TieredAbstractCrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double x, double y) {
        super.draw(recipe, recipeSlotsView, stack, 1, 57);
        AllGuiTextures.JEI_ARROW.render(stack, 85, 32);
        AllGuiTextures.JEI_DOWN_ARROW.render(stack, 43, 4);
        new TieredAnimatedMillstone(Millstones.MILLSTONES.get(TIER.indexOfTier(recipe.getRecipeTier()))).draw(stack, 48, 27);
    }
}
