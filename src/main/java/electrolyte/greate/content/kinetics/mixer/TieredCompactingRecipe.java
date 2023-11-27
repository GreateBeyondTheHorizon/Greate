package electrolyte.greate.content.kinetics.mixer;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Recipe;

public class TieredCompactingRecipe extends TieredBasinRecipe {
    public TieredCompactingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.COMPACTING, params);
    }

    public static TieredCompactingRecipe convertNormalBasin(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredCompactingRecipe::new, recipe.getId())
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
}
