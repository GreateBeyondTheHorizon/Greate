package electrolyte.greate.compat.gtceu.client.data.models;

import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.registry.registrate.provider.GTBlockstateProvider;
import com.gregtechceu.gtceu.client.model.machine.MachineRenderState;
import com.gregtechceu.gtceu.data.model.builder.MachineModelBuilder;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.GreateBlockStateGen.CUTOUT_MIPPED;

public class TieredSawGenerator extends SawGenerator {

    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, GTBlockstateProvider prov, MachineRenderState state) {
        String path = "block/" + ctx.getName() + "_";
        String orientation = state.getValue(SawBlock.FACING).getAxis().isVertical() ? "vertical" : "horizontal";
        return new UncheckedModelFile(prov.modLoc(path + orientation));
    }

    public <T extends Block> void generateModel(DataGenContext<Block, T> ctx, GTBlockstateProvider prov, MachineModelBuilder<BlockModelBuilder> builder, int tier) {
        String material = TM[tier].getName();
        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_blade_horizontal_active", Create.asResource("block/mechanical_saw/blade_horizontal_active"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"))).renderType(CUTOUT_MIPPED);

        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_blade_horizontal_inactive", Create.asResource("block/mechanical_saw/blade_horizontal_inactive"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/static_saw"))
                .texture("stonecutter_saw_reversed", prov.modLoc("block/" + material + "/static_saw"))).renderType(CUTOUT_MIPPED);

        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_blade_horizontal_reversed", Create.asResource("block/mechanical_saw/blade_horizontal_reversed"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"))).renderType(CUTOUT_MIPPED);

        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_blade_vertical_active", Create.asResource("block/mechanical_saw/blade_vertical_active"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"))).renderType(CUTOUT_MIPPED);

        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_blade_vertical_inactive", Create.asResource("block/mechanical_saw/blade_vertical_inactive"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/static_saw"))
                .texture("stonecutter_saw_reversed", prov.modLoc("block/" + material + "/static_saw"))).renderType(CUTOUT_MIPPED);

        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_blade_vertical_reversed", Create.asResource("block/mechanical_saw/blade_vertical_reversed"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"))).renderType(CUTOUT_MIPPED);

        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_horizontal", Create.asResource("block/mechanical_saw/horizontal"))).renderType(CUTOUT_MIPPED);

        prov.models().nested().parent(prov.models().withExistingParent(ctx.getName() + "_vertical", Create.asResource("block/mechanical_saw/vertical"))
                .texture("andesite_casing_short", prov.modLoc("block/saw_side"))).renderType(CUTOUT_MIPPED);

        builder.forAllStatesExcept(state -> ConfiguredModel.builder()
            .modelFile(getModel(ctx, prov, state))
            .rotationX((getXRotation(state) + 360) % 360)
            .rotationY((getYRotation(state) + 360) % 360)
            .build(), GTMachineModelProperties.RECIPE_LOGIC_STATUS);
    }

    private int getXRotation(MachineRenderState state) {
        return state.getValue(SawBlock.FACING) == Direction.DOWN ? 180 : 0;
    }

    private int getYRotation(MachineRenderState state) {
        Direction facing = state.getValue(SawBlock.FACING);
		boolean axisAlongFirst = state.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE);
		if (facing.getAxis()
			.isVertical())
			return (axisAlongFirst ? 270 : 0) + (state.getValue(SawBlock.FLIPPED) ? 180 : 0);
		return horizontalAngle(facing);
    }
}
