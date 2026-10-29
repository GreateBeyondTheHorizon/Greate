package electrolyte.greate.compat.gtceu.client.renderer.machine;

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

import javax.annotation.ParametersAreNonnullByDefault;

import static electrolyte.greate.registry.GreatePartialModels.MILLSTONE_INNER_MODELS;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MillstoneCogRender extends DynamicRender<TieredKineticMachine, MillstoneCogRender> {

    public static final Codec<MillstoneCogRender> CODEC = Codec.unit(MillstoneCogRender::new);
    public static final DynamicRenderType<TieredKineticMachine, MillstoneCogRender> TYPE = new DynamicRenderType<>(CODEC);

    @Override
    public DynamicRenderType<TieredKineticMachine, MillstoneCogRender> getType() {
        return TYPE;
    }

    @Override
    public void render(TieredKineticMachine machine, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        KineticBlockEntity be = machine.getKineticBlockEntity();
        KineticBlockEntityRenderer.renderRotatingBuffer(be,
                CachedBuffers.partial(MILLSTONE_INNER_MODELS[machine.getTier()], be.getBlockState()),
                poseStack, buffer.getBuffer(RenderType.solid()), packedLight);
    }
}
