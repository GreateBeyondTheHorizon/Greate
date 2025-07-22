package electrolyte.greate.compat.jei.category;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedSaw;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.registry.Saws;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredSawingCategory extends GreateRecipeCategory<TieredCuttingRecipe> {

    public TieredSawingCategory(Info<TieredCuttingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<TieredCuttingRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 5)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.value().getIngredients().get(0));

        if(!recipe.value().getFluidIngredients().isEmpty()) {
            SizedFluidIngredient ing = recipe.value().getFluidIngredients().get(0);
            int x = 28;
            int y = 48;
            CreateRecipeCategory.addFluidSlot(builder, x, y, ing);
        }

        List<ProcessingOutput> results = recipe.value().getRollableResults();
        int i = 0;
        for(ProcessingOutput output : results) {
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2) * -19;
            builder.addSlot(RecipeIngredientRole.OUTPUT, 118 + xOffset, 48 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack())
                    .addRichTooltipCallback(CreateRecipeCategory.addStochasticTooltip(output));
            i++;
        }
    }

    @Override
    public void draw(RecipeHolder<TieredCuttingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        super.draw(recipe, recipeSlotsView, graphics, 1, 75);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 70, 6);
        AllGuiTextures.JEI_SHADOW.render(graphics, 72 - 17, 42 + 13);
        new TieredAnimatedSaw(Saws.SAWS[recipe.value().getRecipeTier()].get()).draw(graphics, 72, 42);
    }
}
