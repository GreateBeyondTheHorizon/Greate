package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.ingot;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class GreateMechanicalMixingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new, Greate.id("brass_ingot"))
                .withItemIngredients(
                        Ingredient.of(ChemicalHelper.get(ingot, Copper)),
                        Ingredient.of(ChemicalHelper.get(ingot, Copper)),
                        Ingredient.of(ChemicalHelper.get(ingot, Copper)),
                        Ingredient.of(ChemicalHelper.get(ingot, Zinc)))
                .withItemOutputs(new ProcessingOutput(ChemicalHelper.get(ingot, Brass, 4), 1f))
                .requiresHeat(HeatCondition.HEATED)
                .build(provider);
    }
}
