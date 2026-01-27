package electrolyte.greate.compat.jei.category;

import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import net.minecraft.client.gui.GuiGraphics;

public class TieredFanWashingCategory extends TieredProcessingViaFanCategory.TieredMultiOutput<TieredSplashingRecipe> {
    public TieredFanWashingCategory(Info<TieredSplashingRecipe> info) {
        super(info);
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {}
}
