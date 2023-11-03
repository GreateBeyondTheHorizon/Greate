package electrolyte.greate.content.kinetics.simpleRelays;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;

public class TieredBracketedKineticBlockEntityInstance extends SingleRotatingInstance<TieredBracketedKineticBlockEntity> {
    public TieredBracketedKineticBlockEntityInstance(MaterialManager materialManager, TieredBracketedKineticBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }
}
