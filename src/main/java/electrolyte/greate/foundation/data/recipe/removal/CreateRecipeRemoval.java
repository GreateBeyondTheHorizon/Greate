package electrolyte.greate.foundation.data.recipe.removal;

import com.simibubi.create.Create;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class CreateRecipeRemoval {

    public static void disableCreateRecipes(Consumer<ResourceLocation> recipe) {

        recipe.accept(Create.asResource("crafting/curiosities/brown_toolbox"));

        recipe.accept(Create.asResource("crafting/kinetics/analog_lever"));
        recipe.accept(Create.asResource("crafting/kinetics/basin"));
        recipe.accept(Create.asResource("crafting/kinetics/belt_connector"));
        recipe.accept(Create.asResource("crafting/kinetics/brass_hand"));
        recipe.accept(Create.asResource("crafting/kinetics/chain_conveyor"));
        recipe.accept(Create.asResource("crafting/kinetics/chute"));
        recipe.accept(Create.asResource("crafting/kinetics/clutch"));
        recipe.accept(Create.asResource("crafting/kinetics/cogwheel"));
        recipe.accept(Create.asResource("crafting/kinetics/deployer"));
        recipe.accept(Create.asResource("crafting/kinetics/depot"));
        recipe.accept(Create.asResource("crafting/kinetics/encased_fan"));
        recipe.accept(Create.asResource("crafting/kinetics/flywheel"));
        recipe.accept(Create.asResource("crafting/kinetics/gantry_carriage"));
        recipe.accept(Create.asResource("crafting/kinetics/gearbox"));
        recipe.accept(Create.asResource("crafting/kinetics/gearbox_from_conversion"));
        recipe.accept(Create.asResource("crafting/kinetics/gearshift"));
        recipe.accept(Create.asResource("crafting/kinetics/item_vault"));
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
        recipe.accept(Create.asResource("crafting/kinetics/sequenced_gearshift"));
        recipe.accept(Create.asResource("crafting/kinetics/shaft"));
        recipe.accept(Create.asResource("crafting/kinetics/spout"));
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

        recipe.accept(Create.asResource("mechanical_crafting/crushing_wheel"));

        recipe.accept(Create.asResource("mixing/andesite_alloy"));
        recipe.accept(Create.asResource("mixing/andesite_alloy_from_zinc"));

        recipe.accept(Create.asResource("sequenced_assembly/precision_mechanism"));
        recipe.accept(Create.asResource("sequenced_assembly/sturdy_sheet"));
    }
}
