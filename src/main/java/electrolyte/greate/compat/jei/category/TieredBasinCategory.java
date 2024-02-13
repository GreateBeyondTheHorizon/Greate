package electrolyte.greate.compat.jei.category;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingOutput;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredBasinCategory extends GreateRecipeCategory<TieredBasinRecipe> {

    private final boolean needsHeating;

    public TieredBasinCategory(Info<TieredBasinRecipe> info, boolean needsHeating) {
        super(info);
        this.needsHeating = needsHeating;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TieredBasinRecipe recipe, IFocusGroup focuses) {
        int size = recipe.getIngredients().size() + recipe.getFluidIngredients().size();
        int xOffset = size < 3 ? (3 - size) * 19 / 2 : 0;
        int i = 0;

        for(Ingredient ingredient : recipe.getIngredients()) {
            List<ItemStack> stacks = new ArrayList<>();
            for(ItemStack stack : ingredient.getItems()) {
                stacks.add(stack.copy());
            }

            builder.addSlot(RecipeIngredientRole.INPUT, 17 + xOffset + (i % 3) * 19, 51 - (i / 3) * 19)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addItemStacks(stacks);
            i++;
        }

        for(FluidIngredient ingredient : recipe.getFluidIngredients()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 17 + xOffset + (i % 3) * 19, 51 - (i / 3) * 19)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addIngredients(ForgeTypes.FLUID_STACK, withImprovedVisibility(ingredient.getMatchingFluidStacks()))
                    .addTooltipCallback(addFluidTooltip(ingredient.getRequiredAmount()));
            i++;
        }

        size = recipe.getRollableResults().size() + recipe.getFluidResults().size();
        i = 0;

        for(ProcessingOutput output : recipe.getRollableResults()) {
            int xPosition = 142 - (size % 2 != 0 && i == size - 1 ? 0 : i % 2 == 0 ? 10 : -9);
            int yPosition = -19 * (i / 2) + 51;
            IRecipeSlotBuilder baseBuilder = builder.addSlot(RecipeIngredientRole.OUTPUT, xPosition, yPosition)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack());
            if(output instanceof TieredProcessingOutput tieredProcessingOutput) {
                baseBuilder.addTooltipCallback(addStochasticTooltipWithExtraPercent(tieredProcessingOutput, tieredProcessingOutput.getExtraTierChance()));
            } else {
                baseBuilder.addTooltipCallback(addStochasticTooltip(output));
            }
            i++;
        }

        for(FluidStack fluidResult : recipe.getFluidResults()) {
            int xPosition = 142 - (size % 2 != 0 && i == size - 1 ? 0 : i % 2 == 0 ? 10 : -9);
            int yPosition = -19 * (i / 2) + 51;
            builder.addSlot(RecipeIngredientRole.OUTPUT, xPosition, yPosition)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addIngredient(ForgeTypes.FLUID_STACK, withImprovedVisibility(fluidResult))
                    .addTooltipCallback(addFluidTooltip(fluidResult.getAmount()));
        }

        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if(!requiredHeat.testBlazeBurner(HeatLevel.NONE)) {
            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 134, 81)
                    .addItemStack(AllBlocks.BLAZE_BURNER.asStack());
        }
        if(!requiredHeat.testBlazeBurner(HeatLevel.KINDLED)) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 153, 81)
                    .addItemStack(AllItems.BLAZE_CAKE.asStack());
        }

        ItemStack circuitStack = getCircuitStack(recipe);
        if(!circuitStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, getBackground().getWidth() / 2 - 17, 13)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addItemStack(circuitStack);
        }
    }

    @Override
    public void draw(TieredBasinRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        boolean recipeType = recipe.getId().toString().contains("shapeless") || recipe.getId().toString().contains("shaped");
        double yOffset = recipeType ? 90 : 108;
        super.draw(recipe, recipeSlotsView, graphics, 1, yOffset);
        HeatCondition requiredHeat = recipe.getRequiredHeat();
        boolean noHeat = requiredHeat == HeatCondition.NONE;
        int vRows = (1 + recipe.getFluidResults().size() + recipe.getRollableResults().size()) / 2;
        if(vRows <= 2) {
            AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 136, -19 * (vRows - 1) + 32);
        }

        AllGuiTextures shadow = noHeat ? AllGuiTextures.JEI_SHADOW : AllGuiTextures.JEI_LIGHT;
        shadow.render(graphics, 81, 58 + (noHeat ? 10 : 30));
        if(!needsHeating) return;
        AllGuiTextures heatBar = noHeat ? AllGuiTextures.JEI_NO_HEAT_BAR : AllGuiTextures.JEI_HEAT_BAR;
        heatBar.render(graphics, 4, 80);
        graphics.drawString(Minecraft.getInstance().font, Lang.translateDirect(requiredHeat.getTranslationKey()), 9, 86, requiredHeat.getColor(), false);
    }
}
