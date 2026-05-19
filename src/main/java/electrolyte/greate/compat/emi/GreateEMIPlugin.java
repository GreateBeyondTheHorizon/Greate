package electrolyte.greate.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;

import java.util.Arrays;

@EmiEntrypoint
public class GreateEMIPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(MillstoneEmiRecipe.MILLSTONE_CATEGORY);
        Arrays.stream(GreateMachines.MILLSTONES).forEach(millstone ->
                registry.addWorkstation(MillstoneEmiRecipe.MILLSTONE_CATEGORY, EmiStack.of(millstone.asStack())));
        GreateRecipeTypes.MILLSTONE.getRecipesInCategory(GreateRecipeTypes.MILLSTONE.getCategory()).stream()
                .map(MillstoneEmiRecipe::new)
                .forEach(registry::addRecipe);
    }
}
