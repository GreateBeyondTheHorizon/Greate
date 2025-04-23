package electrolyte.greate.mixin;

import com.google.gson.JsonParseException;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeSerializer;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SequencedRecipe.class)
public abstract class MixinSequencedAssemblyRecipe<T extends ProcessingRecipe<?>, R extends TieredProcessingRecipe<?>> {

    @Shadow(remap = false) private T wrapped;

    @Inject(method = "writeToBuffer", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void greate_writeToBuffer(FriendlyByteBuf buffer, CallbackInfo ci) {
        if(wrapped.getSerializer() instanceof ProcessingRecipeSerializer<?>) {
            ProcessingRecipeSerializer<T> serializer = (ProcessingRecipeSerializer<T>) wrapped.getSerializer();
            buffer.writeResourceLocation(CatnipServices.REGISTRIES.getKeyOrThrow(serializer));
            buffer.writeResourceLocation(wrapped.getId());
            serializer.toNetwork(buffer, wrapped);
        } else if(wrapped.getSerializer() instanceof TieredProcessingRecipeSerializer<?>) {
            TieredProcessingRecipeSerializer<R> serializer = (TieredProcessingRecipeSerializer<R>) wrapped.getSerializer();
            buffer.writeResourceLocation(CatnipServices.REGISTRIES.getKeyOrThrow(serializer));
            buffer.writeResourceLocation(wrapped.getId());
            serializer.toNetwork(buffer, (R) wrapped);
        }
        ci.cancel();
    }

    @Inject(method = "readFromBuffer", at = @At("HEAD"), remap = false, cancellable = true)
    private static void greate_readFromBuffer(FriendlyByteBuf buffer, CallbackInfoReturnable<SequencedRecipe<?>> cir) {
        ResourceLocation loc = buffer.readResourceLocation();
        ResourceLocation loc1 = buffer.readResourceLocation();
        RecipeSerializer<?> serializer = ForgeRegistries.RECIPE_SERIALIZERS.getValue(loc);
        if(serializer instanceof ProcessingRecipeSerializer<?> prs) {
            ProcessingRecipe<?> recipe = prs.fromNetwork(loc1, buffer);
            cir.setReturnValue(new SequencedRecipe<>(recipe));
        } else if(serializer instanceof TieredProcessingRecipeSerializer<?> tprs) {
            TieredProcessingRecipe<?> recipe = tprs.fromNetwork(loc1, buffer);
            cir.setReturnValue(new SequencedRecipe<>(recipe));
        } else {
            throw new JsonParseException("Not a supported recipe type");
        }
        cir.cancel();
    }
}
