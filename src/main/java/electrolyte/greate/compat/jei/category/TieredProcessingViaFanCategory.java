package electrolyte.greate.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.CreateLang;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlock;
import electrolyte.greate.content.processing.recipe.TieredProcessingOutput;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.getResultItem;
import static electrolyte.greate.registry.EncasedFans.FANS;
import static electrolyte.greate.registry.GreatePartialModels.FAN_INNER_MODELS;

@ParametersAreNonnullByDefault
public abstract class TieredProcessingViaFanCategory<T extends Recipe<?>> extends GreateRecipeCategory<T> {

    protected static final int SCALE = 24;

    public TieredProcessingViaFanCategory(Info<T> info) {
        super(info);
    }

    public static Supplier<ItemStack> getFan(BlockEntry<TieredEncasedFanBlock> fan, String name) {
        return () -> fan.asStack().setHoverName(CreateLang.translateDirect("recipe." + name + ".fan").withStyle(s -> s.withItalic(false)));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 21, 48)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 141, 48)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStack(getResultItem(recipe));
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        super.draw(recipe, recipeSlotsView, graphics, 1, 77);
        renderWidgets(graphics, recipe, x, y);

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        translateFan(poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-12.5f));
        poseStack.mulPose(Axis.YP.rotationDegrees(22.5f));

        PartialModel fanInner = AllPartialModels.ENCASED_FAN_INNER;
        BlockState fanState = AllBlocks.ENCASED_FAN.getDefaultState();

        if(recipe instanceof TieredProcessingRecipe<?> tpr) {
            fanInner = FAN_INNER_MODELS[tpr.getRecipeTier()];
            fanState = FANS[tpr.getRecipeTier()].getDefaultState();
        }

        AnimatedKinetics.defaultBlockElement(fanInner)
                .rotateBlock(180, 0, AnimatedKinetics.getCurrentAngle() * 16)
                .scale(SCALE)
                .render(graphics);
        AnimatedKinetics.defaultBlockElement(fanState)
                .rotateBlock(0, 180, 0)
                .atLocal(0, 0, 0)
                .scale(SCALE)
                .render(graphics);

        renderAttachedBlock(graphics);
        poseStack.popPose();
    }

    protected void renderWidgets(GuiGraphics graphics, T recipe, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(graphics, 46, 29);
        getBlockShadow().render(graphics, 65, 39);
        AllGuiTextures.JEI_LONG_ARROW.render(graphics, 54, 51);
    }

    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_SHADOW;
    }

    protected void translateFan(PoseStack poseStack) {
        poseStack.translate(56, 33, 0);
    }

    protected abstract void renderAttachedBlock(GuiGraphics graphics);

    public static abstract class TieredMultiOutput<T extends TieredProcessingRecipe<?>> extends TieredProcessingViaFanCategory<T> {
        public TieredMultiOutput(Info<T> info) {
            super(info);
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
            List<ProcessingOutput> results = recipe.getRollableResults();
            int xOffsetAmount = 1 - Math.min(3, results.size());
            builder
                    .addSlot(RecipeIngredientRole.INPUT, 5 * xOffsetAmount + 21, 48)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addIngredients(recipe.getIngredients().get(0));

            int i = 0;
            boolean excessive = results.size() > 9;
            for(ProcessingOutput output : results) {
                int xOffset = (i % 3) * 19 + 9 * xOffsetAmount;
                int yOffset = (i / 3) * -19 + (excessive ? 8 : 0);

                IRecipeSlotBuilder baseBuilder = builder
                        .addSlot(RecipeIngredientRole.OUTPUT, 141 + xOffset, 48 + yOffset)
                        .setBackground(getRenderedSlot(output), -1, -1)
                        .addItemStack(output.getStack());
                if(output instanceof TieredProcessingOutput tpo) {
                    baseBuilder.addTooltipCallback(addStochasticTooltipWithExtraPercent(tpo, tpo.getExtraTierChance()));
                } else {
                    baseBuilder.addTooltipCallback(addStochasticTooltip(output));
                }
                i++;
            }
        }

        @Override
        protected void renderWidgets(GuiGraphics graphics, T recipe, double mouseX, double mouseY) {
            int size = recipe.getRollableResultsAsItemStacks().size();
            int xOffsetAmount = 1 - Math.min(3, size);

            AllGuiTextures.JEI_SHADOW.render(graphics, 46, 29);
            getBlockShadow().render(graphics, 46, 29);
            AllGuiTextures.JEI_LONG_ARROW.render(graphics, 7 * xOffsetAmount + 54, 51);
        }
    }
}
