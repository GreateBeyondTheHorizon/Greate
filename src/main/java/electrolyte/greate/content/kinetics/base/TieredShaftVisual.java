package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.render.VirtualRenderHelper;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TieredShaftVisual<T extends KineticBlockEntity> extends TieredSingleRotatingVisual<T> {

    protected BlockState state;

    public TieredShaftVisual(VisualizationContext context, T blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
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

    @Override
    protected Model getModel() {
        return VirtualRenderHelper.blockModel(shaft());
    }
}
