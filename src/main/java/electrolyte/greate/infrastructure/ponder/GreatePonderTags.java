package electrolyte.greate.infrastructure.ponder;

import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.*;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Belts.NEW_BELT_CONNECTORS;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;
import static electrolyte.greate.registry.Shafts.NEW_SHAFTS;

public class GreatePonderTags {

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        for(int i = 0; i < TM.length; i++) {

            helper.addToTag(KINETIC_APPLIANCES)
                    .add(FANS[i].getId())
                    .add(MECHANICAL_PUMPS[i].getId());

            helper.addToTag(CONTRAPTION_ACTOR)
                    .add(SAWS[i].getId());

            helper.addToTag(FLUIDS)
                    .add(MECHANICAL_PUMPS[i].getId());
        }

        NEW_SHAFTS.values().forEach(shaft -> helper.addToTag(KINETIC_RELAYS).add(shaft.getId()));
        COGWHEELS.values().forEach(cogwheel -> helper.addToTag(KINETIC_RELAYS).add(cogwheel.getId()));
        LARGE_COGWHEELS.values().forEach(largeCogwheel -> helper.addToTag(KINETIC_RELAYS).add(largeCogwheel.getId()));
        GEARBOXES.values().forEach(gearbox -> helper.addToTag(KINETIC_RELAYS).add(gearbox.getId()));

        NEW_BELT_CONNECTORS.values().forEach(connector -> {
            helper.addToTag(KINETIC_RELAYS).add(connector.getId());
            helper.addToTag(LOGISTICS).add(connector.getId());
        });
    }
}
