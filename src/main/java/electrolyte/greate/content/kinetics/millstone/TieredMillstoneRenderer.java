package electrolyte.greate.content.kinetics.millstone;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

public class TieredMillstoneRenderer extends KineticBlockEntityRenderer<TieredMillstoneBlockEntity> {

    public TieredMillstoneRenderer(Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(TieredMillstoneBlockEntity be, BlockState state) {
        return CachedBufferer.partial(((ITieredPartialModel) state.getBlock()).getPartialModel(), state);
    }
}
