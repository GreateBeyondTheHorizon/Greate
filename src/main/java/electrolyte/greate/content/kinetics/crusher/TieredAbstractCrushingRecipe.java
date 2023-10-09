package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class TieredAbstractCrushingRecipe extends TieredProcessingRecipe<RecipeWrapper> {

    public TieredAbstractCrushingRecipe(IRecipeTypeInfo typeInfo, TieredProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected boolean canSpecifyDuration() {
        return true;
    }
}
