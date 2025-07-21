package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.material.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.material.material.properties.MaterialProperties;

public class CogwheelProperty implements IMaterialProperty {

    private Material previousMaterial;

    public CogwheelProperty(Material previousMaterial) {
        this.previousMaterial = previousMaterial;
    }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {
        materialProperties.ensureSet(GreatePropertyKeys.KINETIC, true);
    }

    public Material getPreviousMaterial() {
        return previousMaterial;
    }

    public void setPreviousMaterial(Material previousMaterial) {
        this.previousMaterial = previousMaterial;
    }
}
