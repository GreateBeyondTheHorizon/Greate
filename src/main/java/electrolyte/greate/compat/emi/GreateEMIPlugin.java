package electrolyte.greate.compat.emi;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.integration.recipeviewer.emi.recipe.GTRecipeEMICategory;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.emi.category.MechanicalSawEmiRecipe;
import electrolyte.greate.compat.emi.category.MillstoneEmiRecipe;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;

@EmiEntrypoint
public class GreateEMIPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(MillstoneEmiRecipe.MILLSTONE_CATEGORY);
        GreateRecipeTypes.MILLSTONE.getRecipesInCategory(GreateRecipeTypes.MILLSTONE.getCategory()).stream()
                .map(MillstoneEmiRecipe::new)
                .forEach(registry::addRecipe);

        registry.addCategory(MechanicalSawEmiRecipe.CUTTING_CATEGORY);
        GreateRecipeTypes.SAW.getRecipesInCategory(GreateRecipeTypes.SAW.getCategory()).stream()
                .map(MechanicalSawEmiRecipe::new)
                .forEach(registry::addRecipe);

        registerWorkstations(registry);
    }

    private static void registerWorkstations(EmiRegistry registry) {
        for(MachineDefinition machine : GTRegistries.MACHINES
                .values()
                .stream()
                .filter(p -> p.getId().getNamespace().equals(Greate.MOD_ID))
                .sorted(GTRecipeEMICategory.sortDefinition)
                .toList()) {
            for(GTRecipeType type : machine.getRecipeTypes()) {
                if(type.equals(GreateRecipeTypes.MILLSTONE)) {
                    registry.addWorkstation(MillstoneEmiRecipe.MILLSTONE_CATEGORY, EmiStack.of(machine.asStack()));
                } else if(type.equals(GreateRecipeTypes.SAW)) {
                    registry.addWorkstation(MechanicalSawEmiRecipe.CUTTING_CATEGORY, EmiStack.of(machine.asStack()));
                }
            }
        }
    }
}
