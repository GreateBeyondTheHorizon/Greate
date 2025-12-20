package electrolyte.greate.foundation.client.models;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.data.pack.GTDynamicResourcePack;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.simibubi.create.content.kinetics.belt.BeltSlope.*;

@MethodsReturnNonnullByDefault
public record BeltModel(TieredBeltBlock beltBlock) {

    private static final Set<BeltModel> MODELS = new HashSet<>();
    public static final Set<ResourceLocation> MODEL_LOCATIONS = new HashSet<>();

    public static void create(TieredBeltBlock block) {
        if(!GTCEu.isClientSide()) return;
        MODELS.add(new BeltModel(block));
    }

    public static void reinitModels() {
        for(BeltPart part : BeltPart.values()) {
            for(BeltSlope slope : BeltSlope.values()) {
                if(slope == VERTICAL) continue;
                ResourceLocation loc = Greate.id("belt_casing");
                String suffix = getSuffix(part, slope, false, false, true);
                GTDynamicResourcePack.addBlockModel(loc.withSuffix(suffix), new ExtendedDelegatedModel(Create.asResource("block/belt_casing" + suffix), RenderType.cutout()));
            }
        }

        for(BeltModel model : MODELS) {
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(model.beltBlock);
            ResourceLocation modelId = blockId.withPrefix("block/");
            String beltMaterial = model.beltBlock().getBeltMaterial().getName();
            ResourceLocation modelIdNoBelt = Greate.id(beltMaterial).withPrefix("block/");

            for(Material material : model.beltBlock.getBeltMaterial().getProperty(GreatePropertyKeys.BELT).getValidShafts()) {
                GTDynamicResourcePack.addBlockModel(Greate.id(material.getName() + "/belt_pulley"), new ExtendedDelegatedModel(Create.asResource("block/belt_pulley"),
                        Map.of("0", Greate.id("block/").withSuffix(material.getName()).withSuffix("/axis"),
                                "1", Greate.id("block/").withSuffix(material.getName()).withSuffix("/axis_top"))));
                MODEL_LOCATIONS.add(Greate.id(material.getName() + "/belt_pulley").withPrefix("block/"));
            }

            GTDynamicResourcePack.addBlockModel(blockId.withSuffix("/particle"), new ExtendedDelegatedModel(Create.asResource("block/belt/particle"),
                    Map.of("particle", Greate.id("block/").withSuffix(beltMaterial).withSuffix("/belt"))));

            for(BeltPart part : BeltPart.values()) {
                if(part == BeltPart.PULLEY) continue;
                for(BeltSlope slope : BeltSlope.values()) {
                    String suffix = getSuffix(part, slope, false, false, false);
                    String textureSuffix = slope.isDiagonal() ? "/belt_diagonal" : "/belt";
                    GTDynamicResourcePack.addBlockModel(blockId.withSuffix(suffix), new ExtendedDelegatedModel(Create.asResource("block/belt" + suffix),
                            RenderType.cutoutMipped(),
                            Map.of("0", modelIdNoBelt.withSuffix(textureSuffix),
                                    "particle", modelIdNoBelt.withSuffix(textureSuffix))));
                    MODEL_LOCATIONS.add(blockId.withSuffix(suffix).withPrefix("block/"));


                    String overlaySuffix = getSuffix(part, slope, true, false, false);
                    GTDynamicResourcePack.addBlockModel(Greate.id("belt").withSuffix(overlaySuffix), new ExtendedDelegatedModel(Create.asResource("block/belt" + suffix),
                            RenderType.cutout(),
                            Map.of("0", Greate.id("block/belt_overlay/empty"))));
                    MODEL_LOCATIONS.add(Greate.id("belt").withSuffix(overlaySuffix).withPrefix("block/"));


                    if(slope.isDiagonal()) continue; //No bottom for diagonal belts
                    String suffixBottom = getSuffix(part, slope, false, true, false);
                    GTDynamicResourcePack.addBlockModel(blockId.withSuffix(suffixBottom), new ExtendedDelegatedModel(Create.asResource("block/belt" + suffixBottom),
                            RenderType.cutoutMipped(),
                            Map.of("1", modelIdNoBelt.withSuffix("/belt_offset"))));
                    MODEL_LOCATIONS.add(blockId.withSuffix(suffixBottom).withPrefix("block/"));

                    String overlaySuffixBottom = getSuffix(part, slope, true, true, false);
                    GTDynamicResourcePack.addBlockModel(Greate.id("belt").withSuffix(overlaySuffixBottom), new ExtendedDelegatedModel(Create.asResource("block/belt" + suffixBottom),
                            RenderType.cutout(),
                            Map.of("1", Greate.id("block/belt_overlay/empty"))));
                    MODEL_LOCATIONS.add(Greate.id("belt").withSuffix(overlaySuffixBottom).withPrefix("block/"));
                }
            }

            ResourceLocation particleLocation = modelId.withSuffix("/particle");
            ResourceLocation casingLocation = Greate.id("block/belt_casing/");
            PropertyDispatch dispatch = PropertyDispatch.properties(BeltBlock.SLOPE, BeltBlock.CASING, BeltBlock.PART, BeltBlock.HORIZONTAL_FACING, BeltBlock.WATERLOGGED)
                    .generate((slope, casing, part, direction, waterlogged) -> {
                        Variant beltModel = Variant.variant();
                        switch(slope) {
                            case HORIZONTAL ->
                                    beltModel.with(VariantProperties.MODEL, !casing ? particleLocation : casingLocation.withSuffix("horizontal_" + part.name().toLowerCase()));
                            case UPWARD, DOWNWARD ->
                                    beltModel.with(VariantProperties.MODEL, !casing ? particleLocation :
                                            part == BeltPart.START ? casingLocation.withSuffix("diagonal_end") :
                                            part == BeltPart.END ? casingLocation.withSuffix("diagonal_start") :
                                            casingLocation.withSuffix("diagonal_" + part.name().toLowerCase()));
                            case VERTICAL, SIDEWAYS ->
                                    beltModel.with(VariantProperties.MODEL, !casing ? particleLocation : casingLocation.withSuffix("sideways_" + part.name().toLowerCase()));
                        }
                        return beltModel
                                .with(VariantProperties.X_ROT, GreateModelUtils.getBeltXRotation(direction, slope))
                                .with(VariantProperties.Y_ROT, GreateModelUtils.getBeltYRotation(direction, slope, casing));
                    });
            GTDynamicResourcePack.addBlockState(blockId, MultiVariantGenerator.multiVariant(model.beltBlock).with(dispatch));
        }
    }

    private static String getSuffix(BeltPart part, BeltSlope slope, boolean overlay, boolean bottom, boolean casing) {
        String suffix = !overlay ? "/" : "_overlay/";

        if(slope.isDiagonal()) suffix += "diagonal_";

        if(!overlay && casing) {
            if(slope == HORIZONTAL) suffix += "horizontal_";
            if(slope == SIDEWAYS) suffix += "sideways_";
        }

        switch(part) {
            case START -> suffix += "start";
            case MIDDLE -> suffix += "middle";
            case END -> suffix += "end";
            case PULLEY -> suffix += "pulley";
        }

        if(bottom && !slope.isDiagonal()) suffix += "_bottom";
        return suffix;
    }
}
