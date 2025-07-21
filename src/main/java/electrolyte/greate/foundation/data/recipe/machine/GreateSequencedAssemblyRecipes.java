package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.material.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.material.material.properties.WireProperties;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.gregtechceu.gtceu.common.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.CogwheelProperty;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.data.material.GTMaterials.*;
import static electrolyte.greate.content.gtceu.machines.GreateRecipeTypes.WIRE_COATING_RECIPES;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.*;

public class GreateSequencedAssemblyRecipes {

    private static final Reference2IntMap<TagPrefix> INSULATION_AMOUNT = Util.make(new Reference2IntOpenHashMap<>(),
            map -> {
            map.put(cableGtSingle, 1);
            map.put(cableGtDouble, 1);
            map.put(cableGtQuadruple, 2);
            map.put(cableGtOctal, 3);
            map.put(cableGtHex, 5);
    });

    public static void register(RecipeOutput provider) {
        new SequencedAssemblyRecipeBuilder(Greate.id("precision_mechanism"))
                .require(AllTags.commonItemTag("plates/gold"))
                .transitionTo(AllItems.INCOMPLETE_PRECISION_MECHANISM)
                .addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(cogwheel, AndesiteAlloy).getItem()))
                .addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(largeCogwheel, AndesiteAlloy).getItem()))
                .addStep(DeployerApplicationRecipe::new, r -> r.require(Items.IRON_NUGGET))
                .addOutput(AllItems.PRECISION_MECHANISM.get(), 120)
                .addOutput(AllItems.GOLDEN_SHEET.get(), 8)
                .addOutput(AllItems.ANDESITE_ALLOY.get(), 8)
                .addOutput(AllBlocks.COGWHEEL.get(), 5)
                .addOutput(Items.GOLD_NUGGET, 3)
                .addOutput(AllBlocks.SHAFT.get(), 2)
                .addOutput(AllItems.CRUSHED_GOLD.get(), 2)
                .addOutput(Items.IRON_INGOT, 1)
                .addOutput(Items.CLOCK, 1)
                .loops(5)
                .build(provider);
    }

    public static void registerMaterialRecipes(RecipeOutput provider, Material material) {
        WireProperties property = material.getProperty(PropertyKey.WIRE);
        if(property != null) {
            addRecipe(provider, property, wireGtSingle, material);
            addRecipe(provider, property, wireGtDouble, material);
            addRecipe(provider, property, wireGtQuadruple, material);
            addRecipe(provider, property, wireGtOctal, material);
            addRecipe(provider, property, wireGtHex, material);
        }
        CogwheelProperty cogwheelProperty = material.getProperty(GreatePropertyKeys.COGWHEEL);
        if(cogwheelProperty != null) {
            addCogwheelDeployingRecipes(provider, material, cogwheelProperty);
        }
    }

    private static void addCogwheelDeployingRecipes(RecipeOutput provider, Material material, CogwheelProperty property) {
        Material prevMat = property.getPreviousMaterial();
        new SequencedAssemblyRecipeBuilder(Greate.id(material.getName() + "_large_cogwheel"))
                    .require(ChemicalHelper.get(shaft, material).getItem())
                    .transitionTo(ChemicalHelper.get(cogwheel, material).getItem())
                    .addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(plate, prevMat).getItem()))
                    .addStep(DeployerApplicationRecipe::new, r -> r.require(ChemicalHelper.get(plate, prevMat).getItem()))
                    .addOutput(ChemicalHelper.get(largeCogwheel, material), 1)
                    .loops(1)
                    .build(provider);
    }

    //WireRecipeHandler
    private static void addRecipe(RecipeOutput provider, WireProperties property, TagPrefix wirePrefix, Material material) {
        if(property.isSuperconductor()) return;
        int cableAmount = (int) (wirePrefix.getMaterialAmount(material) * 2 / M);
        TagPrefix cablePrefix = TagPrefix.get("cable" + wirePrefix.name().substring(4));
        int voltageTier = GTUtil.getTierByVoltage(property.getVoltage());
        int euT = voltageTier > 0 ? voltageTier - 1 : ULV;
        int insulationAmount = INSULATION_AMOUNT.getInt(cablePrefix);

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
