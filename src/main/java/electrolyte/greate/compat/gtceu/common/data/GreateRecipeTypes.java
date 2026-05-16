package electrolyte.greate.compat.gtceu.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.machine.trait.customlogic.MaceratorLogic;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import electrolyte.greate.GreateValues;
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
            .setSound(GTSoundEntries.MACERATOR)  //todo:change
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.byproduct_tier", GreateValues.SNF[GTValues.HV]));

    public static void register() {
        GTRecipeTypes.MACERATOR_RECIPES
            .onRecipeBuild((gtRecipeBuilder, finishedRecipeConsumer) -> {
                long euT = gtRecipeBuilder.buildRawRecipe().getInputEUt().voltage();
                GTRecipeBuilder copiedRecipe = KINETIC_MACERATOR_TEST
                        .copyFrom(gtRecipeBuilder)
                        .input(StressRecipeCapability.STRESS_CAPABILITY, (float) euT);
                copiedRecipe.save(finishedRecipeConsumer);
            });
    }
}
