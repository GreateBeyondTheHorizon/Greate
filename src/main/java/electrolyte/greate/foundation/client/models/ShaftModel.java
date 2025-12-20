package electrolyte.greate.foundation.client.models;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.data.pack.GTDynamicResourcePack;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import electrolyte.greate.Greate;
import electrolyte.greate.content.decoration.girder.TieredGirderEncasedShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.blockstates.VariantProperties.Rotation;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record ShaftModel(Block shaftBlock) {

    private static final Set<ShaftModel> MODELS = new HashSet<>();
    public static final Set<ResourceLocation> MODEL_LOCATIONS = new HashSet<>();

    public static void create(Block block) {
        if(!GTCEu.isClientSide()) return;
        MODELS.add(new ShaftModel(block));
    }

    public static void reinitModels() {
        for(ShaftModel model : MODELS) {
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(model.shaftBlock);
            String material = ((ITieredBlock) model.shaftBlock).getMaterial().getName();
            ResourceLocation modelId = Greate.id("block/" + material + "/");
            ResourceLocation modelIdNoBlock = Greate.id(material + "/");
            if(model.shaftBlock instanceof TieredShaftBlock) {
                generateShaftModel(model, blockId, modelId, modelIdNoBlock);
            } else if(model.shaftBlock instanceof TieredPoweredShaftBlock) {
                generatePoweredShaftModel(model, blockId, modelId, modelIdNoBlock);
            } else if (model.shaftBlock instanceof TieredEncasedShaftBlock) {
                generateEncasedShaftModel(model, blockId, modelId, modelIdNoBlock);
            } else if(model.shaftBlock instanceof TieredGirderEncasedShaftBlock) {
                generateGirderEncasedShaftModel(model, blockId, modelId, modelIdNoBlock);
            }
        }
    }

    private static void generateShaftModel(ShaftModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock) {
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("shaft"), new ExtendedDelegatedModel(Create.asResource("block/shaft"),
                Map.of("0", modelId.withSuffix("axis"),
                        "1", modelId.withSuffix("axis_top"),
                        "particle", modelId.withSuffix("axis"))));
        MODEL_LOCATIONS.add(modelId.withSuffix("shaft"));

        GTDynamicResourcePack.addItemModel(blockId, new DelegatedModel(modelId.withSuffix("shaft")));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("shaft_half"), new ExtendedDelegatedModel(Create.asResource("block/shaft_half"),
                Map.of("0", modelId.withSuffix("axis"),
                        "1", modelId.withSuffix("axis_top"),
                        "particle", modelId.withSuffix("axis"))));
        MODEL_LOCATIONS.add(modelId.withSuffix("shaft_half"));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("cogwheel_shaft"), new ExtendedDelegatedModel(Create.asResource("block/cogwheel_shaft"),
                Map.of("0", modelId.withSuffix("axis_top"),
                        "1", modelId.withSuffix("cogwheel_axis"),
                        "particle", modelId.withSuffix("axis_top"))));
        MODEL_LOCATIONS.add(modelId.withSuffix("cogwheel_shaft"));

        PropertyDispatch dispatch = PropertyDispatch.property(ShaftBlock.AXIS)
                .generate(axis -> {
                    Variant shaftModel = Variant.variant();
                    return shaftModel.with(VariantProperties.MODEL, modelId.withSuffix("shaft"))
                            .with(VariantProperties.X_ROT, axis == Axis.Y ? Rotation.R0 : Rotation.R90)
                            .with(VariantProperties.Y_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R180 : Rotation.R0);
                });
        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.shaftBlock).with(dispatch));
    }

    private static void generatePoweredShaftModel(ShaftModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock) {
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("powered_shaft"), new ExtendedDelegatedModel(Create.asResource("block/powered_shaft"),
                Map.of("2", modelId.withSuffix("axis_top"),
                        "3", modelId.withSuffix("axis"),
                        "particle", modelId.withSuffix("axis_top"))));
        MODEL_LOCATIONS.add(modelId.withSuffix("powered_shaft"));

        PropertyDispatch dispatch = PropertyDispatch.property(ShaftBlock.AXIS)
                .generate(axis -> {
                    Variant shaftModel = Variant.variant();
                    return shaftModel.with(VariantProperties.MODEL, modelId.withSuffix("powered_shaft"))
                            .with(VariantProperties.X_ROT, axis == Axis.Y ? Rotation.R0 : Rotation.R90)
                            .with(VariantProperties.Y_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R180 : Rotation.R0);
                });
        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.shaftBlock).with(dispatch));
    }

    private static void generateEncasedShaftModel(ShaftModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock) {
        String encasedType = blockId.getPath().contains("andesite_encased") ? "andesite" : "brass";
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix(encasedType + "_encased_shaft"), new DelegatedModel(Create.asResource("block/encased_shaft/block_" + encasedType)));
        MODEL_LOCATIONS.add(modelId.withSuffix(encasedType + "_encased_shaft"));

        PropertyDispatch dispatch = PropertyDispatch.property(ShaftBlock.AXIS)
                .generate(axis -> {
                    Variant shaftModel = Variant.variant();
                    return shaftModel.with(VariantProperties.MODEL, modelId.withSuffix(encasedType + "_encased_shaft"))
                            .with(VariantProperties.X_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R90 : Rotation.R0)
                            .with(VariantProperties.Y_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R180 : Rotation.R0)
                            .with(VariantProperties.UV_LOCK, true);
                });
        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.shaftBlock).with(dispatch));
    }

    private static void generateGirderEncasedShaftModel(ShaftModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock) {
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("metal_girder_encased_shaft"), new DelegatedModel(Create.asResource("block/metal_girder_encased_shaft/block")));
        MODEL_LOCATIONS.add(modelId.withSuffix("metal_girder_encased_shaft"));
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("metal_girder_encased_shaft_top"), new DelegatedModel(Create.asResource("block/metal_girder_encased_shaft/block_top")));
        MODEL_LOCATIONS.add(modelId.withSuffix("metal_girder_encased_shaft_top"));
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix("metal_girder_encased_shaft_bottom"), new DelegatedModel(Create.asResource("block/metal_girder_encased_shaft/block_bottom")));
        MODEL_LOCATIONS.add(modelId.withSuffix("metal_girder_encased_shaft_bottom"));

        Variant shaftModelX = Variant.variant().with(VariantProperties.MODEL, modelId.withSuffix("metal_girder_encased_shaft"))
                .with(VariantProperties.Y_ROT, Rotation.R90);
        Variant shaftModelZ = Variant.variant().with(VariantProperties.MODEL, modelId.withSuffix("metal_girder_encased_shaft"));
        Variant shaftModelTop = Variant.variant().with(VariantProperties.MODEL, modelId.withSuffix("metal_girder_encased_shaft_top"));
        Variant shaftModelBottom = Variant.variant().with(VariantProperties.MODEL, modelId.withSuffix("metal_girder_encased_shaft_bottom"));
        GTDynamicResourcePack.addBlockState(blockId, MultiPartGenerator.multiPart(model.shaftBlock)
                .with(Condition.condition().term(GirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.X), shaftModelX)
                .with(Condition.condition().term(GirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.Z), shaftModelZ)
                .with(Condition.condition().term(GirderEncasedShaftBlock.TOP,true), shaftModelTop)
                .with(Condition.condition().term(GirderEncasedShaftBlock.BOTTOM,true), shaftModelBottom));

    }
}
