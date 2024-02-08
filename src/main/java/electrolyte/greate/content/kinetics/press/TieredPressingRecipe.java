package electrolyte.greate.content.kinetics.press;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
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
import electrolyte.greate.registry.MechanicalPresses;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

@ParametersAreNonnullByDefault
public class TieredPressingRecipe extends TieredProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {
    public TieredPressingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.PRESSING, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 2;
    }

    @Override
    public Component getDescriptionForAssembly() {
        return Lang.translateDirect("recipe.assembly.pressing");
    }

    @Override
    public void addRequiredMachines(Set<ItemLike> list) {
        list.add(MechanicalPresses.ALUMINIUM_MECHANICAL_PRESS.get());
    }

    @Override
    public void addAssemblyIngredients(List<Ingredient> list) {}

    @Override
    public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
        return () -> SequencedAssemblySubCategory.AssemblyPressing::new;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }

    public static TieredPressingRecipe convertNormalPressing(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredPressingRecipe::new, recipe.getId())
                .withItemIngredients(recipe.getIngredients()).output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                .withItemOutputs(((ProcessingRecipe<?>) recipe).getRollableResults())
                .recipeTier(ULV).noCircuit().build();
    }

    public static TieredPressingRecipe convertGT(GTRecipe recipe, int machineTier) {
        List<Content> inputContents = recipe.getInputContents(ItemRecipeCapability.CAP);
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredPressingRecipe::new, recipe.getId())
                .withItemIngredientsGT(inputContents)
                .output(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, machineTier)
                .recipeTier(recipeTier)
                .recipeCircuit(getCircuitFromGTRecipe(inputContents))
                .build();
    }
}
