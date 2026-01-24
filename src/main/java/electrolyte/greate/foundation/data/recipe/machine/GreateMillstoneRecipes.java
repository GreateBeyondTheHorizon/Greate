package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

public class GreateMillstoneRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        //Only default create recipes that do not have a GTCEu macerator counterpart
        new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, Greate.id("amethyst_cluster")).withItemIngredients(Ingredient.of(Items.AMETHYST_CLUSTER)).withItemOutputs(List.of(new ProcessingOutput(new ItemStack(Items.AMETHYST_SHARD, 7), 1), new ProcessingOutput(new ItemStack(Items.AMETHYST_SHARD), 0.5f))).recipeTier(ULV).build(provider);
        new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, Greate.id("prismarine_crystals")).withItemIngredients(Ingredient.of(Items.PRISMARINE_CRYSTALS)).withItemOutputs(List.of(new ProcessingOutput(new ItemStack(Items.QUARTZ), 1), new ProcessingOutput(new ItemStack(Items.QUARTZ, 2), 0.5f), new ProcessingOutput(new ItemStack(Items.GLOWSTONE_DUST, 2), 0.1f))).recipeTier(ULV).build(provider);
    }
}
