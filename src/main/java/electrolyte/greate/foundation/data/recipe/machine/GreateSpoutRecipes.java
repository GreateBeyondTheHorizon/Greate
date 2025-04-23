package electrolyte.greate.foundation.data.recipe.machine;

import com.google.common.collect.ImmutableMap;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.WireProperties;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.compat.gtceu.api.capability.recipe.GreateRecipeTypes.WIRE_COATING_RECIPES;

public class GreateSpoutRecipes {

    private static final Map<TagPrefix, Integer> INSULATION_AMOUNT = ImmutableMap.of(
            cableGtSingle, 1,
            cableGtDouble, 1,
            cableGtQuadruple, 2,
            cableGtOctal, 3,
            cableGtHex, 5);

    public static void register(Consumer<FinishedRecipe> provider) {
        wireGtSingle.executeHandler(provider, PropertyKey.WIRE, GreateSpoutRecipes::addRecipe);
        wireGtDouble.executeHandler(provider, PropertyKey.WIRE, GreateSpoutRecipes::addRecipe);
        wireGtQuadruple.executeHandler(provider, PropertyKey.WIRE, GreateSpoutRecipes::addRecipe);
        wireGtOctal.executeHandler(provider, PropertyKey.WIRE, GreateSpoutRecipes::addRecipe);
        wireGtHex.executeHandler(provider, PropertyKey.WIRE, GreateSpoutRecipes::addRecipe);
    }

    public static void addRecipe(TagPrefix wirePrefix, Material material, WireProperties property, Consumer<FinishedRecipe> provider) {
        if(property.isSuperconductor()) return;
        int cableAmount = (int) (wirePrefix.getMaterialAmount(material) * 2 / M);
        TagPrefix cablePrefix = TagPrefix.get("cable" + wirePrefix.name().substring(4));
        int voltageTier = GTUtil.getTierByVoltage(property.getVoltage());
        int euT = voltageTier > 0 ? voltageTier - 1 : ULV;
        int insulationAmount = INSULATION_AMOUNT.get(cablePrefix);

        if(voltageTier < EV) {
            new ProcessingRecipeBuilder<>(FillingRecipe::new, Greate.id(String.format("%s_cable_%d_rubber", material.getName(), cableAmount)))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(wirePrefix, material)))
                    .withFluidIngredients(FluidIngredient.fromFluid(Rubber.getFluid(), L * insulationAmount))
                    .withSingleItemOutput(ChemicalHelper.get(cablePrefix, material))
                    .build(provider);

            WIRE_COATING_RECIPES
                    .recipeBuilder(Greate.id(String.format("cover_%s_%s_rubber", material.getName(), cableAmount)))
                    .EUt(VA[euT]).duration(100)
                    .inputItems(wirePrefix, material)
                    .inputFluids(Rubber.getFluid(L * insulationAmount))
                    .outputItems(cablePrefix, material)
                    .save(provider);


            new ProcessingRecipeBuilder<>(FillingRecipe::new, Greate.id(String.format("%s_cable_%d_slicone", material.getName(), cableAmount)))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(wirePrefix, material)))
                    .withFluidIngredients(FluidIngredient.fromFluid(SiliconeRubber.getFluid(), L * insulationAmount / 2))
                    .withSingleItemOutput(ChemicalHelper.get(cablePrefix, material))
                    .build(provider);

            WIRE_COATING_RECIPES
                    .recipeBuilder(Greate.id(String.format("cover_%s_%s_silicone", material.getName(), cableAmount)))
                    .EUt(VA[euT]).duration(100)
                    .inputItems(wirePrefix, material)
                    .inputFluids(SiliconeRubber.getFluid(L * insulationAmount / 2))
                    .outputItems(cablePrefix, material)
                    .save(provider);


            new ProcessingRecipeBuilder<>(FillingRecipe::new, Greate.id(String.format("%s_cable_%d_styrene_butadiene", material.getName(), cableAmount)))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(wirePrefix, material)))
                    .withFluidIngredients(FluidIngredient.fromFluid(StyreneButadieneRubber.getFluid(), L * insulationAmount / 4))
                    .withSingleItemOutput(ChemicalHelper.get(cablePrefix, material))
                    .build(provider);

           WIRE_COATING_RECIPES
                   .recipeBuilder(Greate.id(String.format("cover_%s_%s_styrene_butadiene", material.getName(), cableAmount)))
                   .EUt(VA[euT]).duration(100)
                   .inputItems(wirePrefix, material)
                   .inputFluids(StyreneButadieneRubber.getFluid(L * insulationAmount / 4))
                   .outputItems(cablePrefix, material)
                   .save(provider);
        }
    }
}
