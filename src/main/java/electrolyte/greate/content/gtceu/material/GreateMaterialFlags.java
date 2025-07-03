package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;

public class GreateMaterialFlags {

	public static final MaterialFlag GENERATE_WHISK = new MaterialFlag.Builder("generate_whisk").requireProps(PropertyKey.INGOT).build();
	public static final MaterialFlag GENERATE_ALLOY = new MaterialFlag.Builder("generate_alloy").requireProps(PropertyKey.INGOT).build();
	public static final MaterialFlag GENERATE_SHAFT = new MaterialFlag.Builder("generate_shaft").requireProps(PropertyKey.INGOT).requireFlags(MaterialFlags.GENERATE_PLATE).build();
	public static final MaterialFlag GENERATE_POWERED_SHAFT = new MaterialFlag.Builder("generate_powered_shaft").requireFlags(GENERATE_SHAFT).build();
	public static final MaterialFlag GENERATE_ANDESITE_ENCASED_SHAFT = new MaterialFlag.Builder("generate_andesite_encased_shaft").requireFlags(GENERATE_SHAFT).build();
	public static final MaterialFlag GENERATE_BRASS_ENCASED_SHAFT = new MaterialFlag.Builder("generate_brass_encased_shaft").requireFlags(GENERATE_SHAFT).build();
}
