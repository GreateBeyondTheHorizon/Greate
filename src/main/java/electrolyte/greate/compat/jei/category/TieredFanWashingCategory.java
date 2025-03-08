package electrolyte.greate.compat.jei.category;

import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.material.Fluids;

public class TieredFanWashingCategory extends TieredProcessingViaFanCategory.TieredMultiOutput<TieredSplashingRecipe> {
    public TieredFanWashingCategory(Info<TieredSplashingRecipe> info) {
        super(info);
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        GuiGameElement.of(Fluids.WATER).scale(SCALE).atLocal(0, 0, 2).lighting(AnimatedKinetics.DEFAULT_LIGHTING).render(graphics);
    }
}
