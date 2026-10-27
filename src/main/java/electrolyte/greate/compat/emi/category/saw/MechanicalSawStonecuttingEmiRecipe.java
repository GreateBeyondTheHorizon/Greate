package electrolyte.greate.compat.emi.category.saw;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;
import net.minecraft.resources.ResourceLocation;

import static com.gregtechceu.gtceu.api.GTValues.UHV;

public class MechanicalSawStonecuttingEmiRecipe extends MechanicalSawBaseEmiRecipe {

    public static final EmiRecipeCategory STONECUTTING_CATEGORY = new EmiRecipeCategory(Greate.id("stonecutting"),
            EmiStack.of(GreateMachines.SAWS[UHV]));

    public MechanicalSawStonecuttingEmiRecipe(GTRecipe recipe) {
        super(recipe, STONECUTTING_CATEGORY);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return STONECUTTING_CATEGORY;
    }

    @Override
    public ResourceLocation getId() {
        return recipe.getId();
    }
}
