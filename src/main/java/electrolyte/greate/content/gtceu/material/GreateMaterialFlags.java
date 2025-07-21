package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.material.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.material.material.properties.PropertyKey;

public class GreateMaterialFlags {

	public static final MaterialFlag GENERATE_WHISK = new MaterialFlag.Builder("generate_whisk").requireProps(PropertyKey.INGOT).build();
	public static final MaterialFlag GENERATE_ALLOY = new MaterialFlag.Builder("generate_alloy").requireProps(PropertyKey.INGOT).build();
}
