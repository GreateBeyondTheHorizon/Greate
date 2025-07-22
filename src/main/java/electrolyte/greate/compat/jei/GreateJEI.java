package electrolyte.greate.compat.jei;

import com.google.common.base.Predicates;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.BlockCuttingCategory.CondensedBlockCuttingRecipe;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.jei.category.*;
import electrolyte.greate.compat.jei.category.GreateRecipeCategory.Info;
import electrolyte.greate.compat.jei.category.TieredBlockCuttingCategory.TieredCondensedBlockCuttingRecipe;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlock;
import electrolyte.greate.content.kinetics.fan.processing.TieredHauntingRecipe;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.registry.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.createmod.catnip.config.ConfigBase.ConfigBool;
import net.createmod.catnip.lang.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static electrolyte.greate.registry.EncasedFans.FANS;
import static mezz.jei.api.recipe.RecipeType.createRecipeHolderType;

@JeiPlugin
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class GreateJEI implements IModPlugin {

    private static final ResourceLocation ID = Greate.id("jei_plugin");
    private final List<GreateRecipeCategory<?>> allCategories = new ArrayList<>();
    private IIngredientManager ingredientManager;

    private void loadCategories() {
        allCategories.clear();

        GreateRecipeCategory<?>

                milling = builder(TieredAbstractCrushingRecipe.class)
                    .addTypedRecipes(ModRecipeTypes.MILLING)
                    .catalysts(Millstones.MILLSTONES)
                    .doubleIconItem(Millstones.NEUTRONIUM_MILLSTONE.get(), AllItems.WHEAT_FLOUR.get())
                    .emptyBackground(177, 78)
                    .build("milling", TieredMillingCategory::new),

                crushing = builder(TieredAbstractCrushingRecipe.class)
                        .addTypedRecipes(ModRecipeTypes.CRUSHING::getType)
                        .addTypedRecipesExcluding(ModRecipeTypes.MILLING::getType, ModRecipeTypes.CRUSHING::getType)
                        .catalysts(CrushingWheels.CRUSHING_WHEELS)
                        .doubleIconItem(CrushingWheels.NEUTRONIUM_CRUSHING_WHEEL.get(), AllItems.CRUSHED_GOLD.get())
                        .emptyBackground(177, 125)
                        .build("crushing", TieredCrushingCategory::new),

                washing = builder(TieredSplashingRecipe.class)
                        .addTypedRecipes(ModRecipeTypes.SPLASHING::getType)
                        .catalysts(EncasedFans.FANS)
                        .doubleIconItem(AllItems.PROPELLER.get(), Items.WATER_BUCKET)
                        .emptyBackground(178, 87)
                        .build("fan_washing", TieredFanWashingCategory::new),

                haunting = builder(TieredHauntingRecipe.class)
                        .addTypedRecipes(ModRecipeTypes.HAUNTING::getType)
                        .catalysts(EncasedFans.FANS)
                        .doubleIconItem(AllItems.PROPELLER.get(), Items.SOUL_CAMPFIRE)
                        .emptyBackground(178, 87)
                        .build("fan_haunting", TieredFanHauntingCategory::new),

                pressing = builder(TieredPressingRecipe.class)
                        .addTypedRecipes(ModRecipeTypes.PRESSING::getType)
                        .catalysts(MechanicalPresses.MECHANICAL_PRESSES)
                        .doubleIconItem(MechanicalPresses.NEUTRONIUM_MECHANICAL_PRESS.get(), AllItems.IRON_SHEET.get())
                        .emptyBackground(177, 85)
                        .build("pressing", TieredPressingCategory::new),

                mixing = builder(TieredBasinRecipe.class)
                        .addTypedRecipes(ModRecipeTypes.MIXING::getType)
                        .catalysts(MechanicalMixers.MECHANICAL_MIXERS)
                        .doubleIconItem(MechanicalMixers.NEUTRONIUM_MECHANICAL_MIXER.get(), AllBlocks.BASIN.get())
                        .emptyBackground(177, 118)
                        .build("mixing", TieredMixingCategory::standard),

                autoShapeless = builder(TieredBasinRecipe.class)
                        .enableWhen(AllConfigs.server().recipes.allowShapelessInMixer)
                        .addAllRecipesIf(r -> r.value() instanceof CraftingRecipe &&
                                !(r.value() instanceof ShapedRecipe) &&
                                r.value().getIngredients().size() > 1 &&
                                !MechanicalPressBlockEntity.canCompress(r.value()) &&
                                !AllRecipeTypes.shouldIgnoreInAutomation(r) &&
                                !ModRecipeTypes.shouldIgnoreInAutomation(r),
                                TieredBasinRecipe::convertShapeless)
                        .catalysts(MechanicalMixers.MECHANICAL_MIXERS)
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalMixers.NEUTRONIUM_MECHANICAL_MIXER, Items.CRAFTING_TABLE)
                        .emptyBackground(177, 100)
                        .build("automatic_shapeless", TieredMixingCategory::autoShapeless),

                brewing = builder(TieredBasinRecipe.class)
                        .enableWhen(AllConfigs.server().recipes.allowBrewingInMixer)
                        .addTypedRecipes(ModRecipeTypes.BREWING::getType)
                        .catalysts(MechanicalMixers.MECHANICAL_MIXERS)
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalMixers.NEUTRONIUM_MECHANICAL_MIXER.get(), Blocks.BREWING_STAND)
                        .emptyBackground(177, 118)
                        .build("automatic_brewing", TieredMixingCategory::autoBrewing),

                packing = builder(TieredBasinRecipe.class)
                        .addTypedRecipes(ModRecipeTypes.COMPACTING::getType)
                        .catalysts(MechanicalPresses.MECHANICAL_PRESSES)
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalPresses.NEUTRONIUM_MECHANICAL_PRESS.get(), AllBlocks.BASIN.get())
                        .emptyBackground(177, 118)
                        .build("packing", TieredPackingCategory::standard),

                autoSquare = builder(TieredBasinRecipe.class)
                        .enableWhen(AllConfigs.server().recipes.allowShapedSquareInPress)
                        .addAllRecipesIf(r -> (r.value() instanceof CraftingRecipe) &&
                                !(r.value() instanceof MechanicalCraftingRecipe) &&
                                MechanicalPressBlockEntity.canCompress(r.value()) &&
                                !AllRecipeTypes.shouldIgnoreInAutomation(r) &&
                                !ModRecipeTypes.shouldIgnoreInAutomation(r),
                                TieredBasinRecipe::convertShapeless)
                        .catalysts(MechanicalPresses.MECHANICAL_PRESSES)
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleIconItem(MechanicalPresses.NEUTRONIUM_MECHANICAL_PRESS, Blocks.CRAFTING_TABLE)
                        .emptyBackground(177, 100)
                        .build("automatic_packing", TieredPackingCategory::autoSquare),

                sawing = builder(TieredCuttingRecipe.class)
                        .addTypedRecipes(ModRecipeTypes.CUTTING::getType)
                        .catalysts(Saws.SAWS)
                        .doubleIconItem(Saws.NEUTRONIUM_SAW, Items.OAK_LOG)
                        .emptyBackground(177, 85)
                        .build("sawing", TieredSawingCategory::new),

                blockCutting = builder(TieredCondensedBlockCuttingRecipe.class)
                        .enableWhen(AllConfigs.server().recipes.allowStonecuttingOnSaw)
                        .addRecipes(() -> TieredBlockCuttingCategory.condenseRecipes(getTypedRecipesExcluding(RecipeType.STONECUTTING, Predicates.or(AllRecipeTypes::shouldIgnoreInAutomation, ModRecipeTypes::shouldIgnoreInAutomation))))
                        .catalysts(Saws.SAWS)
                        .doubleIconItem(Saws.NEUTRONIUM_SAW.get(), Items.STONE_BRICK_STAIRS)
                        .emptyBackground(177, 70)
                        .build("block_cutting", TieredBlockCuttingCategory::new);
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
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "fan_haunting", HauntingRecipe.class));
        registration.getRecipeManager().hideRecipeCategory(mezz.jei.api.recipe.RecipeType.create(Create.ID, "fan_washing", SplashingRecipe.class));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
        for(BlockEntry<TieredEncasedFanBlock> fan : FANS) {
            registration.addRecipeCatalyst(fan.asStack(), mezz.jei.api.recipe.RecipeType.create(Create.ID, "fan_blasting", AbstractCookingRecipe.class));
            registration.addRecipeCatalyst(fan.asStack(), mezz.jei.api.recipe.RecipeType.create(Create.ID, "fan_smoking", SmokingRecipe.class));
        }
    }

    private class CategoryBuilder<T extends Recipe<? extends RecipeInput>> {
        private final Class<? extends T> recipeClass;
        private Supplier<Boolean> config = () -> true;

        private IDrawable background;
        private IDrawable icon;

        private final List<Consumer<List<RecipeHolder<T>>>> recipeListConsumers = new ArrayList<>();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

        public CategoryBuilder(Class<? extends T> recipeClass) {
            this.recipeClass = recipeClass;
        }

        public CategoryBuilder<T> enableWhen(Supplier<Boolean> predicate) {
            this.config = predicate;
            return this;
        }

        public CategoryBuilder<T> enableWhen(ConfigBool configValue) {
            this.config = configValue::get;
            return this;
        }

        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<RecipeHolder<T>>> consumer) {
            recipeListConsumers.add(consumer);
            return this;
        }

        public CategoryBuilder<T> addRecipes(Supplier<Collection<? extends RecipeHolder<T>>> collection) {
            return addRecipeListConsumer(recipes -> recipes.addAll(collection.get()));
        }

        public CategoryBuilder<T> addAllRecipesIf(Predicate<RecipeHolder<T>> predicate) {
            return addRecipeListConsumer(recipes -> consumeAllRecipesOfType(recipe -> {
                if(predicate.test(recipe)) {
                    recipes.add(recipe);
                }
            }));
        }

        public CategoryBuilder<T> addAllRecipesIf(Predicate<RecipeHolder<?>> predicate, Function<RecipeHolder<?>, RecipeHolder<T>> converter) {
            return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
                if(predicate.test(recipe)) {
                    recipes.add(converter.apply(recipe));
                }
            }));
        }

        public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
            return addTypedRecipes(recipeTypeEntry::getType);
        }

        public <I extends RecipeInput, R extends Recipe<I>> CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<R>> recipeType) {
            return addRecipeListConsumer(recipes -> GreateJEI.<T>consumeTypedRecipes(recipe -> {
                if(recipeClass.isInstance(recipe.value())) {
                    recipes.add((RecipeHolder<T>) recipe);
                }
            }, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType, Function<RecipeHolder<?>, RecipeHolder<T>> converter) {
            return addRecipeListConsumer(recipes -> GreateJEI.<T>consumeTypedRecipes(recipe -> recipes.add(converter.apply(recipe)), recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipesIf(Supplier<RecipeType<? extends T>> recipeType, Predicate<RecipeHolder<?>> predicate) {
            return addRecipeListConsumer(recipes -> consumeTypedRecipesTyped(recipe -> {
                if(predicate.test(recipe)) recipes.add(recipe);
            }, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipesExcluding(Supplier<RecipeType<? extends T>> recipeType, Supplier<RecipeType<? extends T>> excluded) {
            return addRecipeListConsumer(recipes -> {
                List<RecipeHolder<?>> excludedRecipes = getTypedRecipes(excluded.get());
                consumeTypedRecipesTyped(recipe -> {
                    for (RecipeHolder<?> excludedRecipe : excludedRecipes) {
                        if (doInputsMatch(recipe.value(), excludedRecipe.value())) {
                            return;
                        }
                    }
                    recipes.add(recipe);
                }, recipeType.get());
            });
        }

        public CategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> {
               List<RecipeHolder<?>> excludedRecipes = getTypedRecipes(recipeType.get());
               recipes.removeIf(recipe -> {
                  for(RecipeHolder<?> excludedRecipe : excludedRecipes) {
                      if(doInputsMatch(recipe.value(), excludedRecipe.value()) && doOutputsMatch(recipe.value(), excludedRecipe.value())) return true;
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

        public final CategoryBuilder<T> catalysts(BlockEntry<?>[] blocks) {
            return catalystStacks(Arrays.stream(blocks).map(s -> (Supplier<ItemStack>) () -> new ItemStack(s.get().asItem())).toList());
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
            Supplier<List<RecipeHolder<T>>> recipesSupplier;
            if(config.get()) {
                recipesSupplier = () -> {
                    List<RecipeHolder<T>> recipes = new ArrayList<>();
                    for(Consumer<List<RecipeHolder<T>>> consumer : recipeListConsumers) {
                        consumer.accept(recipes);
                    }
                    return recipes;
                };
            } else {
                recipesSupplier = Collections::emptyList;
            }

            GreateRecipeCategory.Info<T> info = new Info<>(
                    createRecipeHolderType(Greate.id(name)),
                    Lang.builder(Greate.MOD_ID).translate("recipe." + name).component(),
                            background,
                            icon,
                            recipesSupplier,
                            catalysts);
            GreateRecipeCategory<T> category = factory.create(info);
            allCategories.add(category);
            return category;
        }

        private void consumeAllRecipesOfType(Consumer<RecipeHolder<T>> consumer) {
            GreateJEI.consumeAllRecipes(recipeHolder -> {
                if(recipeClass.isInstance(recipeHolder.value())) {
                    consumer.accept((RecipeHolder<T>) recipeHolder);
                }
            });
        }

        private void consumeTypedRecipesTyped(Consumer<RecipeHolder<T>> consumer, RecipeType<?> type) {
            consumeTypedRecipes(recipeHolder -> {
                if(recipeClass.isInstance(recipeHolder.value())) {
                    consumer.accept((RecipeHolder<T>) recipeHolder);
                }
            }, type);
        }
    }

    public static void consumeAllRecipes(Consumer<RecipeHolder<?>> consumer) {
        Minecraft.getInstance().getConnection().getRecipeManager().getRecipes().forEach(consumer);
    }

    public static <T extends Recipe<?>> void consumeTypedRecipes(Consumer<RecipeHolder<?>> consumer, RecipeType<?> type) {
        List<? extends RecipeHolder<?>> map = Minecraft.getInstance().getConnection().getRecipeManager().getAllRecipesFor((RecipeType) type);
        if(!map.isEmpty()) {
            map.forEach(consumer);
        }
    }

    public static List<RecipeHolder<?>> getTypedRecipes(RecipeType<?> type) {
        List<RecipeHolder<?>> recipes = new ArrayList<>();
        consumeTypedRecipes(recipes::add, type);
        return recipes;
    }

    public static List<RecipeHolder<?>> getTypedRecipesExcluding(RecipeType<?> type, Predicate<RecipeHolder<?>> exclusionPredicate) {
        List<RecipeHolder<?>> recipes = getTypedRecipes(type);
        recipes.removeIf(exclusionPredicate);
        return recipes;
    }

    public static boolean doInputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        if(recipe1.getIngredients().isEmpty() || recipe2.getIngredients().isEmpty()) return false;
        ItemStack[] matchingStacks = recipe1.getIngredients().get(0).getItems();
        if(matchingStacks.length == 0) return false;
        return recipe2.getIngredients().get(0).test(matchingStacks[0]);
    }

    public static boolean doOutputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        return ItemStack.isSameItem(recipe1.getResultItem(registryAccess), recipe2.getResultItem(registryAccess));
    }
}
