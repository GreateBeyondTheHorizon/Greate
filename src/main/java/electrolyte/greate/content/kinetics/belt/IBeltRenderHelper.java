package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllPartialModels;
import electrolyte.greate.Greate;
import electrolyte.greate.registry.GreatePartialModels;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public interface IBeltRenderHelper {

    default PartialModel getBeltPulleyModel(BlockState blockState, TieredBeltBlockEntity be) {
        TieredBeltBlock tieredBeltBlock = (TieredBeltBlock) blockState.getBlock();
        Material beltMaterial = tieredBeltBlock.getBeltMaterial();
        String shaftMaterial;
        if(!be.getShaftType().isEmpty()) {
            shaftMaterial = be.getShaftType().toString().substring(2, be.getShaftType().toString().length() - 6);
        } else {
            shaftMaterial = "andesite_alloy";
            Greate.LOGGER.error("Unable to get shaft material for belt {}, the default create shaft will be used until this is fixed!", beltMaterial.getName());
        }
        ResourceLocation resourceLocation = new ResourceLocation(Greate.MOD_ID, "block/" + beltMaterial.getName() + "_belt_" + shaftMaterial + "_pulley");
        return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).stream().filter(p -> p.getLocation().equals(resourceLocation)).findFirst().orElse(AllPartialModels.BELT_PULLEY);
    }
}
