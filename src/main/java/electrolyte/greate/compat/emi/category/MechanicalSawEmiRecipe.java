package electrolyte.greate.compat.emi.category;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.integration.recipeviewer.emi.recipe.GTEmiRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedSaw;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static com.gregtechceu.gtceu.api.GTValues.UHV;

public class MechanicalSawEmiRecipe extends GTEmiRecipe {

    private final GTRecipe recipe;
    public static final EmiRecipeCategory CUTTING_CATEGORY = new EmiRecipeCategory(Greate.id("cutting"),
            EmiStack.of(GreateMachines.SAWS[UHV].asStack()));

    public MechanicalSawEmiRecipe(GTRecipe recipe) {
        super(recipe, CUTTING_CATEGORY);
        this.recipe = recipe;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CUTTING_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);

        widgets.addDrawable(38, 30, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            int recipeTier = GreateValues.convertStressToTier(recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY));
            new TieredAnimatedSaw(GreateMachines.SAWS[recipeTier].get()).draw(graphics, 0,0);
        });

        widgets.addDrawable(75, 30, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            AllGuiTextures.JEI_ARROW.render(graphics, 0, 0);
        });

        widgets.addDrawable(33, 7, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 0, 0);
        });
    }
}
