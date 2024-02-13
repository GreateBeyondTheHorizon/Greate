package electrolyte.greate.compat.jei;

import com.google.common.base.Predicates;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.BlockCuttingCategory.CondensedBlockCuttingRecipe;
import com.simibubi.create.content.fluids.potion.PotionMixingRecipes;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.config.ConfigBase.ConfigBool;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.jei.category.*;
import electrolyte.greate.compat.jei.category.GreateRecipeCategory.Info;
import electrolyte.greate.compat.jei.category.TieredBlockCuttingCategory.TieredCondensedBlockCuttingRecipe;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingRecipe;
import electrolyte.greate.content.kinetics.millstone.TieredMillingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredCompactingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.kinetics.saw.TieredSawBlockEntity;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.registry.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

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
                    .addTypedRecipesGT(GTRecipeTypes.MACERATOR_RECIPES, (r) -> TieredMillingRecipe.convertGT(r, ULV))
                    .catalysts(Arrays.stream(Millstones.MILLSTONES)
                            .<Supplier<ItemLike>>map(o -> o::get)
                            .collect(Collectors.toList()))
                    .doubleIconItem(Millstones.NEUTRONIUM_MILLSTONE.get(), AllItems.WHEAT_FLOUR.get())
                    .emptyBackground(177, 68)
                    .build("milling", TieredMillingCategory::new),

                crushing = builder(TieredAbstractCrushingRecipe.class)
                        .addTypedRecipesGT(GTRecipeTypes.MACERATOR_RECIPES, r -> TieredCrushingRecipe.convertGT(r, ULV))
                        .addTypedRecipes(ModRecipeTypes.CRUSHING::getType)
                        .addTypedRecipesExcludingGT(AllRecipeTypes.CRUSHING::getType, GTRecipeTypes.MACERATOR_RECIPES, r -> TieredCrushingRecipe.convertNormalCrushing(r, ULV))
                        .addTypedRecipesExcluding(AllRecipeTypes.MILLING::getType, AllRecipeTypes.CRUSHING::getType, r -> TieredCrushingRecipe.convertNormalCrushing(r, ULV))
                        .catalysts(Arrays.stream(CrushingWheels.CRUSHING_WHEELS)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .doubleIconItem(CrushingWheels.NEUTRONIUM_CRUSHING_WHEEL.get(), AllItems.CRUSHED_GOLD.get())
                        .emptyBackground(177, 115)
                        .build("crushing", TieredCrushingCategory::new),

                pressing = builder(TieredPressingRecipe.class)
                        .addTypedRecipesGT(GTRecipeTypes.BENDER_RECIPES, r -> TieredPressingRecipe.convertGT(r, ULV))
                        .addTypedRecipes(ModRecipeTypes.PRESSING::getType)
                        .addTypedRecipesExcludingGT(AllRecipeTypes.PRESSING::getType, GTRecipeTypes.BENDER_RECIPES, TieredPressingRecipe::convertNormalPressing)
                        .catalysts(Arrays.stream(MechanicalPresses.MECHANICAL_PRESSES)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .doubleIconItem(MechanicalPresses.NEUTRONIUM_MECHANICAL_PRESS.get(), AllItems.IRON_SHEET.get())
                        .emptyBackground(177, 85)
                        .build("pressing", TieredPressingCategory::new),

                mixing = builder(TieredBasinRecipe.class)
                        .addTypedRecipesGT(GTRecipeTypes.MIXER_RECIPES, r -> TieredMixingRecipe.convertGTMixing(r, ULV))
                        .addTypedRecipes(ModRecipeTypes.MIXING::getType)
                        .addTypedRecipesExcludingGT(AllRecipeTypes.MIXING::getType, GTRecipeTypes.MIXER_RECIPES, TieredMixingRecipe::convertUntieredRecipe)
                        .catalysts(Arrays.stream(MechanicalMixers.MECHANICAL_MIXERS)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .doubleIconItem(MechanicalMixers.NEUTRONIUM_MECHANICAL_MIXER.get(), AllBlocks.BASIN.get())
                        .emptyBackground(177, 118)
                        .build("mixing", TieredMixingCategory::standard),

                autoShapeless = builder(TieredBasinRecipe.class)
                        .enableWhen(c -> c.allowShapelessInMixer)
                        .addAllRecipesIf(r -> r instanceof CraftingRecipe &&
                                !(r instanceof IShapedRecipe<?>) &&
                                r.getIngredients().size() > 1 &&
                                !MechanicalPressBlockEntity.canCompress(r) &&
                                !AllRecipeTypes.shouldIgnoreInAutomation(r) &&
                                !ModRecipeTypes.shouldIgnoreInAutomation(r),
                                TieredBasinRecipe::convertShapeless)
                        .catalysts(Arrays.stream(MechanicalMixers.MECHANICAL_MIXERS)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalMixers.NEUTRONIUM_MECHANICAL_MIXER, Items.CRAFTING_TABLE)
                        .emptyBackground(177, 100)
                        .build("automatic_shapeless", TieredMixingCategory::autoShapeless),

                brewing = builder(TieredBasinRecipe.class)
                        .enableWhen(c -> c.allowBrewingInMixer)
                        .addRecipes(() -> {
                            ArrayList<TieredMixingRecipe> brewingRecipes = new ArrayList<>();
                            PotionMixingRecipes.ALL.forEach(potionRecipe -> brewingRecipes.add(TieredMixingRecipe.convertUntieredRecipe(potionRecipe)));
                            return brewingRecipes;
                        })
                        .catalysts(Arrays.stream(MechanicalMixers.MECHANICAL_MIXERS)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalMixers.NEUTRONIUM_MECHANICAL_MIXER.get(), Blocks.BREWING_STAND)
                        .emptyBackground(177, 118)
                        .build("automatic_brewing", TieredMixingCategory::autoBrewing),

                packing = builder(TieredBasinRecipe.class)
                        .addTypedRecipes(AllRecipeTypes.COMPACTING::getType, TieredCompactingRecipe::convertNormalBasin)
                        .addTypedRecipes(ModRecipeTypes.COMPACTING::getType)
                        .catalysts(Arrays.stream(MechanicalPresses.MECHANICAL_PRESSES)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalPresses.NEUTRONIUM_MECHANICAL_PRESS.get(), AllBlocks.BASIN.get())
                        .emptyBackground(177, 118)
                        .build("packing", TieredPackingCategory::standard),

                autoSquare = builder(TieredBasinRecipe.class)
                        .enableWhen(c -> c.allowShapedSquareInPress)
                        .addAllRecipesIf(r -> (r instanceof CraftingRecipe) &&
                                !(r instanceof MechanicalCraftingRecipe) &&
                                MechanicalPressBlockEntity.canCompress(r) &&
                                !AllRecipeTypes.shouldIgnoreInAutomation(r) &&
                                !ModRecipeTypes.shouldIgnoreInAutomation(r),
                                TieredBasinRecipe::convertShapeless)
                        .catalysts(Arrays.stream(MechanicalPresses.MECHANICAL_PRESSES)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalPresses.NEUTRONIUM_MECHANICAL_PRESS, Blocks.CRAFTING_TABLE)
                        .emptyBackground(177, 100)
                        .build("automatic_packing", TieredPackingCategory::autoSquare),

                sawing = builder(TieredCuttingRecipe.class)
                        .addTypedRecipesGT(GTRecipeTypes.CUTTER_RECIPES, r -> TieredCuttingRecipe.convertGTCutter(r, ULV))
                        .addTypedRecipes(ModRecipeTypes.CUTTING::getType)
                        .addTypedRecipesExcludingGT(AllRecipeTypes.CUTTING::getType, GTRecipeTypes.CUTTER_RECIPES, TieredCuttingRecipe::convertNormalSawing)
                        .catalysts(Arrays.stream(Saws.SAWS)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .doubleIconItem(Saws.NEUTRONIUM_SAW, Items.OAK_LOG)
                        .emptyBackground(177, 85)
                        .build("sawing", TieredSawingCategory::new),

                blockCutting = builder(TieredCondensedBlockCuttingRecipe.class)
                        .enableWhen(c -> c.allowStonecuttingOnSaw)
                        .addRecipes(() -> TieredBlockCuttingCategory.condenseRecipes(getTypedRecipesExcluding(RecipeType.STONECUTTING, Predicates.or(AllRecipeTypes::shouldIgnoreInAutomation, ModRecipeTypes::shouldIgnoreInAutomation))))
                        .catalysts(Arrays.stream(Saws.SAWS)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .doubleIconItem(Saws.NEUTRONIUM_SAW.get(), Items.STONE_BRICK_STAIRS)
                        .emptyBackground(177, 70)
                        .build("block_cutting", TieredBlockCuttingCategory::new),

                woodCutting = builder(TieredCondensedBlockCuttingRecipe.class)
                        .enableIf(c -> c.allowWoodcuttingOnSaw.get() &&
                                ModList.get().isLoaded("druidcraft"))
                        .addRecipes(() -> TieredBlockCuttingCategory.condenseRecipes(getTypedRecipesExcluding(TieredSawBlockEntity.woodcuttingRecipeType.get(), Predicates.or(AllRecipeTypes::shouldIgnoreInAutomation, ModRecipeTypes::shouldIgnoreInAutomation))))
                        .catalysts(Arrays.stream(Saws.SAWS)
                                .<Supplier<ItemLike>>map(o -> o::get)
                                .collect(Collectors.toList()))
                        .doubleIconItem(Saws.NEUTRONIUM_SAW.get(), Items.OAK_STAIRS)
                        .emptyBackground(177, 70)
                        .build("wood_cutting", TieredBlockCuttingCategory::new);
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
        ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(
                AllBlocks.MILLSTONE.asStack(), AllBlocks.CRUSHING_WHEEL.asStack(),
                AllBlocks.MECHANICAL_PRESS.asStack(), AllBlocks.MECHANICAL_MIXER.asStack(),
                AllBlocks.MECHANICAL_SAW.asStack()));
        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRuntime(IRuntimeRegistration registration) {
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "milling", AbstractCrushingRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "crushing", AbstractCrushingRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "pressing", PressingRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "mixing", BasinRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "automatic_shapeless", BasinRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "automatic_brewing", BasinRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "packing", BasinRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "automatic_packing", BasinRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "sawing", CuttingRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "block_cutting", CondensedBlockCuttingRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "wood_cutting", CondensedBlockCuttingRecipe.class));
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

        public CategoryBuilder<T> addTypedRecipesGT(GTRecipeType recipeType, Function<GTRecipe, T> converter) {
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

        public final CategoryBuilder<T> catalystStacks(Collection<Supplier<ItemStack>> suppliers) {
            catalysts.addAll(suppliers);
            return this;
        }

        @SafeVarargs
        public final CategoryBuilder<T> catalystStacks(Supplier<ItemStack>... suppliers) {
            catalysts.addAll(List.of(suppliers));
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get().asItem()));
        }

        public CategoryBuilder<T> catalysts(Collection<Supplier<ItemLike>> suppliers) {
            return catalystStacks(suppliers.stream()
                    .map(supplier -> (Supplier<ItemStack>) () -> new ItemStack(supplier.get().asItem()))
                    .collect(Collectors.toList()));
        }

        @SafeVarargs
        public final CategoryBuilder<T> catalysts(Supplier<ItemLike>... suppliers) {
            return catalystStacks(Arrays.stream(suppliers)
                    .map(supplier -> (Supplier<ItemStack>) () -> new ItemStack(supplier.get().asItem()))
                    .collect(Collectors.toList()));
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
        if(recipe1.getIngredients().isEmpty() || gtRecipe.getInputContents(ItemRecipeCapability.CAP).isEmpty() || ((Ingredient) gtRecipe.getInputContents(ItemRecipeCapability.CAP).get(0).getContent()).isEmpty()) return false;
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
