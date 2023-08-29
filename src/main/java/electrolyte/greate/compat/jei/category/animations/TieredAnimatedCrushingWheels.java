package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlock;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TieredAnimatedCrushingWheels extends AnimatedKinetics {

    private final BlockState wheel;

    public TieredAnimatedCrushingWheels(TieredCrushingWheelBlock crushingWheel) {
        wheel = crushingWheel.defaultBlockState().setValue(BlockStateProperties.AXIS, Axis.X);
    }

    @Override
    public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        poseStack.pushPose();
        poseStack.translate(xOffset, yOffset, 100);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-22.5F));
        int scale = 22;
        blockElement(wheel).rotateBlock(0, 90, -getCurrentAngle()).scale(scale).render(poseStack);
        blockElement(wheel).rotateBlock(0, 90, getCurrentAngle()).atLocal(2, 0, 0).scale(scale).render(poseStack);
        poseStack.popPose();
    }
}
