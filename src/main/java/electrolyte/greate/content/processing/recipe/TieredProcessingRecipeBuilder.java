package electrolyte.greate.content.processing.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.forge.SizedIngredientImpl;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.foundation.data.SimpleDatagenIngredient;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Pair;
import com.tterrag.registrate.util.DataIngredient;
import electrolyte.greate.GreateEnums.TIER;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TieredProcessingRecipeBuilder<T extends TieredProcessingRecipe<?>> {


    protected TieredProcessingRecipeFactory<T> factory;
    protected TieredProcessingRecipeBuilder.TieredProcessingRecipeParams params;
    protected List<ICondition> recipeConditions;

    public TieredProcessingRecipeBuilder(TieredProcessingRecipeFactory<T> factory, ResourceLocation recipeId) {
        params = new TieredProcessingRecipeBuilder.TieredProcessingRecipeParams(recipeId);
        recipeConditions = new ArrayList<>();
        this.factory = factory;
    }

    public TieredProcessingRecipeBuilder<T> withItemIngredients(Ingredient... ingredients) {
        return withItemIngredients(NonNullList.of(Ingredient.EMPTY, ingredients));
    }

    public TieredProcessingRecipeBuilder<T> withItemIngredients(NonNullList<Ingredient> ingredients) {
        params.ingredients = ingredients;
        return this;
    }

    public TieredProcessingRecipeBuilder<T> withItemIngredientsGT(List<Content> ingredients) {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        for(Content c : ingredients) {
            SizedIngredient ingredient = (SizedIngredient) c.getContent();
            nonNullList.add(ingredient);
        }
        return withItemIngredients(nonNullList);
    }

    public TieredProcessingRecipeBuilder<T> withSingleItemOutput(ItemStack output) {
        return withItemOutputs(new ProcessingOutput(output, 1));
    }

    public TieredProcessingRecipeBuilder<T> withItemOutputs(ProcessingOutput... outputs) {
        return withItemOutputs(NonNullList.of(ProcessingOutput.EMPTY, outputs));
    }

    public TieredProcessingRecipeBuilder<T> withItemOutputs(NonNullList<ProcessingOutput> outputs) {
        params.results = outputs;
        return this;
    }

    public TieredProcessingRecipeBuilder<T> withItemOutputs(List<ProcessingOutput> outputs) {
        NonNullList<ProcessingOutput> list = NonNullList.create();
        list.addAll(outputs);
        return withItemOutputs(list);
    }

    public TieredProcessingRecipeBuilder<T> withItemOutputsGT(List<Content> list) {
        NonNullList<ProcessingOutput> nonNullList = NonNullList.create();
        for(Content c : list) {
            ItemStack[] items = ((SizedIngredientImpl) c.content).getItems();
            for (ItemStack item : items) {
                nonNullList.add(new ProcessingOutput(item, c.chance));
            }
        }
        return withItemOutputs(nonNullList);
    }

    public TieredProcessingRecipeBuilder<T> withFluidIngredients(FluidIngredient... ingredients) {
        return withFluidIngredients(NonNullList.of(FluidIngredient.EMPTY, ingredients));
    }

    public TieredProcessingRecipeBuilder<T> withFluidIngredients(NonNullList<FluidIngredient> ingredients) {
        params.fluidIngredients = ingredients;
        return this;
    }

    public TieredProcessingRecipeBuilder<T> withFluidOutputs(FluidStack... outputs) {
        return withFluidOutputs(NonNullList.of(FluidStack.EMPTY, outputs));
    }

    public TieredProcessingRecipeBuilder<T> withFluidOutputs(NonNullList<FluidStack> outputs) {
        params.fluidResults = outputs;
        return this;
    }

    public TieredProcessingRecipeBuilder<T> duration(int ticks) {
        params.processingDuration = ticks;
        return this;
    }

    public TieredProcessingRecipeBuilder<T> averageProcessingDuration() {
        return duration(100);
    }

    public TieredProcessingRecipeBuilder<T> requiresHeat(HeatCondition condition) {
        params.requiredHeat = condition;
        return this;
    }

    public TieredProcessingRecipeBuilder<T> recipeTier(TIER condition) {
        params.recipeTier = condition;
        return this;
    }

    public T build() {
        return factory.create(params);
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new TieredProcessingRecipeBuilder.DataGenResult<>(build(), recipeConditions));
    }

    public TieredProcessingRecipeBuilder<T> require(TagKey<Item> tag) {
        return require(Ingredient.of(tag));
    }

    public TieredProcessingRecipeBuilder<T> require(ItemLike item) {
        return require(Ingredient.of(item));
    }

    public TieredProcessingRecipeBuilder<T> require(Ingredient ingredient) {
        params.ingredients.add(ingredient);
        return this;
    }

    public TieredProcessingRecipeBuilder<T> require(Mods mod, String id) {
        params.ingredients.add(new SimpleDatagenIngredient(mod, id));
        return this;
    }

    public TieredProcessingRecipeBuilder<T> require(ResourceLocation ingredient) {
        params.ingredients.add(DataIngredient.ingredient(null, ingredient));
        return this;
    }

    public TieredProcessingRecipeBuilder<T> require(Fluid fluid, int amount) {
        return require(FluidIngredient.fromFluid(fluid, amount));
    }

    public TieredProcessingRecipeBuilder<T> require(TagKey<Fluid> fluidTag, int amount) {
        return require(FluidIngredient.fromTag(fluidTag, amount));
    }

    public TieredProcessingRecipeBuilder<T> require(FluidIngredient ingredient) {
        params.fluidIngredients.add(ingredient);
        return this;
    }

    public TieredProcessingRecipeBuilder<T> output(ItemLike item) {
        return output(item, 1);
    }

    public TieredProcessingRecipeBuilder<T> output(float chance, ItemLike item) {
        return output(chance, item, 1);
    }

    public TieredProcessingRecipeBuilder<T> output(ItemLike item, int amount) {
        return output(1, item, amount);
    }

    public TieredProcessingRecipeBuilder<T> output(float chance, ItemLike item, int amount) {
        return output(chance, new ItemStack(item, amount));
    }

    public TieredProcessingRecipeBuilder<T> output(ItemStack output) {
        return output(1, output);
    }

    public TieredProcessingRecipeBuilder<T> output(float chance, ItemStack output) {
        return output(new ProcessingOutput(output, chance));
    }

    public TieredProcessingRecipeBuilder<T> output(float chance, Mods mod, String id, int amount) {
        return output(new ProcessingOutput(Pair.of(mod.asResource(id), amount), chance));
    }

    public TieredProcessingRecipeBuilder<T> output(float chance, ResourceLocation registryName, int amount) {
        return output(new ProcessingOutput(Pair.of(registryName, amount), chance));
    }

    public TieredProcessingRecipeBuilder<T> output(ProcessingOutput output) {
        params.results.add(output);
        return this;
    }

    public TieredProcessingRecipeBuilder<T> output(Fluid fluid, int amount) {
        fluid = FluidHelper.convertToStill(fluid);
        return output(new FluidStack(fluid, amount));
    }

    public TieredProcessingRecipeBuilder<T> output(FluidStack fluidStack) {
        params.fluidResults.add(fluidStack);
        return this;
    }

    public TieredProcessingRecipeBuilder<T> toolNotConsumed() {
        params.keepHeldItem = true;
        return this;
    }

    public TieredProcessingRecipeBuilder<T> whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }

    public TieredProcessingRecipeBuilder<T> whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public TieredProcessingRecipeBuilder<T> withCondition(ICondition condition) {
        recipeConditions.add(condition);
        return this;
    }

    @FunctionalInterface
    public interface TieredProcessingRecipeFactory<T extends TieredProcessingRecipe<?>> {
        T create(TieredProcessingRecipeParams params);
    }

    public static class TieredProcessingRecipeParams extends ProcessingRecipeParams {

        protected ResourceLocation id;
        protected NonNullList<Ingredient> ingredients;
        protected NonNullList<ProcessingOutput> results;
        protected NonNullList<FluidIngredient> fluidIngredients;
        protected NonNullList<FluidStack> fluidResults;
        protected int processingDuration;
        protected HeatCondition requiredHeat;
        protected TIER recipeTier;

        public boolean keepHeldItem;

        protected TieredProcessingRecipeParams(ResourceLocation id) {
            super(id);
            this.id = id;
            ingredients = NonNullList.create();
            results = NonNullList.create();
            fluidIngredients = NonNullList.create();
            fluidResults = NonNullList.create();
            processingDuration = 0;
            requiredHeat = HeatCondition.NONE;
            recipeTier = TIER.NONE;
            keepHeldItem = false;
        }
    }

    public static class DataGenResult<S extends TieredProcessingRecipe<?>> implements FinishedRecipe {

        private List<ICondition> recipeConditions;
        private TieredProcessingRecipeSerializer<S> serializer;
        private ResourceLocation id;
        private S recipe;

        @SuppressWarnings("unchecked")
        public DataGenResult(S recipe, List<ICondition> recipeConditions) {
            this.recipe = recipe;
            this.recipeConditions = recipeConditions;
            IRecipeTypeInfo recipeType = this.recipe.getTypeInfo();
            ResourceLocation typeId = recipeType.getId();

            if (!(recipeType.getSerializer() instanceof TieredProcessingRecipeSerializer))
                throw new IllegalStateException("Cannot datagen ProcessingRecipe of type: " + typeId);

            this.id = new ResourceLocation(recipe.getId().getNamespace(),
                    typeId.getPath() + "/" + recipe.getId().getPath());
            this.serializer = (TieredProcessingRecipeSerializer<S>) recipe.getSerializer();
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            serializer.write(json, recipe);
            if (recipeConditions.isEmpty())
                return;

            JsonArray conds = new JsonArray();
            recipeConditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            json.add("conditions", conds);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return serializer;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }
}
