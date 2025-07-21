package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingRecipe;
import electrolyte.greate.content.processing.recipe.TieredStandardProcessingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

public class GreateMillstoneRecipes {

    public static void register(RecipeOutput provider) {
        //Only default create recipes that do not have a GT macerator counterpart
        new TieredStandardProcessingRecipe.Builder<>(TieredCrushingRecipe::new, Greate.id("amethyst_cluster")).withItemIngredients(Ingredient.of(Items.AMETHYST_CLUSTER)).withItemOutputs(NonNullList.of(new ProcessingOutput(new ItemStack(Items.AMETHYST_SHARD, 7), 1), new ProcessingOutput(new ItemStack(Items.AMETHYST_SHARD), 0.5f))).recipeTier(ULV).build(provider);
        new TieredStandardProcessingRecipe.Builder<>(TieredCrushingRecipe::new, Greate.id("prismarine_crystals")).withItemIngredients(Ingredient.of(Items.PRISMARINE_CRYSTALS)).withItemOutputs(NonNullList.of(new ProcessingOutput(new ItemStack(Items.QUARTZ), 1), new ProcessingOutput(new ItemStack(Items.QUARTZ, 2), 0.5f), new ProcessingOutput(new ItemStack(Items.GLOWSTONE_DUST, 2), 0.1f))).recipeTier(ULV).build(provider);
    }
}
