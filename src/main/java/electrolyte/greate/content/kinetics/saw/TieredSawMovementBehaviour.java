package electrolyte.greate.content.kinetics.saw;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.saw.SawMovementBehaviour;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import net.minecraft.client.renderer.MultiBufferSource;

public class TieredSawMovementBehaviour extends SawMovementBehaviour {

    //todo: this uses normal saw blades in contraptions, might need to fix
    @Override
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld, ContraptionMatrices matrices, MultiBufferSource buffer) {
        TieredSawRenderer.renderInContraption(context, renderWorld, matrices, buffer);
    }
}
