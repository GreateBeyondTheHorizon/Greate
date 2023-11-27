package electrolyte.greate.content.processing.recipe;

import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.slf4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class TieredProcessingRecipe<T extends Container> extends ProcessingRecipe<T> {

    protected ResourceLocation id;
    protected NonNullList<Ingredient> ingredients;
    protected NonNullList<ProcessingOutput> results;
    protected NonNullList<FluidIngredient> fluidIngredients;
    protected NonNullList<FluidStack> fluidResults;
    protected int processingDuration;
    protected HeatCondition requiredHeat;
    protected TIER recipeTier;
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

        if (ingredientCount > getMaxInputCount())
            logger.warn(messageHeader + " has more item inputs (" + ingredientCount + ") than supported ("
                    + getMaxInputCount() + ").");

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
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public NonNullList<FluidIngredient> getFluidIngredients() {
        return fluidIngredients;
    }

    @Override
    public List<ProcessingOutput> getRollableResults() {
        return results;
    }

    @Override
    public NonNullList<FluidStack> getFluidResults() {
        return fluidResults;
    }

    @Override
    public int getProcessingDuration() {
        return processingDuration;
    }

    @Override
    public HeatCondition getRequiredHeat() {
        return requiredHeat;
    }

    public TIER getRecipeTier() {
        return recipeTier;
    }

    public int getCircuitNumber() {
        return circuitNumber;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }
}
