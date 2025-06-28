package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Wood;
import static com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.PLATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.createIngFromMaterialEntry;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateDeployerRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, COGWHEELS[tier].getId())
                    .withItemIngredients(Ingredient.of(SHAFTS[tier].asStack()), createIngFromMaterialEntry(tier != 0 ? PLATE.get(tier - 1) : new MaterialEntry(plate, Wood)))
                    .withItemOutputs(new ProcessingOutput(COGWHEELS[tier].asStack(), 1))
                    .build(provider);
            new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, LARGE_COGWHEELS[tier].getId().withSuffix("_from_little"))
                    .withItemIngredients(Ingredient.of(COGWHEELS[tier].asStack()), createIngFromMaterialEntry(tier != 0 ? PLATE.get(tier - 1) : new MaterialEntry(plate, Wood)))
                    .withItemOutputs(new ProcessingOutput(LARGE_COGWHEELS[tier].asStack(), 1))
                    .build(provider);
        }
    }
}
