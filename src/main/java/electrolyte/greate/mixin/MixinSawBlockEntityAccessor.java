package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SawBlockEntity.class)
public interface MixinSawBlockEntityAccessor {

    @Accessor(value = "recipeIndex", remap = false) int getRecipeIndex();
    @Accessor(value = "recipeIndex", remap = false) void setRecipeIndex(int recipeIndex);
    @Accessor(value = "filtering", remap = false) FilteringBehaviour getFilteringBehaviour();
    @Accessor(value = "cuttingRecipesKey", remap = false) Object getCuttingRecipesKey();
}
