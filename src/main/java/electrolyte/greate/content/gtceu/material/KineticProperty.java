package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;

public class KineticProperty implements IMaterialProperty {

    private int tier;
    private float maxCapacity;

    public KineticProperty(int tier, int maxCapacity) {
        this.tier = tier;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {
        materialProperties.ensureSet(PropertyKey.INGOT, true);
        Material mat = materialProperties.getMaterial();
        if(!mat.hasFlag(MaterialFlags.GENERATE_PLATE)) {
            mat.addFlags(MaterialFlags.GENERATE_PLATE);
        } if(!mat.hasFlag(GreateMaterialFlags.GENERATE_ALLOY)) {
            mat.addFlags(GreateMaterialFlags.GENERATE_ALLOY);
        }
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public float getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(float maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
