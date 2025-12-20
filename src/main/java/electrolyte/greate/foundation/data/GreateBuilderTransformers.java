package electrolyte.greate.foundation.data;

import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.content.fluids.pump.TieredPumpBlock;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlock;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlock;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlock;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedShaftBlock;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;

import java.util.function.Supplier;

import static electrolyte.greate.foundation.data.GreateBlockStateGen.*;

public class GreateBuilderTransformers {

    public static <B extends TieredEncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredEncasedShaft(Supplier<CTSpriteShiftEntry> casingShift) {
        return builder -> encasedBase(builder)
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(casingShift.get())))
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
                        (s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))));
    }

    public static <B extends TieredEncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredEncasedCogwheel(Supplier<CTSpriteShiftEntry> casingShift) {
        return b -> tieredEncasedCogwheelBase(b, casingShift);
    }
    public static <B extends TieredEncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredEncasedLargeCogwheel(Supplier<CTSpriteShiftEntry> casingShift) {
        return b -> tieredEncasedCogwheelBase(b, casingShift)
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(casingShift.get())));
    }

    private static <B extends TieredEncasedCogwheelBlock, P> BlockBuilder<B, P> tieredEncasedCogwheelBase(BlockBuilder<B, P> b,
        Supplier<CTSpriteShiftEntry> casingShift) {
        return encasedBase(b)
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
                        (s, f) -> f.getAxis() == s.getValue(TieredEncasedCogwheelBlock.AXIS) && !s.getValue(f.getAxisDirection() == Direction.AxisDirection.POSITIVE ? TieredEncasedCogwheelBlock.TOP_SHAFT : TieredEncasedCogwheelBlock.BOTTOM_SHAFT))));
    }

    private static <B extends RotatedPillarKineticBlock, P> BlockBuilder<B, P> encasedBase(BlockBuilder<B, P> b) {
        return b.blockstate(NonNullBiConsumer.noop())
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().noLootTable())
                .transform(GStress.setNoImpact());
    }

    public static <B extends TieredMillstoneBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredMillstone() {
        return b -> b.blockstate(tieredMillstoneProvider())
                .item()
                .model((c, p) ->
                        p.withExistingParent(c.getName(), Create.asResource("block/millstone/item"))
                            .texture("5", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 10) + "/millstone"))).build();
    }

    public static <B extends TieredCrushingWheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredCrushingWheel() {
        return b -> b.blockstate(tieredCrushingWheelProvider())
                .item()
                .model((c, p) ->
                        p.withExistingParent(c.getName(), p.modLoc("block/" + c.getName() + "_textures"))
                            .customLoader(ObjModelBuilder::begin).modelLocation(Create.asResource("models/block/crushing_wheel/crushing_wheel.obj")).flipV(true).end()
                            .transforms()
                            .transform(ItemDisplayContext.GUI)
                            .rotation(30, 225, 0)
                            .scale(0.45F, 0.45F, 0.45F)
                            .end()
                            .transform(ItemDisplayContext.FIXED)
                            .rotation(90, 0, 0)
                            .scale(0.45F, 0.45F, 0.45F)
                            .end()).build();
    }

    public static <B extends TieredMechanicalPressBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredMechanicalPress() {
        return b -> b.blockstate(tieredMechanicalPressProvider())
                .item(AssemblyOperatorBlockItem::new)
                .model((c, p) -> {
                    String material = c.getName().substring(0, c.getName().length() - 17);
                    p.withExistingParent(c.getName(), Create.asResource("block/mechanical_press/item"))
                            .texture("0", p.modLoc("block/" + material + "/axis"))
                            .texture("1", p.modLoc("block/" + material + "/axis_top"))
                            .texture("mechanical_press_head", p.modLoc("block/" + material + "/mechanical_press_head"))
                            .texture("8", p.modLoc("block/mechanical_press_side"))
                            .texture("particle", p.modLoc("block/mechanical_press_side"));
                }).build();
    }

    public static <B extends TieredMechanicalMixerBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredMechanicalMixer() {
        return b -> b.blockstate(tieredMechanicalMixerProvider())
                    .item(AssemblyOperatorBlockItem::new)
                    .model((c, p) -> {
                        String material = c.getName().substring(0, c.getName().length() - 17);
                        p.withExistingParent(c.getName(), Create.asResource("block/mechanical_mixer/item"))
                                .texture("6", p.modLoc("block/" + material + "/mixer_head"))
                                .texture("4", p.modLoc("block/mechanical_mixer_base_side"))
                                .texture("particle", p.modLoc("block/mechanical_mixer_base_side"))
                                .texture("1_2", p.modLoc("block/" + material + "/cogwheel"));
                    }).build();
    }

    public static <B extends TieredPumpBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredMechanicalPump() {
        return b -> b.blockstate(tieredMechanicalPumpProvider())
                .item()
                .model((c, p) -> {
                    String material = c.getName().substring(0, c.getName().length() - 16);
                    p.withExistingParent(c.getName(), Create.asResource("block/mechanical_pump/item"))
                            .texture("4", p.modLoc("block/" + material + "/pump"))
                            .texture("particle", p.modLoc("block/" + material + "/pump"));
                }).build();
    }

    public static void tieredSaw(DataGenContext<Item, BlockItem> ctx, RegistrateItemModelProvider prov) {
        prov.withExistingParent(ctx.getName(), Create.asResource("block/mechanical_saw/item"))
                .texture("stonecutter_saw", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 15) + "/saw"));
    }

    public static <B extends TieredEncasedFanBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tieredEncasedFan() {
        return b -> b.blockstate(tieredEncasedFanProvider())
                .item()
                .model((c, p) -> {
                    String material = c.getName().substring(0, c.getName().length() - 12);
                    p.withExistingParent(c.getName(), Create.asResource("block/encased_fan/item"))
                            .texture("fan_blades", p.modLoc("block/" + material + "/fan_blades"));
                }).build();
    }
}
