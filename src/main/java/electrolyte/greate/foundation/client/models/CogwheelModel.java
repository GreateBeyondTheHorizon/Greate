package electrolyte.greate.foundation.client.models;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.data.pack.GTDynamicResourcePack;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.blockstates.VariantProperties.Rotation;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record CogwheelModel(Block cogweelBlock) {

    private static final Set<CogwheelModel> MODELS = new HashSet<>();
    public static final Set<ResourceLocation> MODEL_LOCATIONS = new HashSet<>();

    public static void create(Block block) {
        if(!GTCEu.isClientSide()) return;
        MODELS.add(new CogwheelModel(block));
    }

    public static void reinitModels() {
        for(CogwheelModel model : MODELS) {
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(model.cogweelBlock);
            String material = ((ITieredBlock) model.cogweelBlock).getMaterial().getName();
            ResourceLocation modelId = Greate.id("block/" + material + "/");
            ResourceLocation modelIdNoBlock = Greate.id(material + "/");
            if(model.cogweelBlock instanceof TieredCogwheelBlock tcb) {
                generateCogwheelModel(model, blockId, modelId, modelIdNoBlock, tcb.isLargeCog());
            } else if(model.cogweelBlock instanceof TieredEncasedCogwheelBlock tecb) {
                generateEncasedCogModel(model, blockId, modelId, modelIdNoBlock, tecb.isLargeCog());
            }
        }
    }

    public static void generateCogwheelModel(CogwheelModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock, boolean isLargeCog) {
        String cogwheelSuffix = isLargeCog ? "large_cogwheel" : "cogwheel";
        String textureKey = isLargeCog ? "4" : "1_2";
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix(cogwheelSuffix + "_shaftless"), new ExtendedDelegatedModel(Create.asResource("block/" + cogwheelSuffix + "_shaftless"),
                Map.of(textureKey, modelId.withSuffix(cogwheelSuffix),
                        "particle", modelId.withSuffix(cogwheelSuffix))));
        MODEL_LOCATIONS.add(modelId.withSuffix(cogwheelSuffix + "_shaftless"));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix(cogwheelSuffix), new ExtendedDelegatedModel(Create.asResource("block/" + cogwheelSuffix),
                Map.of("0", modelId.withSuffix("cogwheel_axis"),
                        "3", modelId.withSuffix("axis_top"),
                        textureKey, modelId.withSuffix(cogwheelSuffix),
                        "particle", modelId.withSuffix(cogwheelSuffix))));
        MODEL_LOCATIONS.add(modelId.withSuffix(cogwheelSuffix));

        GTDynamicResourcePack.addItemModel(blockId, new DelegatedModel(modelId.withSuffix(cogwheelSuffix)));

        PropertyDispatch dispatch = PropertyDispatch.property(CogWheelBlock.AXIS)
                .generate(axis -> {
                    Variant cogModel = Variant.variant();
                    return cogModel.with(VariantProperties.MODEL, modelId.withSuffix(cogwheelSuffix))
                            .with(VariantProperties.X_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R90 : Rotation.R0)
                            .with(VariantProperties.Y_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R180 : Rotation.R0);
                });
        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.cogweelBlock).with(dispatch));
    }
    public static void generateEncasedCogModel(CogwheelModel model, ResourceLocation blockId, ResourceLocation modelId, ResourceLocation modelIdNoBlock, boolean isLargeCog) {
        String cogwheelSuffix = isLargeCog ? "large_cogwheel" : "cogwheel";
        String encasingSuffix = blockId.getPath().contains("andesite_encased") ? "andesite" : "brass";
        String encasedSuffix = "_encased_cogwheel_side" + (isLargeCog ? "_connected" : "");
        String blockFolder = "encased_" + cogwheelSuffix;
        String wood = encasingSuffix.equals("andesite") ? "spruce" : "dark_oak";
        String gearbox = encasingSuffix.equals("andesite") ? "gearbox" : "brass_gearbox";
        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix), new ExtendedDelegatedModel(Create.asResource("block/" + blockFolder + "/block"),
                RenderType.cutoutMipped(),
                Map.of("casing", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "particle", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "4", Create.asResource("block/" + gearbox),
                        "1", ResourceLocation.parse("block/stripped_" + wood + "_log_top"),
                        "side", Create.asResource("block/" + encasingSuffix + encasedSuffix))));
        MODEL_LOCATIONS.add(modelId.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix + "_top"), new ExtendedDelegatedModel(Create.asResource("block/" + blockFolder + "/block_top"),
                RenderType.cutoutMipped(),
                Map.of("casing", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "particle", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "4", Create.asResource("block/" + gearbox),
                        "1", ResourceLocation.parse("block/stripped_" + wood + "_log_top"),
                        "side", Create.asResource("block/" + encasingSuffix + encasedSuffix))));
        MODEL_LOCATIONS.add(modelId.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix + "_top"));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix + "_bottom"), new ExtendedDelegatedModel(Create.asResource("block/" + blockFolder + "/block_bottom"),
                RenderType.cutoutMipped(),
                Map.of("casing", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "particle", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "4", Create.asResource("block/" + gearbox),
                        "1", ResourceLocation.parse("block/stripped_" + wood + "_log_top"),
                        "side", Create.asResource("block/" + encasingSuffix + encasedSuffix))));
        MODEL_LOCATIONS.add(modelId.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix + "_bottom"));

        GTDynamicResourcePack.addBlockModel(modelIdNoBlock.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix + "_top_bottom"), new ExtendedDelegatedModel(Create.asResource("block/" + blockFolder + "/block_top_bottom"),
                RenderType.cutoutMipped(),
                Map.of("casing", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "particle", Create.asResource("block/" + encasingSuffix + "_casing"),
                        "4", Create.asResource("block/" + gearbox),
                        "1", ResourceLocation.parse("block/stripped_" + wood + "_log_top"),
                        "side", Create.asResource("block/" + encasingSuffix + encasedSuffix))));
        MODEL_LOCATIONS.add(modelId.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix + "_top_bottom"));

        PropertyDispatch dispatch = PropertyDispatch.properties(BlockStateProperties.AXIS, EncasedCogwheelBlock.TOP_SHAFT, EncasedCogwheelBlock.BOTTOM_SHAFT)
                .generate((axis, top, bottom) -> {
                    Variant cogModel = Variant.variant();
                    String topSuffix = top ? "_top" : "";
                    String bottomSuffix = bottom ? "_bottom" : "";

                    return cogModel.with(VariantProperties.MODEL, modelId.withSuffix(encasingSuffix + "_encased_" + cogwheelSuffix + topSuffix + bottomSuffix))
                            .with(VariantProperties.X_ROT, axis == Axis.Y ? Rotation.R0 : Rotation.R90)
                            .with(VariantProperties.Y_ROT, axis == Axis.X ? Rotation.R90 : axis == Axis.Z ? Rotation.R180 : Rotation.R0);
                });
        GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.cogweelBlock).with(dispatch));

    }
}
