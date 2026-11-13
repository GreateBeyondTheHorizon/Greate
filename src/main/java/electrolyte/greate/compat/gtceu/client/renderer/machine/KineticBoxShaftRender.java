package electrolyte.greate.compat.gtceu.client.renderer.machine;

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part.StressPartMachine;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer.renderRotatingBuffer;

public class KineticBoxShaftRender extends BaseKineticDynamicRender<StressPartMachine, KineticBoxShaftRender> {


    public static final Codec<KineticBoxShaftRender> CODEC = Codec.unit(KineticBoxShaftRender::new);
    public static final DynamicRenderType<StressPartMachine, KineticBoxShaftRender> TYPE = new DynamicRenderType<>(CODEC);


    @Override
    public DynamicRenderType<StressPartMachine, KineticBoxShaftRender> getType() {
        return TYPE;
    }

    @Override
    public void render(StressPartMachine machine, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = machine.getBlockState().getValue(BlockStateProperties.FACING);
        SuperByteBuffer shaft = CachedBuffers.partialFacing(GreateModelUtils.getPartialModel(machine.getBlockState().getBlock(), "/shaft_half"), machine.getBlockState());
        int light = LevelRenderer.getLightColor(machine.getLevel(), machine.getBlockPos().relative(direction));
        renderRotatingBuffer(machine.getKineticBlockEntity(), shaft, poseStack, buffer.getBuffer(RenderType.cutoutMipped()), light);
    }
}
