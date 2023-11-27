package electrolyte.greate.content.kinetics.crusher;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Recipe;
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

    public static TieredCrushingRecipe convertNormalCrushing(Recipe<?> recipe, TIER machineTier) {
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, recipe.getId()).duration(((ProcessingRecipe<?>) recipe).getProcessingDuration()).withItemIngredients(recipe.getIngredients()).output(recipe.getResultItem(Minecraft.getInstance().getConnection().registryAccess())).withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults(), TIER.ULTRA_LOW, machineTier).recipeTier(TIER.ULTRA_LOW).build();
    }

    public static TieredCrushingRecipe convertTieredCrushing(Recipe<?> recipe, TIER machineTier) {
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, recipe.getId()).duration(((ProcessingRecipe<?>) recipe).getProcessingDuration()).withItemIngredients(recipe.getIngredients()).output(recipe.getResultItem(Minecraft.getInstance().getConnection().registryAccess())).withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults(), ((TieredProcessingRecipe<?>) recipe).getRecipeTier(), machineTier).recipeTier(((TieredProcessingRecipe<?>) recipe).getRecipeTier()).build();
    }

    public static TieredCrushingRecipe convertGT(GTRecipe recipe, TIER machineTier) {
        TIER recipeTier = TIER.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, recipe.getId()).duration(recipe.duration).withItemIngredientsGT(recipe.getInputContents(ItemRecipeCapability.CAP)).output(recipe.getResultItem(Minecraft.getInstance().getConnection().registryAccess())).withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, machineTier).recipeTier(recipeTier).build();
    }
}
