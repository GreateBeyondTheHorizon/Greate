package electrolyte.greate.compat.jei.category;

import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedMixer;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.registry.MechanicalMixers;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TieredMixingCategory extends TieredBasinCategory {

    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();
    MixingType type;

    protected enum MixingType {
        MIXING, AUTO_SHAPELESS, AUTO_BREWING
    }

    public static TieredMixingCategory standard(Info<TieredBasinRecipe> info) {
        return new TieredMixingCategory(info, MixingType.MIXING);
    }

    public static TieredMixingCategory autoShapeless(Info<TieredBasinRecipe> info) {
        return new TieredMixingCategory(info, MixingType.AUTO_SHAPELESS);
    }

    public static TieredMixingCategory autoBrewing(Info<TieredBasinRecipe> info) {
        return new TieredMixingCategory(info, MixingType.AUTO_BREWING);
    }

    protected TieredMixingCategory(Info<TieredBasinRecipe> info, MixingType type) {
        super(info, type != MixingType.AUTO_SHAPELESS);
        this.type = type;
    }

    @Override
    public void draw(TieredBasinRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        boolean mixingType = type == MixingType.AUTO_SHAPELESS;
        double yOffset = mixingType ? 90 : 107;
        super.draw(recipe, recipeSlotsView, graphics, 1, yOffset);
        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if(requiredHeat != HeatCondition.NONE) {
            heater.withHeat(requiredHeat.visualizeAsBlazeBurner()).draw(graphics, getBackground().getWidth() / 2 + 3, 55);
        }
        new TieredAnimatedMixer(MechanicalMixers.MECHANICAL_MIXERS[recipe.getRecipeTier()].get()).draw(graphics, getBackground().getWidth() / 2 + 3, 34);
    }
}
