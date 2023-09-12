package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlock;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TieredAnimatedCrushingWheels extends AnimatedKinetics {

    private final BlockState wheel;

    public TieredAnimatedCrushingWheels(TieredCrushingWheelBlock crushingWheel) {
        wheel = crushingWheel.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X);
    }

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(xOffset, yOffset, 100);
        poseStack.mulPose(Axis.YP.rotationDegrees(-22.5F));
        int scale = 22;
        blockElement(wheel).rotateBlock(0, 90, -getCurrentAngle()).scale(scale).render(graphics);
        blockElement(wheel).rotateBlock(0, 90, getCurrentAngle()).atLocal(2, 0, 0).scale(scale).render(graphics);
        poseStack.popPose();
    }
}
