package electrolyte.greate.compat.emi;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedMillstone;
import electrolyte.greate.compat.xei.widgets.MillstoneWidget;
import electrolyte.greate.registry.Millstones;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static com.gregtechceu.gtceu.api.GTValues.UHV;

public class MillstoneEmiRecipe extends ModularEmiRecipe<MillstoneWidget> {

    private final GTRecipe recipe;

    public MillstoneEmiRecipe(GTRecipe recipe) {
        //super(() -> new GTRecipeWidget(recipe));
        super(() -> new MillstoneWidget(recipe));
        this.recipe = recipe;
    }

    public static final EmiRecipeCategory MILLSTONE_CATEGORY = new EmiRecipeCategory(Greate.id("milling"),
            EmiStack.of(GreateMachines.MILLSTONES[UHV].asStack()));

    @Override
    public EmiRecipeCategory getCategory() {
        return MILLSTONE_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);
        /*var widget = this.widget.get();
        var modular = new ModularWrapper<>(widget);
        modular.setRecipeWidget(0, 0);

        synchronized (CACHE_OPENED) {
            CACHE_OPENED.add(modular);
        }
        List<Widget> slots = new ArrayList<>();
        for (com.lowdragmc.lowdraglib.gui.widget.Widget w : getFlatWidgetCollection(widget)) {
            if (w instanceof IRecipeIngredientSlot slot) {
                if (w.getParent() instanceof DraggableScrollableWidgetGroup draggable && draggable.isUseScissor()) {
                    continue;
                }
                var io = slot.getIngredientIO();
                if (io != null && io != IngredientIO.RENDER_ONLY) {
                    // noinspection unchecked
                    var ingredients = EmiIngredient
                            .of((List<? extends EmiIngredient>) (List<?>) slot.getXEIIngredients());

                    SlotWidget slotWidget = null;
                    if (slot instanceof com.gregtechceu.gtceu.api.gui.widget.SlotWidget slotW) {
                        slotW.setHandlerSlot((IItemHandlerModifiable) EmptyHandler.INSTANCE, 0);
                        slotW.setDrawHoverOverlay(false).setDrawHoverTips(false);
                    } else if (slot instanceof com.gregtechceu.gtceu.api.gui.widget.TankWidget tankW) {
                        tankW.setFluidTank(EmptyFluidHandler.INSTANCE);
                        tankW.setDrawHoverOverlay(false).setDrawHoverTips(false);
                        long capacity = Math.max(1, ingredients.getAmount());
                        slotWidget = new TankWidget(ingredients, w.getPosition().x, w.getPosition().y,
                                w.getSize().width, w.getSize().height, capacity);
                    }
                    if (slotWidget == null) {
                        //actual itemsstack positions
                        slotWidget = new SlotWidget(ingredients, w.getPosition().x, w.getPosition().y);
                    }

                    slotWidget
                            .customBackground(null, w.getPosition().x, w.getPosition().y, w.getSize().width,
                                    w.getSize().height)
                            .drawBack(false);
                    if (io == IngredientIO.CATALYST) {
                        slotWidget.catalyst(true);
                    } else if (io == IngredientIO.OUTPUT) {
                        slotWidget.recipeContext(this);
                    }
                    for (Component component : w.getTooltipTexts()) {
                        slotWidget.appendTooltip(component);
                    }
                    slots.add(slotWidget);
                }
            }
        }
        widgets.add(new ModularWrapperWidget(modular, slots));
        slots.forEach(widgets::add);
        widgets.add(new ModularForegroundRenderWidget(modular));
        */

        widgets.addDrawable(38, 30, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            int recipeTier = GreateValues.convertStressToTier(recipe.getInputContents(StressRecipeCapability.STRESS_CAPABILITY));
            new TieredAnimatedMillstone(Millstones.MILLSTONES[recipeTier].get()).draw(graphics, 0,0);
        });

        widgets.addDrawable(75, 30, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            AllGuiTextures.JEI_ARROW.render(graphics, 0, 0);
        });

        widgets.addDrawable(33, 7, 16, 16, (graphics, mouseX, mouseY, delta) -> {
            AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 0, 0);
        });
    }
}
