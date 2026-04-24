package electrolyte.greate.compat.gtceu.common.data;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.machine.trait.customlogic.MaceratorLogic;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ELECTRIC;

public class GreateRecipeTypes {

    public static final String KINETIC = "kinetic";

    public static final GTRecipeType WIRE_COATING_RECIPES = GTRecipeTypes.register("wire_coating", ELECTRIC)
            .setMaxIOSize(3, 1, 1, 0)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.WIREMILL_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_WIREMILL, FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);

    public static final GTRecipeType KINETIC_MACERATOR_TEST = GTRecipeTypes.register("kinetic_macerator_test", KINETIC)
            .setMaxIOSize(1, 4, 0, 0)
            .setEUIO(IO.NONE)
            .addCustomRecipeLogic(MaceratorLogic.INSTANCE)
            .setSound(GTSoundEntries.COMBUSTION);

    public static void register() {
        GTRecipeTypes.MACERATOR_RECIPES
            .onRecipeBuild((gtRecipeBuilder, finishedRecipeConsumer) -> {
                long euT = gtRecipeBuilder.buildRawRecipe().getInputEUt().voltage();
                GTRecipeBuilder copiedRecipe = KINETIC_MACERATOR_TEST
                        .copyFrom(gtRecipeBuilder)
                        .input(StressRecipeCapability.STRESS_CAPABILITY, (float) euT);
                copiedRecipe.tickInput.remove(EURecipeCapability.CAP);
                copiedRecipe.save(finishedRecipeConsumer);
            });
    }
}
