package electrolyte.greate.content.kinetics.base;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TieredShaftInstance<T extends KineticBlockEntity> extends TieredSingleRotatingInstance<T> {

    protected BlockState state;

    public TieredShaftInstance(MaterialManager materialManager, T blockEntity) {
        super(materialManager, blockEntity);
        state = blockEntity.getBlockState().getBlock() instanceof ITieredShaftBlock shaftBlock ? shaftBlock.getShaft().defaultBlockState() : null;
    }

    @Override
    protected BlockState shaft(BlockState state) {
        return super.shaft(state);
    }

    @Override
    protected BlockState shaft() {
        return shaft(state);
    }

    @Override
    protected BlockState getRenderedBlockState() {
        return shaft();
    }
}
