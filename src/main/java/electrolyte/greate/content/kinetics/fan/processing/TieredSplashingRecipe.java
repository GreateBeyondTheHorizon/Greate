package electrolyte.greate.content.kinetics.fan.processing;

import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe.TieredSplashingWrapper;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TieredSplashingRecipe extends TieredProcessingRecipe<TieredSplashingWrapper> {
    public TieredSplashingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.SPLASHING, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    public boolean matches(TieredSplashingWrapper container, Level level) {
        if(container.isEmpty()) return false;
        return ingredients.get(0).test(container.getItem(0));
    }

    public static class TieredSplashingWrapper extends RecipeWrapper {
        public TieredSplashingWrapper() {
            super(new ItemStackHandler(1));
        }
    }
}
