package electrolyte.greate.content.kinetics.simpleRelays;

import net.minecraft.world.item.crafting.Recipe;

public interface ITieredProcessingRecipeHolder {

    Recipe<?> getRecipe();
    void setRecipe(Recipe<?> recipe);
}
