package electrolyte.greate.content.kinetics.saw;

import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.saw.SawMovementBehaviour;
import net.minecraft.client.renderer.MultiBufferSource;

public class TieredSawMovementBehaviour extends SawMovementBehaviour {

    @Override
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld, ContraptionMatrices matrices, MultiBufferSource buffer) {
        TieredSawRenderer.renderInContraption(context, renderWorld, matrices, buffer);
    }
}
