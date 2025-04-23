package electrolyte.greate.compat.jei.category;

import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.content.kinetics.fan.processing.TieredHauntingRecipe;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.Blocks;

public class TieredFanHauntingCategory extends TieredProcessingViaFanCategory.TieredMultiOutput<TieredHauntingRecipe> {
    public TieredFanHauntingCategory(Info<TieredHauntingRecipe> info) {
        super(info);
    }

    @Override
    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        GuiGameElement.of(Blocks.SOUL_FIRE.defaultBlockState()).scale(SCALE).atLocal(0, 0, 2).lighting(AnimatedKinetics.DEFAULT_LIGHTING).render(graphics);
    }
}
