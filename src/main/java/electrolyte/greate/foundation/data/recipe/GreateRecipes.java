package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.foundation.data.recipe.machine.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Consumer;

public class GreateRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        GreateAlloySmelterRecipes.register(provider);
        GreateAssemblerRecipes.register(provider);
        GreateChemicalBath.register(provider);
        GreateCraftingTableRecipes.register(provider);
        GreateCuttingMachineRecipes.register(provider);
        GreateDeployerRecipes.register(provider);
        GreateMaceratorRecipes.register(provider);
        GreateMillstoneRecipes.register(provider);
        GreateMechanicalCraftingRecipes.register(provider);
        GreateMechanicalMixingRecipes.register(provider);
        GreateSawingRecipes.register(provider);
        GreateSequencedAssemblyRecipes.register(provider);
        GreateSplashingRecipes.register(provider);
        GreateSpoutRecipes.register(provider);
    }

    public static void conversionCycle(Consumer<FinishedRecipe> provider, List<ItemProviderEntry<? extends ItemLike>> cycle) {
        for (int i = 0; i < cycle.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
            var builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nextEntry).requires(currentEntry).unlockedBy("has_cycle_origin", RegistrateRecipeProvider.has(currentEntry));
            builder.save(provider, RecipeBuilder.getDefaultRecipeId(builder.getResult()).withSuffix("_from_conversion"));
        }
    }

    public static Ingredient createIngFromTag(String namespace, String path) {
        return Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(namespace, path)));
    }

    public static Ingredient createIngFromUnificationEntry(Object ingredient) {
        return Ingredient.of(ChemicalHelper.getTag(((UnificationEntry) ingredient).tagPrefix, ((UnificationEntry) ingredient).material));
    }
}
