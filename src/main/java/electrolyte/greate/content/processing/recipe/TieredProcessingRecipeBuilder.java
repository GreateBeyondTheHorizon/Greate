package electrolyte.greate.content.processing.recipe;

import com.google.common.base.Joiner;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import electrolyte.greate.Greate;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe.Factory;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;

public abstract class TieredProcessingRecipeBuilder<P extends TieredProcessingRecipeParams, R extends TieredProcessingRecipe<?, P>, S extends TieredProcessingRecipeBuilder<P, R, S>> extends ProcessingRecipeBuilder<P, R, S> {

    public TieredProcessingRecipeBuilder(Factory<P, R> factory, ResourceLocation recipeId) {
        super(factory, recipeId);
        this.recipeId = recipeId;
        this.factory = factory;
        this.params = createParams();
        this.recipeConditions = new ArrayList<>();
    }

    public S withItemIngredientsGT(List<Content> ingredients) {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        for(Content c : ingredients) {
            //TODO: fix
            /*if((Ingredient) c.getContent() instanceof SizedIngredient sizedIng) {
                if(sizedIng.getInner() instanceof IntCircuitIngredient) {
                    continue;
                }
            }*/
            if(c.getContent() instanceof IntCircuitIngredient) continue;
            nonNullList.add((Ingredient) c.getContent());
        }
        return withItemIngredients(nonNullList);
    }

    public S withItemOutputsGT(List<Content> list) {
        NonNullList<ProcessingOutput> nonNullList = NonNullList.create();
        for(Content c : list) {
            ItemStack[] items = ((Ingredient) c.content).getItems();
            for (ItemStack item : items) {
                nonNullList.add(new ProcessingOutput(item, (float) c.chance / 10000));
            }
        }
        return withItemOutputs(nonNullList);
    }

    public S withFluidIngredientsGT(List<Content> ingredients) {
        NonNullList<FluidIngredient> nonNullList = NonNullList.create();
        //TODO: fix
        /*for(Content c : ingredients) {
            com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient ingredient = (com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient) c.getContent();
            for(com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient.Value value : ingredient.values) {
                if(value instanceof TagValue tag) {
                    nonNullList.add(FluidIngredient.fromTag(tag.getTag(), ingredient.getAmount()));
                } else {
                    nonNullList.add(FluidIngredient.fromFluid(ingredient.getStacks()[0].getFluid(), ingredient.getAmount()));
                }
            }
        }*/
        return withFluidIngredients(nonNullList);
    }

    public S recipeTier(int condition) {
        params.recipeTier = condition;
        return self();
    }

    public S recipeCircuit(int condition) {
        params.circuitNumber = condition;
        return self();
    }

    public S noCircuit() {
        params.circuitNumber = -1;
        return self();
    }

    public R build() {
        return factory.create(params);
    }

    public void build(RecipeOutput consumer) {
        R recipe = build();
        IRecipeTypeInfo recipeTypeInfo = recipe.getTypeInfo();
        ResourceLocation typeId = recipeTypeInfo.getId();
        ResourceLocation id = recipeId.withPrefix(typeId.getPath() + "/");
        List<String> errors = recipe.validate();
        if(!errors.isEmpty()) {
            errors.add(recipe.getClass().getSimpleName() + "with id " + id + " failed validation:");
            Greate.LOGGER.warn(Joiner.on("\n").join(errors));
        }
        consumer.accept(id, recipe, null, recipeConditions.toArray(new ICondition[0]));
    }
}
