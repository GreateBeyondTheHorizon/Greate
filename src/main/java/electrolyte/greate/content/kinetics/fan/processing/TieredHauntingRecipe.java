package electrolyte.greate.content.kinetics.fan.processing;

import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeParams;
import electrolyte.greate.content.processing.recipe.TieredStandardProcessingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TieredHauntingRecipe extends TieredStandardProcessingRecipe<SingleRecipeInput> {
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
    public boolean matches(SingleRecipeInput inv, Level level) {
        if(inv.isEmpty()) return false;
        return ingredients.get(0).test(inv.getItem(0));
    }
}
