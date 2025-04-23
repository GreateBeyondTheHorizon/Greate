package electrolyte.greate.infrastructure.ponder;

import com.simibubi.create.Create;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.simibubi.create.infrastructure.ponder.scenes.*;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.PumpScenes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import electrolyte.greate.infrastructure.ponder.scenes.TieredFanScenes;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

import static electrolyte.greate.registry.Belts.BELT_CONNECTORS;
import static electrolyte.greate.registry.Cogwheels.COGWHEELS;
import static electrolyte.greate.registry.Cogwheels.LARGE_COGWHEELS;
import static electrolyte.greate.registry.CrushingWheels.CRUSHING_WHEELS;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.Gearboxes.VERTICAL_GEARBOXES;
import static electrolyte.greate.registry.MechanicalMixers.MECHANICAL_MIXERS;
import static electrolyte.greate.registry.MechanicalPresses.MECHANICAL_PRESSES;
import static electrolyte.greate.registry.Millstones.MILLSTONES;
import static electrolyte.greate.registry.Pumps.MECHANICAL_PUMPS;
import static electrolyte.greate.registry.Saws.SAWS;
import static electrolyte.greate.registry.Shafts.*;

public class GreatePonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.forComponents(SHAFTS).addStoryBoard(Create.asResource("shaft/relay"), KineticsScenes::shaftAsRelay, AllCreatePonderTags.KINETIC_RELAYS);
        HELPER.forComponents(SHAFTS).addStoryBoard(Create.asResource("shaft/encasing"), KineticsScenes::shaftsCanBeEncased);
        HELPER.forComponents(ANDESITE_ENCASED_SHAFTS).addStoryBoard(Create.asResource("shaft/encasing"), KineticsScenes::shaftsCanBeEncased);
        HELPER.forComponents(BRASS_ENCASED_SHAFTS).addStoryBoard(Create.asResource("shaft/encasing"), KineticsScenes::shaftsCanBeEncased);
        HELPER.forComponents(COGWHEELS)
                .addStoryBoard(Create.asResource("cog/small"), KineticsScenes::cogAsRelay, AllCreatePonderTags.KINETIC_RELAYS)
                .addStoryBoard(Create.asResource("cog/speedup"), KineticsScenes::cogsSpeedUp)
                .addStoryBoard(Create.asResource("cog/encasing"), KineticsScenes::cogwheelsCanBeEncased);
        HELPER.forComponents(LARGE_COGWHEELS)
                .addStoryBoard(Create.asResource("cog/speedup"), KineticsScenes::cogsSpeedUp)
                .addStoryBoard(Create.asResource("cog/large"), KineticsScenes::largeCogAsRelay, AllCreatePonderTags.KINETIC_RELAYS)
                .addStoryBoard(Create.asResource("cog/encasing"), KineticsScenes::cogwheelsCanBeEncased);
        HELPER.forComponents(BELT_CONNECTORS)
                .addStoryBoard(Create.asResource("belt/connect"), BeltScenes::beltConnector, AllCreatePonderTags.KINETIC_RELAYS)
                .addStoryBoard(Create.asResource("belt/directions"), BeltScenes::directions)
                .addStoryBoard(Create.asResource("belt/transport"), BeltScenes::transport, AllCreatePonderTags.LOGISTICS)
                .addStoryBoard(Create.asResource("belt/encasing"), BeltScenes::beltsCanBeEncased);
        HELPER.forComponents(GEARBOXES).addStoryBoard(Create.asResource("gearbox"), KineticsScenes::gearbox, AllCreatePonderTags.KINETIC_RELAYS);
        HELPER.forComponents(VERTICAL_GEARBOXES).addStoryBoard(Create.asResource("gearbox"), KineticsScenes::gearbox, AllCreatePonderTags.KINETIC_RELAYS);
        HELPER.forComponents(FANS)
                .addStoryBoard(Create.asResource("fan/direction"), FanScenes::direction, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard(Create.asResource("fan/processing"), TieredFanScenes::processing); //TODO: fix
        HELPER.forComponents(MILLSTONES).addStoryBoard(Create.asResource("millstone"), ProcessingScenes::millstone);
        HELPER.forComponents(CRUSHING_WHEELS).addStoryBoard(Create.asResource("crushing_wheel"), ProcessingScenes::crushingWheels);
        HELPER.forComponents(MECHANICAL_MIXERS).addStoryBoard(Create.asResource("mechanical_mixer/mixing"), ProcessingScenes::mixing);
        HELPER.forComponents(MECHANICAL_PRESSES).addStoryBoard(Create.asResource("mechanical_press/compacting"), ProcessingScenes::compacting);
        HELPER.forComponents(SAWS)
                .addStoryBoard(Create.asResource("mechanical_saw/processing"), MechanicalSawScenes::processing, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard(Create.asResource("mechanical_saw/breaker"), MechanicalSawScenes::treeCutting)
                .addStoryBoard(Create.asResource("mechanical_saw/contraption"), MechanicalSawScenes::contraption, AllCreatePonderTags.CONTRAPTION_ACTOR);
        HELPER.forComponents(MECHANICAL_PUMPS)
                .addStoryBoard(Create.asResource("mechanical_pump/flow"), PumpScenes::flow, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard(Create.asResource("mechanical_pump/speed"), PumpScenes::speed);
    }
}
