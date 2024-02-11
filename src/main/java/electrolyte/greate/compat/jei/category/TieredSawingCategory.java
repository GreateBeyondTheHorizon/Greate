package electrolyte.greate.compat.jei.category;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import electrolyte.greate.compat.jei.category.animations.TieredAnimatedSaw;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingOutput;
import electrolyte.greate.registry.Saws;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredSawingCategory extends GreateRecipeCategory<TieredCuttingRecipe> {

    public TieredSawingCategory(Info<TieredCuttingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TieredCuttingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 5)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));

        if(!recipe.getFluidIngredients().isEmpty()) {
            FluidIngredient ing = recipe.getFluidIngredients().get(0);
            builder.addSlot(RecipeIngredientRole.INPUT, 28, 48)
                    .setBackground(getRenderedSlot(), - 1, - 1)
                    .addIngredients(ForgeTypes.FLUID_STACK, withImprovedVisibility(ing.getMatchingFluidStacks()))
                    .addTooltipCallback(addFluidTooltip(ing.getRequiredAmount()));
        }

        List<ProcessingOutput> results = recipe.getRollableResults();
        int i = 0;
        for(ProcessingOutput output : results) {
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2) * -19;
            IRecipeSlotBuilder baseBuilder = builder.addSlot(RecipeIngredientRole.OUTPUT, 118 + xOffset, 48 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack());
            if(output instanceof TieredProcessingOutput tieredProcessingOutput) {
                baseBuilder.addTooltipCallback(addStochasticTooltipWithExtraPercent(tieredProcessingOutput, tieredProcessingOutput.getExtraTierChance()));
            } else {
                baseBuilder.addTooltipCallback(addStochasticTooltip(output));
            }
            i++;
        }
    }

    @Override
    public void draw(TieredCuttingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        super.draw(recipe, recipeSlotsView, graphics, 1, 75);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 70, 6);
        AllGuiTextures.JEI_SHADOW.render(graphics, 72 - 17, 42 + 13);
        new TieredAnimatedSaw(Saws.SAWS[recipe.getRecipeTier()].get()).draw(graphics, 72, 42);
    }
}
