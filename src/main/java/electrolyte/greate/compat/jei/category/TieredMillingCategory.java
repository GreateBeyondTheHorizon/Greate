package electrolyte.greate.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedMillstone;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredMillingCategory extends GreateRecipeCategory<TieredAbstractCrushingRecipe> {

    private final AnimatedMillstone millstone = new AnimatedMillstone();

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
    public void draw(TieredAbstractCrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        AllGuiTextures.JEI_ARROW.render(stack, 85, 32);
        AllGuiTextures.JEI_DOWN_ARROW.render(stack, 43, 4);
        millstone.draw(stack, 48, 27);
    }
}
