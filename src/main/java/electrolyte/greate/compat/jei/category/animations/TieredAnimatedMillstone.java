package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import net.minecraft.client.gui.GuiGraphics;

public class TieredAnimatedMillstone extends AnimatedKinetics {

    private final TieredMillstoneBlock block;

    public TieredAnimatedMillstone(TieredMillstoneBlock block) {
        this.block = block;
    }

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(xOffset, yOffset, 0);
        AllGuiTextures.JEI_SHADOW.render(graphics, -16, 13);
        poseStack.translate(-2, 18, 0);
        int scale = 22;
        blockElement(block.getPartialModel()).rotateBlock(22.5, getCurrentAngle() * 2, 0).scale(scale).render(graphics);
        blockElement(block.defaultBlockState()).rotateBlock(22.5, 22.5, 0).scale(scale).render(graphics);
        poseStack.popPose();
    }
}
