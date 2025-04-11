package electrolyte.greate.content.kinetics.millstone;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

@ParametersAreNonnullByDefault
public class TieredMillingRecipe extends TieredAbstractCrushingRecipe {

    public TieredMillingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.MILLING, params);
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }

    public boolean matches(RecipeWrapper container, Level level, int machineTier) {
        boolean containerMatches = matches(container, level);
        return containerMatches && this.getRecipeTier() <= machineTier;
    }

    public static TieredMillingRecipe convertNormal(Recipe<?> recipe) {
        ProcessingRecipe<?> processingRecipe = (ProcessingRecipe<?>) recipe;
        return new TieredProcessingRecipeBuilder<>(TieredMillingRecipe::new, Greate.id("integration/" + processingRecipe.getId().toString().replace(":", "/")))
                .duration(processingRecipe.getProcessingDuration())
                .withItemIngredients(processingRecipe.getIngredients())
                .withItemOutputs(processingRecipe.getRollableResults())
                .recipeTier(ULV)
                .build();
    }

    public static TieredMillingRecipe convertGT(GTRecipe recipe) {
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredMillingRecipe::new, Greate.id("integration/" + recipe.getId().toString().replace(":", "/")))
                .duration(recipe.duration)
                .withItemIngredientsGT(recipe.getInputContents(ItemRecipeCapability.CAP))
                .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, ULV)
                .recipeTier(recipeTier)
                .build();
    }
}
