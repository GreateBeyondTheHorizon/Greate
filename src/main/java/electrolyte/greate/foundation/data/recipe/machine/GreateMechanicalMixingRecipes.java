package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.ingot;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Wheat;
import static com.gregtechceu.gtceu.common.data.GTMaterials.WroughtIron;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.ModItems.ALLOYS;

public class GreateMechanicalMixingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new, Greate.id("dough"))
                .withItemIngredients(Ingredient.of(ChemicalHelper.get(new UnificationEntry(dust, Wheat), 1)))
                .withFluidIngredients(FluidIngredient.fromFluid(Fluids.WATER, 1000))
                .withItemOutputs(new ProcessingOutput(AllItems.DOUGH.asStack(), 1))
                .build(provider);

        for(int tier = 0; tier < TM.length; tier++) {
            if(tier != 0) {
                new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new, ALLOYS[tier].getId())
                        .withItemIngredients(Ingredient.of(ChemicalHelper.get(dust, GreateValues.getMaterialFromTier(tier)), ChemicalHelper.get(ingot, GreateValues.getMaterialFromTier(tier))), Ingredient.of(Blocks.ANDESITE))
                        .withSingleItemOutput(ALLOYS[tier].asStack())
                        .requiresHeat(HeatCondition.HEATED)
                        .duration(100)
                        .recipeTier(tier)
                        .recipeCircuit(4)
                        .build(provider);
            } else {
                new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new, ALLOYS[tier].getId())
                        .withItemIngredients(Ingredient.of(ChemicalHelper.get(dust, WroughtIron), ChemicalHelper.get(ingot, WroughtIron)), Ingredient.of(Blocks.ANDESITE))
                        .withSingleItemOutput(ALLOYS[tier].asStack())
                        .requiresHeat(HeatCondition.HEATED)
                        .duration(100)
                        .recipeTier(tier)
                        .recipeCircuit(4)
                        .build(provider);
            }
        }
    }
}
