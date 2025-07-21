package electrolyte.greate.content.processing.recipe;

import com.google.common.base.Joiner;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.item.behavior.IntCircuitBehaviour;
import com.gregtechceu.gtceu.data.item.GTItems;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class TieredProcessingRecipe<I extends RecipeInput, P extends TieredProcessingRecipeParams> extends ProcessingRecipe<I, P> {

    protected int recipeTier;
    protected int circuitNumber;

    public TieredProcessingRecipe(IRecipeTypeInfo typeInfo, P params) {
        super(typeInfo, params);
        this.recipeTier = params.recipeTier;
        this.circuitNumber = params.circuitNumber;
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        int ingredientCount = ingredients.size();
        int outputCount = results.size();

        if(ingredientCount > getMaxInputCount()) {
            errors.add("Recipe has more item inputs (" + ingredientCount + ") than supported ("
                    + getMaxInputCount() + ").");
        }

        if(outputCount > getMaxOutputCount())
            errors.add("Recipe has more item outputs (" + outputCount + ") than supported ("
                    + getMaxOutputCount() + ").");

        if (processingDuration > 0 && !canSpecifyDuration())
            errors.add("Recipe specified a duration. Durations have no impact on this type of recipe.");

        if (requiredHeat != HeatCondition.NONE && !canRequireHeat())
            errors.add("Recipe specified a heat condition. Heat conditions have no impact on this type of recipe.");

        ingredientCount = fluidIngredients.size();
        outputCount = fluidResults.size();

        if (ingredientCount > getMaxFluidInputCount())
            errors.add("Recipe has more fluid inputs (" + ingredientCount + ") than supported ("
                    + getMaxFluidInputCount() + ").");

        if (outputCount > getMaxFluidOutputCount())
            errors.add("Recipe has more fluid outputs (" + outputCount + ") than supported ("
                    + getMaxFluidOutputCount() + ").");
        return errors;
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
            if(((Ingredient) c.getContent()).getItems().length == 0) continue;
            if(((Ingredient) c.getContent()).getItems()[0].is(GTItems.PROGRAMMED_CIRCUIT.asItem())) {
                ItemStack circuit = ((Ingredient) c.getContent()).getItems()[0];
                circuitNumber = IntCircuitBehaviour.getCircuitConfiguration(circuit);
                break;
            }
        }
        return circuitNumber;
    }

    public static <P extends TieredProcessingRecipeParams, R extends TieredProcessingRecipe<?, P>>MapCodec<R> codec(
            Factory<P, R> factory, MapCodec<P> paramsCodec) {
        return paramsCodec.xmap(factory::create, r -> r.getParams())
                .validate(r -> {
                    List<String> errors = r.validate();
                    if(errors.isEmpty()) return DataResult.success(r);
                    errors.add(r.getClass().getSimpleName() + "failed validation:");
                    return DataResult.error(() -> Joiner.on('\n').join(errors), r);
                });
    }

    public static <P extends TieredProcessingRecipeParams, R extends TieredProcessingRecipe<?, P>> StreamCodec<RegistryFriendlyByteBuf, R> streamCodec(
            Factory<P, R> factory, StreamCodec<RegistryFriendlyByteBuf, P> streamCodec) {
        return streamCodec.map(factory::create, TieredProcessingRecipe::getParams);
    }

    @FunctionalInterface
    public interface Factory<P extends TieredProcessingRecipeParams, R extends TieredProcessingRecipe<?, P>> extends ProcessingRecipe.Factory<P, R> {
        R create(P params);
    }
}
