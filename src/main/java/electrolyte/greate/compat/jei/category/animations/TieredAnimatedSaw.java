package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import electrolyte.greate.content.kinetics.saw.TieredSawBlock;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TieredAnimatedSaw extends AnimatedKinetics {

    private final TieredSawBlock block;

    public TieredAnimatedSaw(TieredSawBlock block) {
        this.block = block;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(xOffset, yOffset, 0);
        poseStack.translate(0, 0, 200);
        poseStack.translate(2, 22, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        poseStack.mulPose(Axis.YP.rotationDegrees(22.5f + 90));
        int scale = 25;

        blockElement(block.getShaft().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X))
                .rotateBlock(-getCurrentAngle(), 0, 0)
                .scale(scale)
                .render(guiGraphics);

        blockElement(block.defaultBlockState().setValue(SawBlock.FACING, Direction.UP))
                .rotateBlock(0, 0, 0)
                .scale(scale)
                .render(guiGraphics);

        blockElement(block.getSawModels()[3])
                .rotateBlock(0, -90, -90)
                .scale(scale)
                .render(guiGraphics);

        poseStack.popPose();
    }
}
