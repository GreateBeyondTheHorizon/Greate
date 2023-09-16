package electrolyte.greate;

import electrolyte.greate.foundation.data.GreateTagGen;
import electrolyte.greate.foundation.data.recipe.GreateMechanicalCraftingRecipeGen;
import electrolyte.greate.foundation.data.recipe.GreateStandardRecipeGen;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;

import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static electrolyte.greate.Greate.REGISTRATE;

public class GreateDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ExistingFileHelper helper = new ExistingFileHelper(List.of(Paths.get(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES))), Set.of("create"), false, null, null);
        Pack pack = fabricDataGenerator.createPack();
        REGISTRATE.setupDatagen(pack, helper);
        pack.addProvider(GreateStandardRecipeGen::new);
        pack.addProvider(GreateMechanicalCraftingRecipeGen::new);
        pack.addProvider(GreateTagGen::new);
    }
}
