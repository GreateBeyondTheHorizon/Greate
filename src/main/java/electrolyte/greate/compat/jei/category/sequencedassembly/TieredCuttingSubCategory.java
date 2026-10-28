package electrolyte.greate.compat.jei.category.sequencedassembly;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedSaw;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import net.minecraft.client.gui.GuiGraphics;

import static electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines.SAWS;


public class TieredCuttingSubCategory extends SequencedAssemblySubCategory {

    public TieredCuttingSubCategory() {
        super(25);
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        TieredAnimatedSaw saw = new TieredAnimatedSaw(SAWS[((TieredCuttingRecipe) recipe.getRecipe()).getRecipeTier()].get());
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(0, 51.5f, 0);
        poseStack.scale(0.6f, 0.6f, 0.6f);
        saw.draw(graphics, getWidth() / 2, 30);
        poseStack.popPose();
    }
}
