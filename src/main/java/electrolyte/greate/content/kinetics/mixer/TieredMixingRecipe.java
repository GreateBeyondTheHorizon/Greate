package electrolyte.greate.content.kinetics.mixer;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class TieredMixingRecipe extends TieredBasinRecipe {
    public TieredMixingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.MIXING, params);
    }

    public static TieredMixingRecipe convertUntieredRecipe(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new, recipe.getId())
                .withItemIngredients(recipe.getIngredients())
                .withFluidIngredients(((ProcessingRecipe<?>) recipe).getFluidIngredients())
                .output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                .withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults())
                .withFluidOutputs(((ProcessingRecipe<?>) recipe).getFluidResults())
                .requiresHeat(((ProcessingRecipe<?>) recipe).getRequiredHeat())
                .recipeTier(TIER.ULTRA_LOW)
                .noCircuit()
                .build();
    }

    public static TieredMixingRecipe convertGTMixing(GTRecipe recipe, TIER machineTier) {
        List<Content> itemInputContents = recipe.getInputContents(ItemRecipeCapability.CAP);
        List<Content> fluidInputContents = recipe.getInputContents(FluidRecipeCapability.CAP);
        TIER recipeTier = TIER.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new, recipe.getId())
                .withItemIngredientsGT(itemInputContents)
                .withFluidIngredientsGT(fluidInputContents)
                .output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, machineTier)
                .withFluidOutputsGT(recipe.getOutputContents(FluidRecipeCapability.CAP))
                .requiresHeat(HeatCondition.HEATED)
                .recipeTier(recipeTier)
                .recipeCircuit(getCircuitFromGTRecipe(itemInputContents))
                .build();
    }
}
