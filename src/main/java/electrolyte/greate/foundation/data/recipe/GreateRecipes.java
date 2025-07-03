package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.foundation.data.recipe.machine.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Consumer;

public class GreateRecipes {
    public static void register(Consumer<FinishedRecipe> provider) {
        GreateChemicalBath.register(provider);
        GreateCraftingTableRecipes.register(provider);
        GreateMillstoneRecipes.register(provider);
        GreateMechanicalCraftingRecipes.register(provider);
        GreateSequencedAssemblyRecipes.register(provider);

        for(Material material : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(material.hasFlag(MaterialFlags.NO_UNIFICATION)) continue;
            GreateAlloySmelterRecipes.registerMaterialRecipes(provider, material);
            GreateCraftingTableRecipes.registerMaterialRecipes(provider, material);
            GreateCuttingMachineRecipes.registerMaterialRecipes(provider, material);
            GreateDeployerRecipes.registerMaterialRecipes(provider, material);
            GreateMechanicalMixingRecipes.registerMaterialRecipes(provider, material);
            GreateSawingRecipes.registerMaterialRecipes(provider, material);
            GreateSequencedAssemblyRecipes.registerMaterialRecipes(provider, material);
            GreateSpoutRecipes.registerCableRecipes(provider, material);
        }
    }

    public static void conversionCycle(Consumer<FinishedRecipe> provider, List<ItemProviderEntry<? extends ItemLike>> cycle) {
        for (int i = 0; i < cycle.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
            var builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nextEntry).requires(currentEntry).unlockedBy("has_cycle_origin", RegistrateRecipeProvider.has(currentEntry));
            builder.save(provider, RecipeBuilder.getDefaultRecipeId(builder.getResult()).withSuffix("_from_conversion"));
        }
    }
}
