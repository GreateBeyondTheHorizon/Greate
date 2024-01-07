package electrolyte.greate.content.kinetics.saw;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class TieredSawGenerator extends SawGenerator {

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        String path = "block/" + ctx.getName() + "_";
        String orientation = state.getValue(SawBlock.FACING).getAxis().isVertical() ? "vertical" : "horizontal";
        return new UncheckedModelFile(prov.modLoc(path + orientation));
    }

    public <T extends TieredSawBlock> void generateModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov) {
        String material = ctx.getName().substring(0, ctx.getName().length() - 15);
        prov.models().withExistingParent(ctx.getName() + "_blade_horizontal_active", Create.asResource("block/mechanical_saw/blade_horizontal_active"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"));

        prov.models().withExistingParent(ctx.getName() + "_blade_horizontal_inactive", Create.asResource("block/mechanical_saw/blade_horizontal_inactive"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/static_saw"))
                .texture("stonecutter_saw_reversed", prov.modLoc("block/" + material + "/static_saw"));

        prov.models().withExistingParent(ctx.getName() + "_blade_horizontal_reversed", Create.asResource("block/mechanical_saw/blade_horizontal_reversed"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"));

        prov.models().withExistingParent(ctx.getName() + "_blade_vertical_active", Create.asResource("block/mechanical_saw/blade_vertical_active"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"));

        prov.models().withExistingParent(ctx.getName() + "_blade_vertical_inactive", Create.asResource("block/mechanical_saw/blade_vertical_inactive"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/static_saw"))
                .texture("stonecutter_saw_reversed", prov.modLoc("block/" + material + "/static_saw"));

        prov.models().withExistingParent(ctx.getName() + "_blade_vertical_reversed", Create.asResource("block/mechanical_saw/blade_vertical_reversed"))
                .texture("stonecutter_saw", prov.modLoc("block/" + material + "/saw"));

        prov.models().withExistingParent(ctx.getName() + "_horizontal", Create.asResource("block/mechanical_saw/horizontal"));

        prov.models().withExistingParent(ctx.getName() + "_vertical", Create.asResource("block/mechanical_saw/vertical"));

        prov.getVariantBuilder(ctx.getEntry()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(getModel(ctx, prov, state))
                .rotationX((getXRotation(state) + 360) % 360)
                .rotationY((getYRotation(state) + 360) % 360)
                .build());
    }
}
