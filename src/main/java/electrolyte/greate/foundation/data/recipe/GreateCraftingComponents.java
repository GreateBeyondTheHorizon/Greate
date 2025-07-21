package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.recipe.component.CraftingComponent;
import com.simibubi.create.AllItems;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.data.material.GTMaterials.*;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.*;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;

public class GreateCraftingComponents {

    public static CraftingComponent SHAFT;
    public static CraftingComponent ALLOY;
    public static CraftingComponent COGWHEEL;
    public static CraftingComponent LARGE_COGWHEEL;
    public static CraftingComponent GEARBOX;
    public static CraftingComponent VERTICAL_GEARBOX;
    public static CraftingComponent PUMP;


    public static void register() {
        SHAFT = CraftingComponent.of("shaft", shaft, AndesiteAlloy)
                .add(ULV, shaft, AndesiteAlloy)
                .add(LV, shaft, Steel)
                .add(MV, shaft, Aluminium)
                .add(HV, shaft, StainlessSteel)
                .add(EV, shaft, Titanium)
                .add(IV, shaft, TungstenSteel)
                .add(LuV, shaft, RhodiumPlatedPalladium)
                .add(ZPM, shaft, NaquadahAlloy)
                .add(UV, shaft, Darmstadtium)
                .add(UHV, shaft, Neutronium);

        ALLOY = CraftingComponent.of("alloy", alloy, AndesiteAlloy)
                .add(ULV, AllItems.ANDESITE_ALLOY.asStack())
                .add(LV, alloy, Steel)
                .add(MV, alloy, Aluminium)
                .add(HV, alloy, StainlessSteel)
                .add(EV, alloy, Titanium)
                .add(IV, alloy, TungstenSteel)
                .add(LuV, alloy, RhodiumPlatedPalladium)
                .add(ZPM, alloy, NaquadahAlloy)
                .add(UV, alloy, Darmstadtium)
                .add(UHV, alloy, Neutronium);

        COGWHEEL = CraftingComponent.of("cogwheel", cogwheel, AndesiteAlloy)
                .add(ULV, cogwheel, AndesiteAlloy)
                .add(LV, cogwheel, Steel)
                .add(MV, cogwheel, Aluminium)
                .add(HV, cogwheel, StainlessSteel)
                .add(EV, cogwheel, Titanium)
                .add(IV, cogwheel, TungstenSteel)
                .add(LuV, cogwheel, RhodiumPlatedPalladium)
                .add(ZPM, cogwheel, NaquadahAlloy)
                .add(UV, cogwheel, Darmstadtium)
                .add(UHV, cogwheel, Neutronium);

        LARGE_COGWHEEL = CraftingComponent.of("large_cogwheel", largeCogwheel, AndesiteAlloy)
                .add(ULV, largeCogwheel, AndesiteAlloy)
                .add(LV, largeCogwheel, Steel)
                .add(MV, largeCogwheel, Aluminium)
                .add(HV, largeCogwheel, StainlessSteel)
                .add(EV, largeCogwheel, Titanium)
                .add(IV, largeCogwheel, TungstenSteel)
                .add(LuV, largeCogwheel, RhodiumPlatedPalladium)
                .add(ZPM, largeCogwheel, NaquadahAlloy)
                .add(UV, largeCogwheel, Darmstadtium)
                .add(UHV, largeCogwheel, Neutronium);

        GEARBOX = CraftingComponent.of("gearbox", gearbox, AndesiteAlloy)
                .add(ULV, gearbox, AndesiteAlloy)
                .add(LV, gearbox, Steel)
                .add(MV, gearbox, Aluminium)
                .add(HV, gearbox, StainlessSteel)
                .add(EV, gearbox, Titanium)
                .add(IV, gearbox, TungstenSteel)
                .add(LuV, gearbox, RhodiumPlatedPalladium)
                .add(ZPM, gearbox, NaquadahAlloy)
                .add(UV, gearbox, Darmstadtium)
                .add(UHV, gearbox, Neutronium);

        VERTICAL_GEARBOX = CraftingComponent.of("vertical_gearbox", verticalGearbox, AndesiteAlloy)
                .add(ULV, verticalGearbox, AndesiteAlloy)
                .add(LV, verticalGearbox, Steel)
                .add(MV, verticalGearbox, Aluminium)
                .add(HV, verticalGearbox, StainlessSteel)
                .add(EV, verticalGearbox, Titanium)
                .add(IV, verticalGearbox, TungstenSteel)
                .add(LuV, verticalGearbox, RhodiumPlatedPalladium)
                .add(ZPM, verticalGearbox, NaquadahAlloy)
                .add(UV, verticalGearbox, Darmstadtium)
                .add(UHV, verticalGearbox, Neutronium);

        PUMP = CraftingComponent.of("pump", MECHANICAL_PUMPS[ULV].asStack())
                .add(ULV, MECHANICAL_PUMPS[ULV].asStack())
                .add(LV, MECHANICAL_PUMPS[LV].asStack())
                .add(MV, MECHANICAL_PUMPS[MV].asStack())
                .add(HV, MECHANICAL_PUMPS[HV].asStack())
                .add(EV, MECHANICAL_PUMPS[EV].asStack())
                .add(IV, MECHANICAL_PUMPS[IV].asStack())
                .add(LuV, MECHANICAL_PUMPS[LuV].asStack())
                .add(ZPM, MECHANICAL_PUMPS[ZPM].asStack())
                .add(UV, MECHANICAL_PUMPS[UV].asStack())
                .add(UHV, MECHANICAL_PUMPS[UHV].asStack());
    }
}
