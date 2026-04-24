package electrolyte.greate.compat.xei.widgets;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.jei.IngredientIO;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.mutable.MutableInt;

public class MillstoneWidget extends WidgetGroup {

    public MillstoneWidget(GTRecipe recipe) {
        super(0, 0, 176, 80);
        setClientSideWidget();
        setRecipe(recipe);
    }

    public void setRecipe(GTRecipe recipe) {
        WidgetGroup inputGroup = new WidgetGroup();
        recipe.getInputContents(ItemRecipeCapability.CAP).forEach(content -> {
            if(content.getContent() instanceof ItemStack stack) {
                ItemStackTransfer handler = new ItemStackTransfer(stack);
                inputGroup.addWidget(new SlotWidget(handler, 0, 5, 5)
                        .setCanTakeItems(false)
                        .setCanPutItems(false)
                        .setIngredientIO(IngredientIO.INPUT));
            } else if(content.getContent() instanceof Ingredient ingredient) {
                ItemStackTransfer handler = new ItemStackTransfer(ingredient.getItems()[0]);
                inputGroup.addWidget(new SlotWidget(handler, 0, 5, 5)
                        .setCanTakeItems(false)
                        .setCanPutItems(false)
                        .setIngredientIO(IngredientIO.INPUT));
            }
        });

        int i = 0;
        WidgetGroup outputGroup = new WidgetGroup();
        ItemStackHandler handler = new ItemStackHandler(recipe.getOutputContents(ItemRecipeCapability.CAP).size());
        for(int j = 0; j < recipe.getOutputContents(ItemRecipeCapability.CAP).size(); j++) {
            Content content = recipe.getOutputContents(ItemRecipeCapability.CAP).get(j);
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2)  * -19;
            if(content.getContent() instanceof Ingredient ing) {
                handler.setStackInSlot(i, ing.getItems()[0]);
                int recipeTier = RecipeHelper.getRecipeEUtTier(recipe);
                com.gregtechceu.gtceu.api.gui.widget.SlotWidget widget = new com.gregtechceu.gtceu.api.gui.widget.SlotWidget(handler, i, 125 + xOffset, 27 + yOffset);
                ItemRecipeCapability.CAP.applyWidgetInfo(widget, i, true, IO.OUT, null, recipe.getType(), recipe,
                        content, handler, recipeTier, recipeTier);
                widget.setOverlay(content.createOverlay(false, recipeTier, recipeTier, recipe.getType().getChanceFunction()));
                        outputGroup.addWidget(widget);
            }
            i++;
        }
        this.addWidgets(inputGroup);
        this.addWidget(outputGroup);
        this.addWidget(new LabelWidget(3, 60, Component.translatable("gtceu.recipe.duration", FormattingUtil.formatNumbers(recipe.duration / 20f))));

        WidgetGroup capGroup = new WidgetGroup();
        int yOffset = 10 + capGroup.getSize().height;
        MutableInt yOff = new MutableInt(yOffset);
        for (var capability : recipe.inputs.entrySet()) {
            capability.getKey().addXEIInfo(capGroup, 2, recipe, capability.getValue(), false, true, yOff);
        }
        for (var capability : recipe.tickInputs.entrySet()) {
            capability.getKey().addXEIInfo(capGroup, 2, recipe, capability.getValue(), true, true, yOff);
        }
        for (var capability : recipe.outputs.entrySet()) {
            capability.getKey().addXEIInfo(capGroup, 2, recipe, capability.getValue(), false, false, yOff);
        }
        for (var capability : recipe.tickOutputs.entrySet()) {
            capability.getKey().addXEIInfo(capGroup, 2, recipe, capability.getValue(), true, false, yOff);
        }
        this.addWidgets(capGroup);
    }
}
