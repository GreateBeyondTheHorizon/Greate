package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.GreateMaterialFlags;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class GreateSawingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {}

    public static void registerMaterialRecipes(Consumer<FinishedRecipe> provider, Material material) {
        if(material.hasFlag(GreateMaterialFlags.GENERATE_SHAFT)) {
            new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, Greate.id(material.getName() + "_shaft_water"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(alloy, material)))
                    .withFluidIngredients(FluidIngredient.fromFluidStack(GTMaterials.Water.getFluid(Math.max(4, Math.min(1000, 100 * VA[LV] / 320)))))
                    .withSingleItemOutput(ChemicalHelper.get(shaft, material).copyWithCount(4))
                    .duration(300)
                    .recipeTier(LV)
                    .build(provider);

            new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, Greate.id(material.getName() + "_shaft_distilled_water"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(alloy, material)))
                    .withFluidIngredients(FluidIngredient.fromFluidStack(GTMaterials.DistilledWater.getFluid(Math.max(3, Math.min(750, 100 * VA[LV] / 426)))))
                    .withSingleItemOutput(ChemicalHelper.get(shaft, material).copyWithCount(4))
                    .duration(300)
                    .recipeTier(LV)
                    .build(provider);

            new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, Greate.id(material.getName() + "_shaft_lubricant"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(alloy, material)))
                    .withFluidIngredients(FluidIngredient.fromFluidStack(GTMaterials.Lubricant.getFluid(Math.max(1, Math.min(250, 100 * VA[LV] / 1280)))))
                    .withSingleItemOutput(ChemicalHelper.get(shaft, material).copyWithCount(4))
                    .duration(300)
                    .recipeTier(LV)
                    .build(provider);
        }
    }
}
