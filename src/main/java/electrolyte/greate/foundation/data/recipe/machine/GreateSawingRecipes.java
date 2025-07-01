package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.common.data.GTMaterials.VOLTAGE_COMMON_MATERIALS;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateSawingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, SHAFTS[tier].getId().withSuffix("_water"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(alloy, VOLTAGE_COMMON_MATERIALS[tier])))
                    .withFluidIngredients(FluidIngredient.fromFluidStack(GTMaterials.Water.getFluid(
                            Math.max(4, Math.min(1000, 100 * VA[tier] / 320)))))
                    .withSingleItemOutput(new ItemStack(SHAFTS[tier], 4))
                    .averageProcessingDuration()
                    .recipeTier(tier)
                    .build(provider);

            new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, SHAFTS[tier].getId().withSuffix("_distilled_water"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(alloy, VOLTAGE_COMMON_MATERIALS[tier])))
                    .withFluidIngredients(FluidIngredient.fromFluidStack(GTMaterials.DistilledWater.getFluid(
                            Math.max(3, Math.min(750, 100 * VA[tier] / 426)))))
                    .withSingleItemOutput(new ItemStack(SHAFTS[tier], 4))
                    .averageProcessingDuration()
                    .recipeTier(tier)
                    .build(provider);

            new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, SHAFTS[tier].getId().withSuffix("_lubricant"))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(alloy, VOLTAGE_COMMON_MATERIALS[tier])))
                    .withFluidIngredients(FluidIngredient.fromFluidStack(GTMaterials.Lubricant.getFluid(
                            Math.max(1, Math.min(250, 100 * VA[tier] / 1280)))))
                    .withSingleItemOutput(new ItemStack(SHAFTS[tier], 4))
                    .averageProcessingDuration()
                    .recipeTier(tier)
                    .build(provider);
        }
    }
}
