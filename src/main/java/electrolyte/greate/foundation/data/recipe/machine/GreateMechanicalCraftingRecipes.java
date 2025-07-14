package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Steel;
import static com.gregtechceu.gtceu.common.data.GTMaterials.VOLTAGE_COMMON_MATERIALS;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.CrushingWheels.CRUSHING_WHEELS;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class GreateMechanicalCraftingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            MechanicalCraftingRecipeBuilder.shapedRecipe(CRUSHING_WHEELS[tier], 2)
                    .key('A', ChemicalHelper.get(alloy, VOLTAGE_COMMON_MATERIALS[tier]).getItem())
                    .key('C', ChemicalHelper.get(plate, Steel).getItem())
                    .key('S', ChemicalHelper.get(shaft, TM[tier]).getItem())
                    .patternLine(" AAA ")
                    .patternLine("AACAA")
                    .patternLine("ACSCA")
                    .patternLine("AACAA")
                    .patternLine(" AAA ")
                    .build(provider);
        }
    }
}
