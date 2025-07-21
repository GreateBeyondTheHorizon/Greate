package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeParams;
import electrolyte.greate.content.processing.recipe.TieredStandardProcessingRecipe;
import net.minecraft.world.item.crafting.RecipeInput;

public abstract class TieredAbstractCrushingRecipe extends TieredStandardProcessingRecipe<RecipeInput> {

    public TieredAbstractCrushingRecipe(IRecipeTypeInfo typeInfo, TieredProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    public boolean canSpecifyDuration() {
        return true;
    }
}
