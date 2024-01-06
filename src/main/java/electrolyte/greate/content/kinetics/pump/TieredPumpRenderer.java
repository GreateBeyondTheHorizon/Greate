package electrolyte.greate.content.kinetics.pump;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

public class TieredPumpRenderer extends KineticBlockEntityRenderer<TieredPumpBlockEntity> {

	public TieredPumpRenderer(Context context) {
		super(context);
	}

	@Override
	protected SuperByteBuffer getRotatedModel(TieredPumpBlockEntity be, BlockState state) {
		return CachedBufferer.partialFacing(((ITieredPartialModel) state.getBlock()).getPartialModel(), state);
	}
}
