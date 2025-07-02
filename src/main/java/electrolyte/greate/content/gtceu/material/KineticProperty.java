package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;

public class KineticProperty implements IMaterialProperty {

    private int tier;
    private float impact;
    private float generatedCapacity;
    private float maxCapacity;

    public KineticProperty(int tier, float impact, float generatedCapacity, float maxCapacity) {
        this.tier = tier;
        this.impact = impact;
        this.generatedCapacity = generatedCapacity;
        this.maxCapacity = maxCapacity;
    }

    public KineticProperty(int tier, int maxCapacity) {
        this.tier = tier;
        this.impact = 0;
        this.generatedCapacity = 0;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {
        if(materialProperties.hasProperty(PropertyKey.INGOT)) {
            Material mat = materialProperties.getMaterial();
            if(!mat.hasFlag(GreateMaterialFlags.GENERATE_SHAFT)) {
                mat.addFlags(GreateMaterialFlags.GENERATE_SHAFT);
            }
        }
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public float getImpact() {
        return impact;
    }

    public void setImpact(float impact) {
        this.impact = impact;
    }

    public float getGeneratedCapacity() {
        return generatedCapacity;
    }

    public void setGeneratedCapacity(float generatedCapacity) {
        this.generatedCapacity = generatedCapacity;
    }

    public float getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(float maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
