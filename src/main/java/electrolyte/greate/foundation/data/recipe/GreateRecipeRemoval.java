package electrolyte.greate.foundation.data.recipe;

import com.simibubi.create.Create;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class GreateRecipeRemoval {

    public static void init(Consumer<ResourceLocation> recipe) {
        disableCreateRecipes(recipe);
    }

    private static void disableCreateRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("crafting/materials/andesite_alloy"));
        recipe.accept(Create.asResource("crafting/materials/andesite_alloy_from_zinc"));

        recipe.accept(Create.asResource("crafting/kinetics/shaft"));
        recipe.accept(Create.asResource("crafting/kinetics/cogwheel"));
        recipe.accept(Create.asResource("crafting/kinetics/large_cogwheel"));
        recipe.accept(Create.asResource("crafting/kinetics/large_cogwheel_from_little"));
        recipe.accept(Create.asResource("crafting/kinetics/gearbox"));
        recipe.accept(Create.asResource("crafting/kinetics/gearboxfrom_conversion"));
        recipe.accept(Create.asResource("crafting/kinetics/vertical_gearboxfrom_conversion"));
        recipe.accept(Create.asResource("crafting/kinetics/millstone"));

        recipe.accept(Create.asResource("cutting/andesite_alloy"));

        recipe.accept(Create.asResource("mechanical_crafting/crushing_wheel"));
    }
}
