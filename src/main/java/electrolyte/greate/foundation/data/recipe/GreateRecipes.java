package electrolyte.greate.foundation.data.recipe;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.registry.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.HULL;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static electrolyte.greate.GreateValues.BM;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Belts.BELT_CONNECTORS;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.Gearboxes.VERTICAL_GEARBOXES;
import static electrolyte.greate.registry.MechanicalMixers.MECHANICAL_MIXERS;
import static electrolyte.greate.registry.MechanicalPresses.MECHANICAL_PRESSES;
import static electrolyte.greate.registry.Millstones.MILLSTONES;
import static electrolyte.greate.registry.ModItems.*;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;
import static electrolyte.greate.registry.Shafts.ANDESITE_SHAFT;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        // Andesite conversion cycles
        conversionCycle(provider, ImmutableList.of(AllBlocks.SHAFT, Shafts.ANDESITE_SHAFT));
        conversionCycle(provider, ImmutableList.of(AllBlocks.COGWHEEL, Cogwheels.ANDESITE_COGWHEEL));
        conversionCycle(provider, ImmutableList.of(AllBlocks.LARGE_COGWHEEL, Cogwheels.LARGE_ANDESITE_COGWHEEL));
        conversionCycle(provider, ImmutableList.of(AllBlocks.CRUSHING_WHEEL, CrushingWheels.ANDESITE_CRUSHING_WHEEL));
        conversionCycle(provider, ImmutableList.of(AllItems.BELT_CONNECTOR, Belts.RUBBER_BELT_CONNECTOR));
        conversionCycle(provider, ImmutableList.of(AllBlocks.MECHANICAL_PRESS, MechanicalPresses.ANDESITE_MECHANICAL_PRESS));
        conversionCycle(provider, ImmutableList.of(AllBlocks.MECHANICAL_MIXER, MechanicalMixers.ANDESITE_MECHANICAL_MIXER));
        conversionCycle(provider, ImmutableList.of(AllBlocks.MECHANICAL_SAW, Saws.ANDESITE_SAW));
        
        for (int tier = 0; tier < TM.length; tier++) {
            Material tierMaterial = TM[tier];
            String materialName = tierMaterial.getName();
            // Alloys
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_alloy"), ALLOYS[tier].asStack(), "NA", "AN", "fh", 'N', new UnificationEntry(nugget, tierMaterial), 'A', new ItemStack(Blocks.ANDESITE));
            // Shafts
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_shaft"), SHAFTS[tier].asStack(4), "s ", " A", 'A', ALLOYS[tier].asStack());
            // Cogwheels
            Material previousTierMaterial = tier - 1 == -1 ? Wood : TM[tier - 1];
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_cogwheel"), COGWHEELS[tier].asStack(), "SP", " f", 'S', SHAFTS[tier].asStack(), 'P', new UnificationEntry(plate, previousTierMaterial));
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id("large_" + materialName + "_cogwheel"), LARGE_COGWHEELS[tier].asStack(), "SP", "Pf", 'S', SHAFTS[tier].asStack(), 'P', new UnificationEntry(plate, previousTierMaterial));
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id("large_" + materialName + "_cogwheel_from_little"), LARGE_COGWHEELS[tier].asStack(), "CP", " f", 'C', COGWHEELS[tier].asStack(), 'P', new UnificationEntry(plate, previousTierMaterial));
            // Gearbox
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_gearbox"), GEARBOXES[tier].asStack(), " S ", "SCS", "fSh", 'S', SHAFTS[tier].asStack(), 'C', AllBlocks.ANDESITE_CASING);
            conversionCycle(provider, ImmutableList.of(GEARBOXES[tier], VERTICAL_GEARBOXES[tier]));
            // Mechanical Presses
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_mechanical_press"), MECHANICAL_PRESSES[tier].asStack(), " S ", "CMC", " B ", 'S', SHAFTS[tier].asStack(), 'C', CIRCUIT.getIngredient(tier), 'M', CASING.getIngredient(tier), 'B', new UnificationEntry(block, tierMaterial));
            // Mechanical Mixers
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_mechanical_mixer"), MECHANICAL_MIXERS[tier].asStack(), " S ", "CMC", " W ", 'S', SHAFTS[tier].asStack(), 'C', CIRCUIT.getIngredient(tier), 'M', CASING.getIngredient(tier), 'W', WHISKS[tier].asStack());
            // Millstones
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_millstone"), MILLSTONES[tier].asStack(), " A ", "WHW", "CSC", 'A', COGWHEELS[tier].asStack(), 'W', GreateTags.mcItemTag("wooden_slabs"), 'H', HULL[tier].asStack(), 'C', CIRCUIT.getIngredient(tier), 'S', SHAFTS[tier].asStack());
            // Saws (special case since they use conveyors and motors)
            if(tier != 0) {
                VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_mechanical_saw"), SAWS[tier].asStack(), "MHE", "CSC", 'M', CONVEYOR.getIngredient(tier), 'H', HULL[tier].asStack(), 'E', MOTOR.getIngredient(tier), 'C', CIRCUIT.getIngredient(tier), 'S', SHAFTS[tier].asStack());
            }
            // Pumps
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_mechanical_pump"), MECHANICAL_PUMPS[tier].asStack(), " R ", "wPC", " R ", 'R', new UnificationEntry(ring, Rubber), 'P', AllBlocks.FLUID_PIPE, 'C', COGWHEELS[tier].asStack());
            // Whisks
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_whisk"), WHISKS[tier].asStack(), "fId", "PIP", "PPP", 'I', new UnificationEntry(ingot, tierMaterial), 'P', new UnificationEntry(plate, tierMaterial));
        }

        // Andesite Saw (special case since they use conveyors and motors)
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id("andesite_mechanical_saw"), Saws.ANDESITE_SAW.asStack(), "MHE", "CSC", 'M', ULV_CONVEYOR_MODULE, 'H', HULL[ULV].asStack(), 'E', ULV_ELECTRIC_MOTOR, 'C', GreateTags.gtceuItemTag("circuits/ulv"), 'S', ANDESITE_SHAFT.asStack());

        //ULV Conveyor + Motor
        final Map<String, Material> rubberMaterials = new Object2ObjectOpenHashMap<>();
        rubberMaterials.put("rubber", Rubber);
        rubberMaterials.put("silicone_rubber", SiliconeRubber);
        rubberMaterials.put("styrene_butadiene_rubber", StyreneButadieneRubber);

        for(Map.Entry<String, Material> materialEntry : rubberMaterials.entrySet()) {
            Material material = materialEntry.getValue();
            String name = materialEntry.getKey();

            VanillaRecipeHelper.addShapedRecipe(provider, material.equals(Rubber), Greate.id(String.format("conveyor_module_ulv_%s", name)), ULV_CONVEYOR_MODULE.asStack(), "RRR", "MCM", "RRR", 'R', new UnificationEntry(plate, material), 'C', new UnificationEntry(cableGtSingle, RedAlloy), 'M', ULV_ELECTRIC_MOTOR.asStack());

            ASSEMBLER_RECIPES.recipeBuilder("conveyor_module_ulv_" + name)
                    .inputItems(cableGtSingle, RedAlloy)
                    .inputItems(ULV_ELECTRIC_MOTOR, 2)
                    .inputFluids(materialEntry.getValue().getFluid(L * 6))
                    .circuitMeta(1)
                    .outputItems(ULV_CONVEYOR_MODULE)
                    .duration(100).EUt(VA[ULV]).save(provider);
        }
        VanillaRecipeHelper.addShapedRecipe(provider, true, Greate.id("electric_motor_ulv"), ULV_ELECTRIC_MOTOR.asStack(), "CWR", "WMW", "RWC", 'C', new UnificationEntry(cableGtSingle, RedAlloy), 'W', new UnificationEntry(wireGtSingle, Tin), 'R', new UnificationEntry(rod, Copper), 'M', new UnificationEntry(rod, IronMagnetic));

        ASSEMBLER_RECIPES.recipeBuilder("electric_motor_ulv")
                .inputItems(cableGtSingle, RedAlloy, 2)
                .inputItems(rod, Copper, 2)
                .inputItems(rod, IronMagnetic)
                .inputItems(wireGtSingle, Tin, 4)
                .outputItems(ULV_ELECTRIC_MOTOR)
                .duration(100).EUt(VA[ULV]).save(provider);

        // Belts
        for (int beltTier = 0; beltTier < BM.length; beltTier++) {
            Material beltMaterial = BM[beltTier];
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(beltMaterial.getName() + "_belt_connector"), BELT_CONNECTORS[beltTier].asStack(), "FFF", "FFF", 'F', new UnificationEntry(foil, beltMaterial));
        }
    }

    private static void conversionCycle(Consumer<FinishedRecipe> provider, List<ItemProviderEntry<? extends ItemLike>> cycle) {
        for (int i = 0; i < cycle.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
            var builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nextEntry).requires(currentEntry).unlockedBy("has_cycle_origin", RegistrateRecipeProvider.has(currentEntry));
            builder.save(provider, RecipeBuilder.getDefaultRecipeId(builder.getResult()).withSuffix("_from_conversion"));
        }
    }
}
