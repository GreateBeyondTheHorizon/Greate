package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;

import java.util.List;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_PLATE;

public class BeltProperty implements IMaterialProperty {

    private List<Material> validShafts;
    private int maxLength;

    public BeltProperty(List<Material> validShafts) {
        this.validShafts = validShafts;
        this.maxLength = 20;
    }

    public BeltProperty(List<Material> validShafts, int maxLength) {
        this.validShafts = validShafts;
        this.maxLength = maxLength;
    }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {
        materialProperties.ensureSet(PropertyKey.INGOT, true);
        if(!materialProperties.getMaterial().hasFlag(GENERATE_PLATE)) {
            materialProperties.getMaterial().addFlags(GENERATE_PLATE);
        }
        this.maxLength = Math.max(1, maxLength);
    }

    public List<Material> getValidShafts() {
        return validShafts;
    }

    public void setValidShafts(List<Material> validShafts) {
        this.validShafts = validShafts;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
