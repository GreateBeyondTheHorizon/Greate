package electrolyte.greate.foundation.data.recipe.machine;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import electrolyte.greate.Greate;
import electrolyte.greate.foundation.data.recipe.GreateRecipes;
import electrolyte.greate.registry.Cogwheels;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.CASING_WATERTIGHT;
import static com.gregtechceu.gtceu.common.data.GTItems.ELECTRIC_PUMP_IV;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static com.gregtechceu.gtceu.data.recipe.CustomTags.IV_CIRCUITS;
import static electrolyte.greate.GreateValues.BM;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.content.gtceu.machines.GreateMultiblockMachines.WIRE_COATING_FACTORY;
import static electrolyte.greate.foundation.data.recipe.GreateRecipes.conversionCycle;
import static electrolyte.greate.registry.Belts.BELT_CONNECTORS;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.Gearboxes.VERTICAL_GEARBOXES;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.whisk;
import static electrolyte.greate.registry.MechanicalMixers.MECHANICAL_MIXERS;
import static electrolyte.greate.registry.MechanicalPresses.MECHANICAL_PRESSES;
import static electrolyte.greate.registry.Millstones.MILLSTONES;
import static electrolyte.greate.registry.ModItems.ALLOYS;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateCraftingTableRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for (int tier = 0; tier < TM.length; tier++) {
            Material tierMaterial = TM[tier];
            VanillaRecipeHelper.addShapedRecipe(provider, ALLOYS[tier].getId(), ALLOYS[tier].asStack(),
                    "NA", "AN", "fh",
                    'N', new UnificationEntry(plate, tierMaterial),
                    'A', new ItemStack(Blocks.ANDESITE));
            VanillaRecipeHelper.addShapedRecipe(provider, SHAFTS[tier].getId(), SHAFTS[tier].asStack(2),
                    "s ", " A",
                    'A', new UnificationEntry(plate, tierMaterial));

            Material previousTierMaterial = tier - 1 == -1 ? Wood : TM[tier - 1];
            VanillaRecipeHelper.addShapelessRecipe(provider, COGWHEELS[tier].getId(), COGWHEELS[tier].asStack(), SHAFTS[tier].asStack(),
                    new UnificationEntry(plate, previousTierMaterial),
                    GreateRecipes.createIngFromTag("forge", "tools/files"));
            VanillaRecipeHelper.addShapelessRecipe(provider, LARGE_COGWHEELS[tier].getId(), LARGE_COGWHEELS[tier].asStack(),
                    SHAFTS[tier],
                    new UnificationEntry(plate, previousTierMaterial),
                    new UnificationEntry(plate, previousTierMaterial),
                    GreateRecipes.createIngFromTag("forge", "tools/files"));
            VanillaRecipeHelper.addShapelessRecipe(provider, LARGE_COGWHEELS[tier].getId().withSuffix("_from_little"), LARGE_COGWHEELS[tier].asStack(),
                    COGWHEELS[tier],
                    new UnificationEntry(plate, previousTierMaterial),
                    GreateRecipes.createIngFromTag("forge", "tools/files"));
            VanillaRecipeHelper.addShapedRecipe(provider, GEARBOXES[tier].getId(), GEARBOXES[tier].asStack(),
                    " S ", "SCS", "wSh",
                    'S', SHAFTS[tier],
                    'C', AllBlocks.ANDESITE_CASING);
            VanillaRecipeHelper.addShapedRecipe(provider, VERTICAL_GEARBOXES[tier].getId(), VERTICAL_GEARBOXES[tier].asStack(),
                    "S S", "wCh", "S S",
                    'S', SHAFTS[tier],
                    'C', AllBlocks.ANDESITE_CASING);
            VanillaRecipeHelper.addShapedRecipe(provider, String.format("%s_whisk", tierMaterial.getName()), ChemicalHelper.get(whisk, tierMaterial),
                    "fId", "PIP", "PPP",
                    'I', new UnificationEntry(ingot, tierMaterial),
                    'P', new UnificationEntry(plate, tierMaterial));
            conversionCycle(provider, ImmutableList.of(GEARBOXES[tier], VERTICAL_GEARBOXES[tier]));

            // Machines
            VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_PUMPS[tier].getId(), MECHANICAL_PUMPS[tier].asStack(),
                    " RS", "wPC", " RS",
                    'S', new UnificationEntry(screw, tierMaterial),
                    'R', new UnificationEntry(ring, Rubber),
                    'P', AllBlocks.FLUID_PIPE,
                    'C', COGWHEELS[tier]);
            if(tier != 0) {
                VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_PRESSES[tier].getId(), MECHANICAL_PRESSES[tier].asStack(),
                        "PSP", "CMC", "wBh",
                        'P', new UnificationEntry(plate, tierMaterial),
                        'S', SHAFTS[tier],
                        'C', CIRCUIT.getIngredient(tier),
                        'M', CASING.getIngredient(tier),
                        'B', new UnificationEntry(block, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_MIXERS[tier].getId(), MECHANICAL_MIXERS[tier].asStack(),
                        " S ", "CMC", "wWh",
                        'S', SHAFTS[tier],
                        'C', CIRCUIT.getIngredient(tier),
                        'M', CASING.getIngredient(tier),
                        'W', new UnificationEntry(whisk, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, MILLSTONES[tier].getId(), MILLSTONES[tier].asStack(),
                        "CAC", "WHW", "wSh",
                        'A', COGWHEELS[tier],
                        'W', Ingredient.of(ItemTags.WOODEN_SLABS),
                        'H', CASING.getIngredient(tier),
                        'C', CIRCUIT.getIngredient(tier),
                        'S', SHAFTS[tier]);
                VanillaRecipeHelper.addShapedRecipe(provider, String.format("%s_whisk", tierMaterial.getName()), ChemicalHelper.get(whisk, tierMaterial),
                        "fId", "PIP", "PPP",
                        'I', new UnificationEntry(ingot, tierMaterial),
                        'P', new UnificationEntry(plate, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, FANS[tier].getId(), FANS[tier].asStack(),
                        " S ", "CMC", "wRh",
                        'S', SHAFTS[tier],
                        'C', CIRCUIT.getIngredient(tier),
                        'M', CASING.getIngredient(tier),
                        'R', new UnificationEntry(rotor, tierMaterial));

                if(tier != 9) {
                    VanillaRecipeHelper.addShapedRecipe(provider, SAWS[tier].getId(), SAWS[tier].asStack(),
                            "GSG", "MCM", "OHO",
                            'G', CIRCUIT.getIngredient(tier),
                            'S', new UnificationEntry(toolHeadBuzzSaw, tierMaterial),
                            'M', MOTOR.getIngredient(tier),
                            'C', CASING.getIngredient(tier),
                            'H', SHAFTS[tier],
                            'O', CONVEYOR.getIngredient(tier));
                }
            }
        }

        // ULS machines
        VanillaRecipeHelper.addShapedRecipe(provider, SAWS[0].getId(), SAWS[0].asStack(),
                "GSG", "OCO", "MHM",
                'G', new UnificationEntry(TagPrefix.pipeSmallFluid, GTMaterials.TinAlloy),
                'S', new UnificationEntry(toolHeadBuzzSaw, AndesiteAlloy),
                'M', new UnificationEntry(plate, WroughtIron),
                'C', CASING.getIngredient(ULV), 'H', SHAFTS[ULV],
                'O', new UnificationEntry(plate, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_PRESSES[0].getId(), MECHANICAL_PRESSES[0].asStack(),
                "PSP", "CMC", "wBh",
                'P', new UnificationEntry(plate, AndesiteAlloy),
                'S', SHAFTS[0],
                'C', new UnificationEntry(plate, WroughtIron),
                'M', CASING.getIngredient(0),
                'B', new UnificationEntry(block, TM[0]));
        VanillaRecipeHelper.addShapedRecipe(provider, MECHANICAL_MIXERS[0].getId(), MECHANICAL_MIXERS[0].asStack(),
                "PSP", "CMC", "wWh",
                'P', new UnificationEntry(plate, AndesiteAlloy),
                'S', SHAFTS[0],
                'C', new UnificationEntry(plate, WroughtIron),
                'M', CASING.getIngredient(0),
                'W', new UnificationEntry(whisk, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, MILLSTONES[0].getId(), MILLSTONES[0].asStack(),
                "CAC", "WHW", "wSh",
                'A', COGWHEELS[0],
                'C', new UnificationEntry(plate, AndesiteAlloy),
                'H', CASING.getIngredient(0),
                'W', Ingredient.of(ItemTags.WOODEN_SLABS),
                'S', SHAFTS[0]);
        VanillaRecipeHelper.addShapedRecipe(provider, FANS[0].getId(), FANS[0].asStack(),
                "ASA", "CMC", "wRh",
                'S', SHAFTS[0],
                'A', new UnificationEntry(plate, AndesiteAlloy),
                'C', new UnificationEntry(plate, WroughtIron),
                'M', CASING.getIngredient(0),
                'R', new UnificationEntry(rotor, AndesiteAlloy));

        //Default Create Things
        VanillaRecipeHelper.addShapedRecipe(provider, AllItems.WRENCH.getId(), AllItems.WRENCH.asStack(),
                "PP", "PC", " S",
                'P', new UnificationEntry(plate, Gold),
                'C', COGWHEELS[ULV],
                'S', new UnificationEntry(rod, Wood));
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.CHUTE.getId(), AllBlocks.CHUTE.asStack(),
                "PGP", "PCP", "wPh",
                'P', new UnificationEntry(plate, Iron),
                'G', new UnificationEntry(gearSmall, Iron),
                'C', Ingredient.of(Tags.Items.CHESTS_WOODEN));
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.BASIN.getId(), AllBlocks.BASIN.asStack(),
                "AhA", "AAA",
                'A', new UnificationEntry(plate, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, AllItems.BRASS_HAND.getId(), AllItems.BRASS_HAND.asStack(),
                " A ", "PPP", "hPf",
                'A', new UnificationEntry(plate, AndesiteAlloy),
                'P', new UnificationEntry(plate, Brass));
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.DEPLOYER.getId(), AllBlocks.DEPLOYER.asStack(),
                " C ", " R ", "hAf",
                'C', AllItems.ELECTRON_TUBE,
                'R', AllItems.BRASS_HAND,
                'A', AllBlocks.ANDESITE_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.DEPOT.getId(), AllBlocks.DEPOT.asStack(),
                " A ", "hCf",
                'A', new UnificationEntry(plate, AndesiteAlloy),
                'C', AllBlocks.ANDESITE_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.SPOUT.getId(), AllBlocks.SPOUT.asStack(),
                " C ", "hPf",
                'C', MECHANICAL_PUMPS[ULV],
                'P', AllBlocks.COPPER_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.MECHANICAL_CRAFTER.getId(), new ItemStack(AllBlocks.MECHANICAL_CRAFTER.asItem(), 3),
                " C ", " R ", "wAh",
                'C', AllItems.ELECTRON_TUBE,
                'R', Blocks.CRAFTING_TABLE,
                'A', AllBlocks.BRASS_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.TOOLBOXES.get(DyeColor.BROWN).getId(), AllBlocks.TOOLBOXES.get(DyeColor.BROWN).asStack(),
                " C ", "PHP", "wLf",
                'C', Cogwheels.ANDESITE_COGWHEEL,
                'P', new UnificationEntry(plate, Gold),
                'H', Ingredient.of(Tags.Items.CHESTS_WOODEN),
                'L', Items.LEATHER);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.WEIGHTED_EJECTOR.getId(), AllBlocks.WEIGHTED_EJECTOR.asStack(),
                " G ", " D ", "wCf",
                'G', new UnificationEntry(plate, Gold),
                'D', AllBlocks.DEPOT,
                'C', COGWHEELS[LV]);
        VanillaRecipeHelper.addShapelessRecipe(provider, AllBlocks.GEARSHIFT.getId(), AllBlocks.GEARSHIFT.asStack(),
                AllBlocks.ANDESITE_CASING,
                COGWHEELS[ULV],
                Blocks.REDSTONE_WIRE);
        VanillaRecipeHelper.addShapelessRecipe(provider, AllBlocks.SEQUENCED_GEARSHIFT.getId(), AllBlocks.SEQUENCED_GEARSHIFT.asStack(),
                AllBlocks.BRASS_CASING,
                COGWHEELS[ULV],
                AllItems.ELECTRON_TUBE);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.GANTRY_CARRIAGE.getId(), AllBlocks.GANTRY_CARRIAGE.asStack(),
                " S ", " O ", "wCf",
                'S', ItemTags.WOODEN_SLABS,
                'O', AllBlocks.ANDESITE_CASING,
                'C', COGWHEELS[LV]);
        VanillaRecipeHelper.addShapelessRecipe(provider, AllBlocks.CLUTCH.getId(), AllBlocks.CLUTCH.asStack(),
                AllBlocks.ANDESITE_CASING,
                SHAFTS[ULV],
                Blocks.REDSTONE_WIRE);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.TURNTABLE.getId(), AllBlocks.TURNTABLE.asStack(),
                " S ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'H', SHAFTS[LV]);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.FLYWHEEL.getId(), AllBlocks.FLYWHEEL.asStack(),
                "BBB", "BSB", "BBB",
                'B', new UnificationEntry(plate, Brass),
                'S', SHAFTS[ULV]);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.WATER_WHEEL.getId(), AllBlocks.WATER_WHEEL.asStack(),
                "BBB", "BSB", "BBB",
                'B', new UnificationEntry(plate, Wood),
                'S', SHAFTS[ULV]);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.LARGE_WATER_WHEEL.getId(), AllBlocks.LARGE_WATER_WHEEL.asStack(),
                "PPP", "PWP", "PPP",
                'P', new UnificationEntry(plate, Wood),
                'W', SHAFTS[LV]);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.WINDMILL_BEARING.getId(), AllBlocks.WINDMILL_BEARING.asStack(),
                " S ", " T ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'T', Tags.Items.STONE,
                'H', SHAFTS[MV]);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.MECHANICAL_BEARING.getId(), AllBlocks.MECHANICAL_BEARING.asStack(),
                " S ", " C ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'C', AllBlocks.ANDESITE_CASING,
                'H', SHAFTS[LV]);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.ANALOG_LEVER.getId(), AllBlocks.ANALOG_LEVER.asStack(),
                " B ", " S ", " C ",
                'B', Blocks.STONE_BUTTON,
                'S', Tags.Items.RODS_WOODEN,
                'C', AllBlocks.ANDESITE_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.DISPLAY_LINK.getId(), AllBlocks.DISPLAY_LINK.asStack(),
                " E ", " C ", " P ",
                'E', AllItems.ELECTRON_TUBE,
                'C', AllBlocks.BRASS_CASING,
                'P', new UnificationEntry(plate, Copper));
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.FLUID_TANK.getId(), AllBlocks.FLUID_TANK.asStack(),
                "SPS", "PBP", "SPS",
                'S', new UnificationEntry(screw, Copper),
                'P', new UnificationEntry(plate, Glass),
                'B', GTMachines.WOODEN_DRUM.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.FLUID_TANK.getId().withSuffix("_annealed"), new ItemStack(AllBlocks.FLUID_TANK.asItem(), 2),
                "SPS", "PBP", "SPS",
                'S', new UnificationEntry(screw, AnnealedCopper),
                'P', new UnificationEntry(plate, Glass),
                'B', GTMachines.WOODEN_DRUM.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.ITEM_VAULT.getId(), AllBlocks.ITEM_VAULT.asStack(),
                "SPS", "PBP", "SPS",
                'S', new UnificationEntry(screw, Iron),
                'P', new UnificationEntry(plate, Iron),
                'B', GTMachines.WOODEN_CRATE.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, AllBlocks.CHAIN_CONVEYOR.getId(), new ItemStack(AllBlocks.CHAIN_CONVEYOR.asItem(), 2),
                " A ", "ACA", " A ",
                'A', AllBlocks.ANDESITE_CASING,
                'C', LARGE_COGWHEELS[ULV]);
        VanillaRecipeHelper.addShapelessRecipe(provider, AllBlocks.SEQUENCED_GEARSHIFT.getId(), AllBlocks.SEQUENCED_GEARSHIFT.asStack(),
                AllBlocks.BRASS_CASING, COGWHEELS[ULV], AllItems.ELECTRON_TUBE);

        if(ConfigHolder.INSTANCE.recipes.hardToolArmorRecipes) {
            VanillaRecipeHelper.addShapedRecipe(provider, AllItems.COPPER_DIVING_HELMET.getId(), AllItems.COPPER_DIVING_HELMET.asStack(),
                    "PPP", "PGP",
                    'P', new UnificationEntry(plate, Copper),
                    'G', Tags.Items.GLASS);
            VanillaRecipeHelper.addShapedRecipe(provider, AllItems.COPPER_BACKTANK.getId(), AllItems.COPPER_BACKTANK.asStack(),
                    "ASA", "CBC", "wCf",
                    'A', new UnificationEntry(plate, AndesiteAlloy),
                    'S', SHAFTS[LV],
                    'C', new UnificationEntry(plate, Copper),
                    'B', Blocks.COPPER_BLOCK);
            VanillaRecipeHelper.addShapedRecipe(provider, AllItems.COPPER_DIVING_HELMET.getId(), AllItems.COPPER_DIVING_HELMET.asStack(),
                    "P P", "P P", "AhA",
                    'P', new UnificationEntry(plate, Copper),
                    'A', new UnificationEntry(plate, AndesiteAlloy));
        }

        if(GTCEuAPI.isHighTier()) {
            VanillaRecipeHelper.addShapedRecipe(provider, SAWS[9].getId(), SAWS[9].asStack(),
                    "GSG", "MCM", "OHO",
                    'G', CIRCUIT.getIngredient(UHV),
                    'S', new UnificationEntry(toolHeadBuzzSaw, Neutronium),
                    'M', MOTOR.getIngredient(UHV),
                    'C', CASING.getIngredient(UHV),
                    'H', SHAFTS[UHV],
                    'O', CONVEYOR.getIngredient(UHV));
        }

        for (int beltTier = 0; beltTier < BM.length; beltTier++) {
            Material beltMaterial = BM[beltTier];
            VanillaRecipeHelper.addShapedRecipe(provider, BELT_CONNECTORS[beltTier].getId(), BELT_CONNECTORS[beltTier].asStack(),
                    "PPP", "PPP", "f h",
                    'P', new UnificationEntry(plate, beltMaterial));
        }

        VanillaRecipeHelper.addShapedRecipe(provider, true, Greate.id("wire_coating_factory"), WIRE_COATING_FACTORY.asStack(),
                "WCW", "PSP", "WCW",
                'W', CASING_WATERTIGHT,
                'C', IV_CIRCUITS,
                'P', ELECTRIC_PUMP_IV.asStack(),
                'S', AllBlocks.SPOUT.asStack());

        //GT Components
        VanillaRecipeHelper.addShapedRecipe(provider, AllItems.ELECTRON_TUBE.getId(), AllItems.ELECTRON_TUBE.asStack(),
                " G ", " R ", "SSS",
                'G', GTItems.GLASS_TUBE,
                'R', AllItems.POLISHED_ROSE_QUARTZ,
                'S', new UnificationEntry(wireGtSingle, Steel));
    }
}
