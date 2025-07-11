package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import electrolyte.greate.foundation.data.recipe.machine.*;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class GreateRecipes {
    public static void register(Consumer<FinishedRecipe> provider) {
        GreateChemicalBath.register(provider);
        GreateCraftingTableRecipes.register(provider);
        GreateCuttingMachineRecipes.register(provider);
        GreateMillstoneRecipes.register(provider);
        GreateMechanicalCraftingRecipes.register(provider);
        GreateSequencedAssemblyRecipes.register(provider);

        for(Material material : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(material.hasFlag(MaterialFlags.NO_UNIFICATION)) continue;
            GreateCraftingTableRecipes.registerMaterialRecipes(provider, material);
            GreateCuttingMachineRecipes.registerMaterialRecipes(provider, material);
            GreateDeployerRecipes.registerMaterialRecipes(provider, material);
            GreateMixingRecipes.registerMaterialRecipes(provider, material);
            GreateSequencedAssemblyRecipes.registerMaterialRecipes(provider, material);
            GreateSpoutRecipes.registerCableRecipes(provider, material);
        }
    }
}
