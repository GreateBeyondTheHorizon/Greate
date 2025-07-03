package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.CogwheelProperty;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static electrolyte.greate.registry.GreateTagPrefixes.*;

public class GreateDeployerRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {}

    public static void registerMaterialRecipes(Consumer<FinishedRecipe> provider, Material material) {
        CogwheelProperty cogwheelProperty = material.getProperty(GreatePropertyKeys.COGWHEEL);
        if(cogwheelProperty != null) {
            Material prevMat = cogwheelProperty.getPreviousMaterial();
            new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, Greate.id(material.getName() + "_cogwheel"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(shaft, material)), Ingredient.of(ChemicalHelper.get(plate, prevMat)))
                    .withItemOutputs(new ProcessingOutput(ChemicalHelper.get(cogwheel, material), 1))
                    .build(provider);
            new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, Greate.id(material.getName()).withSuffix("_cogwheel_from_little"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(cogwheel, material)), Ingredient.of(ChemicalHelper.get(plate, prevMat)))
                    .withItemOutputs(new ProcessingOutput(ChemicalHelper.get(largeCogwheel, material), 1))
                    .build(provider);
        }
    }
}
