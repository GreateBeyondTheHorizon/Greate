package electrolyte.greate.content.kinetics.base;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

public class TieredShaftInstance<T extends KineticBlockEntity> extends TieredSingleRotatingInstance<T> {

    public TieredShaftInstance(MaterialManager materialManager, T blockEntity) {
        super(materialManager, blockEntity);
    }
}
