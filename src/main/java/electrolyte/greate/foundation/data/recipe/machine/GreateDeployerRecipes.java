package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.CogwheelProperty;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.Ingredient;

import static com.gregtechceu.gtceu.api.tag.TagPrefix.plate;
import static electrolyte.greate.registry.GreateTagPrefixes.*;

public class GreateDeployerRecipes {

    public static void register(RecipeOutput provider) {}

    public static void registerMaterialRecipes(RecipeOutput provider, Material material) {
        CogwheelProperty cogwheelProperty = material.getProperty(GreatePropertyKeys.COGWHEEL);
        if(cogwheelProperty != null) {
            Material prevMat = cogwheelProperty.getPreviousMaterial();
            new ItemApplicationRecipe.Builder<>(DeployerApplicationRecipe::new, Greate.id(material.getName() + "_cogwheel"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(shaft, material)), Ingredient.of(ChemicalHelper.get(plate, prevMat)))
                    .withItemOutputs(new ProcessingOutput(ChemicalHelper.get(cogwheel, material), 1))
                    .build(provider);
            new ItemApplicationRecipe.Builder<>(DeployerApplicationRecipe::new, Greate.id(material.getName()).withSuffix("_cogwheel_from_little"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(cogwheel, material)), Ingredient.of(ChemicalHelper.get(plate, prevMat)))
                    .withItemOutputs(new ProcessingOutput(ChemicalHelper.get(largeCogwheel, material), 1))
                    .build(provider);
        }
    }
}
