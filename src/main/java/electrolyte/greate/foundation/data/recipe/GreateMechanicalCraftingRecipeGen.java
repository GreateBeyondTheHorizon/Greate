package electrolyte.greate.foundation.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.registry.CrushingWheels;
import electrolyte.greate.registry.GreateTags;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.UnaryOperator;

public class GreateMechanicalCraftingRecipeGen extends GreateRecipeProvider {
    public GreateMechanicalCraftingRecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Greate's Mechanical Crafting Recipes";
    }

    GeneratedRecipe
            ANDESITE_CRUSHING_WHEEL = create(CrushingWheels.ANDESITE_CRUSHING_WHEEL).returns(2)
            .recipe(b -> b.key('A', AllItems.ANDESITE_ALLOY::get)
                    .key('C', GreateTags.forgeItemTag("circuits/ulv"))
                    .key('S', GreateTags.greateItemTag("shafts/andesite"))
                    .patternLine(" AAA ")
                    .patternLine("AACAA")
                    .patternLine("ACSCA")
                    .patternLine("AACAA")
                    .patternLine(" AAA ")),
            STEEL_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.STEEL_CRUSHING_WHEEL, "lv"),
            ALUMINIUM_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.ALUMINIUM_CRUSHING_WHEEL, "mv"),
            STAINLESS_STEEL_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.STAINLESS_STEEL_CRUSHING_WHEEL, "hv"),
            TITANIUM_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.TITANIUM_CRUSHING_WHEEL, "ev"),
            TUNGSTEN_STEEL_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.TUNGSTEN_STEEL_CRUSHING_WHEEL, "iv"),
            PALLADIUM_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.PALLADIUM_CRUSHING_WHEEL, "luv"),
            NAQUADAH_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.NAQUADAH_CRUSHING_WHEEL, "zpm"),
            DARMSTADTIUM_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.DARMSTADTIUM_CRUSHING_WHEEL, "uv"),
            NEUTRONIUM_CRUSHING_WHEEL = createMaterialCrushingWheelRecipe(CrushingWheels.NEUTRONIUM_CRUSHING_WHEEL, "uhv");
    GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
        return new GeneratedRecipeBuilder(result);
    }

    GeneratedRecipe createMaterialCrushingWheelRecipe(ItemProviderEntry<? extends ItemLike> crushingWheel, String tier) {
        String material = crushingWheel.getId().getPath().substring(0, crushingWheel.getId().getPath().length() - 15);
        return create(crushingWheel).returns(2)
                .recipe(b -> b.key('A', ForgeRegistries.ITEMS.getValue(new ResourceLocation(Greate.MOD_ID, material + "_alloy"))::asItem)
                        .key('C', GreateTags.forgeItemTag("circuits/" + tier))
                        .key('S', GreateTags.greateItemTag("shafts/" + material))
                        .patternLine(" AAA ")
                        .patternLine("AACAA")
                        .patternLine("ACSCA")
                        .patternLine("AACAA")
                        .patternLine(" AAA "));

    }

    class GeneratedRecipeBuilder {
        private String suffix;
        private ItemProviderEntry<? extends ItemLike> result;
        private int amount;

        public GeneratedRecipeBuilder(ItemProviderEntry<? extends ItemLike> result) {
            this.suffix = "";
            this.result = result;
            this.amount = 1;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe recipe(UnaryOperator<MechanicalCraftingRecipeBuilder> builder) {
            return register(consumer -> {
                MechanicalCraftingRecipeBuilder b = builder.apply(MechanicalCraftingRecipeBuilder.shapedRecipe(result.get(), amount));
                ResourceLocation location = new ResourceLocation(Greate.MOD_ID, "mechanical_crafting/" + RegisteredObjects.getKeyOrThrow(result.get().asItem()).getPath() + suffix);
                b.build(consumer, location);
            });
        }
    }
}
