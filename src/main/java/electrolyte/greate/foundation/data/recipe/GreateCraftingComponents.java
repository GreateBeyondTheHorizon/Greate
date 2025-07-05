package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent;
import com.simibubi.create.AllItems;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.*;

public class GreateCraftingComponents {

    //TODO: add other crafting components (alloys)
    public static CraftingComponent SHAFT;
    public static CraftingComponent ALLOY;
    public static CraftingComponent COGWHEEL;
    public static CraftingComponent LARGE_COGWHEEL;
    public static CraftingComponent GEARBOX;
    public static CraftingComponent VERTICAL_GEARBOX;


    public static void register() {
        SHAFT = CraftingComponent.of("shaft", shaft, AndesiteAlloy)
                .add(0, shaft, AndesiteAlloy)
                .add(1, shaft, Steel)
                .add(2, shaft, Aluminium)
                .add(3, shaft, StainlessSteel)
                .add(4, shaft, Titanium)
                .add(5, shaft, TungstenSteel)
                .add(6, shaft, RhodiumPlatedPalladium)
                .add(7, shaft, NaquadahAlloy)
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
                .add(7, alloy, NaquadahAlloy)
                .add(8, alloy, Darmstadtium)
                .add(9, alloy, Neutronium);

        COGWHEEL = CraftingComponent.of("cogwheel", cogwheel, AndesiteAlloy)
                .add(0, cogwheel, AndesiteAlloy)
                .add(1, cogwheel, Steel)
                .add(2, cogwheel, Aluminium)
                .add(3, cogwheel, StainlessSteel)
                .add(4, cogwheel, Titanium)
                .add(5, cogwheel, TungstenSteel)
                .add(6, cogwheel, RhodiumPlatedPalladium)
                .add(7, cogwheel, NaquadahAlloy)
                .add(8, cogwheel, Darmstadtium)
                .add(9, cogwheel, Neutronium);

        LARGE_COGWHEEL = CraftingComponent.of("large_cogwheel", largeCogwheel, AndesiteAlloy)
                .add(0, largeCogwheel, AndesiteAlloy)
                .add(1, largeCogwheel, Steel)
                .add(2, largeCogwheel, Aluminium)
                .add(3, largeCogwheel, StainlessSteel)
                .add(4, largeCogwheel, Titanium)
                .add(5, largeCogwheel, TungstenSteel)
                .add(6, largeCogwheel, RhodiumPlatedPalladium)
                .add(7, largeCogwheel, NaquadahAlloy)
                .add(8, largeCogwheel, Darmstadtium)
                .add(9, largeCogwheel, Neutronium);

        GEARBOX = CraftingComponent.of("gearbox", gearbox, AndesiteAlloy)
                .add(0, gearbox, AndesiteAlloy)
                .add(1, gearbox, Steel)
                .add(2, gearbox, Aluminium)
                .add(3, gearbox, StainlessSteel)
                .add(4, gearbox, Titanium)
                .add(5, gearbox, TungstenSteel)
                .add(6, gearbox, RhodiumPlatedPalladium)
                .add(7, gearbox, NaquadahAlloy)
                .add(8, gearbox, Darmstadtium)
                .add(9, gearbox, Neutronium);

        VERTICAL_GEARBOX = CraftingComponent.of("vertical_gearbox", verticalGearbox, AndesiteAlloy)
                .add(0, verticalGearbox, AndesiteAlloy)
                .add(1, verticalGearbox, Steel)
                .add(2, verticalGearbox, Aluminium)
                .add(3, verticalGearbox, StainlessSteel)
                .add(4, verticalGearbox, Titanium)
                .add(5, verticalGearbox, TungstenSteel)
                .add(6, verticalGearbox, RhodiumPlatedPalladium)
                .add(7, verticalGearbox, NaquadahAlloy)
                .add(8, verticalGearbox, Darmstadtium)
                .add(9, verticalGearbox, Neutronium);
    }
}
