package electrolyte.greate.compat.jei;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.foundation.config.ConfigBase.ConfigBool;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.jei.category.GreateRecipeCategory;
import electrolyte.greate.compat.jei.category.GreateRecipeCategory.Info;
import electrolyte.greate.compat.jei.category.TieredCrushingCategory;
import electrolyte.greate.compat.jei.category.TieredMillingCategory;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingRecipe;
import electrolyte.greate.content.kinetics.millstone.TieredMillingRecipe;
import electrolyte.greate.registry.CrushingWheels;
import electrolyte.greate.registry.Millstones;
import electrolyte.greate.registry.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@JeiPlugin
@ParametersAreNonnullByDefault
public class GreateJEI implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(Greate.MOD_ID, "jei_plugin");
    private final List<GreateRecipeCategory<?>> allCategories = new ArrayList<>();
    private IIngredientManager ingredientManager;

    private void loadCategories() {
        allCategories.clear();

        GreateRecipeCategory<?>

                milling = builder(TieredAbstractCrushingRecipe.class)
                    .addTypedRecipes(ModRecipeTypes.MILLING)
                    .addTypedRecipes(AllRecipeTypes.MILLING::getType, TieredMillingRecipe::convertNormalMilling)
                    .addTypedRecipes(GTRecipeTypes.MACERATOR_RECIPES, TieredMillingRecipe::convertGT)
                    .catalyst(Millstones.ANDESITE_MILLSTONE::get)
                    .catalyst(Millstones.STEEL_MILLSTONE::get)
                    .catalyst(Millstones.ALUMINIUM_MILLSTONE::get)
                    .catalyst(Millstones.STAINLESS_STEEL_MILLSTONE::get)
                    .catalyst(Millstones.TITANIUM_MILLSTONE::get)
                    .catalyst(Millstones.TUNGSTEN_STEEL_MILLSTONE::get)
                    .catalyst(Millstones.PALLADIUM_MILLSTONE::get)
                    .catalyst(Millstones.NAQUADAH_MILLSTONE::get)
                    .catalyst(Millstones.DARMSTADTIUM_MILLSTONE::get)
                    .catalyst(Millstones.NEUTRONIUM_MILLSTONE::get)
                    .doubleIconItem(Millstones.NEUTRONIUM_MILLSTONE.get(), AllItems.WHEAT_FLOUR.get())
                    .emptyBackground(177, 68)
                    .build("milling", TieredMillingCategory::new),

                crushing = builder(TieredAbstractCrushingRecipe.class)
                        .addTypedRecipes(GTRecipeTypes.MACERATOR_RECIPES, r -> TieredCrushingRecipe.convertGT(r, 0))
                        .addTypedRecipes(ModRecipeTypes.CRUSHING::getType, r -> TieredCrushingRecipe.convertTieredCrushing(r, 0))
                        .addTypedRecipesExcludingGT(AllRecipeTypes.CRUSHING::getType, GTRecipeTypes.MACERATOR_RECIPES, r -> TieredCrushingRecipe.convertNormalCrushing(r, 0))
                        .addTypedRecipesExcluding(AllRecipeTypes.MILLING::getType, AllRecipeTypes.CRUSHING::getType, r -> TieredCrushingRecipe.convertNormalCrushing(r, 0))
                        .catalyst(CrushingWheels.ANDESITE_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.STEEL_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.ALUMINIUM_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.STAINLESS_STEEL_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.TITANIUM_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.TUNGSTEN_STEEL_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.PALLADIUM_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.NAQUADAH_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.DARMSTADTIUM_CRUSHING_WHEEL::get)
                        .catalyst(CrushingWheels.NEUTRONIUM_CRUSHING_WHEEL::get)
                        .doubleIconItem(CrushingWheels.NEUTRONIUM_CRUSHING_WHEEL.get(), AllItems.CRUSHED_GOLD.get())
                        .emptyBackground(177, 115)
                        .build("crushing", TieredCrushingCategory::new);
    }

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        loadCategories();
        registration.addRecipeCategories(allCategories.toArray(IRecipeCategory[]::new));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(AllBlocks.MILLSTONE.asStack()));
        ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(AllBlocks.CRUSHING_WHEEL.asStack()));
        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
    }

    private class CategoryBuilder<T extends Recipe<?>> {
        private final Class<? extends T> recipeClass;
        private Predicate<CRecipes> predicate = cRecipes -> true;

        private IDrawable background;
        private IDrawable icon;

        private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

        public CategoryBuilder(Class<? extends T> recipeClass) {
            this.recipeClass = recipeClass;
        }

        public CategoryBuilder<T> enableIf(Predicate<CRecipes> predicate) {
            this.predicate = predicate;
            return this;
        }

        public CategoryBuilder<T> enableWhen(Function<CRecipes, ConfigBool> configValue) {
            predicate = c -> configValue.apply(c).get();
            return this;
        }

        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
            recipeListConsumers.add(consumer);
            return this;
        }

        public CategoryBuilder<T> addRecipes(Supplier<Collection<? extends T>> collection) {
            return addRecipeListConsumer(recipes -> recipes.addAll(collection.get()));
        }

        public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> predicate) {
            return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
                if(predicate.test(recipe)) {
                    recipes.add((T) recipe);
                }
            }));
        }

        public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> predicate, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
                if(predicate.test(recipe)) {
                    recipes.add(converter.apply(recipe));
                }
            }));
        }

        public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
            return addTypedRecipes(recipeTypeEntry::getType);
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> GreateJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> GreateJEI.<T>consumeTypedRecipes(recipe -> recipes.add(converter.apply(recipe)), recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipes(GTRecipeType recipeType, Function<GTRecipe, T> converter) {
            return addRecipeListConsumer(recipes -> GreateJEI.<T>consumeTypedRecipes(recipe -> recipes.add(converter.apply((GTRecipe) recipe)), recipeType));
        }

        public CategoryBuilder<T> addTypedRecipesIf(Supplier<RecipeType<? extends T>> recipeType, Predicate<Recipe<?>> predicate) {
            return addRecipeListConsumer(recipes -> GreateJEI.<T>consumeTypedRecipes(recipe -> {
                if(predicate.test(recipe)) recipes.add(recipe);
            }, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipesExcluding(Supplier<RecipeType<? extends T>> recipeType, Supplier<RecipeType<? extends T>> excluded, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> {
                List<Recipe<?>> excludedRecipes = getTypedRecipes(excluded.get());
                GreateJEI.<T>consumeTypedRecipes(recipe -> {
                   for(Recipe<?> excludedRecipe : excludedRecipes) {
                       if(doInputsMatch(recipe, excludedRecipe)) {
                           return;
                       }
                   }
                   recipes.add(converter.apply(recipe));
                }, recipeType.get());
            });
        }

        public CategoryBuilder<T> addTypedRecipesExcludingGT(Supplier<RecipeType<? extends T>> recipeType, GTRecipeType excluded, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> {
                List<Recipe<?>> excludedRecipes = getTypedRecipes(excluded);
                GreateJEI.<T>consumeTypedRecipes(recipe -> {
                    for(Recipe<?> excludedRecipe : excludedRecipes) {
                        if(doInputsMatchGT(recipe, excludedRecipe)) {
                            return;
                        }
                    }
                    recipes.add(converter.apply(recipe));
                }, recipeType.get());
            });
        }

        public CategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> {
               List<Recipe<?>> excludedRecipes = getTypedRecipes(recipeType.get());
               recipes.removeIf(recipe -> {
                  for(Recipe<?> excludedRecipe : excludedRecipes) {
                      if(doInputsMatch(recipe, excludedRecipe) && doOutputsMatch(recipe, excludedRecipe)) return true;
                  }
                   return false;
               });
            });
        }

        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            catalysts.add(supplier);
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get().asItem()));
        }

        public CategoryBuilder<T> icon(IDrawable icon) {
            this.icon = icon;
            return this;
        }

        public CategoryBuilder<T> itemIcon(ItemLike item) {
            icon(new ItemIcon(() -> new ItemStack(item)));
            return this;
        }

        public CategoryBuilder<T> doubleIconItem(ItemLike item1, ItemLike item2) {
            icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
            return this;
        }

        public CategoryBuilder<T> background(IDrawable background) {
            this.background = background;
            return this;
        }

        public CategoryBuilder<T> emptyBackground(int with, int height) {
            background(new EmptyBackground(with, height));
            return this;
        }

        public GreateRecipeCategory<T> build(String name, GreateRecipeCategory.Factory<T> factory) {
            Supplier<List<T>> recipesSupplier;
            if(predicate.test(AllConfigs.server().recipes)) {
                recipesSupplier = () -> {
                    List<T> recipes = new ArrayList<>();
                    for(Consumer<List<T>> consumer : recipeListConsumers) {
                        consumer.accept(recipes);
                    }
                    return recipes;
                };
            } else {
                recipesSupplier = Collections::emptyList;
            }

            GreateRecipeCategory.Info<T> info = new Info<>(
                    new mezz.jei.api.recipe.RecipeType<>(new ResourceLocation(Greate.MOD_ID, name), recipeClass),
                    Lang.builder(Greate.MOD_ID).translate("recipe." + name).component(), background, icon, recipesSupplier, catalysts);
            GreateRecipeCategory<T> category = factory.create(info);
            allCategories.add(category);
            return category;
        }
    }

    public static void consumeAllRecipes(Consumer<Recipe<?>> consumer) {
        Minecraft.getInstance().getConnection().getRecipeManager().getRecipes().forEach(consumer);
    }

    public static <T extends Recipe<?>> void consumeTypedRecipes(Consumer<T> consumer, RecipeType<?> type) {
        Map<ResourceLocation, Recipe<?>> map = Minecraft.getInstance().getConnection().getRecipeManager().recipes.get(type);
        if(map != null) {
            map.values().forEach(recipe -> consumer.accept((T) recipe));
        }
    }

    public static List<Recipe<?>> getTypedRecipes(RecipeType<?> type) {
        List<Recipe<?>> recipes = new ArrayList<>();
        consumeTypedRecipes(recipes::add, type);
        return recipes;
    }

    public static List<Recipe<?>> getTypedRecipesExcluding(RecipeType<?> type, Predicate<Recipe<?>> exclusionPredicate) {
        List<Recipe<?>> recipes = getTypedRecipes(type);
        recipes.removeIf(exclusionPredicate);
        return recipes;
    }

    public static boolean doInputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        if(recipe1.getIngredients().isEmpty() || recipe2.getIngredients().isEmpty()) return false;
        ItemStack[] matchingStacks = recipe1.getIngredients().get(0).getItems();
        if(matchingStacks.length == 0) return false;
        return recipe2.getIngredients().get(0).test(matchingStacks[0]);
    }

    public static boolean doInputsMatchGT(Recipe<?> recipe1, Recipe<?> recipe2) {
        GTRecipe gtRecipe = (GTRecipe) recipe2;
        if(recipe1.getIngredients().isEmpty() || ((Ingredient) gtRecipe.getInputContents(ItemRecipeCapability.CAP).get(0).getContent()).isEmpty()) return false;
        ItemStack[] matchingStacks = recipe1.getIngredients().get(0).getItems();
        if(matchingStacks.length == 0) return false;
        Ingredient ing = (Ingredient) gtRecipe.getInputContents(ItemRecipeCapability.CAP).get(0).getContent();
        return ing.test(matchingStacks[0]);
    }

    public static boolean doOutputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        return ItemStack.isSameItem(recipe1.getResultItem(registryAccess), recipe2.getResultItem(registryAccess));
    }
}
