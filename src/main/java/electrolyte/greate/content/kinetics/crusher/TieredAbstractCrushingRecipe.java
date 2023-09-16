package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import net.minecraft.world.Container;

public abstract class TieredAbstractCrushingRecipe extends TieredProcessingRecipe<Container> {

    public TieredAbstractCrushingRecipe(IRecipeTypeInfo typeInfo, TieredProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }
}
