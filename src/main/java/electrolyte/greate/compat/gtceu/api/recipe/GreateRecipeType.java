package electrolyte.greate.compat.gtceu.api.recipe;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.item.behavior.IntCircuitBehaviour;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.item.ItemHelper;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.gregtechceu.gtceu.api.GTValues.VA;

public class GreateRecipeType extends GTRecipeType {

    final ArrayList<StonecuttingGroup> uniqueStonecuttingRecipes = new ArrayList<>();

    public GreateRecipeType(ResourceLocation registryName, String group, RecipeType<?>... proxyRecipes) {
        super(registryName, group, proxyRecipes);
    }

    @Override
    public void beginStagingRecipes() {
        uniqueStonecuttingRecipes.clear();
        super.beginStagingRecipes();
    }

    @Override
    public GTRecipe toGTrecipe(ResourceLocation id, Recipe<?> recipe) {
        if(recipe instanceof ProcessingRecipe<?> processingRecipe) {
            String recipePath = id.withPrefix("/integration/" + id.getNamespace() + "/").getPath();
            ResourceLocation recipeId = Greate.id(recipePath);
            GTRecipeBuilder builder = recipeBuilder(recipeId);
            builder.perTick(true);
            builder.input(StressRecipeCapability.STRESS_CAPABILITY, (float) VA[ULV]);
            builder.perTick(false);
            builder.duration(processingRecipe.getProcessingDuration());
            for(var ingredient : processingRecipe.getIngredients()) {
                builder.inputItems(ingredient);
            }
            for(var ingredient : processingRecipe.getFluidIngredients()) {
                for(var fluidIngredient : ingredient.getMatchingFluidStacks()) {
                    builder.inputFluids(FluidIngredient.of(fluidIngredient.getFluid(), ingredient.getRequiredAmount())); //todo: check
                }
            }
            //builder.outputItems(processingRecipe.getResultItem(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY))); //todo:check if needed
            for(ProcessingOutput output : processingRecipe.getRollableResults()) {
                builder.chancedOutput(output.getStack(), (int) (output.getChance() * 10000));
            }
            for(var fluidOutput : processingRecipe.getFluidResults()) {
                builder.chancedOutput(new FluidStack(fluidOutput.getFluid(), fluidOutput.getAmount()), 10000); //todo:check (needs 100%)
            }
            return GTRecipeSerializer.SERIALIZER.fromJson(recipeId, builder.build().serializeRecipe());
        } else if(recipe.getType() == RecipeType.STONECUTTING) {
            ItemStack result = recipe.getResultItem(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY));
            Ingredient ingredient = recipe.getIngredients().get(0);
            StonecuttingGroup group = null;
            for(StonecuttingGroup g : uniqueStonecuttingRecipes) {
                if(ItemHelper.matchIngredients(ingredient, g.ingredient)) {
                    group = g;
                    break;
                }
            }
            if(group == null) {
                group = new StonecuttingGroup(ingredient);
                uniqueStonecuttingRecipes.add(group);
            }
            String recipePath = id.withPrefix("/integration/" + id.getNamespace() + "/").getPath();
            ResourceLocation recipeId = Greate.id(recipePath);
            GTRecipeBuilder builder = recipeBuilder(recipeId);
            builder.circuitMeta(group.circuit);
            builder.inputItems(recipe.getIngredients().get(0));
            builder.outputItems(result);
            if(group.circuit + 1 > IntCircuitBehaviour.CIRCUIT_MAX) {
                Greate.LOGGER.error("Stonecutting recipe {} has more than 32 stonecutting recipes with the same ingredient.", recipe.getId());
            } else group.circuit++;
            return GTRecipeSerializer.SERIALIZER.fromJson(recipeId, builder.build().serializeRecipe());
        }
        return super.toGTrecipe(id, recipe);
    }

    public void addProxyRecipeType(RecipeType<?> recipeType) {
        this.proxyRecipes.put(recipeType, new ArrayList<>());
    }

    private static class StonecuttingGroup {
        final Ingredient ingredient;
        int circuit = 0;

        public StonecuttingGroup(Ingredient ingredient) {
            this.ingredient = ingredient;
        }
    }
}


