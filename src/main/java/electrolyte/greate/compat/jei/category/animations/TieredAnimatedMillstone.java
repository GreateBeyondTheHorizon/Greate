package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.Block;

import static electrolyte.greate.registry.GreatePartialModels.MILLSTONE_INNER_MODELS;

public class TieredAnimatedMillstone extends AnimatedKinetics {

    private final Block block;

    public TieredAnimatedMillstone(Block block) {
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
        blockElement(MILLSTONE_INNER_MODELS[((ITieredBlock)block).getTier()]).rotateBlock(22.5, getCurrentAngle() * 2, 0).scale(scale).render(graphics);
        blockElement(block.defaultBlockState()).rotateBlock(22.5, 22.5, 0).scale(scale).render(graphics);
        poseStack.popPose();
    }
}
