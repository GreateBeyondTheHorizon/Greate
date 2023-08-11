package electrolyte.greate.foundation.data;

import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedShaftBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static electrolyte.greate.foundation.data.GreateBlockStateGen.tieredEncasedShaftProvider;

public class GreateBuilderTransformers {

    public static <B extends TieredEncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredAndesiteEncasedShaft(BlockEntry<TieredShaftBlock> shaftBlock, Supplier<CTSpriteShiftEntry> casingShift) {
        return builder -> encasedBase(builder, shaftBlock::get)
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(casingShift.get())))
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
                        (s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
                .blockstate(tieredEncasedShaftProvider())
                .item()
                .build();
    }

    public static <B extends TieredEncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredBrassEncasedShaft(BlockEntry<TieredShaftBlock> shaftBlock, Supplier<CTSpriteShiftEntry> casingShift) {
        return builder -> encasedBase(builder, shaftBlock::get)
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(casingShift.get())))
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
                        (s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
                .blockstate(tieredEncasedShaftProvider())
                .item()
                .build();
    }

    public static <B extends TieredEncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredEncasedCogwheel(BlockEntry<TieredCogwheelBlock> cogwheel, Supplier<CTSpriteShiftEntry> casingShift) {
        return b -> tieredEncasedCogwheelBase(b, casingShift, cogwheel::get, false);
    }
    public static <B extends TieredEncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredEncasedLargeCogwheel(BlockEntry<TieredCogwheelBlock> cogwheel, Supplier<CTSpriteShiftEntry> casingShift) {
        return b -> tieredEncasedCogwheelBase(b, casingShift, cogwheel::get, true)
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(casingShift.get())));
    }

    private static <B extends TieredEncasedCogwheelBlock, P> BlockBuilder<B, P> tieredEncasedCogwheelBase(BlockBuilder<B, P> b,
        Supplier<CTSpriteShiftEntry> casingShift, Supplier<ItemLike> drop, boolean large) {
        String cogwheelSize = large ? "encased_large_cogwheel" : "encased_cogwheel";
        return encasedBase(b, drop).addLayer(() -> RenderType::cutoutMipped)
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
                        (s, f) -> f.getAxis() == s.getValue(TieredEncasedCogwheelBlock.AXIS) && !s.getValue(f.getAxisDirection() == Direction.AxisDirection.POSITIVE ? TieredEncasedCogwheelBlock.TOP_SHAFT : TieredEncasedCogwheelBlock.BOTTOM_SHAFT))))
                .blockstate((c, p) -> axisBlock(c, p, blockState -> {
                    String suffix = (blockState.getValue(TieredEncasedCogwheelBlock.TOP_SHAFT) ? "_top" : "") +
                            (blockState.getValue(TieredEncasedCogwheelBlock.BOTTOM_SHAFT) ? "_bottom" : "");
                    String modelName = c.getName() + suffix;
                    String casingType = c.getName().contains("brass") ? "brass_" : "andesite_";
                    return p.models()
                            .withExistingParent(modelName, p.modLoc("block/base/" + casingType + cogwheelSize + suffix));
                }, false))
                .item()
                .model((c, p) -> {
                    String casingType = c.getName().contains("brass") ? "brass_" : "andesite_";
                    p.withExistingParent(c.getName(), p.modLoc("block/base/" + casingType + "encased_cogwheel_item"));
                })
                .build();
    }

    private static <B extends RotatedPillarKineticBlock, P> BlockBuilder<B, P> encasedBase(BlockBuilder<B, P> b, Supplier<ItemLike> drop) {
        return b.initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(BlockStressDefaults.setNoImpact())
                .loot((p, loot) -> p.dropOther(loot, drop.get()));
    }
}
