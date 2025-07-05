package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import electrolyte.greate.content.gtceu.material.GreateMaterialFlags;
import electrolyte.greate.content.gtceu.material.GreateMaterialIconTypes;

import static com.gregtechceu.gtceu.api.GTValues.M;

public class GreateTagPrefixes {
	public static TagPrefix whisk = new TagPrefix("whisk")
			.defaultTagPath("whisks/%s")
			.unformattedTagPath("whisks")
			.materialAmount(M * 7)
			.materialIconType(GreateMaterialIconTypes.whisk)
			.unificationEnabled(true)
			.generateItem(true)
			.enableRecycling()
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_WHISK));

	public static TagPrefix alloy = new TagPrefix("alloy")
			.defaultTagPath("alloys/%s")
			.unformattedTagPath("alloys")
			.materialAmount(M * 2)
			.materialIconType(GreateMaterialIconTypes.alloy)
			.unificationEnabled(true)
			.generateItem(true)
			.enableRecycling()
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_ALLOY));

	public static TagPrefix shaft = new TagPrefix("shaft")
			.defaultTagPath("shafts/%s")
			.unformattedTagPath("shafts")
			.itemTable(() -> Shafts.NEW_SHAFTS)
			.materialAmount(M / 2)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix poweredShaft = new TagPrefix("powered_shaft")
			.itemTable(() -> Shafts.POWERED_SHAFTS);

	public static TagPrefix andesiteEncasedShaft = new TagPrefix("andesite_encased_shaft")
			.itemTable(() -> Shafts.ANDESITE_ENCASED_SHAFTS);

	public static TagPrefix brassEncasedShaft = new TagPrefix("brass_encased_shaft")
			.itemTable(() -> Shafts.BRASS_ENCASED_SHAFTS);

	public static TagPrefix girderEncasedShaft = new TagPrefix("girder_encased_shaft")
			.itemTable(() -> Girders.GIRDERS);

	public static TagPrefix cogwheel = new TagPrefix("cogwheel")
			.defaultTagPath("cogwheels/%s")
			.unformattedTagPath("cogwheels")
			.itemTable(() -> Cogwheels.COGWHEELS)
			.materialAmount(M / 2)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix largeCogwheel = new TagPrefix("large_cogwheel")
			.defaultTagPath("large_cogwheels/%s")
			.unformattedTagPath("large_cogwheels")
			.itemTable(() -> Cogwheels.LARGE_COGWHEELS)
			.materialAmount(M / 2)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix andesiteEncasedCogwheel = new TagPrefix("andesite_encased_cogwheel")
			.itemTable(() -> Cogwheels.ANDESITE_ENCASED_COGWHEELS);

	public static TagPrefix brassEncasedCogwheel = new TagPrefix("brass_encased_cogwheel")
			.itemTable(() -> Cogwheels.BRASS_ENCASED_COGWHEELS);

	public static TagPrefix andesiteEncasedLargeCogwheel = new TagPrefix("andesite_encased_large_cogwheel")
			.itemTable(() -> Cogwheels.ANDESITE_ENCASED_LARGE_COGWHEELS);

	public static TagPrefix brassEncasedLargeCogwheel = new TagPrefix("brass_encased_large_cogwheel")
			.itemTable(() -> Cogwheels.BRASS_ENCASED_LARGE_COGWHEELS);

	public static TagPrefix gearbox = new TagPrefix("gearbox")
			.defaultTagPath("gearboxes/%s")
			.unformattedTagPath("gearboxes")
			.itemTable(() -> Gearboxes.NEW_GEARBOXES)
			.materialAmount(M * 2)
			.unificationEnabled(true)
			.enableRecycling();

	public static TagPrefix verticalGearbox = new TagPrefix("vertical_gearbox")
			.defaultTagPath("vertical_gearboxes/%s")
			.unformattedTagPath("vertical_gearboxes")
			.itemTable(() -> Gearboxes.NEW_VERTICAL_GEARBOXES)
			.materialAmount(M * 2)
			.unificationEnabled(true)
			.enableRecycling();

	public static void register() {}
}
