package electrolyte.greate.content.kinetics.millstone;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

import static electrolyte.greate.registry.GreatePartialModels.MILLSTONE_INNER_MODELS;

public class TieredMillstoneRenderer extends KineticBlockEntityRenderer<TieredMillstoneBlockEntity> {

    public TieredMillstoneRenderer(Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(TieredMillstoneBlockEntity be, BlockState state) {
        int tier = ((TieredMillstoneBlock) state.getBlock()).getTier();
        return CachedBuffers.partial(MILLSTONE_INNER_MODELS[tier], state);
    }
}
