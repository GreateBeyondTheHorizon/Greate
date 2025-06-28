package electrolyte.greate.foundation.data.recipe.machine;

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
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.content.gtceu.machines.GreateRecipeTypes.WIRE_COATING_RECIPES;

public class GreateSpoutRecipes {

    private static final Reference2IntMap<TagPrefix> INSULATION_AMOUNT = Util.make(new Reference2IntOpenHashMap<>(),
            map -> {
            map.put(cableGtSingle, 1);
            map.put(cableGtDouble, 1);
            map.put(cableGtQuadruple, 2);
            map.put(cableGtOctal, 3);
            map.put(cableGtHex, 5);
    });

    public static void registerCableRecipes(Consumer<FinishedRecipe> provider, Material material) {
        WireProperties property = material.getProperty(PropertyKey.WIRE);
        if(property != null) {
            addRecipe(provider, property, wireGtSingle, material);
            addRecipe(provider, property, wireGtDouble, material);
            addRecipe(provider, property, wireGtQuadruple, material);
            addRecipe(provider, property, wireGtOctal, material);
            addRecipe(provider, property, wireGtHex, material);
        }
    }

    public static void addRecipe(Consumer<FinishedRecipe> provider, WireProperties property, TagPrefix wirePrefix, Material material) {
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
