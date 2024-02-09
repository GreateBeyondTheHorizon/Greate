package electrolyte.greate.content.kinetics.millstone;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.GreateValues;

import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

@ParametersAreNonnullByDefault
public class TieredMillingRecipe extends TieredAbstractCrushingRecipe {

    public TieredMillingRecipe(TieredProcessingRecipeParams params) {
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

    public static TieredMillingRecipe convertNormalMilling(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredMillingRecipe::new, recipe.getId()).withItemIngredients(recipe.getIngredients()).output(recipe.getResultItem(Minecraft.getInstance().getConnection().registryAccess())).withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults()).recipeTier(ULV).build();
    }

    public static TieredMillingRecipe convertGT(GTRecipe recipe, int machineTier) {
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredMillingRecipe::new, recipe.getId()).duration(recipe.duration).withItemIngredientsGT(recipe.getInputContents(ItemRecipeCapability.CAP)).output(recipe.getResultItem(Minecraft.getInstance().getConnection().registryAccess())).withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, machineTier).recipeTier(recipeTier).build();
    }
}
