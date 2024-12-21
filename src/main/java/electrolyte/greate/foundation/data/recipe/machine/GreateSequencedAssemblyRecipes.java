package electrolyte.greate.foundation.data.recipe.machine;

import com.google.common.collect.ImmutableMap;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.WireProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.material.Fluids;

import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.PLATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.compat.gtceu.api.capability.recipe.GreateRecipeTypes.WIRE_COATING_RECIPES;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.createIngFromTag;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.createIngFromUnificationEntry;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateSequencedAssemblyRecipes {

    private static final Map<TagPrefix, Integer> INSULATION_AMOUNT = ImmutableMap.of(
            cableGtSingle, 1,
            cableGtDouble, 1,
            cableGtQuadruple, 2,
            cableGtOctal, 3,
            cableGtHex, 5);

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            int finalTier = tier;
            new SequencedAssemblyRecipeBuilder(LARGE_COGWHEELS[tier].getId())
                    .require(SHAFTS[tier])
                    .transitionTo(COGWHEELS[tier])
                    .addStep(DeployerApplicationRecipe::new, r -> r.require(createIngFromUnificationEntry(finalTier != 0 ? PLATE.getIngredient(finalTier - 1) : new UnificationEntry(plate, Wood))))
                    .addStep(DeployerApplicationRecipe::new, r -> r.require(createIngFromUnificationEntry(finalTier != 0 ? PLATE.getIngredient(finalTier - 1) : new UnificationEntry(plate, Wood))))
                    .addOutput(LARGE_COGWHEELS[tier], 1)
                    .loops(1)
                    .build(provider);
        }

        new SequencedAssemblyRecipeBuilder(Greate.id("sturdy_sheet"))
                .require(createIngFromTag("forge", "dusts/obsidian"))
                .transitionTo(AllItems.INCOMPLETE_REINFORCED_SHEET)
                .addStep(FillingRecipe::new, r -> r.require(Fluids.LAVA, 500))
                .addStep(PressingRecipe::new, r -> r)
                .addStep(PressingRecipe::new, r -> r)
                .addOutput(AllItems.STURDY_SHEET.asItem(), 1)
                .loops(1)
                .build(provider);

        wireGtSingle.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtDouble.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtQuadruple.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtOctal.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);
        wireGtHex.executeHandler(provider, PropertyKey.WIRE, GreateSequencedAssemblyRecipes::addRecipe);

    }

    //WireRecipeHandler
    public static void addRecipe(TagPrefix wirePrefix, Material material, WireProperties property, Consumer<FinishedRecipe> provider) {
        if(property.isSuperconductor()) return;
        int cableAmount = (int) (wirePrefix.getMaterialAmount(material) * 2 / M);
        TagPrefix cablePrefix = TagPrefix.get("cable" + wirePrefix.name().substring(4));
        int voltageTier = GTUtil.getTierByVoltage(property.getVoltage());
        int euT = voltageTier > 0 ? voltageTier - 1 : ULV;
        int insulationAmount = INSULATION_AMOUNT.get(cablePrefix);

        if(voltageTier >= EV) {
            SequencedAssemblyRecipeBuilder siliconeAssemblyBuilder = new SequencedAssemblyRecipeBuilder(Greate.id(String.format("%s_cable_%d_silicone", material.getName(), cableAmount)))
                .require(ChemicalHelper.get(wirePrefix, material).getItem())
                .transitionTo(ChemicalHelper.get(wirePrefix, material).getItem())
                .addOutput(ChemicalHelper.get(cablePrefix, material), 1)
                .loops(1);
            GTRecipeBuilder siliconeCoatingFactoryBuilder = WIRE_COATING_RECIPES
                    .recipeBuilder(Greate.id(String.format("%s_cable_%d_silicone", material.getName(), cableAmount)))
                    .EUt(VA[euT]).duration(100)
                    .inputItems(wirePrefix, material)
                    .outputItems(cablePrefix, material);
            if(voltageTier >= LuV) {
                siliconeAssemblyBuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyphenyleneSulfide, insulationAmount).getItem()));
                siliconeCoatingFactoryBuilder.inputItems(foil, PolyphenyleneSulfide, insulationAmount);
            }
            siliconeAssemblyBuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyvinylChloride, insulationAmount).getItem()));
            siliconeAssemblyBuilder.addStep(FillingRecipe::new, r -> r.require(FluidIngredient.fromFluid(SiliconeRubber.getFluid(), L * insulationAmount / 2)));
            siliconeAssemblyBuilder.build(provider);

            siliconeCoatingFactoryBuilder.inputItems(foil, PolyvinylChloride, insulationAmount);
            siliconeCoatingFactoryBuilder.inputFluids(SiliconeRubber.getFluid(L * insulationAmount / 2));
            siliconeCoatingFactoryBuilder.save(provider);

            SequencedAssemblyRecipeBuilder styreneAssemblyBuilder = new SequencedAssemblyRecipeBuilder(Greate.id(String.format("%s_cable_%d_styrene", material.getName(), cableAmount)))
                    .require(ChemicalHelper.get(wirePrefix, material).getItem())
                    .transitionTo(ChemicalHelper.get(wirePrefix, material).getItem())
                    .addOutput(ChemicalHelper.get(cablePrefix, material), 1)
                    .loops(1);
            GTRecipeBuilder styreneCoatingFactoryBuilder = WIRE_COATING_RECIPES
                    .recipeBuilder(Greate.id(String.format("%s_cable_%d_styrene", material.getName(), cableAmount)))
                    .EUt(VA[euT]).duration(100)
                    .inputItems(wirePrefix, material)
                    .outputItems(cablePrefix, material);
            if(voltageTier >= LuV) {
                styreneAssemblyBuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyphenyleneSulfide, insulationAmount).getItem()));
                styreneCoatingFactoryBuilder.inputItems(foil, PolyphenyleneSulfide, insulationAmount);
            }
            styreneAssemblyBuilder.addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(foil, PolyvinylChloride, insulationAmount).getItem()));
            styreneAssemblyBuilder.addStep(FillingRecipe::new, r -> r.require(FluidIngredient.fromFluid(StyreneButadieneRubber.getFluid(), L * insulationAmount / 4)));
            styreneAssemblyBuilder.build(provider);

            styreneCoatingFactoryBuilder.inputItems(foil, PolyvinylChloride, insulationAmount);
            styreneCoatingFactoryBuilder.inputFluids(StyreneButadieneRubber.getFluid(L * insulationAmount / 4));
            styreneCoatingFactoryBuilder.save(provider);
        }
    }
}
