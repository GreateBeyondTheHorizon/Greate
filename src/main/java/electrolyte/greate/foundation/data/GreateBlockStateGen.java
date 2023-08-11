package electrolyte.greate.foundation.data;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class GreateBlockStateGen {

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredShaftProvider() {
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry()).forAllStatesExcept(state -> {
            Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
            return ConfiguredModel.builder()
                    .modelFile(prov.models().withExistingParent(ctx.getName() + "_half", "greate:block/base/shaft_half")
                            .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("0", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("1", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis_top")))
                    .modelFile(prov.models().withExistingParent(ctx.getName(), "greate:block/base/shaft")
                            .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("0", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis"))
                            .texture("1", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 6) + "/axis_top")))
                    .uvLock(false)
                    .rotationX(axis == Direction.Axis.Y ? 0 : 90)
                    .rotationY(axis == Direction.Axis.X ? 90 : axis == Direction.Axis.Z ? 180 : 0)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredEncasedShaftProvider() {
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry()).forAllStatesExcept(state -> {
            Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
            String shaftType = ctx.getName().contains("andesite") ? "andesite_shaft" : "brass_shaft";
            return ConfiguredModel.builder()
                    .modelFile(prov.models().withExistingParent(ctx.getId().toString(), "greate:block/base/encased_" + shaftType))
                    .uvLock(true)
                    .rotationX(axis == Direction.Axis.X ? 90 : axis == Direction.Axis.Z ? 90 : 0)
                    .rotationY(axis == Direction.Axis.X ? 90 : axis == Direction.Axis.Z ? 180 : 0)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> tieredCogwheelProvider(boolean largeCogwheel) {
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry()).forAllStatesExcept(state -> {
            Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
            int x = axis == Direction.Axis.X ? 90 : axis == Direction.Axis.Z ? 90 : 0;
            int y = axis == Direction.Axis.X ? 90 : axis == Direction.Axis.Z ? 180 : 0;
            if(!largeCogwheel) {
                return ConfiguredModel.builder()
                        .modelFile(prov.models().withExistingParent(ctx.getName() + "_shaftless", "greate:block/base/cogwheel_shaftless")
                                .texture("1_2", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/cogwheel"))
                                .texture("particle", prov.modLoc("block/" + ctx.getName().substring(0, ctx.getName().length() - 9) + "/cogwheel")))
                        .modelFile(prov.models().withExistingParent(ctx.getName(), "greate:block/base/cogwheel")
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
                        .modelFile(prov.models().withExistingParent(ctx.getName() + "_shaftless", "greate:block/base/large_cogwheel_shaftless")
                                .texture("4", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/large_cogwheel"))
                                .texture("particle", prov.modLoc("block/" + ctx.getName().substring(6, ctx.getName().length() - 9) + "/large_cogwheel")))
                        .modelFile(prov.models().withExistingParent(ctx.getName(), "greate:block/base/large_cogwheel")
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
}
