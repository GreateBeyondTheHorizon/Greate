package electrolyte.greate.content.kinetics.crafter;

import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;

public class TieredMechanicalCrafterBlock extends MechanicalCrafterBlock implements ITieredBlock {

    private int tier;

    public TieredMechanicalCrafterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }
}
