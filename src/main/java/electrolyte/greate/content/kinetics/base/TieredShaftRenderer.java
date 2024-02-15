package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.state.BlockState;

public class TieredShaftRenderer<T extends KineticBlockEntity> extends KineticBlockEntityRenderer<T> {

    public TieredShaftRenderer(Context context) {
        super(context);
    }

    private BlockState shaft(BlockState state, Axis axis) {
        return state.setValue(ShaftBlock.AXIS, axis);
    }

    @Override
    protected BlockState getRenderedBlockState(T be) {
        BlockState shaftState = ((ITieredShaftBlock) be.getBlockState().getBlock()).getShaft().defaultBlockState();
        return shaft(shaftState, getRotationAxisOf(be));
    }
}
