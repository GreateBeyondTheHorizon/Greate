package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.Greate;
import electrolyte.greate.registry.GreatePartialModels;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public interface IBeltRenderHelper {

    default PartialModel getBeltPulleyModel(TieredBeltBlockEntity be, BlockState blockState) {
        Material shaftMaterial = be.getShaftMaterial();
        if(shaftMaterial == null)
            shaftMaterial = ((TieredBeltBlock) blockState.getBlock()).getShaftMaterial();
        if(shaftMaterial == null) return AllPartialModels.BELT_PULLEY;
        Material beltMaterial = ((TieredBeltBlock) blockState.getBlock()).getBeltMaterial();
        ResourceLocation resourceLocation = Greate.id("block/" + shaftMaterial.getName() + "_belt_pulley");
        return GreatePartialModels.BELT_MODELS.get(beltMaterial).stream().filter(p -> p.modelLocation().equals(resourceLocation)).findFirst().orElse(AllPartialModels.BELT_PULLEY);
    }
}
