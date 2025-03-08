package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlock;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import static electrolyte.greate.registry.GreatePartialModels.COGWHEEL_SHAFTLESS_MODELS;
import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_MIXER_HEAD_MODELS;

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

        blockElement(COGWHEEL_SHAFTLESS_MODELS[block.getTier()]).rotateBlock(0, getCurrentAngle() * 2, 0).atLocal(0, 0, 0).scale(scale).render(guiGraphics);
        blockElement(block.defaultBlockState()).atLocal(0, 0, 0).scale(scale).render(guiGraphics);

        float animation = ((Mth.sin(AnimationTickHolder.getRenderTime() / 32f) + 1) / 5) + 0.5f;

        blockElement(AllPartialModels.MECHANICAL_MIXER_POLE).atLocal(0, animation, 0).scale(scale).render(guiGraphics);
        blockElement(MECHANICAL_MIXER_HEAD_MODELS[block.getTier()]).rotateBlock(0, getCurrentAngle() * 4, 0).atLocal(0, animation, 0).scale(scale).render(guiGraphics);
        blockElement(AllBlocks.BASIN.getDefaultState()).atLocal(0, 1.65, 0).scale(scale).render(guiGraphics);

        matrixStack.popPose();
    }
}
