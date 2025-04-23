package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.Greate;
import electrolyte.greate.registry.GreatePartialModels;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public interface IBeltRenderHelper {

    default PartialModel getBeltPulleyModel(BlockState blockState) {
        TieredBeltBlock tieredBeltBlock = (TieredBeltBlock) blockState.getBlock();
        Material beltMaterial = tieredBeltBlock.getBeltMaterial();
        ResourceLocation resourceLocation = Greate.id("block/" + ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).getPath() + "_pulley");
        return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).stream().filter(p -> p.modelLocation().equals(resourceLocation)).findFirst().orElse(AllPartialModels.BELT_PULLEY);
    }
}
