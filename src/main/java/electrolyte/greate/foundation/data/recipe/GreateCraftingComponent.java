package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateCraftingComponent {

    //TODO: add other crafting components (alloys)
    public static CraftingComponent SHAFT;

    public static void init() {
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
    }
}
