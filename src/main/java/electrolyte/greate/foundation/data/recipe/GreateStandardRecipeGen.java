package electrolyte.greate.foundation.data.recipe;

import com.google.common.base.Supplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.registry.Cogwheels;
import electrolyte.greate.registry.GreateTags;
import electrolyte.greate.registry.ModItems;
import electrolyte.greate.registry.Shafts;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class GreateStandardRecipeGen extends GreateRecipeProvider {
    public GreateStandardRecipeGen(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public String getName() {
        return "Greate's Recipes";
    }

    private Marker MATERIALS = enterFolder("materials");

    GeneratedRecipe
            ANDESITE_ALLOY = create(AllItems.ANDESITE_ALLOY).unlockedByTag(GreateTags.forgeItemTag("nuggets/iron"))
            .viaShaped(b -> b.define('N', GreateTags.forgeItemTag("nuggets/iron"))
                    .define('A', Blocks.ANDESITE)
                    .define('F', GreateTags.forgeItemTag("tools/files"))
                    .define('H', GreateTags.forgeItemTag("tools/hammers"))
                    .pattern("NA")
                    .pattern("AN")
                    .pattern("FH")),
            ANDESITE_ALLOY_FROM_ZINC = create(AllItems.ANDESITE_ALLOY).withSuffix("_from_zinc").unlockedByTag(GreateTags.forgeItemTag("nuggets/zinc"))
                    .viaShaped(b -> b.define('N', GreateTags.forgeItemTag("nuggets/zinc"))
                            .define('A', Blocks.ANDESITE)
                            .define('F', GreateTags.forgeItemTag("tools/files"))
                            .define('H', GreateTags.forgeItemTag("tools/hammers"))
                            .pattern("NA")
                            .pattern("AN")
                            .pattern("FH")),
            STEEL_ALLOY = createMaterialAlloyRecipe(ModItems.STEEL_ALLOY),
            ALUMINIUM_ALLOY = createMaterialAlloyRecipe(ModItems.ALUMINIUM_ALLOY),
            STAINLESS_STEEL_ALLOY = createMaterialAlloyRecipe(ModItems.STAINLESS_STEEL_ALLOY),
            TITANIUM_ALLOY = createMaterialAlloyRecipe(ModItems.TITANIUM_ALLOY),
            TUNGSTENSTEEL_ALLOY = createMaterialAlloyRecipe(ModItems.TUNGSTENSTEEL_ALLOY),
            PALLADIUM_ALLOY = createMaterialAlloyRecipe(ModItems.PALLADIUM_ALLOY),
            NAQUADAH_ALLOY = createMaterialAlloyRecipe(ModItems.NAQUADAH_ALLOY),
            DARMSTADTIUM_ALLOY = createMaterialAlloyRecipe(ModItems.DARMSTADTIUM_ALLOY),
            NEUTRONIUM_ALLOY = createMaterialAlloyRecipe(ModItems.NEUTRONIUM_ALLOY);

    private Marker KINETICS = enterFolder("kinetics");

    GeneratedRecipe
            ANDESITE_SHAFT = createMaterialShaftRecipe(Shafts.ANDESITE_SHAFT, AllItems.ANDESITE_ALLOY),
            STEEL_SHAFT = createMaterialShaftRecipe(Shafts.STEEL_SHAFT, ModItems.STEEL_ALLOY),
            ALUMINIUM_SHAFT = createMaterialShaftRecipe(Shafts.ALUMINIUM_SHAFT, ModItems.ALUMINIUM_ALLOY),
            STAINLESS_STEEL_SHAFT = createMaterialShaftRecipe(Shafts.STAINLESS_STEEL_SHAFT, ModItems.STAINLESS_STEEL_ALLOY),
            TITANIUM_SHAFT = createMaterialShaftRecipe(Shafts.TITANIUM_SHAFT, ModItems.TITANIUM_ALLOY),
            TUNGSTENSTEEL_SHAFT = createMaterialShaftRecipe(Shafts.TUNGSTENSTEEL_SHAFT, ModItems.TUNGSTENSTEEL_ALLOY),
            PALLADIUM_SHAFT = createMaterialShaftRecipe(Shafts.PALLADIUM_SHAFT, ModItems.PALLADIUM_ALLOY),
            NAQUADAH_SHAFT = createMaterialShaftRecipe(Shafts.NAQUADAH_SHAFT, ModItems.NAQUADAH_ALLOY),
            DARMSTADTIUM_SHAFT = createMaterialShaftRecipe(Shafts.DARMSTADTIUM_SHAFT, ModItems.DARMSTADTIUM_ALLOY),
            NEUTRONIUM_SHAFT = createMaterialShaftRecipe(Shafts.NEUTRONIUM_SHAFT, ModItems.NEUTRONIUM_ALLOY),

            ANDESITE_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.ANDESITE_COGWHEEL, Shafts.ANDESITE_SHAFT, "wood"),
            STEEL_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.STEEL_COGWHEEL, Shafts.STEEL_SHAFT, "stone"),
            ALUMINIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.ALUMINIUM_COGWHEEL, Shafts.ALUMINIUM_SHAFT, "steel"),
            STAINLESS_STEEL_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.STAINLESS_STEEL_COGWHEEL, Shafts.STAINLESS_STEEL_SHAFT, "aluminium"),
            TITANIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.TITANIUM_COGWHEEL, Shafts.TITANIUM_SHAFT, "stainless_steel"),
            TUNGSTENSTEEL_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.TUNGSTENSTEEL_COGWHEEL, Shafts.TUNGSTENSTEEL_SHAFT, "titanium"),
            PALLADIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.PALLADIUM_COGWHEEL, Shafts.PALLADIUM_SHAFT, "tungstensteel"),
            NAQUADAH_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.NAQUADAH_COGWHEEL, Shafts.NAQUADAH_SHAFT, "palladium"),
            DARMSTADTIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.DARMSTADTIUM_COGWHEEL, Shafts.DARMSTADTIUM_SHAFT, "naquadah"),
            NEUTRONIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.NEUTRONIUM_COGWHEEL, Shafts.NEUTRONIUM_SHAFT, "darmstadtium"),

            LARGE_ANDESITE_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_ANDESITE_COGWHEEL, Shafts.ANDESITE_SHAFT, "wood"),
            LARGE_STEEL_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_STEEL_COGWHEEL, Shafts.STEEL_SHAFT, "stone"),
            LARGE_ALUMINIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_ALUMINIUM_COGWHEEL, Shafts.ALUMINIUM_SHAFT, "steel"),
            LARGE_STAINLESS_STEEL_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL, Shafts.STAINLESS_STEEL_SHAFT, "aluminium"),
            LARGE_TITANIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_TITANIUM_COGWHEEL, Shafts.TITANIUM_SHAFT, "stainless_steel"),
            LARGE_TUNGSTENSTEEL_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL, Shafts.TUNGSTENSTEEL_SHAFT, "titanium"),
            LARGE_PALLADIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_PALLADIUM_COGWHEEL, Shafts.PALLADIUM_SHAFT, "tungstensteel"),
            LARGE_NAQUADAH_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_NAQUADAH_COGWHEEL, Shafts.NAQUADAH_SHAFT, "palladium"),
            LARGE_DARMSTADTIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL, Shafts.DARMSTADTIUM_SHAFT, "naquadah"),
            LARGE_NEUTRONIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_NEUTRONIUM_COGWHEEL, Shafts.NEUTRONIUM_SHAFT, "darmstadtium"),

            LARGE_ANDESITE_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_ANDESITE_COGWHEEL, Cogwheels.ANDESITE_COGWHEEL, "wood"),
            LARGE_STEEL_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_STEEL_COGWHEEL, Cogwheels.STEEL_COGWHEEL, "stone"),
            LARGE_ALUMINIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_ALUMINIUM_COGWHEEL, Cogwheels.ALUMINIUM_COGWHEEL, "steel"),
            LARGE_STAINLESS_STEEL_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL, Cogwheels.STAINLESS_STEEL_COGWHEEL, "aluminium"),
            LARGE_TITANIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_TITANIUM_COGWHEEL, Cogwheels.TITANIUM_COGWHEEL, "stainless_steel"),
            LARGE_TUNGSTENSTEEL_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL, Cogwheels.TUNGSTENSTEEL_COGWHEEL, "titanium"),
            LARGE_PALLADIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_PALLADIUM_COGWHEEL, Cogwheels.PALLADIUM_COGWHEEL, "tungstensteel"),
            LARGE_NAQUADAH_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_NAQUADAH_COGWHEEL, Cogwheels.NAQUADAH_COGWHEEL, "palladium"),
            LARGE_DARMSTADTIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL, Cogwheels.DARMSTADTIUM_COGWHEEL, "naquadah"),
            LARGE_NEUTRONIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_NEUTRONIUM_COGWHEEL, Cogwheels.NEUTRONIUM_COGWHEEL, "darmstadtium");
    private GeneratedRecipe createMaterialAlloyRecipe(ItemEntry<Item> alloy) {
        String material = alloy.getId().getPath().substring(0, alloy.getId().getPath().length() - 6);
        return create(alloy).unlockedByTag(GreateTags.forgeItemTag("ingots/" + material))
                .viaShaped(b -> b.define('N', GreateTags.forgeItemTag("nuggets/" + material))
                        .define('A', Blocks.ANDESITE)
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .define('H', GreateTags.forgeItemTag("tools/hammers"))
                        .pattern("NA")
                        .pattern("AN")
                        .pattern("FH"));
    }

    private GeneratedRecipe createMaterialShaftRecipe(BlockEntry<TieredShaftBlock> shaft, ItemEntry<Item> alloy) {
        return create(shaft).returns(4).unlockedBy(alloy::get)
                .viaShaped(b -> b.define('A', alloy::get)
                        .define('S', GreateTags.forgeItemTag("tools/saws"))
                        .pattern("S ")
                        .pattern(" A"));
    }

    private GeneratedRecipe createMaterialCogwheelRecipe(BlockEntry<TieredCogwheelBlock> cogwheel, BlockEntry<TieredShaftBlock> shaft, String material) {
        return create(cogwheel).unlockedBy(shaft::get)
                .viaShaped(b -> b.define('S', shaft.get())
                        .define('P', GreateTags.forgeItemTag("plates/" + material))
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .pattern("SP")
                        .pattern("F "));
    }

    private GeneratedRecipe createMaterialLargeCogwheelRecipe(BlockEntry<TieredCogwheelBlock> cogwheel, BlockEntry<TieredShaftBlock> shaft, String material) {
        return create(cogwheel).unlockedBy(shaft::get)
                .viaShaped(b -> b.define('S', shaft.get())
                        .define('P', GreateTags.forgeItemTag("plates/" + material))
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .pattern("SP")
                        .pattern("PF"));
    }

    private GeneratedRecipe createMaterialLargeCogwheelFromSmallRecipe(BlockEntry<TieredCogwheelBlock> cogwheel, BlockEntry<TieredCogwheelBlock> smallCogwheel, String material) {
        return create(cogwheel).withSuffix("from_little").unlockedBy(smallCogwheel::get)
                .viaShaped(b -> b.define('S', smallCogwheel.get())
                        .define('P', GreateTags.forgeItemTag("plates/" + material))
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .pattern("SP")
                        .pattern("F "));
    }

    String currentFolder = "";

    Marker enterFolder(String folder) {
        currentFolder = folder;
        return new Marker();
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ResourceLocation result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
        return create(result::get);
    }

    //modified from StandardRecipeGen#GeneratedRecipeBuilder
    class GeneratedRecipeBuilder {

        private String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDatagenOutput;
        List<ICondition> recipeConditions;

        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDatagenOutput = result;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(item.get())
                    .build();
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder unlockedByTag(TagKey<Item> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag)
                    .build();
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(new ModLoadedCondition(modid));
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(new NotCondition(new ModLoadedCondition(modid)));
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder withCondition(ICondition condition) {
            recipeConditions.add(condition);
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {
                ShapedRecipeBuilder b = builder.apply(ShapedRecipeBuilder.shaped(result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createGreateLocation("crafting"));
            });
        }

        GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(consumer -> {
                ShapelessRecipeBuilder b = builder.apply(ShapelessRecipeBuilder.shapeless(result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createGreateLocation("crafting"));
            });
        }

        GeneratedRecipe viaSmithing(Supplier<? extends Item> base, Supplier<Ingredient> upgradeMaterial) {
            return register(consumer -> {
                UpgradeRecipeBuilder b =
                        UpgradeRecipeBuilder.smithing(Ingredient.of(base.get()), upgradeMaterial.get(), result.get()
                                .asItem());
                b.unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(base.get())
                        .build()));
                b.save(consumer, createLocation("crafting"));
            });
        }

        private ResourceLocation createSimpleLocation(String recipeType) {
            return new ResourceLocation("greate", recipeType + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createLocation(String recipeType) {
            return Create.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createGreateLocation(String recipeType) {
            return new ResourceLocation("greate",recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? RegisteredObjects.getKeyOrThrow(result.get()
                    .asItem()) : compatDatagenOutput;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
            return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingTag(TagKey<Item> tag) {
            return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag));
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
            return new GreateStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder(ingredient);
        }

        class GeneratedCookingRecipeBuilder {

            private Supplier<Ingredient> ingredient;
            private float exp;
            private int cookingTime;

            private final SimpleCookingSerializer<?> FURNACE = RecipeSerializer.SMELTING_RECIPE,
                    SMOKER = RecipeSerializer.SMOKING_RECIPE, BLAST = RecipeSerializer.BLASTING_RECIPE,
                    CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;

            GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
                this.ingredient = ingredient;
                cookingTime = 200;
                exp = 0;
            }

            GreateStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder forDuration(int duration) {
                cookingTime = duration;
                return this;
            }

            GreateStandardRecipeGen.GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder rewardXP(float xp) {
                exp = xp;
                return this;
            }

            GeneratedRecipe inFurnace() {
                return inFurnace(b -> b);
            }

            GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                return create(FURNACE, builder, 1);
            }

            GeneratedRecipe inSmoker() {
                return inSmoker(b -> b);
            }

            GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                create(CAMPFIRE, builder, 3);
                return create(SMOKER, builder, .5f);
            }

            GeneratedRecipe inBlastFurnace() {
                return inBlastFurnace(b -> b);
            }

            GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                return create(BLAST, builder, .5f);
            }

            private GeneratedRecipe create(SimpleCookingSerializer<?> serializer,
                                           UnaryOperator<SimpleCookingRecipeBuilder> builder, float cookingTimeModifier) {
                return register(consumer -> {
                    boolean isOtherMod = compatDatagenOutput != null;

                    SimpleCookingRecipeBuilder b = builder.apply(
                            SimpleCookingRecipeBuilder.cooking(ingredient.get(), isOtherMod ? Items.DIRT : result.get(),
                                    exp, (int) (cookingTime * cookingTimeModifier), serializer));
                    if (unlockedBy != null)
                        b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                    b.save(result -> {
                        consumer.accept(
                                isOtherMod ? new ModdedCookingRecipeResult(result, compatDatagenOutput, recipeConditions)
                                        : result);
                    }, createSimpleLocation(RegisteredObjects.getKeyOrThrow(serializer).getPath()));
                });
            }
        }
    }

    private static class ModdedCookingRecipeResult implements FinishedRecipe {

        private FinishedRecipe wrapped;
        private ResourceLocation outputOverride;
        private List<ICondition> conditions;

        public ModdedCookingRecipeResult(FinishedRecipe wrapped, ResourceLocation outputOverride,
                                         List<ICondition> conditions) {
            this.wrapped = wrapped;
            this.outputOverride = outputOverride;
            this.conditions = conditions;
        }

        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(JsonObject object) {
            wrapped.serializeRecipeData(object);
            object.addProperty("result", outputOverride.toString());

            JsonArray conds = new JsonArray();
            conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            object.add("conditions", conds);
        }

    }
}
