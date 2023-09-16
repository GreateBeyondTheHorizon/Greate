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
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

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
    public boolean matches(Container pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }

    public static TieredCrushingRecipe convertNormalCrushing(Recipe<?> recipe, float extraPercent) {
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, recipe.getId()).duration(((ProcessingRecipe<?>) recipe).getProcessingDuration()).withItemIngredients(recipe.getIngredients()).output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())).withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults(), extraPercent).recipeTier(TIER.ULTRA_LOW).build();
    }

    public static TieredCrushingRecipe convertTieredCrushing(Recipe<?> recipe, float extraPercent) {
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, recipe.getId()).duration(((ProcessingRecipe<?>) recipe).getProcessingDuration()).withItemIngredients(recipe.getIngredients()).output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())).withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults(), extraPercent).recipeTier(((TieredProcessingRecipe<?>) recipe).getRecipeTier()).build();
    }

    public static TieredCrushingRecipe convertGT(GTRecipe recipe, float extraPercent) {
        return new TieredProcessingRecipeBuilder<>(TieredCrushingRecipe::new, recipe.getId()).duration(recipe.duration).withItemIngredientsGT(recipe.getInputContents(ItemRecipeCapability.CAP)).output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())).withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), extraPercent).recipeTier(TIER.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP))).build();
    }
}
