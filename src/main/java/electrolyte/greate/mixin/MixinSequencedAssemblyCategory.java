package electrolyte.greate.mixin;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.SequencedAssemblyCategory;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.utility.CreateLang;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SequencedAssemblyCategory.class)
public abstract class MixinSequencedAssemblyCategory extends CreateRecipeCategory<SequencedAssemblyRecipe> {

    @Shadow protected abstract MutableComponent chanceComponent(float chance);
    @Shadow private SequencedAssemblySubCategory getSubCategory(SequencedRecipe<?> recipe) { return null; }

    public MixinSequencedAssemblyCategory(Info<SequencedAssemblyRecipe> info) {
        super(info);
    }

    @Inject(method = "getTooltipStrings(Lcom/simibubi/create/content/processing/sequenced/SequencedAssemblyRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;DD)Ljava/util/List;", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate_getTooltipStrings(SequencedAssemblyRecipe recipe, IRecipeSlotsView iRecipeSlotsView, double mouseX, double mouseY, CallbackInfoReturnable<List<Component>> cir) {
        if(!ModList.get().isLoaded("emi")) return;
        List<Component> tooltip = new ArrayList<>();

        MutableComponent junk = CreateLang.translateDirect("recipe.assembly.junk");

        boolean singleOutput = recipe.getOutputChance() == 1;
        boolean willRepeat = recipe.getLoops() > 1;

        int xOffset = -7;
        int minX = 150 + xOffset;
        int maxX = minX + 18;
        int minY = 90;
        int maxY = minY + 18;
        if (!singleOutput && mouseX >= minX && mouseX < maxX && mouseY >= minY && mouseY < maxY) {
            float chance = recipe.getOutputChance();
            tooltip.add(junk);
            tooltip.add(chanceComponent(1 - chance));
            cir.setReturnValue(tooltip);
        }

        minX = 55 + xOffset;
        maxX = minX + 65;
        minY = 92;
        maxY = minY + 24;
        if (willRepeat && mouseX >= minX && mouseX < maxX && mouseY >= minY && mouseY < maxY) {
            tooltip.add(CreateLang.translateDirect("recipe.assembly.repeat", recipe.getLoops()));
            cir.setReturnValue(tooltip);
        }

        if (mouseY > 30 && mouseY < 84) {
            int width = 0;
            int margin = 3;
            for (SequencedRecipe<?> sequencedRecipe : recipe.getSequence())
                width += getSubCategory(sequencedRecipe).getWidth() + margin;
            width -= margin;
            xOffset = width / 2 + getBackground().getWidth() / -2;

            double relativeX = mouseX + xOffset;
            List<SequencedRecipe<?>> sequence = recipe.getSequence();
            for (int i = 0; i < sequence.size(); i++) {
                SequencedRecipe<?> sequencedRecipe = sequence.get(i);
                SequencedAssemblySubCategory subCategory = getSubCategory(sequencedRecipe);
                if (relativeX >= 0 && relativeX < subCategory.getWidth()) {
                    tooltip.add(CreateLang.translateDirect("recipe.assembly.step", i + 1));
                    tooltip.add(sequencedRecipe.getAsAssemblyRecipe()
                            .getDescriptionForAssembly()
                            .plainCopy()
                            .withStyle(ChatFormatting.DARK_GREEN));
                    cir.setReturnValue(tooltip);
                }
                relativeX -= subCategory.getWidth() + margin;
            }
        }
        cir.setReturnValue(tooltip);
    }
}
