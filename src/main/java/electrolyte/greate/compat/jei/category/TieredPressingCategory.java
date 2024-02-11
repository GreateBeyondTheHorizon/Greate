package electrolyte.greate.compat.jei.category;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import electrolyte.greate.compat.jei.category.animations.TieredAnimatedMechanicalPress;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingOutput;
import electrolyte.greate.registry.MechanicalPresses;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredPressingCategory extends GreateRecipeCategory<TieredPressingRecipe> {

    public TieredPressingCategory(Info<TieredPressingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TieredPressingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 51)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));

        List<ProcessingOutput> results = recipe.getRollableResults();
        int i = 0;
        for(ProcessingOutput output : results) {
            IRecipeSlotBuilder baseBuilder = builder.addSlot(RecipeIngredientRole.OUTPUT, 131 + 19 * i, 50)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack());
            if(output instanceof TieredProcessingOutput tieredProcessingOutput) {
                baseBuilder.addTooltipCallback(addStochasticTooltipWithExtraPercent(tieredProcessingOutput, tieredProcessingOutput.getExtraTierChance()));
            } else {
                baseBuilder.addTooltipCallback(addStochasticTooltip(output));
            }
            i++;
        }

        ItemStack circuitStack = getCircuitStack(recipe);
        if(!circuitStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, getBackground().getWidth() / 2 - 37, 2)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addItemStack(circuitStack);
        }
    }

    @Override
    public void draw(TieredPressingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        super.draw(recipe, recipeSlotsView, graphics, 1, 75);
        AllGuiTextures.JEI_SHADOW.render(graphics, 61, 41);
        AllGuiTextures.JEI_LONG_ARROW.render(graphics, 52, 54);
        new TieredAnimatedMechanicalPress(MechanicalPresses.MECHANICAL_PRESSES[recipe.getRecipeTier()].get(), false).draw(graphics, getBackground().getWidth() / 2 - 17, 22);
    }
}
