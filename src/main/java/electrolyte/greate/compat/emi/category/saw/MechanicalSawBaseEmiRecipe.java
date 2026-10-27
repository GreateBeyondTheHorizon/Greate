package electrolyte.greate.compat.emi.category.saw;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.integration.recipeviewer.emi.recipe.GTEmiRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.widget.WidgetHolder;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedSaw;

public class MechanicalSawBaseEmiRecipe extends GTEmiRecipe {

    protected GTRecipe recipe;
    public MechanicalSawBaseEmiRecipe(GTRecipe recipe, EmiRecipeCategory category) {
        super(recipe, category);
        this.recipe = recipe;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);

        int xOffset = recipe.getType().getMaxInputs(ItemRecipeCapability.CAP) * 18;

        widgets.addDrawable(20 + xOffset, 30, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            int recipeTier = GreateValues.convertStressToTier(recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY));
            new TieredAnimatedSaw(GreateMachines.SAWS[recipeTier].get()).draw(graphics, 0,0);
        });

        widgets.addDrawable(57 + xOffset, 30, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            AllGuiTextures.JEI_ARROW.render(graphics, 0, 0);
        });

        widgets.addDrawable(15 + xOffset, 7, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 0, 0);
        });
    }
}
