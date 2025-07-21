package electrolyte.greate.compat.jei.category;

import com.gregtechceu.gtceu.common.item.behavior.IntCircuitBehaviour;
import com.gregtechceu.gtceu.data.item.GTItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.CreateLang;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.createmod.catnip.lang.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GreateRecipeCategory<T extends Recipe<?>> implements IRecipeCategory<RecipeHolder<T>> {

    private static final IDrawable BASIC_SLOT = asDrawable(AllGuiTextures.JEI_SLOT);
    private static final IDrawable CHANCE_SLOT = asDrawable(AllGuiTextures.JEI_CHANCE_SLOT);

    protected final RecipeType<RecipeHolder<T>> type;
    protected final Component title;
    protected final IDrawable background;
    protected final IDrawable icon;

    private final Supplier<List<RecipeHolder<T>>> recipes;
    private final List<Supplier<? extends ItemStack>> catalysts;

    public GreateRecipeCategory(Info<T> info) {
        this.type = info.recipeType();
        this.title = info.title();
        this.background = info.background();
        this.icon = info.icon();
        this.recipes = info.recipes();
        this.catalysts = info.catalysts();
    }

    @Override
    public RecipeType<RecipeHolder<T>> getRecipeType() {
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

    public static ItemStack getCircuitStack(TieredProcessingRecipe<?, ?> recipe) {
        if(recipe.getCircuitNumber() == -1) return ItemStack.EMPTY;
        ItemStack circuitStack = new ItemStack(GTItems.PROGRAMMED_CIRCUIT.asItem());
        IntCircuitBehaviour.setCircuitConfiguration(circuitStack, recipe.getCircuitNumber());
        return circuitStack;
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

    public static IRecipeSlotTooltipCallback addFluidTooltip(int mbAmount) {
        return (view, tooltip) -> {
            Optional<FluidStack> displayed = view.getDisplayedIngredient(NeoForgeTypes.FLUID_STACK);
            if (displayed.isEmpty()) return;

            FluidStack fluidStack = displayed.get();

            if (fluidStack.getFluid().isSame(AllFluids.POTION.get())) {
                Component name = fluidStack.getDisplayName();
                if (tooltip.isEmpty()) tooltip.add(0, name);
                else tooltip.set(0, name);

                ArrayList<Component> potionTooltip = new ArrayList<>();
                PotionFluidHandler.addPotionTooltip(fluidStack, potionTooltip::add, 1);
                tooltip.addAll(1, potionTooltip.stream().toList());
            }

            int amount = mbAmount == -1 ? fluidStack.getAmount() : mbAmount;
            Component text = Component.literal(String.valueOf(amount)).append(CreateLang.translateDirect("generic.unit.millibuckets")).withStyle(ChatFormatting.GOLD);
            if (tooltip.isEmpty()) tooltip.add(0, text);
            else {
                List<Component> siblings = tooltip.get(0).getSiblings();
                siblings.add(Component.literal(" "));
                siblings.add(text);
            }
        };
    }

    protected static IDrawable asDrawable(AllGuiTextures texture) {
        return new IDrawable() {
            @Override
            public int getWidth() {
                return texture.getWidth();
            }

            @Override
            public int getHeight() {
                return texture.getHeight();
            }

            @Override
            public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
                texture.render(graphics, xOffset, yOffset);
            }
        };
    }

    public record Info<T extends Recipe<?>>(RecipeType<RecipeHolder<T>> recipeType, Component title, IDrawable background, IDrawable icon, Supplier<List<RecipeHolder<T>>> recipes, List<Supplier<? extends ItemStack>> catalysts) {}

    public interface Factory<T extends Recipe<?>> {
        GreateRecipeCategory<T> create(Info<T> info);
    }

    @Override
    public void draw(RecipeHolder<T> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, graphics, x, y);
        graphics.drawString(Minecraft.getInstance().font, Lang.builder(Greate.MOD_ID).translate("jei.recipe_tier").component().getString() + GreateValues.SN[((TieredProcessingRecipe<?,?>) recipe.value()).getRecipeTier()], (float) x, (float) y, 0x3f3f3f, false);
    }
}