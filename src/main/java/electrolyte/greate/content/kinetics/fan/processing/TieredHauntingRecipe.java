package electrolyte.greate.content.kinetics.fan.processing;

import electrolyte.greate.content.kinetics.fan.processing.TieredHauntingRecipe.TieredHauntingWrapper;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TieredHauntingRecipe extends TieredProcessingRecipe<TieredHauntingWrapper> {
    public TieredHauntingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.HAUNTING, params);
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
    public boolean matches(TieredHauntingWrapper container, Level level) {
        if(container.isEmpty()) return false;
        return ingredients.get(0).test(container.getItem(0));
    }

    public static class TieredHauntingWrapper extends RecipeWrapper {
        public TieredHauntingWrapper() {
            super(new ItemStackHandler(1));
        }
    }
}
