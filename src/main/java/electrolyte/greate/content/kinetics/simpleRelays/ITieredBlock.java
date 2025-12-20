package electrolyte.greate.content.kinetics.simpleRelays;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

public interface ITieredBlock {

    int getTier();
    void setTier(int tier);
    Material getMaterial();
}
