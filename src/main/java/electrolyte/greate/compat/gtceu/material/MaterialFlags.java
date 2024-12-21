package electrolyte.greate.compat.gtceu.material;

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;

public class MaterialFlags {

	public static final MaterialFlag GENERATE_WHISK = new MaterialFlag.Builder("generate_whisk")
			.requireProps(PropertyKeys.WHISK)
			.build();
}
