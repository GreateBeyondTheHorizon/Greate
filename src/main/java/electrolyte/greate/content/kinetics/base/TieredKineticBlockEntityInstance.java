package electrolyte.greate.content.kinetics.base;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TieredKineticBlockEntityInstance<T extends KineticBlockEntity> extends KineticBlockEntityInstance<T> {


    public TieredKineticBlockEntityInstance(MaterialManager materialManager, T blockEntity) {
        super(materialManager, blockEntity);
    }

    protected BlockState shaft(BlockState state) {
        return shaft(state, getRotationAxis());
    }

    public static BlockState shaft(BlockState state, Direction.Axis axis) {
        return state.setValue(ShaftBlock.AXIS, axis);
    }
}
