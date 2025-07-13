package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.CogwheelProperty;
import electrolyte.greate.content.gtceu.material.GreateMaterialFlags;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
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
import static com.gregtechceu.gtceu.data.recipe.CustomTags.IV_CIRCUITS;
import static com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.*;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.content.gtceu.machines.GreateMultiblockMachines.WIRE_COATING_FACTORY;
import static electrolyte.greate.foundation.data.recipe.GreateCraftingComponents.*;
import static electrolyte.greate.foundation.data.recipe.GreateCraftingComponents.PUMP;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.*;
import static electrolyte.greate.registry.MechanicalMixers.MECHANICAL_MIXERS;
import static electrolyte.greate.registry.MechanicalPresses.MECHANICAL_PRESSES;
import static electrolyte.greate.registry.Millstones.MILLSTONES;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;

public class GreateCraftingTableRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for (int tier = 0; tier < TM.length; tier++) {
            Material tierMaterial = TM[tier];

            // Machines
            VanillaRecipeHelper.addShapedRecipe(provider, true, MECHANICAL_PUMPS[tier].getId(), MECHANICAL_PUMPS[tier].asStack(),
                    " RS", "wPC", " RS",
                    'S', new MaterialEntry(screw, tierMaterial),
                    'R', new MaterialEntry(ring, Rubber),
                    'P', AllBlocks.FLUID_PIPE,
                    'C', COGWHEEL.get(tier));
            if(tier != 0) {
                VanillaRecipeHelper.addShapedRecipe(provider, true, MECHANICAL_PRESSES[tier].getId(), MECHANICAL_PRESSES[tier].asStack(),
                        "PSP", "CMC", "wBh",
                        'P', new MaterialEntry(plate, tierMaterial),
                        'S', SHAFT.get(tier),
                        'C', CIRCUIT.get(tier),
                        'M', CASING.get(tier),
                        'B', new MaterialEntry(block, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, true, MECHANICAL_MIXERS[tier].getId(), MECHANICAL_MIXERS[tier].asStack(),
                        " S ", "CMC", "wWh",
                        'S', SHAFT.get(tier),
                        'C', CIRCUIT.get(tier),
                        'M', CASING.get(tier),
                        'W', new MaterialEntry(whisk, tierMaterial));
                VanillaRecipeHelper.addShapedRecipe(provider, true, MILLSTONES[tier].getId(), MILLSTONES[tier].asStack(),
                        "CAC", "WHW", "wSh",
                        'A', COGWHEEL.get(tier),
                        'W', Ingredient.of(ItemTags.WOODEN_SLABS),
                        'H', CASING.get(tier),
                        'C', CIRCUIT.get(tier),
                        'S', SHAFT.get(tier));
                VanillaRecipeHelper.addShapedRecipe(provider, true, FANS[tier].getId(), FANS[tier].asStack(),
                        " S ", "CMC", "wRh",
                        'S', SHAFT.get(tier),
                        'C', CIRCUIT.get(tier),
                        'M', CASING.get(tier),
                        'R', new MaterialEntry(rotor, tierMaterial));

                if(tier != 9) {
                    VanillaRecipeHelper.addShapedRecipe(provider, true, SAWS[tier].getId(), SAWS[tier].asStack(),
                            "GSG", "MCM", "OHO",
                            'G', CIRCUIT.get(tier),
                            'S', new MaterialEntry(toolHeadBuzzSaw, tierMaterial),
                            'M', MOTOR.get(tier),
                            'C', CASING.get(tier),
                            'H', SHAFT.get(tier),
                            'O', CONVEYOR.get(tier));
                }
            }
        }

