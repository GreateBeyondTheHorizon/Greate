package electrolyte.greate.compat.gtceu.client.renderer.machine;

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part.StressPartMachine;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;

import static com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer.kineticRotationTransform;

public class KineticBoxShaftRender extends BaseKineticDynamicRender<StressPartMachine, KineticBoxShaftRender> {


    public static final Codec<KineticBoxShaftRender> CODEC = Codec.unit(KineticBoxShaftRender::new);
    public static final DynamicRenderType<StressPartMachine, KineticBoxShaftRender> TYPE = new DynamicRenderType<>(CODEC);


    @Override
    public DynamicRenderType<StressPartMachine, KineticBoxShaftRender> getType() {
        return TYPE;
    }

    @Override
    public void render(StressPartMachine machine, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Axis axis = getRotationAxisOf(machine);
        Direction direction = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        float time = AnimationTickHolder.getRenderTime(machine.getLevel());

        float angle = (time * machine.getKineticMachineTrait().getKineticBlockEntity().getSpeed() * 3f / 10) % 360;
        angle = angle + (KineticBlockEntityVisual.shouldOffset(axis, machine.getBlockPos()) ? 22.5f : 0);
        SuperByteBuffer shaft = CachedBuffers.partialFacingVertical(GreateModelUtils.getPartialModel(machine.getBlockState().getBlock(), "/shaft"), machine.getBlockState(), direction);
        angle = angle / 180f * (float) Math.PI;
        int test = LevelRenderer.getLightColor(machine.getLevel(), machine.getBlockPos().relative(direction));
        kineticRotationTransform(shaft, machine.getKineticMachineTrait().getKineticBlockEntity(), axis, angle, test);
        shaft.renderInto(poseStack, buffer.getBuffer(RenderType.cutoutMipped()));
    }
}
