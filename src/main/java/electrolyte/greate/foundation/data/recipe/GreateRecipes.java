package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.material.material.Material;
import electrolyte.greate.Greate;
import electrolyte.greate.foundation.data.recipe.machine.*;
import net.minecraft.data.recipes.RecipeOutput;

public class GreateRecipes {
    public static void register(RecipeOutput provider) {
        if(Greate.CONFIG.enableHardCreateRecipes) {
            GreateCraftingTableRecipes.registerHardCreateRecipes(provider);
        } else GreateCraftingTableRecipes.registerEasyCreateRecipes(provider);

        GreateAssemblerRecipes.register(provider);
        GreateChemicalBathRecipes.register(provider);
        GreateCraftingTableRecipes.register(provider);
        GreateCraftingTableRecipes.registerCreateRecipes(provider);
        GreateCuttingMachineRecipes.register(provider);
        GreateMillstoneRecipes.register(provider);
        GreateMechanicalCraftingRecipes.register(provider);
        GreateSequencedAssemblyRecipes.register(provider);

        for(Material material : GTCEuAPI.materialManager) {
            //TODO:fix
            //if(material.hasFlag(MaterialFlags.NO_UNIFICATION)) continue;
            GreateCraftingTableRecipes.registerMaterialRecipes(provider, material);
            GreateCuttingMachineRecipes.registerMaterialRecipes(provider, material);
            GreateDeployerRecipes.registerMaterialRecipes(provider, material);
            GreateMixingRecipes.registerMaterialRecipes(provider, material);
            GreateSequencedAssemblyRecipes.registerMaterialRecipes(provider, material);
            GreateSpoutRecipes.registerCableRecipes(provider, material);
        }
    }
}
