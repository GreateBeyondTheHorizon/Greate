package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent;
import com.simibubi.create.AllItems;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class GreateCraftingComponents {

    //TODO: add other crafting components (alloys)
    public static CraftingComponent SHAFT;
    public static CraftingComponent ALLOY;

    public static void register() {
        SHAFT = CraftingComponent.of("shaft", shaft, AndesiteAlloy)
                .add(0, shaft, AndesiteAlloy)
                .add(1, shaft, Steel)
                .add(2, shaft, Aluminium)
                .add(3, shaft, StainlessSteel)
                .add(4, shaft, Titanium)
                .add(5, shaft, TungstenSteel)
                .add(6, shaft, RhodiumPlatedPalladium)
                .add(7, shaft, Naquadah)
                .add(8, shaft, Darmstadtium)
                .add(9, shaft, Neutronium);

        ALLOY = CraftingComponent.of("alloy", alloy, AndesiteAlloy)
                .add(0, AllItems.ANDESITE_ALLOY.asStack())
                .add(1, alloy, Steel)
                .add(2, alloy, Aluminium)
                .add(3, alloy, StainlessSteel)
                .add(4, alloy, Titanium)
                .add(5, alloy, TungstenSteel)
                .add(6, alloy, RhodiumPlatedPalladium)
                .add(7, alloy, Naquadah)
                .add(8, alloy, Darmstadtium)
                .add(9, alloy, Neutronium);
    }
}
