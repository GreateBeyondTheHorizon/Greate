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
import electrolyte.greate.compat.emi.category.MillingEmiRecipe;
import electrolyte.greate.compat.emi.category.saw.CuttingEmiRecipe;
import electrolyte.greate.compat.emi.category.saw.StonecuttingEmiRecipe;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;

@EmiEntrypoint
public class GreateEMIPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(MillingEmiRecipe.MILLING_CATEGORY);
        GreateRecipeTypes.MILLING.getRecipesInCategory(GreateRecipeTypes.MILLING.getCategory()).stream()
                .map(MillingEmiRecipe::new)
                .forEach(registry::addRecipe);

        registry.addCategory(CuttingEmiRecipe.CUTTING_CATEGORY);
        GreateRecipeTypes.CUTTING.getRecipesInCategory(GreateRecipeTypes.CUTTING.getCategory()).stream()
                .map(CuttingEmiRecipe::new)
                .forEach(registry::addRecipe);

        registry.addCategory(StonecuttingEmiRecipe.STONECUTTING_CATEGORY);
        GreateRecipeTypes.KINETIC_STONECUTTING.getRecipesInCategory(GreateRecipeTypes.KINETIC_STONECUTTING.getCategory()).stream()
                .map(StonecuttingEmiRecipe::new)
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
                if(type.equals(GreateRecipeTypes.MILLING)) {
                    registry.addWorkstation(MillingEmiRecipe.MILLING_CATEGORY, EmiStack.of(machine.asStack()));
                } else if(type.equals(GreateRecipeTypes.CUTTING)) {
                    registry.addWorkstation(CuttingEmiRecipe.CUTTING_CATEGORY, EmiStack.of(machine.asStack()));
                } else if(type.equals(GreateRecipeTypes.KINETIC_STONECUTTING)) {
                    registry.addWorkstation(StonecuttingEmiRecipe.STONECUTTING_CATEGORY, EmiStack.of(machine.asStack()));
                }
            }
        }
    }
}
