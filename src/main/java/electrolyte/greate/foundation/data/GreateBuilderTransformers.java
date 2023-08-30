package electrolyte.greate.foundation.data;

import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlock;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlock;
import electrolyte.greate.content.kinetics.gearbox.TieredVerticalGearboxItem;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedShaftBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static electrolyte.greate.foundation.data.GreateBlockStateGen.*;

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
        return encasedBase(b, drop).addLayer(() -> RenderType::cutoutMipped)
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
                        (s, f) -> f.getAxis() == s.getValue(TieredEncasedCogwheelBlock.AXIS) && !s.getValue(f.getAxisDirection() == Direction.AxisDirection.POSITIVE ? TieredEncasedCogwheelBlock.TOP_SHAFT : TieredEncasedCogwheelBlock.BOTTOM_SHAFT))))
                .blockstate((c, p) -> axisBlock(c, p, blockState -> {
                    String encasedSuffix = "_encased_cogwheel_side" + (large ? "_connected" : "");
                    String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
                    String wood = c.getName().contains("brass") ? "dark_oak" : "spruce";
                    String gearbox = c.getName().contains("brass") ? "brass_gearbox" : "gearbox";
                    String casing = c.getName().contains("brass") ? "brass" : "andesite";
                    String suffix = (blockState.getValue(EncasedCogwheelBlock.TOP_SHAFT) ? "_top" : "")
                            + (blockState.getValue(EncasedCogwheelBlock.BOTTOM_SHAFT) ? "_bottom" : "");
                    String modelName = c.getName() + suffix;
                    return p.models().withExistingParent(modelName, Create.asResource("block/" + blockFolder + "/block" + suffix))
                            .texture("casing", Create.asResource("block/" + casing + "_casing"))
                            .texture("particle", Create.asResource("block/" + casing + "_casing"))
                            .texture("4", Create.asResource("block/" + gearbox))
                            .texture("1", new ResourceLocation("block/stripped_" + wood + "_log_top"))
                            .texture("side", Create.asResource("block/" + casing + encasedSuffix));
                }, false))
                .item()
                .model((c, p) -> {
                    String encasedSuffix = "_encased_cogwheel_side" + (large ? "_connected" : "");
                    String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
                    String wood = c.getName().contains("brass") ? "dark_oak" : "spruce";
                    String casing = c.getName().contains("brass") ? "brass" : "andesite";
                    p.withExistingParent(c.getName(), Create.asResource("block/" + blockFolder + "/item"))
                            .texture("casing", Create.asResource("block/" + casing + "_casing"))
                            .texture("particle", Create.asResource("block/" + casing + "_casing"))
                            .texture("1", new ResourceLocation("block/stripped_" + wood + "_log_top"))
                            .texture("side", Create.asResource("block/" + casing + encasedSuffix));
                }).build();
    }

    private static <B extends RotatedPillarKineticBlock, P> BlockBuilder<B, P> encasedBase(BlockBuilder<B, P> b, Supplier<ItemLike> drop) {
        return b.initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(BlockStressDefaults.setNoImpact())
                .loot((p, loot) -> p.dropOther(loot, drop.get()));
    }

    public static <B extends TieredGearboxBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredGearbox() {
        return b -> b.blockstate(tieredGearboxProvider())
                .item()
                .model((c, p) -> p.withExistingParent(c.getName(), Create.asResource("block/gearbox/item"))
                        .texture("particle", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 8) + "/axis"))
                        .texture("1_0", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 8) + "/axis"))
                        .texture("1_1", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 8) + "/axis_top"))).build();
    }

    public static <I extends TieredVerticalGearboxItem, P> NonNullUnaryOperator<ItemBuilder<I, P>> tieredGearboxVertical() {
        return b -> b.model((c, p) -> p.withExistingParent(c.getName(), Create.asResource("block/gearbox/item_vertical"))
                .texture("particle", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 17) + "/axis"))
                .texture("0", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 17) + "/axis"))
                .texture("1", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 17) + "/axis_top")));
    }

    public static <B extends TieredMillstoneBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredMillstone() {
        return b -> b.blockstate(tieredMillstoneProvider())
                .item()
                .model((c, p) -> {
                    p.withExistingParent(c.getName(), Create.asResource("block/millstone/item"))
                            .texture("5", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 10) + "/millstone"));
                }).build();
    }

    public static <B extends TieredCrushingWheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredCrushingWheel() {
        return b -> b.blockstate(tieredCrushingWheelProvider())
                .item()
                .model((c, p) -> {
                    p.withExistingParent(c.getName(), p.modLoc("block/" + c.getName() + "_textures"))
                            .customLoader(ObjModelBuilder::begin).modelLocation(Create.asResource("models/block/crushing_wheel/crushing_wheel.obj")).flipV(true).end()
                            .transforms()
                            .transform(TransformType.GUI)
                            .rotation(30, 225, 0)
                            .scale(0.45F, 0.45F, 0.45F)
                            .end()
                            .transform(TransformType.FIXED)
                            .rotation(90, 0, 0)
                            .scale(0.45F, 0.45F, 0.45F)
                            .end();
                }).build();
    }
}
