package electrolyte.greate.foundation.client.models;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.data.pack.GTDynamicResourcePack;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlock;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.blockstates.VariantProperties.Rotation;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record GearboxModel(TieredGearboxBlock gearboxBlock) {

    private static final Set<GearboxModel> MODELS = new HashSet<>();
    public static final Set<ResourceLocation> MODEL_LOCATIONS = new HashSet<>();

    public static void create(TieredGearboxBlock block) {
        if(!GTCEu.isClientSide()) return;
        MODELS.add(new GearboxModel(block));
    }

    public static void reinitModels() {
        for(GearboxModel model : MODELS) {
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(model.gearboxBlock);
            String material = model.gearboxBlock.getMaterial().getName();
            ResourceLocation modelId = Greate.id("block/" + material + "/");
            ResourceLocation modelIdNoBlock = Greate.id(material + "/");
            generateGearboxModel(model, blockId, modelId, modelIdNoBlock);
        }
    }

    private static void generateGearboxModel(GearboxModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock) {
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("gearbox"), new DelegatedModel(Create.asResource("block/gearbox/block")));
        MODEL_LOCATIONS.add(modelId.withSuffix("gearbox"));

        GTDynamicResourcePack.addItemModel(blockId, new ExtendedDelegatedModel(Create.asResource("block/gearbox/item"),
                Map.of("particle", modelId.withSuffix("axis"),
                        "1_0", modelId.withSuffix("axis"),
                        "1_1", modelId.withSuffix("axis_top"))));

        GTDynamicResourcePack.addItemModel(Greate.id(model.gearboxBlock.getMaterial().getName() + "_vertical_gearbox"), new ExtendedDelegatedModel(Create.asResource("block/gearbox/item_vertical"),
                Map.of("particle", modelId.withSuffix("axis"),
                        "0", modelId.withSuffix("axis"),
                        "1", modelId.withSuffix("axis_top"))));

        PropertyDispatch dispatch = PropertyDispatch.property(ShaftBlock.AXIS)
                .generate(axis -> {
                    Variant gearboxModel = Variant.variant();
                    return gearboxModel.with(VariantProperties.MODEL, modelId.withSuffix("gearbox"))
                            .with(VariantProperties.X_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R90 : Rotation.R0)
                            .with(VariantProperties.Y_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R180 : Rotation.R0)
                            .with(VariantProperties.UV_LOCK, true);
                });
        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.gearboxBlock).with(dispatch));
    }
}
