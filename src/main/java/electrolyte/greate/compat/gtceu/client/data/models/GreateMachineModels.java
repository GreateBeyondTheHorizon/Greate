package electrolyte.greate.compat.gtceu.client.data.models;

import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder.ModelInitializer;
import com.simibubi.create.AllBlocks;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;

public class GreateMachineModels {

    public static ModelInitializer createMillstoneModel() {
        return ((context, provider, builder) -> {
           ResourceLocation parentModel = ModelLocationUtils.getModelLocation(AllBlocks.MILLSTONE.get()).withSuffix("/block");
           builder.forAllStatesModels(state ->
                   provider.models().nested().parent(provider.models().getExistingFile(parentModel)));
        });
    }
}
