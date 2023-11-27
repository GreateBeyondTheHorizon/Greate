package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlock;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class TieredAnimatedMixer extends AnimatedKinetics {

    private final TieredMechanicalMixerBlock block;

    public TieredAnimatedMixer(TieredMechanicalMixerBlock block) {
        this.block = block;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = 23;

        blockElement(block.getCogwheelModel()).rotateBlock(0, getCurrentAngle() * 2, 0).atLocal(0, 0, 0).scale(scale).render(guiGraphics);
        blockElement(block.defaultBlockState()).atLocal(0, 0, 0).scale(scale).render(guiGraphics);

        float animation = ((Mth.sin(AnimationTickHolder.getRenderTime() / 32f) + 1) / 5) + 0.5f;

        blockElement(AllPartialModels.MECHANICAL_MIXER_POLE).atLocal(0, animation, 0).scale(scale).render(guiGraphics);
        blockElement(block.getPartialModel()).rotateBlock(0, getCurrentAngle() * 4, 0).atLocal(0, animation, 0).scale(scale).render(guiGraphics);
        blockElement(AllBlocks.BASIN.getDefaultState()).atLocal(0, 1.65, 0).scale(scale).render(guiGraphics);

        matrixStack.popPose();
    }
}
