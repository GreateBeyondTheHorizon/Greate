package electrolyte.greate.foundation.data;

import com.google.common.base.Predicates;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.sun.jna.platform.unix.solaris.LibKstat.Kstat;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.registry.Belts;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder.PartialBlockstate;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreateBlockStateGen {

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredShaftProvider() {
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry()).forAllStatesExcept(state -> {
            Axis axis = state.getValue(BlockStateProperties.AXIS);
            return ConfiguredModel.builder()
                    .modelFile(prov.models().withExistingParent(ctx.getName().substring(0, ctx.getName().length() - 5) + "cogwheel_shaft", Create.asResource("block/cogwheel_shaft"))
                            .texture("0", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis_top"))
                            .texture("1", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/cogwheel_axis"))
                            .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis_top")))
                    .modelFile(prov.models().withExistingParent(ctx.getName() + "_half", Create.asResource("block/shaft_half"))
                            .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("0", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("1", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis_top")))
                    .modelFile(prov.models().withExistingParent(ctx.getName(), Create.asResource("block/shaft"))
                            .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("0", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("1", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis_top")))
                    .uvLock(false)
                    .rotationX(axis == Axis.Y ? 0 : 90)
                    .rotationY(axis == Axis.X ? 90 : axis == Axis.Z ? 180 : 0)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredPoweredShaftProvider() {
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry()).forAllStatesExcept(state -> {
            Axis axis = state.getValue(BlockStateProperties.AXIS);
            return ConfiguredModel.builder()
                    .modelFile(prov.models().withExistingParent(ctx.getName(), Create.asResource("block/powered_shaft"))
                            .texture("particle", prov.modLoc("block/" + ctx.getName().substring(8, ctx.getName().length() - 6) + "/axis"))
                            .texture("3", prov.modLoc("block/" + ctx.getName().substring(8, ctx.getName().length() - 6) + "/axis"))
                            .texture("2", prov.modLoc("block/" + ctx.getName().substring(8, ctx.getName().length() - 6) + "/axis_top")))
                    .uvLock(false)
                    .rotationX(axis == Axis.Y ? 0 : 90)
                    .rotationY(axis == Axis.X ? 90 : axis == Axis.Z ? 180 : 0)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredEncasedShaftProvider() {
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry()).forAllStatesExcept(state -> {
            Axis axis = state.getValue(BlockStateProperties.AXIS);
            String shaftType = ctx.getName().contains("andesite") ? "andesite" : "brass";
            return ConfiguredModel.builder()
                    .modelFile(prov.models().withExistingParent(ctx.getName(), Create.asResource("block/encased_shaft/block_" + shaftType)))
                    .uvLock(true)
                    .rotationX(axis == Axis.X ? 90 : axis == Axis.Z ? 90 : 0)
                    .rotationY(axis == Axis.X ? 90 : axis == Axis.Z ? 180 : 0)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredCogwheelProvider(boolean largeCogwheel) {
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry()).forAllStatesExcept(state -> {
            Axis axis = state.getValue(BlockStateProperties.AXIS);
            int x = axis == Axis.X ? 90 : axis == Axis.Z ? 90 : 0;
            int y = axis == Axis.X ? 90 : axis == Axis.Z ? 180 : 0;
            if(!largeCogwheel) {
                return ConfiguredModel.builder()
                        .modelFile(prov.models().withExistingParent(ctx.getName() + "_shaftless", Create.asResource("block/cogwheel_shaftless"))
                                .texture("1_2", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/cogwheel"))
                                .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/cogwheel")))
                        .modelFile(prov.models().withExistingParent(ctx.getName(), Create.asResource("block/cogwheel"))
                                .texture("0", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/cogwheel_axis"))
                                .texture("3", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/axis_top"))
                                .texture("1_2", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/cogwheel"))
                                .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/cogwheel")))
                        .uvLock(false)
                        .rotationX(x)
                        .rotationY(y)
                        .build();
            } else {
                return ConfiguredModel.builder()
                        .modelFile(prov.models().withExistingParent(ctx.getName() + "_shaftless", Create.asResource("block/large_cogwheel_shaftless"))
                                .texture("4", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/large_cogwheel"))
                                .texture("particle", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/large_cogwheel")))
                        .modelFile(prov.models().withExistingParent(ctx.getName(), Create.asResource("block/large_cogwheel"))
                                .texture("0", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/cogwheel_axis"))
                                .texture("3", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/axis_top"))
                                .texture("4", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/large_cogwheel"))
                                .texture("particle", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/large_cogwheel")))
                        .uvLock(false)
                        .rotationX(x)
                        .rotationY(y)
                        .build();
            }
        }, BlockStateProperties.WATERLOGGED);
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredGearboxProvider() {
        return (c, p) -> p.getVariantBuilder(c.getEntry()).forAllStates(state -> {
            Axis axis = state.getValue(BlockStateProperties.AXIS);
            return ConfiguredModel.builder()
                    .modelFile(p.models().withExistingParent(c.getName(), Create.asResource("block/gearbox/block")))
                    .uvLock(true)
                    .rotationX(axis == Axis.X ? 90 : axis == Axis.Z ? 90 : 0)
                    .rotationY(axis == Axis.X ? 90 : axis == Axis.Z ? 180 : 0)
                    .build();
        });
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredMillstoneProvider() {
        return (c, p) -> p.getVariantBuilder(c.getEntry()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(p.models().withExistingParent(c.getName() + "_inner", Create.asResource("block/millstone/inner"))
                        .texture("5", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 10) + "/millstone"))
                        .texture("particle", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 10) + "/axis"))
                        .texture("1_0", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 10) + "/axis"))
                        .texture("1_1", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 10) + "/axis_top")))
                .modelFile(p.models().withExistingParent(c.getName(), Create.asResource("block/millstone/block")))
                .build());
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredCrushingWheelProvider() {
        return (c, p) -> p.getVariantBuilder(c.getEntry()).forAllStates(state -> {
            Axis axis = state.getValue(BlockStateProperties.AXIS);
            return ConfiguredModel.builder()
                    .modelFile(p.models().withExistingParent(c.getName() + "_textures", Create.asResource("block/crushing_wheel/textures"))
                            .texture("axis", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 15)) + "/axis")
                            .texture("axis_top", p.modLoc("block/" + c.getName().substring(0, c.getName().length() - 15)) + "/axis_top"))
                    .modelFile(p.models().withExistingParent(c.getName(), p.modLoc("block/" + c.getName() + "_textures"))
                    .customLoader(ObjModelBuilder::begin).modelLocation(Create.asResource("models/block/crushing_wheel/crushing_wheel.obj")).flipV(true).end())
                    .rotationX(axis == Axis.X ? 90 : axis == Axis.Z ? 90 : 0)
                    .rotationY(axis == Axis.X ? 90 : axis == Axis.Z ? 180 : 0)
                    .build();
        });
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredCrushingWheelControllerProvider() {
        return (c, p) -> p.getVariantBuilder(c.getEntry()).forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(new UncheckedModelFile("minecraft:block/air"))
                .build(), BlockStateProperties.FACING);
    }



   /* public static void tieredBeltProvider(DataGenContext<Block, TieredBeltBlock> c, RegistrateBlockstateProvider p) {
        VariantBlockStateBuilder builder =  p.getVariantBuilder(c.get());
        String material = c.getName().substring(0, c.getName().length() - 5);
        final ConfiguredModel[][] models = {new ConfiguredModel[1]};
        builder.forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BeltSlope slope = state.getValue(BeltBlock.SLOPE);
            if(!state.getValue(BeltBlock.CASING)) {
                models[0] = ConfiguredModel.builder()
                        .modelFile(new UncheckedModelFile(p.modLoc("block/" + material + "/particle")))
                        .rotationX(slope == BeltSlope.VERTICAL ? 90 : slope == BeltSlope.SIDEWAYS && dir.getAxisDirection() == AxisDirection.NEGATIVE ? 180 : 0)
                        .rotationY((dir.getAxis().isVertical() ? 0 : (int) dir.toYRot()) + (slope == BeltSlope.UPWARD ? 180 : 0) + ())
                        .build();
            }
            if(state.getValue(BeltBlock.CASING)) {
                models[0] = ConfiguredModel.builder()
                        .modelFile(new UncheckedModelFile(p.modLoc("block/" + material + "/particle")))
                        .build();
            }

            return models[0];
        });
    }*/
}
