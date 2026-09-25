package electrolyte.greate.foundation.client.models;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.data.pack.GTDynamicResourcePack;
import com.simibubi.create.Create;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.chainConveyor.TieredChainConveyorBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record ChainConveyorModel(Block chainConveyorBlock) {

    private static final Set<ChainConveyorModel> MODELS = new HashSet<>();
    public static final Set<ResourceLocation> MODEL_LOCATIONS = new HashSet<>();

    public static void create(Block block) {
        if(!GTCEu.isClientSide()) return;
        MODELS.add(new ChainConveyorModel(block));
    }

    public static void reinitModels() {
        for(ChainConveyorModel model : MODELS) {
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(model.chainConveyorBlock);
            String material = ((ITieredBlock) model.chainConveyorBlock).getMaterial().getName();
            ResourceLocation modelId = Greate.id("block/" + material + "/");
            ResourceLocation modelIdNoBlock = Greate.id(material + "/");
            if(model.chainConveyorBlock instanceof TieredChainConveyorBlock) {
                generateChainConveyorModel(model, blockId, modelId, modelIdNoBlock);
            }
        }
    }

    private static void generateChainConveyorModel(ChainConveyorModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock) {
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("chain_conveyor"), new DelegatedModel(Create.asResource("block/chain_conveyor/block")));
        MODEL_LOCATIONS.add(modelId.withSuffix("chain_conveyor"));

        GTDynamicResourcePack.addItemModel(blockId, new ExtendedDelegatedModel(Create.asResource("block/chain_conveyor/item"),
                Map.of("axis", modelId.withSuffix("axis"),
                        "axis_top", modelId.withSuffix("axis_top"))));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("chain_conveyor"), new DelegatedModel(Create.asResource("block/chain_conveyor/block")));
        MODEL_LOCATIONS.add(modelId.withSuffix("chain_conveyor"));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("chain_conveyor_shaft"), new ExtendedDelegatedModel(Create.asResource("block/chain_conveyor/shaft"),
                Map.of("axis", modelId.withSuffix("axis"),
                        "axis_top", modelId.withSuffix("axis_top"))));
        MODEL_LOCATIONS.add(modelId.withSuffix("chain_conveyor_shaft"));

        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.chainConveyorBlock,
                Variant.variant().with(VariantProperties.MODEL, modelId.withSuffix("chain_conveyor"))));
    }
}
