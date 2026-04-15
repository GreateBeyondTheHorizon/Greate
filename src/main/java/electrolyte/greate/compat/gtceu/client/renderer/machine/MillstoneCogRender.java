package electrolyte.greate.compat.gtceu.client.renderer.machine;

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

import static electrolyte.greate.registry.GreatePartialModels.MILLSTONE_INNER_MODELS;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MillstoneCogRender extends DynamicRender<TieredMillstoneMachine, MillstoneCogRender> {

    public static final Codec<MillstoneCogRender> CODEC = Codec.unit(MillstoneCogRender::new);
    public static final DynamicRenderType<TieredMillstoneMachine, MillstoneCogRender> TYPE = new DynamicRenderType<>(CODEC);

    @Override
    public DynamicRenderType<TieredMillstoneMachine, MillstoneCogRender> getType() {
        return TYPE;
    }

    @Override
    public void render(TieredMillstoneMachine machine, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        KineticBlockEntity be = machine.getTraitHolder().getTrait(KineticMachineTrait.TYPE).getKineticBlockEntity();
        KineticBlockEntityRenderer.renderRotatingBuffer(be,
                CachedBuffers.partial(MILLSTONE_INNER_MODELS[machine.getTier()], be.getBlockState()),
                poseStack, buffer.getBuffer(RenderType.solid()), packedLight);
    }
}
