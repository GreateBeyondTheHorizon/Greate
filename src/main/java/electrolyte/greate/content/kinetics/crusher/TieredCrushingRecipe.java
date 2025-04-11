package electrolyte.greate.content.kinetics.crusher;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

@ParametersAreNonnullByDefault
public class TieredCrushingRecipe extends TieredAbstractCrushingRecipe {
    public TieredCrushingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.CRUSHING, params);
    }

    @Override
    protected int getMaxOutputCount() {
        return 7;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }

    public static TieredCrushingRecipe convertNormalCrushing(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, Greate.id("integration/" + recipe.getId().toString().replace(":", "/")))
                .duration(((ProcessingRecipe<?>) recipe).getProcessingDuration())
                .withItemIngredients(recipe.getIngredients())
                .withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults())
                .recipeTier(ULV)
                .build();
    }

    public static TieredCrushingRecipe convertGT(GTRecipe recipe) {
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, Greate.id("integration/" + recipe.getId().toString().replace(":", "/")))
                .duration(recipe.duration)
                .withItemIngredientsGT(recipe.getInputContents(ItemRecipeCapability.CAP))
                .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, ULV)
                .recipeTier(recipeTier)
                .build();
    }
}
