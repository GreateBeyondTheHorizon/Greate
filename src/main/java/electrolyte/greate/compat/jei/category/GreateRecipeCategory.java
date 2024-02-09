package electrolyte.greate.compat.jei.category;

import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GreateRecipeCategory<T extends Recipe<?>> implements IRecipeCategory<T> {

    private static final IDrawable BASIC_SLOT = asDrawable(AllGuiTextures.JEI_SLOT);
    private static final IDrawable CHANCE_SLOT = asDrawable(AllGuiTextures.JEI_CHANCE_SLOT);

    protected final RecipeType<T> type;
    protected final Component title;
    protected final IDrawable background;
    protected final IDrawable icon;

    private final Supplier<List<T>> recipes;
    private final List<Supplier<? extends ItemStack>> catalysts;

    public GreateRecipeCategory(Info<T> info) {
        this.type = info.recipeType();
        this.title = info.title();
        this.background = info.background();
        this.icon = info.icon();
        this.recipes = info.recipes();
        this.catalysts = info.catalysts();
    }

    @NotNull
    @Override
    public RecipeType<T> getRecipeType() {
        return type;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(type, recipes.get());
    }

    public void registerCatalysts(IRecipeCatalystRegistration registration) {
        catalysts.forEach(s -> registration.addRecipeCatalyst(s.get(), type));
    }

    public static IDrawable getRenderedSlot() {
        return BASIC_SLOT;
    }

    public static IDrawable getRenderedSlot(ProcessingOutput output) {
        return getRenderedSlot(output.getChance());
    }

    public static IDrawable getRenderedSlot(float chance) {
        if(chance == 1) {
            return BASIC_SLOT;
        }
        return CHANCE_SLOT;
    }

    public static ItemStack getCircuitStack(TieredProcessingRecipe<?> recipe) {
        if(recipe.getCircuitNumber() == -1) return ItemStack.EMPTY;
        ItemStack circuitStack = new ItemStack(GTItems.INTEGRATED_CIRCUIT);
        IntCircuitBehaviour.setCircuitConfiguration(circuitStack, recipe.getCircuitNumber());
        return circuitStack;
    }

    public static IRecipeSlotTooltipCallback addStochasticTooltip(ProcessingOutput output) {
        return (view, tooltip) -> {
            float chance = output.getChance();
            if(chance != 1) {
                tooltip.add(1, Lang.translateDirect("recipe.processing.chance", chance < 0.01 ? "<1" : chance * 100).withStyle(ChatFormatting.GOLD));
            }
        };
    }

    public static IRecipeSlotTooltipCallback addStochasticTooltipWithExtraPercent(ProcessingOutput output, float extraPercent) {
        return (view, tooltip) -> {
            float chance = output.getChance();
            if (chance != 1) {
                MutableComponent component = Lang.translateDirect("recipe.processing.chance", chance < 0.01 ? "<1" : chance * 100);
                tooltip.add(1, component.withStyle(ChatFormatting.GOLD));
                if(extraPercent != 0) {
                    String s = String.format("%2.2f", extraPercent * 100);
                    component.append(" + " + s + Lang.builder(Greate.MOD_ID).translate("recipe.processing.extra_chance").component().getString());
                }
            }
        };
    }

    public static List<FluidStack> withImprovedVisibility(List<FluidStack> stacks) {
        return stacks.stream().map(GreateRecipeCategory::withImprovedVisibility).collect(Collectors.toList());
    }

    public static FluidStack withImprovedVisibility(FluidStack stack) {
        FluidStack display = stack.copy();
        int displayedAmount = (int) (stack.getAmount() * .75f) + 250;
        display.setAmount(displayedAmount);
        return display;
    }

    public static IRecipeSlotTooltipCallback addFluidTooltip() {
        return addFluidTooltip(-1);
    }

    public static IRecipeSlotTooltipCallback addFluidTooltip(int mbAmount) {
        return (view, tooltip) -> {
            Optional<FluidStack> displayed = view.getDisplayedIngredient(ForgeTypes.FLUID_STACK);
            if (displayed.isEmpty()) return;

            FluidStack fluidStack = displayed.get();

            if (fluidStack.getFluid().isSame(AllFluids.POTION.get())) {
                Component name = fluidStack.getDisplayName();
                if (tooltip.isEmpty()) tooltip.add(0, name);
                else tooltip.set(0, name);

                ArrayList<Component> potionTooltip = new ArrayList<>();
                PotionFluidHandler.addPotionTooltip(fluidStack, potionTooltip, 1);
                tooltip.addAll(1, potionTooltip.stream().toList());
            }

            int amount = mbAmount == -1 ? fluidStack.getAmount() : mbAmount;
            Component text = Components.literal(String.valueOf(amount)).append(Lang.translateDirect("generic.unit.millibuckets")).withStyle(ChatFormatting.GOLD);
            if (tooltip.isEmpty()) tooltip.add(0, text);
            else {
                List<Component> siblings = tooltip.get(0).getSiblings();
                siblings.add(Components.literal(" "));
                siblings.add(text);
            }
        };
    }

    protected static IDrawable asDrawable(AllGuiTextures texture) {
        return new IDrawable() {
            @Override
            public int getWidth() {
                return texture.width;
            }

            @Override
            public int getHeight() {
                return texture.height;
            }

            @Override
            public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
                texture.render(graphics, xOffset, yOffset);
            }
        };
    }

    public record Info<T extends Recipe<?>>(RecipeType<T> recipeType, Component title, IDrawable background, IDrawable icon, Supplier<List<T>> recipes, List<Supplier<? extends ItemStack>> catalysts) {}

    public interface Factory<T extends Recipe<?>> {
        GreateRecipeCategory<T> create(Info<T> info);
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, graphics, x, y);
        graphics.drawString(Minecraft.getInstance().font, Lang.builder(Greate.MOD_ID).translate("jei.recipe_tier").component().getString() + GreateValues.TMS[((TieredProcessingRecipe<?>) recipe).getRecipeTier()], (float) x, (float) y, 0x3f3f3f, false);
    }
}