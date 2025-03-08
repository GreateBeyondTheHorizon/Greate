package electrolyte.greate.content.fluids.pump;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_PUMP_COG_MODELS;

public class TieredPumpRenderer extends KineticBlockEntityRenderer<TieredPumpBlockEntity> {

	public TieredPumpRenderer(Context context) {
		super(context);
	}

	@Override
	protected SuperByteBuffer getRotatedModel(TieredPumpBlockEntity be, BlockState state) {
		return CachedBuffers.partialFacing(MECHANICAL_PUMP_COG_MODELS[be.getTier()], state);
	}
}
