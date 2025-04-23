package electrolyte.greate.content.kinetics.crusher;

import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TieredCrushingRecipe extends TieredAbstractCrushingRecipe {
    public TieredCrushingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.CRUSHING, params);
    }

    @Override
    protected int getMaxOutputCount() {
        return 7;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }
}
