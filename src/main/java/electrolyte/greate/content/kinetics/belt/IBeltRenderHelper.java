package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.Greate;
import net.minecraft.world.level.block.state.BlockState;

public interface IBeltRenderHelper {

    default PartialModel getBeltPulleyModel(TieredBeltBlockEntity be, BlockState blockState) {
        Material shaftMaterial = be.getShaftMaterial();
        if(shaftMaterial == null || shaftMaterial == GTMaterials.NULL)
            shaftMaterial = ((TieredBeltBlock) blockState.getBlock()).getShaftMaterial();
        if(shaftMaterial == null || shaftMaterial == GTMaterials.NULL) return AllPartialModels.BELT_PULLEY;
        return PartialModel.of(Greate.id("block/" + shaftMaterial.getName() + "/belt_pulley"));
    }
}
