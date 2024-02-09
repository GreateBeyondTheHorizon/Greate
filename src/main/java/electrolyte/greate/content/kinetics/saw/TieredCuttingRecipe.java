package electrolyte.greate.content.kinetics.saw;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import electrolyte.greate.registry.Saws;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

@ParametersAreNonnullByDefault
public class TieredCuttingRecipe extends TieredProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {

    public TieredCuttingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.CUTTING, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    @Override
    protected boolean canSpecifyDuration() {
        return true;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getDescriptionForAssembly() {
        return Lang.translateDirect("recipe.assembly.cutting");
    }

    @Override
    public void addRequiredMachines(Set<ItemLike> list) {
        list.addAll(Saws.SAWS);
    }

    @Override
    public void addAssemblyIngredients(List<Ingredient> list) {}

    @Override
    public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
        return () -> SequencedAssemblySubCategory.AssemblyCutting::new;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }

    public static TieredCuttingRecipe convertNormalSawing(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, recipe.getId())
                .withItemIngredients(recipe.getIngredients())
                .output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                .withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults())
                .recipeTier(ULV)
                .build();
    }

    public static TieredCuttingRecipe convertGTCutter(GTRecipe recipe, int machineTier) {
        List<Content> inputContents = recipe.getInputContents(ItemRecipeCapability.CAP);
        List<Content> fluidContents = recipe.getInputContents(FluidRecipeCapability.CAP);
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredCuttingRecipe::new, recipe.getId())
                .withItemIngredientsGT(inputContents)
                .withFluidIngredientsGT(fluidContents)
                .output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, machineTier)
                .recipeTier(recipeTier)
                .duration(recipe.duration)
                .build();
    }
}
