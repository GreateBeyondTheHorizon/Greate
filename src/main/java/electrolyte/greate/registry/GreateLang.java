package electrolyte.greate.registry;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class GreateLang {

    public static void register(RegistrateLangProvider provider) {
        provider.add("itemGroup.greate", "Greate");
        provider.add("greate.tooltip.max_speed", "Max Speed: ");
        provider.add("greate.tooltip.circuit_number", "Circuit Number");
        provider.add("greate.tooltip.belt_maxlength", "Max Length: ");
        provider.add("greate.tooltip.belt_usable", "Usable on: ");
        provider.add("greate.tooltip.pump_pressure", "Pump Pressure: ");
        provider.add("greate.tooltip.pump_transfer", "Transferring ");
        provider.add("greate.message.saw.recipe_type_toggle", "Changed active recipe type to: ");
        provider.add("greate.tooltip.saw.recipe_type_toggle", "Right-Click with a Screwdriver to toggle active recipe state.");
        provider.add("greate.saw", "Cutting");
        provider.add("greate.kinetic_stonecutting", "Stonecutting");
        provider.add("create.chain_conveyor.not_equal_tier", "Conveyors are not the same tier"); //intentional create prefix
        provider.add("greate.recipe.milling", "Milling");
        provider.add("greate.recipe.crushing", "Crushing");
        provider.add("greate.recipe.pressing", "Pressing");
        provider.add("greate.recipe.mixing", "Mixing");
        provider.add("greate.recipe.sawing", "Sawing");
        provider.add("greate.recipe.packing", "Packing");
        provider.add("greate.recipe.fan_washing", "Bulk Washing");
        provider.add("greate.recipe.fan_haunting", "Bulk Haunting");
        provider.add("greate.recipe.block_cutting", "Block Cutting");
        provider.add("greate.recipe.wood_cutting", "Wood Cutting");
        provider.add("greate.recipe.automatic_shapeless", "Automated Shapeless Crafting");
        provider.add("greate.recipe.automatic_packing", "Automated Shaped Crafting");
        provider.add("greate.recipe.automatic_brewing", "Automated Brewing");
        provider.add("greate.recipe.assembly.pressing", "Process in %s or higher Press");
        provider.add("greate.recipe.assembly.cutting", "Cut with %s or higher Saw");
        provider.add("greate.jei.recipe_tier", "Recipe Tier: ");
        provider.add("greate.recipe.processing.extra_chance", "% extra chance per tier");
        provider.add("tagprefix.whisk", "%s Whisk");
        provider.add("tagprefix.alloy", "%s Alloy");
        provider.add("item.gtceu.naquadah_alloy_alloy", "Naquadah Alloy");
        provider.add("config.screen.greate", "Greate Recipe Config");
        provider.add("config.greate.option.enableHardCreateRecipes", "Enable Hard Create Recipes");
        provider.add("config.greate.option.fanSpeedMultiplier", "Encased Fan Speed Multiplier");
        provider.add("config.greate.option.useCreateItemsInRecipes", "Use Create Items in Recipes");
        provider.add("config.greate.option.disableConflictingRecipes", "Disable Conflicting Recipes");
        provider.add("config.greate.option.processItemEntitiesWithFan", "Process Item Entities With Encased Fan");
        provider.add("config.greate.option.logUnremovableRecipes", "Log Unremovable Recipes");
        provider.add("config.greate.option.ignoredRecipeTypes", "Ignored Recipe Types");
        provider.add("config.greate.option.ignoredModIds", "Ignored Mod Ids");

        provider.add("greate.recipe.stress_input", "Uses %s su/tick");
        provider.add("greate.recipe.rpm_input_min", "Requires >= %s RPM");
        provider.add("greate.recipe.rpm_input_max", "Requires <= %s RPM");
        provider.add("greate.recipe.stress_output", "Generates %s su/tick @ %s RPM");

        provider.add("material.greate.andesite_alloy", "Andesite Alloy");
        provider.add("material.greate.rose_quartz", "Rose Quartz");
        provider.add("material.greate.chromatic_compound", "Chromatic Compound");
        provider.add("material.greate.shadow_steel", "Shadow Steel");
        provider.add("material.greate.refined_radiance", "Refined Radiance");

        provider.add("emi.category.greate.milling", "Milling");
        provider.add("emi.category.greate.cutting", "Sawing");
        provider.add("emi.category.greate.kinetic_stonecutting", "Stonecutting");

        provider.add("recipe.capability.stress.name", "Stress");
        provider.add("greate.recipe_modifier.insufficient_machine_tier", "Machine tier too low.");
        provider.add("greate.recipe_modifier.insufficient_stress_overclock", "Not enough stress to overclock recipe. Required: %ssu");
        provider.add("greate.recipe_modifier.insufficient_min_rpm", "Supplied RPM is too low. Required: >= %s RPM");
        provider.add("greate.recipe_modifier.insufficient_max_rpm", "Supplied RPM is too high. Required: <= %s RPM");

        provider.add("greate.tooltip.kinetic_input_box", "Kinetic Input for Multiblocks");
        provider.add("greate.tooltip.kinetic_output_box", "Kinetic Output for Multiblocks");
    }
}
