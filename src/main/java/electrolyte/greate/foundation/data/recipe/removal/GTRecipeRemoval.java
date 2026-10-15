package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.GTCEu;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;


public class GTRecipeRemoval {

    public static void disableGTRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(GTCEu.id("assembler/hopper_iron"));
        recipe.accept(GTCEu.id("assembler/hopper_wrought_iron"));
    }
}
