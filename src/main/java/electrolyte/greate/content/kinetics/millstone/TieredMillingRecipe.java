package electrolyte.greate.content.kinetics.millstone;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TieredMillingRecipe extends TieredAbstractCrushingRecipe {
    public TieredMillingRecipe(TieredProcessingRecipeBuilder.TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.MILLING, params);
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }

    public static TieredMillingRecipe convert(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredMillingRecipe::new, recipe.getId()).withItemIngredients(recipe.getIngredients()).output(recipe.getResultItem()).withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults()).recipeTier(TIER.ULTRA_LOW).build();
    }
}
