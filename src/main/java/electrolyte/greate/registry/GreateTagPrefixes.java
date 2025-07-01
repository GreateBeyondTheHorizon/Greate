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

	public static void register() {}
}
