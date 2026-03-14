package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.GTCEu;
import com.simibubi.create.Create;
import electrolyte.greate.mixin.MixinWoodMachineRecipesAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class CreateRecipeRemoval {

    public static void disableCreateRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("crafting/curiosities/brown_toolbox"));

        recipe.accept(Create.asResource("crafting/kinetics/belt_connector"));
        recipe.accept(Create.asResource("crafting/kinetics/clutch"));
        recipe.accept(Create.asResource("crafting/kinetics/chain_conveyor"));
        recipe.accept(Create.asResource("crafting/kinetics/cogwheel"));
        recipe.accept(Create.asResource("crafting/kinetics/encased_fan"));
        recipe.accept(Create.asResource("crafting/kinetics/flywheel"));
        recipe.accept(Create.asResource("crafting/kinetics/gantry_carriage"));
        recipe.accept(Create.asResource("crafting/kinetics/gearbox"));
        recipe.accept(Create.asResource("crafting/kinetics/gearbox_from_conversion"));
        recipe.accept(Create.asResource("crafting/kinetics/gearshift"));
        recipe.accept(Create.asResource("crafting/kinetics/large_cogwheel"));
        recipe.accept(Create.asResource("crafting/kinetics/large_cogwheel_from_little"));
        recipe.accept(Create.asResource("crafting/kinetics/large_water_wheel"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_bearing"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_crafter"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_mixer"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_press"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_pump"));
        recipe.accept(Create.asResource("crafting/kinetics/mechanical_saw"));
        recipe.accept(Create.asResource("crafting/kinetics/millstone"));
        recipe.accept(Create.asResource("crafting/kinetics/propeller"));
        recipe.accept(Create.asResource("crafting/kinetics/shaft"));
        recipe.accept(Create.asResource("crafting/kinetics/sequenced_gearshift"));
        recipe.accept(Create.asResource("crafting/kinetics/turntable"));
        recipe.accept(Create.asResource("crafting/kinetics/vertical_gearbox"));
        recipe.accept(Create.asResource("crafting/kinetics/vertical_gearbox_from_conversion"));
        recipe.accept(Create.asResource("crafting/kinetics/water_wheel"));
        recipe.accept(Create.asResource("crafting/kinetics/weighted_ejector"));
        recipe.accept(Create.asResource("crafting/kinetics/whisk"));
        recipe.accept(Create.asResource("crafting/kinetics/windmill_bearing"));
        recipe.accept(Create.asResource("crafting/kinetics/wrench"));

        recipe.accept(Create.asResource("cutting/andesite_alloy"));

        recipe.accept(Create.asResource("crafting/materials/andesite_alloy"));
        recipe.accept(Create.asResource("crafting/materials/andesite_alloy_from_zinc"));

        recipe.accept(Create.asResource("deploying/cogwheel"));
        recipe.accept(Create.asResource("deploying/large_cogwheel"));

        recipe.accept(Create.asResource("item_application/andesite_casing_from_log"));
        recipe.accept(Create.asResource("item_application/andesite_casing_from_wood"));
        recipe.accept(Create.asResource("item_application/brass_casing_from_log"));
        recipe.accept(Create.asResource("item_application/brass_casing_from_wood"));
        recipe.accept(Create.asResource("item_application/copper_casing_from_log"));
        recipe.accept(Create.asResource("item_application/copper_casing_from_wood"));

        recipe.accept(Create.asResource("mechanical_crafting/crushing_wheel"));

        recipe.accept(Create.asResource("milling/andesite"));
        recipe.accept(Create.asResource("milling/granite"));

        recipe.accept(Create.asResource("mixing/andesite_alloy"));
        recipe.accept(Create.asResource("mixing/andesite_alloy_from_zinc"));
        recipe.accept(Create.asResource("mixing/brass_ingot"));

        recipe.accept(Create.asResource("sequenced_assembly/precision_mechanism"));
    }

    public static void disableConfigurableCreateRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("crafting/materials/brass_ingot_from_decompacting"));
        recipe.accept(Create.asResource("crafting/materials/brass_nugget_from_decompacting"));
        recipe.accept(Create.asResource("crafting/materials/brass_block_from_compacting"));
        recipe.accept(Create.asResource("crafting/materials/brass_ingot_from_compacting"));
        recipe.accept(Create.asResource("crafting/materials/copper_nugget"));
        recipe.accept(Create.asResource("crafting/materials/copper_ingot"));
        recipe.accept(Create.asResource("crafting/materials/zinc_ingot_from_decompacting"));
        recipe.accept(Create.asResource("crafting/materials/zinc_nugget_from_decompacting"));
        recipe.accept(Create.asResource("crafting/materials/zinc_block_from_compacting"));
        recipe.accept(Create.asResource("crafting/materials/zinc_ingot_from_compacting"));


        recipe.accept(Create.asResource("crafting/kinetics/analog_lever"));
        recipe.accept(Create.asResource("crafting/kinetics/basin"));
        recipe.accept(Create.asResource("crafting/kinetics/brass_hand"));
        recipe.accept(Create.asResource("crafting/kinetics/chute"));
        recipe.accept(Create.asResource("crafting/kinetics/deployer"));
        recipe.accept(Create.asResource("crafting/kinetics/depot"));
        recipe.accept(Create.asResource("crafting/kinetics/fluid_tank"));
        recipe.accept(Create.asResource("crafting/kinetics/item_vault"));
        recipe.accept(Create.asResource("crafting/kinetics/spout"));

        recipe.accept(Create.asResource("crafting/logistics/display_link"));

        recipe.accept(Create.asResource("crafting/materials/electron_tube"));
        recipe.accept(Create.asResource("crafting/materials/transmitter"));
    }

    public static void disableConflictingCreateRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("crushing/amethyst_block"));
        recipe.accept(Create.asResource("crushing/blaze_rod"));
        recipe.accept(Create.asResource("crushing/copper_ore"));
        recipe.accept(Create.asResource("crushing/deepslate_copper_ore"));
        recipe.accept(Create.asResource("crushing/deepslate_gold_ore"));
        recipe.accept(Create.asResource("crushing/deepslate_iron_ore"));
        recipe.accept(Create.asResource("crushing/diamond_horse_armor"));
        recipe.accept(Create.asResource("crushing/diorite"));
        recipe.accept(Create.asResource("crushing/diorite_recycling"));
        recipe.accept(Create.asResource("crushing/gold_ore"));
        recipe.accept(Create.asResource("crushing/golden_horse_armor"));
        recipe.accept(Create.asResource("crushing/gravel"));
        recipe.accept(Create.asResource("crushing/iron_horse_armor"));
        recipe.accept(Create.asResource("crushing/iron_ore"));
        recipe.accept(Create.asResource("crushing/lead_ore"));
        recipe.accept(Create.asResource("crushing/netherrack"));
        recipe.accept(Create.asResource("crushing/nickel_ore"));
        recipe.accept(Create.asResource("crushing/obsidian"));
        recipe.accept(Create.asResource("crushing/platinum_ore"));
        recipe.accept(Create.asResource("crushing/raw_copper"));
        recipe.accept(Create.asResource("crushing/raw_copper_block"));
        recipe.accept(Create.asResource("crushing/raw_gold"));
        recipe.accept(Create.asResource("crushing/raw_gold_block"));
        recipe.accept(Create.asResource("crushing/raw_iron"));
        recipe.accept(Create.asResource("crushing/raw_iron_block"));
        recipe.accept(Create.asResource("crushing/raw_lead"));
        recipe.accept(Create.asResource("crushing/raw_lead_block"));
        recipe.accept(Create.asResource("crushing/raw_nickel"));
        recipe.accept(Create.asResource("crushing/raw_nickel_block"));
        recipe.accept(Create.asResource("crushing/raw_platinum"));
        recipe.accept(Create.asResource("crushing/raw_platinum_block"));
        recipe.accept(Create.asResource("crushing/raw_silver"));
        recipe.accept(Create.asResource("crushing/raw_silver_block"));
        recipe.accept(Create.asResource("crushing/raw_tin"));
        recipe.accept(Create.asResource("crushing/raw_tin_block"));
        recipe.accept(Create.asResource("crushing/raw_zinc_block"));
        recipe.accept(Create.asResource("crushing/silver_ore"));
        recipe.accept(Create.asResource("crushing/tin_ore"));

        recipe.accept(Create.asResource("pressing/copper_ingot"));
        recipe.accept(Create.asResource("pressing/iron_ingot"));
        recipe.accept(Create.asResource("pressing/gold_ingot"));
        recipe.accept(Create.asResource("pressing/brass_ingot"));

        recipe.accept(Create.asResource("milling/sandstone"));
        recipe.accept(Create.asResource("milling/charcoal"));
        recipe.accept(Create.asResource("milling/sugar_cane"));
        recipe.accept(Create.asResource("milling/bone"));
        recipe.accept(Create.asResource("milling/clay"));
        recipe.accept(Create.asResource("milling/lapis_lazuli"));
        recipe.accept(Create.asResource("milling/coal"));
        recipe.accept(Create.asResource("milling/gravel"));
        recipe.accept(Create.asResource("milling/calcite"));
        recipe.accept(Create.asResource("milling/wheat"));
        recipe.accept(Create.asResource("milling/terracotta"));

        for(var entry : MixinWoodMachineRecipesAccessor.getDefaultEntries()) {
            ResourceLocation air = ResourceLocation.parse("air");
            ResourceLocation log = ForgeRegistries.ITEMS.getKey(entry.log);
            ResourceLocation planks = ForgeRegistries.ITEMS.getKey(entry.planks);
            ResourceLocation strippedLog = ForgeRegistries.ITEMS.getKey(entry.strippedLog);
            ResourceLocation wood = ForgeRegistries.ITEMS.getKey(entry.wood);
            ResourceLocation button = ForgeRegistries.ITEMS.getKey(entry.button);
            ResourceLocation slab = ForgeRegistries.ITEMS.getKey(entry.slab);
            ResourceLocation strippedWood = ForgeRegistries.ITEMS.getKey(entry.strippedWood);

            if(!strippedLog.equals(air)) {
                recipe.accept(Create.asResource(String.format("cutting/runtime_generated/compat/%s/%s_to_%s", strippedLog.getNamespace(), strippedLog.getPath(), planks.getPath())));
            }
            if(!strippedWood.equals(air) && !planks.equals(air)) {
                recipe.accept(Create.asResource(String.format("cutting/runtime_generated/compat/%s/%s_to_%s", strippedWood.getNamespace(), strippedWood.getPath(), planks.getPath())));
            }
            if(!wood.equals(air) && !strippedWood.equals(air)) {
                recipe.accept(Create.asResource(String.format("cutting/runtime_generated/compat/%s/%s_to_%s", wood.getNamespace(), wood.getPath(), strippedWood.getPath())));
            }
            if(!planks.equals(air)) {
                if(planks.getNamespace().equals(GTCEu.MOD_ID) && planks.getPath().contains("treated_wood")) continue; //no default recipe for treated wood buttons/slabs on the saw
                if(!button.equals(air)) {
                    recipe.accept(Create.asResource(String.format("cutting/runtime_generated/compat/%s/%s_to_%s", planks.getNamespace(), planks.getPath(), button.getPath())));
                }
                if(!slab.equals(air)) {
                    recipe.accept(Create.asResource(String.format("cutting/runtime_generated/compat/%s/%s_to_%s", planks.getNamespace(), planks.getPath(), slab.getPath())));
                }
            }
            if(!log.equals(air) && !strippedLog.equals(air)) {
                recipe.accept(Create.asResource(String.format("cutting/runtime_generated/compat/%s/%s_to_%s", log.getNamespace(), log.getPath(), strippedLog.getPath())));
            }
        }
    }
}
