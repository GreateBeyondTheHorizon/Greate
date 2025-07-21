package electrolyte.greate.content.processing.recipe;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class TieredStandardProcessingRecipe<T extends RecipeInput> extends TieredProcessingRecipe<T, TieredProcessingRecipeParams> {
    public TieredStandardProcessingRecipe(IRecipeTypeInfo typeInfo, TieredProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @FunctionalInterface
    public interface Factory<R extends TieredStandardProcessingRecipe<?>> extends TieredProcessingRecipe.Factory<TieredProcessingRecipeParams, R> {
        R create(TieredProcessingRecipeParams params);
    }

    public static class Builder<R extends TieredStandardProcessingRecipe<?>> extends TieredProcessingRecipeBuilder<TieredProcessingRecipeParams, R, Builder<R>> {

        public Builder(Factory<R> factory, ResourceLocation recipeId) {
            super(factory, recipeId);
        }

        @Override
        protected TieredProcessingRecipeParams createParams() {
            return new TieredProcessingRecipeParams();
        }

        @Override
        public Builder<R> self() {
            return this;
        }
    }

    public static class Serializer<R extends TieredStandardProcessingRecipe<?>> implements RecipeSerializer<R> {
        private final Factory<R> factory;
        private final MapCodec<R> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, R> streamCodec;

        public Serializer(Factory<R> factory) {
            this.factory = factory;
            this.codec = TieredProcessingRecipe.codec(factory, TieredProcessingRecipeParams.CODEC);
            this.streamCodec = TieredProcessingRecipe.streamCodec(factory, TieredProcessingRecipeParams.STREAM_CODEC);
        }

        @Override
        public MapCodec<R> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, R> streamCodec() {
            return streamCodec;
        }

        public Factory<R> factory() {
            return factory;
        }
    }
}
