package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import electrolyte.greate.compat.gtceu.material.MaterialIconTypes;
import electrolyte.greate.compat.gtceu.material.PropertyKeys;

import java.util.function.Predicate;

public class GreateTagPrefixes {
	public static TagPrefix whisk;

	public static void register() {
		whisk = new TagPrefix("whisk")
				.defaultTagPath("whisks/%s")
				.unformattedTagPath("whisks")
				.materialAmount(GTValues.M)
				.materialIconType(MaterialIconTypes.whisk)
				.unificationEnabled(true)
				.generateItem(true)
				.generationCondition(Conditions.hasWhiskProperty);
	}

	public static class Conditions {
		public static final Predicate<Material> hasWhiskProperty = mat -> mat.hasProperty(PropertyKeys.WHISK);
	}
}
