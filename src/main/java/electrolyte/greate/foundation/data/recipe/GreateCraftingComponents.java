package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent;
import com.simibubi.create.AllItems;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateCraftingComponents {

    //TODO: add other crafting components (alloys)
    public static CraftingComponent SHAFT;
    public static CraftingComponent ALLOY;

    public static void register() {
        SHAFT = CraftingComponent.of("shaft", SHAFTS[ULV].asStack())
                .add(0, SHAFTS[ULV].asStack())
                .add(1, SHAFTS[LV].asStack())
                .add(2, SHAFTS[MV].asStack())
                .add(3, SHAFTS[HV].asStack())
                .add(4, SHAFTS[EV].asStack())
                .add(5, SHAFTS[IV].asStack())
                .add(6, SHAFTS[LuV].asStack())
                .add(7, SHAFTS[ZPM].asStack())
                .add(8, SHAFTS[UV].asStack())
                .add(9, SHAFTS[UHV].asStack());

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
