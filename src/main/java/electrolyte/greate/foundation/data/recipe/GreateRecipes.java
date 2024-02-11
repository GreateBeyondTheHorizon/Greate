package electrolyte.greate.foundation.data.recipe;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gregtechceu.gtceu.data.recipe.builder.ShapedRecipeBuilder;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.registry.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.rod;
import static com.gregtechceu.gtceu.common.data.GTItems.ELECTRIC_MOTOR_LV;
import static com.gregtechceu.gtceu.common.data.GTMachines.HULL;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.SteelMagnetic;
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
import static electrolyte.greate.registry.ModItems.ALLOYS;
import static electrolyte.greate.registry.ModItems.WHISKS;
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
            // Saws
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_mechanical_saw"), SAWS[tier].asStack(), "MHE", "CSC", 'M', CONVEYOR.getIngredient(tier), 'H', HULL[tier].asStack(), 'E', MOTOR.getIngredient(tier), 'C', CIRCUIT.getIngredient(tier), 'S', SHAFTS[tier].asStack());
            // Pumps
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_mechanical_pump"), MECHANICAL_PUMPS[tier].asStack(), " R ", "wPC", " R ", 'R', new UnificationEntry(ring, Rubber), 'P', AllBlocks.FLUID_PIPE, 'C', COGWHEELS[tier].asStack());
            // Whisks
            VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(materialName + "_whisk"), WHISKS[tier].asStack(), "fId", "PIP", "PPP", 'I', new UnificationEntry(ingot, tierMaterial), 'P', new UnificationEntry(plate, tierMaterial));
        }

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
