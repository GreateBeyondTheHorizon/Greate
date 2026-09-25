package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.gtceu.material.GreateMaterialFlags;
import electrolyte.greate.compat.gtceu.material.GreateMaterialIconTypes;

import static com.gregtechceu.gtceu.api.GTValues.M;

public class GreateTagPrefixes {
	public static TagPrefix whisk = new TagPrefix(Greate.id("whisk"))
			.defaultTagPath("whisks/%s")
			.unformattedTagPath("whisks")
			.materialAmount(M * 7)
			.materialIconType(GreateMaterialIconTypes.whisk)
			.unificationEnabled(true)
			.enableRecycling()
			.generateItem(true)
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_WHISK));

	public static TagPrefix alloy = new TagPrefix(Greate.id("alloy"))
			.defaultTagPath("alloys/%s")
			.unformattedTagPath("alloys")
			.materialAmount(M)
			.materialIconType(GreateMaterialIconTypes.alloy)
			.unificationEnabled(true)
			.enableRecycling()
			.generateItem(true)
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_ALLOY));

	public static TagPrefix shaft = new TagPrefix(Greate.id("shaft"))
			.defaultTagPath("shafts/%s")
			.unformattedTagPath("shafts")
			.customLookupTable(() -> Shafts.SHAFTS)
			.materialAmount(M / 6)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix poweredShaft = new TagPrefix(Greate.id("powered_shaft"))
			.customLookupTable(() -> Shafts.POWERED_SHAFTS);

	public static TagPrefix andesiteEncasedShaft = new TagPrefix(Greate.id("andesite_encased_shaft"))
			.customLookupTable(() -> Shafts.ANDESITE_ENCASED_SHAFTS);

	public static TagPrefix brassEncasedShaft = new TagPrefix(Greate.id("brass_encased_shaft"))
			.customLookupTable(() -> Shafts.BRASS_ENCASED_SHAFTS);

	public static TagPrefix girderEncasedShaft = new TagPrefix(Greate.id("girder_encased_shaft"))
			.customLookupTable(() -> Girders.GIRDERS);

	public static TagPrefix cogwheel = new TagPrefix(Greate.id("cogwheel"))
			.defaultTagPath("cogwheels/%s")
			.unformattedTagPath("cogwheels")
			.customLookupTable(() -> Cogwheels.COGWHEELS)
			.materialAmount(M / 4)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix largeCogwheel = new TagPrefix(Greate.id("large_cogwheel"))
			.defaultTagPath("large_cogwheels/%s")
			.unformattedTagPath("large_cogwheels")
			.customLookupTable(() -> Cogwheels.LARGE_COGWHEELS)
			.materialAmount(M / 4)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix andesiteEncasedCogwheel = new TagPrefix(Greate.id("andesite_encased_cogwheel"))
			.customLookupTable(() -> Cogwheels.ANDESITE_ENCASED_COGWHEELS);

	public static TagPrefix brassEncasedCogwheel = new TagPrefix(Greate.id("brass_encased_cogwheel"))
			.customLookupTable(() -> Cogwheels.BRASS_ENCASED_COGWHEELS);

	public static TagPrefix andesiteEncasedLargeCogwheel = new TagPrefix(Greate.id("andesite_encased_large_cogwheel"))
			.customLookupTable(() -> Cogwheels.ANDESITE_ENCASED_LARGE_COGWHEELS);

	public static TagPrefix brassEncasedLargeCogwheel = new TagPrefix(Greate.id("brass_encased_large_cogwheel"))
			.customLookupTable(() -> Cogwheels.BRASS_ENCASED_LARGE_COGWHEELS);

	public static TagPrefix gearbox = new TagPrefix(Greate.id("gearbox"))
			.defaultTagPath("gearboxes/%s")
			.unformattedTagPath("gearboxes")
			.customLookupTable(() -> Gearboxes.GEARBOXES)
			.materialAmount(M)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix verticalGearbox = new TagPrefix(Greate.id("vertical_gearbox"))
			.defaultTagPath("vertical_gearboxes/%s")
			.unformattedTagPath("vertical_gearboxes")
			.customLookupTable(() -> Gearboxes.VERTICAL_GEARBOXES)
			.materialAmount(M)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix belt = new TagPrefix(Greate.id("belt"))
			.customLookupTable(() -> Belts.BELTS);

	public static TagPrefix beltConnector = new TagPrefix(Greate.id("belt_connector"))
			.defaultTagPath("belt_connectors/%s")
			.unformattedTagPath("belt_connectors")
			.customLookupTable(() -> Belts.BELT_CONNECTORS)
			.materialAmount(M * 6)
			.unificationEnabled(true)
			.enableRecycling();

	public static void register() {}
}
