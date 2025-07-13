package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.GTCEu;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static electrolyte.greate.foundation.data.recipe.removal.CableRecipeRemoval.disableCableRecipes;

public class GTRecipeRemoval {

    public static void disableGTRecipes(Consumer<ResourceLocation> recipe) {
        if(!Greate.CONFIG.enableGTWireCoatingRecipes) {
            disableCableRecipes(recipe);
        }

        recipe.accept(GTCEu.id("assembler/hopper_iron"));
        recipe.accept(GTCEu.id("assembler/hopper_wrought_iron"));
    }
}
