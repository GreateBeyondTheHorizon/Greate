package electrolyte.greate.content.processing.recipe;

import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import electrolyte.greate.Greate;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.slf4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class TieredProcessingRecipe<T extends Container> extends ProcessingRecipe<T> {

    protected int recipeTier;
    protected int circuitNumber;

    public TieredProcessingRecipe(IRecipeTypeInfo typeInfo, TieredProcessingRecipeParams params) {
        super(typeInfo, params);
        this.processingDuration = params.processingDuration;
        this.fluidIngredients = params.fluidIngredients;
        this.fluidResults = params.fluidResults;
        this.requiredHeat = params.requiredHeat;
        this.ingredients = params.ingredients;
        this.results = params.results;
        this.recipeTier = params.recipeTier;
        this.circuitNumber = params.circuitNumber;
        this.id = params.id;

        this.validate(typeInfo.getId());
    }

    private void validate(ResourceLocation recipeTypeId) {
        String messageHeader = "Your custom " + recipeTypeId + " recipe (" + id.toString() + ")";
        Logger logger = Greate.LOGGER;
        int ingredientCount = ingredients.size();
        int outputCount = results.size();

        if (ingredientCount > getMaxInputCount()) {
            logger.warn(messageHeader + " has more item inputs (" + ingredientCount + ") than supported ("
                    + getMaxInputCount() + ").");
        }

        if (outputCount > getMaxOutputCount())
            logger.warn(messageHeader + " has more item outputs (" + outputCount + ") than supported ("
                    + getMaxOutputCount() + ").");

        if (processingDuration > 0 && !canSpecifyDuration())
            logger.warn(messageHeader + " specified a duration. Durations have no impact on this type of recipe.");

        if (requiredHeat != HeatCondition.NONE && !canRequireHeat())
            logger.warn(
                    messageHeader + " specified a heat condition. Heat conditions have no impact on this type of recipe.");

        ingredientCount = fluidIngredients.size();
        outputCount = fluidResults.size();

        if (ingredientCount > getMaxFluidInputCount())
            logger.warn(messageHeader + " has more fluid inputs (" + ingredientCount + ") than supported ("
                    + getMaxFluidInputCount() + ").");

        if (outputCount > getMaxFluidOutputCount())
            logger.warn(messageHeader + " has more fluid outputs (" + outputCount + ") than supported ("
                    + getMaxFluidOutputCount() + ").");
    }

    @Override
    public NonNullList<ProcessingOutput> getRollableResults() {
        return results;
    }

    public int getRecipeTier() {
        return recipeTier;
    }

    public int getCircuitNumber() {
        return circuitNumber;
    }

    public static int getCircuitFromGTRecipe(List<Content> inputContents) {
        int circuitNumber = -1;
        for(Content c : inputContents) {
            if(((Ingredient) c.getContent()).getItems()[0].is(GTItems.PROGRAMMED_CIRCUIT.asItem())) {
                ItemStack circuit = ((Ingredient) c.getContent()).getItems()[0];
                circuitNumber = IntCircuitBehaviour.getCircuitConfiguration(circuit);
                break;
            }
        }
        return circuitNumber;
    }
}
