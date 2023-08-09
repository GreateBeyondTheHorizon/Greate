package electrolyte.greate.content.kinetics.simpleRelays;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;

public class TieredBracketedKineticBlockEntityInstance extends SingleRotatingInstance<TieredKineticBlockEntity> {
    public TieredBracketedKineticBlockEntityInstance(MaterialManager materialManager, TieredKineticBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }
}
