package electrolyte.greate.content.kinetics.chainConveyor;

import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorBlockEntity;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorRenderer;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

public class TieredChainConveyorRenderer extends ChainConveyorRenderer {
    public TieredChainConveyorRenderer(Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(ChainConveyorBlockEntity be, BlockState state) {
        return CachedBuffers.partial(GreateModelUtils.getPartialModel(state.getBlock(), "/chain_conveyor_shaft"), state);
    }
}
