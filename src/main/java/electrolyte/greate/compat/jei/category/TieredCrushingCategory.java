package electrolyte.greate.compat.jei.category;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedCrushingWheels;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.registry.CrushingWheels;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.layout.LayoutHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredCrushingCategory extends GreateRecipeCategory<TieredAbstractCrushingRecipe> {

    public TieredCrushingCategory(Info<TieredAbstractCrushingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<TieredAbstractCrushingRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 3)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.value().getIngredients().get(0));

        int xOffset = getBackground().getWidth() / 2;
        int yOffset = 86;

        layoutOutput(recipe.value()).forEach(entry -> {
            IRecipeSlotBuilder baseBuilder = builder
                    .addSlot(RecipeIngredientRole.OUTPUT, (xOffset) + entry.posX() + 1, yOffset + entry.posY() + 1)
                    .setBackground(getRenderedSlot(entry.output()), - 1, - 1)
                    .addItemStack(entry.output().getStack());
            baseBuilder.addRichTooltipCallback(CreateRecipeCategory.addStochasticTooltip(entry.output));
        });
    }

    private List<LayoutEntry> layoutOutput(ProcessingRecipe<?, ?> recipe) {
        int size = recipe.getRollableResults().size();
        List<LayoutEntry> positions = new ArrayList<>(size);
        LayoutHelper layout = LayoutHelper.centeredHorizontal(size, 1, 18, 18, 1);
        for(ProcessingOutput output : recipe.getRollableResults()) {
            positions.add(new LayoutEntry(output, layout.getX(), layout.getY()));
            layout.next();
        }
        return positions;
    }

    private record LayoutEntry(ProcessingOutput output, int posX, int posY) {}

    @Override
    public void draw(RecipeHolder<TieredAbstractCrushingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        super.draw(recipe, recipeSlotsView, graphics, 1, 103);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 72, 7);
        new TieredAnimatedCrushingWheels(CrushingWheels.CRUSHING_WHEELS[recipe.value().getRecipeTier()].get()).draw(graphics, 62, 59);
        graphics.drawString(Minecraft.getInstance().font, Lang.builder(Greate.MOD_ID).translate("jei.byproducts").component().getString(), 1, 116, 0x3f3f3f, false);
    }
}
