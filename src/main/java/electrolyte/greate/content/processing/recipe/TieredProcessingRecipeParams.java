package electrolyte.greate.content.processing.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.function.Supplier;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

public class TieredProcessingRecipeParams extends ProcessingRecipeParams {

    public static MapCodec<TieredProcessingRecipeParams> CODEC = tieredCodec(TieredProcessingRecipeParams::new);
    public static StreamCodec<RegistryFriendlyByteBuf, TieredProcessingRecipeParams> STREAM_CODEC = tieredStreamCodec(TieredProcessingRecipeParams::new);

    protected int recipeTier;
    protected int circuitNumber;

    protected TieredProcessingRecipeParams() {
        super();
        recipeTier = ULV;
        circuitNumber = -1;
    }

    protected static <P extends TieredProcessingRecipeParams> MapCodec<P> tieredCodec(Supplier<P> factory) {
        return RecordCodecBuilder.mapCodec(i -> i.group(
                Codec.either(FluidIngredient.CODEC, Ingredient.CODEC).listOf().fieldOf("ingredients")
                        .forGetter(TieredProcessingRecipeParams::ingredients),
                Codec.either(FluidStack.CODEC, ProcessingOutput.CODEC).listOf().fieldOf("results")
                        .forGetter(TieredProcessingRecipeParams::results),
                Codec.INT.optionalFieldOf("processing_time", 0)
                        .forGetter(TieredProcessingRecipeParams::processingDuration),
                HeatCondition.CODEC.optionalFieldOf("heat_requirement", HeatCondition.NONE)
                        .forGetter(TieredProcessingRecipeParams::requiredHeat),
                Codec.INT.optionalFieldOf("recipe_tier", ULV)
                        .forGetter(TieredProcessingRecipeParams::recipeTier),
                Codec.INT.optionalFieldOf("circuit_number", -1)
                        .forGetter(TieredProcessingRecipeParams::circuitNumber))
                .apply(i, (ingredients, results, processingDuration, heatCondition, recipeTier, recipeCircuit) -> {
                    P params = factory.get();
                    ingredients.forEach(e -> e
                            .ifRight(params.ingredients::add)
                            .ifLeft(params.fluidIngredients::add));
                    results.forEach(e -> e
                            .ifRight(params.results::add)
                            .ifLeft(params.fluidResults::add));
                    params.processingDuration = processingDuration;
                    params.requiredHeat = heatCondition;
                    params.recipeTier = recipeTier;
                    params.circuitNumber = recipeCircuit;
                    return params;
                }));
    }

    protected static <P extends TieredProcessingRecipeParams> StreamCodec<RegistryFriendlyByteBuf, P> tieredStreamCodec(Supplier<P> factory) {
        return StreamCodec.of(
                (buffer, params) -> params.encode(buffer),
                buffer -> {
                    P params = factory.get();
                    params.decode(buffer);
                    return params;
                });
    }

    protected final int recipeTier() {
        return recipeTier;
    }

    protected final int circuitNumber() {
        return circuitNumber;
    }

    @Override
    protected void encode(RegistryFriendlyByteBuf buffer) {
        super.encode(buffer);
        ByteBufCodecs.VAR_INT.encode(buffer, recipeTier);
        ByteBufCodecs.VAR_INT.encode(buffer, circuitNumber);
    }

    @Override
    protected void decode(RegistryFriendlyByteBuf buffer) {
        super.decode(buffer);
        recipeTier = ByteBufCodecs.VAR_INT.decode(buffer);
        circuitNumber = ByteBufCodecs.VAR_INT.decode(buffer);
    }
}
