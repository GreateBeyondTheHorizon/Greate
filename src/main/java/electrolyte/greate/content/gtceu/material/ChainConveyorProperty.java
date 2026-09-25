package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;

public class ChainConveyorProperty implements IMaterialProperty {

    private int maxConnections;
    private float maxLength;

    public ChainConveyorProperty(int maxConnections, float maxLength) {
        this.maxConnections = maxConnections;
        this.maxLength = maxLength;
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {
        properties.ensureSet(GreatePropertyKeys.KINETIC, true);
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        if (maxConnections <= 0) throw new IllegalArgumentException("Max connections must be greater than zero!");
        this.maxConnections = maxConnections;
    }

    public float getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(float maxLength) {
        if (maxLength < 2.5) throw new IllegalArgumentException("Max length must be greater than 2.5!");
        this.maxLength = maxLength;
    }
}
