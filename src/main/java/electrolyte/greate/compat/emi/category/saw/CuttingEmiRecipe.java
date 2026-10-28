package electrolyte.greate.compat.emi.category.saw;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static com.gregtechceu.gtceu.api.GTValues.UHV;

public class CuttingEmiRecipe extends SawBaseEmiRecipe {

    public static final EmiRecipeCategory CUTTING_CATEGORY = new EmiRecipeCategory(Greate.id("cutting"),
            EmiStack.of(GreateMachines.SAWS[UHV].asStack()));

    public CuttingEmiRecipe(GTRecipe recipe) {
        super(recipe, CUTTING_CATEGORY);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CUTTING_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return recipe.getId();
    }
}
