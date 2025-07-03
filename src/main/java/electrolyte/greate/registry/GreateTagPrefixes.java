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
			.enableRecycling()
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_SHAFT));

	public static TagPrefix poweredShaft = new TagPrefix("powered_shaft")
			.itemTable(() -> Shafts.NEW_POWERED_SHAFTS)
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_SHAFT));

	public static TagPrefix andesiteEncasedShaft = new TagPrefix("andesite_encased_shaft")
			.itemTable(() -> Shafts.NEW_ANDESITE_ENCASED_SHAFTS)
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_SHAFT));

	public static TagPrefix brassEncasedShaft = new TagPrefix("brass_encased_shaft")
			.itemTable(() -> Shafts.NEW_BRASS_ENCASED_SHAFTS)
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_SHAFT));

	public static TagPrefix girderEncasedShaft = new TagPrefix("girder_encased_shaft")
			.itemTable(() -> Girders.NEW_GIRDERS)
			.generationCondition(m -> m.hasFlag(GreateMaterialFlags.GENERATE_SHAFT));

	public static void register() {}
}