        // ULS machines
        VanillaRecipeHelper.addShapedRecipe(provider, true, SAWS[0].getId(), SAWS[0].asStack(),
                "GSG", "OCO", "MHM",
                'G', new MaterialEntry(TagPrefix.pipeSmallFluid, GTMaterials.TinAlloy),
                'S', new MaterialEntry(toolHeadBuzzSaw, AndesiteAlloy),
                'M', new MaterialEntry(plate, WroughtIron),
                'C', CASING.get(ULV), 'H', SHAFT.get(ULV),
                'O', new MaterialEntry(plate, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, true, MECHANICAL_PRESSES[0].getId(), MECHANICAL_PRESSES[0].asStack(),
                "PSP", "CMC", "wBh",
                'P', new MaterialEntry(plate, AndesiteAlloy),
                'S', SHAFT.get(0),
                'C', new MaterialEntry(plate, WroughtIron),
                'M', CASING.get(0),
                'B', new MaterialEntry(block, TM[0]));
        VanillaRecipeHelper.addShapedRecipe(provider, true, MECHANICAL_MIXERS[0].getId(), MECHANICAL_MIXERS[0].asStack(),
                "PSP", "CMC", "wWh",
                'P', new MaterialEntry(plate, AndesiteAlloy),
                'S', SHAFT.get(0),
                'C', new MaterialEntry(plate, WroughtIron),
                'M', CASING.get(0),
                'W', new MaterialEntry(whisk, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, true, MILLSTONES[0].getId(), MILLSTONES[0].asStack(),
                "CAC", "WHW", "wSh",
                'A', COGWHEEL.get(0),
                'C', new MaterialEntry(plate, AndesiteAlloy),
                'H', CASING.get(0),
                'W', Ingredient.of(ItemTags.WOODEN_SLABS),
                'S', SHAFT.get(0));
        VanillaRecipeHelper.addShapedRecipe(provider, true, FANS[0].getId(), FANS[0].asStack(),
                "ASA", "CMC", "wRh",
                'S', SHAFT.get(0),
                'A', new MaterialEntry(plate, AndesiteAlloy),
                'C', new MaterialEntry(plate, WroughtIron),
                'M', CASING.get(0),
                'R', new MaterialEntry(rotor, AndesiteAlloy));

        VanillaRecipeHelper.addShapedRecipe(provider, AndesiteAlloy.getName() + "_shaft", ChemicalHelper.get(shaft, AndesiteAlloy).copyWithCount(4),
                    "s ", " A",
                    'A', new MaterialEntry(alloy, WroughtIron)); //special case for andesite alloy (from create)

        if(ConfigHolder.INSTANCE.recipes.hardToolArmorRecipes) {
            VanillaRecipeHelper.addShapedRecipe(provider, AllItems.COPPER_DIVING_HELMET.getId(), AllItems.COPPER_DIVING_HELMET.asStack(),
                    "PPP", "PGP",
                    'P', new MaterialEntry(plate, Copper),
                    'G', Tags.Items.GLASS);
            VanillaRecipeHelper.addShapedRecipe(provider, AllItems.COPPER_BACKTANK.getId(), AllItems.COPPER_BACKTANK.asStack(),
                    "ASA", "CBC", "wCf",
                    'A', new MaterialEntry(plate, AndesiteAlloy),
                    'S', SHAFT.get(LV),
                    'C', new MaterialEntry(plate, Copper),
                    'B', Blocks.COPPER_BLOCK);
            VanillaRecipeHelper.addShapedRecipe(provider, AllItems.COPPER_DIVING_HELMET.getId(), AllItems.COPPER_DIVING_HELMET.asStack(),
                    "P P", "P P", "AhA",
                    'P', new MaterialEntry(plate, Copper),
                    'A', new MaterialEntry(plate, AndesiteAlloy));
        }

        if(GTCEuAPI.isHighTier()) {
            VanillaRecipeHelper.addShapedRecipe(provider, true, SAWS[9].getId(), SAWS[9].asStack(),
                    "GSG", "MCM", "OHO",
                    'G', CIRCUIT.get(UHV),
                    'S', new MaterialEntry(toolHeadBuzzSaw, Neutronium),
                    'M', MOTOR.get(UHV),
                    'C', CASING.get(UHV),
                    'H', SHAFT.get(UHV),
                    'O', CONVEYOR.get(UHV));
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
                'S', new MaterialEntry(wireGtSingle, Steel));
    }

    public static void registerMaterialRecipes(Consumer<FinishedRecipe> provider, Material material) {
        if(material.hasFlag(GreateMaterialFlags.GENERATE_ALLOY)) {
            VanillaRecipeHelper.addShapedRecipe(provider, material.getName() + "_alloy", ChemicalHelper.get(alloy, material),
                    "NA", "AN", "fh",
                    'N', new MaterialEntry(plate, material),
                    'A', new ItemStack(Blocks.ANDESITE));
        }

        if(material.hasFlag(GreateMaterialFlags.GENERATE_WHISK)) {
            VanillaRecipeHelper.addShapedRecipe(provider, material.getName() + "_whisk", ChemicalHelper.get(whisk, material),
                    "fId", "PIP", "PPP",
                    'I', new MaterialEntry(ingot, material),
                    'P', new MaterialEntry(plate, material));
        }

        if(material.hasProperty(GreatePropertyKeys.BELT)) {
            VanillaRecipeHelper.addShapedRecipe(provider, true, material.getName() + "_belt_connector", ChemicalHelper.get(beltConnector, material),
                    "PPP", "PPP", "f h",
                    'P', new MaterialEntry(plate, material));
        }

        //<!!! [ONLY KINETIC RELATED RECIPES BELOW THIS LINE] !!!>
        if(!material.hasProperty(GreatePropertyKeys.KINETIC)) return;

        if(!material.getName().equals(AndesiteAlloy.getName())) { //special case, since wrought iron is 'andesite alloy'
            VanillaRecipeHelper.addShapedRecipe(provider, material.getName() + "_shaft", ChemicalHelper.get(shaft, material).copyWithCount(4),
                    "s ", " A",
                    'A', ChemicalHelper.get(alloy, material));
        }

        if(material.hasProperty(GreatePropertyKeys.COGWHEEL)) {
            CogwheelProperty prop = material.getProperty(GreatePropertyKeys.COGWHEEL);
            Material previousTierMaterial = prop.getPreviousMaterial();
            VanillaRecipeHelper.addShapedRecipe(provider, true,material.getName() + "_cogwheel", ChemicalHelper.get(cogwheel, material),
                    "SP", "f ",
                    'S', new MaterialEntry(shaft, material),
                    'P', new MaterialEntry(plate, previousTierMaterial));

            VanillaRecipeHelper.addShapedRecipe(provider, true,material.getName() + "_large_cogwheel", ChemicalHelper.get(largeCogwheel, material),
                    "SP", "Pf",
                    'S', new MaterialEntry(shaft, material),
                    'P', new MaterialEntry(plate, previousTierMaterial));

            VanillaRecipeHelper.addShapedRecipe(provider,material.getName() + "_large_cogwheel_from_little", ChemicalHelper.get(largeCogwheel, material),
                    "CP", "f ",
                    'C', new MaterialEntry(cogwheel, material),
                    'P', new MaterialEntry(plate, previousTierMaterial));

            VanillaRecipeHelper.addShapedRecipe(provider, true, material.getName() + "_gearbox", ChemicalHelper.get(gearbox, material),
                    " S ", "SCS", "wSh",
                    'S', new MaterialEntry(shaft, material),
                    'C', AllBlocks.ANDESITE_CASING);

            VanillaRecipeHelper.addShapedRecipe(provider, true, material.getName() + "_vertical_gearbox", ChemicalHelper.get(verticalGearbox, material),
                    "S S", "wCh", "S S",
                    'S', new MaterialEntry(shaft, material),
                    'C', AllBlocks.ANDESITE_CASING);

            VanillaRecipeHelper.addShapelessRecipe(provider, material.getName() + "_gearbox_from_conversion", ChemicalHelper.get(gearbox, material),
                    ChemicalHelper.get(verticalGearbox, material));

            VanillaRecipeHelper.addShapelessRecipe(provider, material.getName() + "_vertical_gearbox_from_conversion", ChemicalHelper.get(verticalGearbox, material),
                    ChemicalHelper.get(gearbox, material));
        }
    }

    public static void registerCreateRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllItems.WRENCH.getId().getPath()), AllItems.WRENCH.asStack(),
                "PP", "PC", " S",
                'P', new MaterialEntry(plate, Gold),
                'C', COGWHEEL.get(ULV),
                'S', new MaterialEntry(rod, Wood));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.TOOLBOXES.get(DyeColor.BROWN).getId().getPath()), AllBlocks.TOOLBOXES.get(DyeColor.BROWN).asStack(),
                " C ", "PHP", "wLf",
                'C', COGWHEEL.get(ULV),
                'P', new MaterialEntry(plate, Gold),
                'H', Ingredient.of(Tags.Items.CHESTS_WOODEN),
                'L', Items.LEATHER);
        VanillaRecipeHelper.addShapelessRecipe(provider, Greate.id(AllBlocks.GEARSHIFT.getId().getPath()), AllBlocks.GEARSHIFT.asStack(),
                AllBlocks.ANDESITE_CASING,
                COGWHEEL.get(ULV),
                Blocks.REDSTONE_WIRE);
        VanillaRecipeHelper.addShapelessRecipe(provider, Greate.id(AllBlocks.SEQUENCED_GEARSHIFT.getId().getPath()), AllBlocks.SEQUENCED_GEARSHIFT.asStack(),
                AllBlocks.BRASS_CASING,
                COGWHEEL.get(ULV),
                AllItems.ELECTRON_TUBE);
        VanillaRecipeHelper.addShapelessRecipe(provider, Greate.id(AllBlocks.CLUTCH.getId().getPath()), AllBlocks.CLUTCH.asStack(),
                AllBlocks.ANDESITE_CASING,
                SHAFT.get(ULV),
                Blocks.REDSTONE_WIRE);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.FLYWHEEL.getId().getPath()), AllBlocks.FLYWHEEL.asStack(),
                "BBB", "BSB", "BBB",
                'B', new MaterialEntry(plate, Brass),
                'S', SHAFT.get(ULV));

    }

    public static void registerEasyCreateRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.WATER_WHEEL.getId().getPath()), AllBlocks.WATER_WHEEL.asStack(),
                "BBB", "BSB", "BBB",
                'B', ItemTags.WOODEN_SLABS,
                'S', SHAFT.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.LARGE_WATER_WHEEL.getId().getPath()), AllBlocks.LARGE_WATER_WHEEL.asStack(),
                "PPP", "PWP", "PPP",
                'P', ItemTags.WOODEN_SLABS,
                'W', SHAFT.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.WINDMILL_BEARING.getId().getPath()), AllBlocks.WINDMILL_BEARING.asStack(),
                " S ", " T ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'T', Tags.Items.STONE,
                'H', SHAFT.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.MECHANICAL_BEARING.getId().getPath()), AllBlocks.MECHANICAL_BEARING.asStack(),
                " S ", " C ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'C', AllBlocks.ANDESITE_CASING,
                'H', SHAFT.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.CHAIN_CONVEYOR.getId().getPath()), new ItemStack(AllBlocks.CHAIN_CONVEYOR.asItem(), 2),
                " A ", "ACA", " A ",
                'A', AllBlocks.ANDESITE_CASING,
                'C', LARGE_COGWHEEL.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.MECHANICAL_CRAFTER.getId().getPath()), new ItemStack(AllBlocks.MECHANICAL_CRAFTER.asItem(), 3),
                " C ", "EAE", " R ",
                'C', AllItems.ELECTRON_TUBE,
                'R', Blocks.CRAFTING_TABLE,
                'E', COGWHEEL.get(ULV),
                'A', AllBlocks.BRASS_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.WEIGHTED_EJECTOR.getId().getPath()), AllBlocks.WEIGHTED_EJECTOR.asStack(),
                " G ", " D ", " C ",
                'G', new MaterialEntry(plate, Gold),
                'D', AllBlocks.DEPOT,
                'C', COGWHEEL.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.GANTRY_CARRIAGE.getId().getPath()), AllBlocks.GANTRY_CARRIAGE.asStack(),
                " S ", " O ", " C ",
                'S', ItemTags.WOODEN_SLABS,
                'O', AllBlocks.ANDESITE_CASING,
                'C', COGWHEEL.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.TURNTABLE.getId().getPath()), AllBlocks.TURNTABLE.asStack(),
                " S ", " H ",
                'S', ItemTags.WOODEN_SLABS,
                'H', SHAFT.get(ULV));
    }

    public static void registerHardCreateRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.CHUTE.getId().getPath()), AllBlocks.CHUTE.asStack(),
                "PGP", "PCP", "wPh",
                'P', new MaterialEntry(plate, Iron),
                'G', new MaterialEntry(gearSmall, Iron),
                'C', Ingredient.of(Tags.Items.CHESTS_WOODEN));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.BASIN.getId().getPath()), AllBlocks.BASIN.asStack(),
                "AhA", "AAA",
                'A', new MaterialEntry(plate, AndesiteAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllItems.BRASS_HAND.getId().getPath()), AllItems.BRASS_HAND.asStack(),
                " A ", "PPP", "hPf",
                'A', new MaterialEntry(plate, AndesiteAlloy),
                'P', new MaterialEntry(plate, Brass));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.DEPLOYER.getId().getPath()), AllBlocks.DEPLOYER.asStack(),
                " C ", "SRS", "hAf",
                'C', AllItems.ELECTRON_TUBE,
                'R', AllItems.BRASS_HAND,
                'S', SHAFT.get(ULV),
                'A', AllBlocks.ANDESITE_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.DEPOT.getId().getPath()), AllBlocks.DEPOT.asStack(),
                " A ", "hCf",
                'A', new MaterialEntry(plate, AndesiteAlloy),
                'C', AllBlocks.ANDESITE_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.SPOUT.getId().getPath()), AllBlocks.SPOUT.asStack(),
                " C ", "hPf",
                'C', PUMP.get(ULV),
                'P', AllBlocks.COPPER_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.MECHANICAL_CRAFTER.getId().getPath()), new ItemStack(AllBlocks.MECHANICAL_CRAFTER.asItem(), 3),
                " C ", "EAE", "wRh",
                'C', AllItems.ELECTRON_TUBE,
                'R', Blocks.CRAFTING_TABLE,
                'E', COGWHEEL.get(ULV),
                'A', AllBlocks.BRASS_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.WEIGHTED_EJECTOR.getId().getPath()), AllBlocks.WEIGHTED_EJECTOR.asStack(),
                " G ", " D ", "wCf",
                'G', new MaterialEntry(plate, Gold),
                'D', AllBlocks.DEPOT,
                'C', COGWHEEL.get(LV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.GANTRY_CARRIAGE.getId().getPath()), AllBlocks.GANTRY_CARRIAGE.asStack(),
                " S ", " O ", "wCf",
                'S', ItemTags.WOODEN_SLABS,
                'O', AllBlocks.ANDESITE_CASING,
                'C', COGWHEEL.get(LV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.TURNTABLE.getId().getPath()), AllBlocks.TURNTABLE.asStack(),
                " S ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'H', SHAFT.get(LV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.WATER_WHEEL.getId().getPath()), AllBlocks.WATER_WHEEL.asStack(),
                "BBB", "BSB", "BBB",
                'B', new MaterialEntry(plate, Wood),
                'S', SHAFT.get(ULV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.LARGE_WATER_WHEEL.getId().getPath()), AllBlocks.LARGE_WATER_WHEEL.asStack(),
                "PPP", "PWP", "PPP",
                'P', new MaterialEntry(plate, Wood),
                'W', SHAFT.get(LV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.WINDMILL_BEARING.getId().getPath()), AllBlocks.WINDMILL_BEARING.asStack(),
                " S ", " T ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'T', Tags.Items.STONE,
                'H', SHAFT.get(MV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.MECHANICAL_BEARING.getId().getPath()), AllBlocks.MECHANICAL_BEARING.asStack(),
                " S ", " C ", "wHf",
                'S', ItemTags.WOODEN_SLABS,
                'C', AllBlocks.ANDESITE_CASING,
                'H', SHAFT.get(LV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.ANALOG_LEVER.getId().getPath()), AllBlocks.ANALOG_LEVER.asStack(),
                " B ", " S ", " C ",
                'B', Blocks.STONE_BUTTON,
                'S', Tags.Items.RODS_WOODEN,
                'C', AllBlocks.ANDESITE_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllItems.TRANSMITTER.getId().getPath()), AllItems.TRANSMITTER.asStack(),
                " E ", "CCC", "wRh",
                'E', AllItems.ELECTRON_TUBE,
                'R', Items.REDSTONE,
                'C', new MaterialEntry(plate, Copper));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.DISPLAY_LINK.getId().getPath()), AllBlocks.DISPLAY_LINK.asStack(),
                " E ", "wCh",
                'E', AllItems.TRANSMITTER,
                'C', AllBlocks.BRASS_CASING);
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.FLUID_TANK.getId().getPath()), AllBlocks.FLUID_TANK.asStack(),
                "SPS", "PBP", "SPS",
                'S', new MaterialEntry(screw, Copper),
                'P', new MaterialEntry(plate, Glass),
                'B', GTMachines.WOODEN_DRUM.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.FLUID_TANK.getId().getPath()).withSuffix("_annealed"), new ItemStack(AllBlocks.FLUID_TANK.asItem(), 2),
                "SPS", "PBP", "SPS",
                'S', new MaterialEntry(screw, AnnealedCopper),
                'P', new MaterialEntry(plate, Glass),
                'B', GTMachines.WOODEN_DRUM.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.ITEM_VAULT.getId().getPath()), AllBlocks.ITEM_VAULT.asStack(),
                "SPS", "PBP", "SPS",
                'S', new MaterialEntry(screw, Iron),
                'P', new MaterialEntry(plate, Iron),
                'B', GTMachines.WOODEN_CRATE.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.ITEM_VAULT.getId().getPath()), new ItemStack(AllBlocks.ITEM_VAULT.asItem(), 2),
                "SPS", "PBP", "SPS",
                'S', new MaterialEntry(screw, WroughtIron),
                'P', new MaterialEntry(plate, Iron),
                'B', GTMachines.WOODEN_CRATE.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllBlocks.CHAIN_CONVEYOR.getId().getPath()), new ItemStack(AllBlocks.CHAIN_CONVEYOR.asItem(), 2),
                " A ", "ACA", "wAh",
                'A', AllBlocks.ANDESITE_CASING,
                'C', LARGE_COGWHEEL.get(LV));
        VanillaRecipeHelper.addShapedRecipe(provider, Greate.id(AllItems.ELECTRON_TUBE.getId().getPath()), AllItems.ELECTRON_TUBE.asStack(),
                " R ", " G ", "SSS",
                'R', AllItems.POLISHED_ROSE_QUARTZ,
                'G', GTItems.GLASS_TUBE,
                'S', new MaterialEntry(wireGtSingle, Steel));
    }
}
