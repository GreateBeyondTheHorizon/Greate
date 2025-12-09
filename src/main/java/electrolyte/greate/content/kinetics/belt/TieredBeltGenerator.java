package electrolyte.greate.content.kinetics.belt;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltGenerator;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

import static electrolyte.greate.foundation.data.GreateBlockStateGen.CUTOUT;
import static electrolyte.greate.foundation.data.GreateBlockStateGen.CUTOUT_MIPPED;

public class TieredBeltGenerator extends BeltGenerator {

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        Boolean casing = state.getValue(BeltBlock.CASING);
        String beltMaterial = ((TieredBeltBlock) ctx.get()).getBeltMaterial().getName();

        if (!casing)
            return new UncheckedModelFile(prov.modLoc("block/" + beltMaterial + "_belt_particle"));

        BeltPart part = state.getValue(BeltBlock.PART);
        Direction direction = state.getValue(BeltBlock.HORIZONTAL_FACING);
        BeltSlope slope = state.getValue(BeltBlock.SLOPE);
        boolean downward = slope == BeltSlope.DOWNWARD;
        boolean diagonal = slope == BeltSlope.UPWARD || downward;
        boolean vertical = slope == BeltSlope.VERTICAL;
        boolean pulley = part == BeltPart.PULLEY;
        boolean sideways = slope == BeltSlope.SIDEWAYS;
        boolean negative = direction.getAxisDirection() == AxisDirection.NEGATIVE;

        if (!casing && pulley)
            part = BeltPart.MIDDLE;

        if ((vertical && negative || downward || sideways && negative) && part != BeltPart.MIDDLE && !pulley)
            part = part == BeltPart.END ? BeltPart.START : BeltPart.END;

        if (!casing && vertical)
            slope = BeltSlope.HORIZONTAL;
        if (casing && vertical)
            slope = BeltSlope.SIDEWAYS;

        String path = "block/belt" + (casing ? "_casing/" : "/");
        String slopeName = slope.getSerializedName();
        String partName = part.getSerializedName();

        if (diagonal)
            slopeName = "diagonal";

        ResourceLocation location = prov.modLoc(path + slopeName + "_" + partName);
        if(casing) location = prov.modLoc("block/belt_casing_" + slopeName + "_" + partName);
        return new UncheckedModelFile(location);
    }

    public <T extends Block> void generateModel(DataGenContext<Block, T> c, RegistrateBlockstateProvider p) {
        TieredBeltBlock beltBlock = (TieredBeltBlock) c.get();
        String beltMaterial = beltBlock.getBeltMaterial().getName();

        p.models().withExistingParent(beltMaterial + "_belt_diagonal_end", Create.asResource("block/belt/diagonal_end"))
                .texture("0", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_diagonal_end", Create.asResource("block/belt/diagonal_end"))
                .texture("0", p.modLoc("block/belt_overlay/empty"))
                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .renderType(CUTOUT);

        p.models().withExistingParent(beltMaterial + "_belt_diagonal_middle", Create.asResource("block/belt/diagonal_middle"))
                .texture("0", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_diagonal_middle", Create.asResource("block/belt/diagonal_middle"))
                .texture("0", p.modLoc("block/belt_overlay/empty"))
                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .renderType(CUTOUT);

        p.models().withExistingParent(beltMaterial + "_belt_diagonal_start", Create.asResource("block/belt/diagonal_start"))
                .texture("0", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_diagonal_start", Create.asResource("block/belt/diagonal_start"))
                .texture("0", p.modLoc("block/belt_overlay/empty"))
                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                .renderType(CUTOUT);


        p.models().withExistingParent(beltMaterial + "_belt_end", Create.asResource("block/belt/end"))
                .texture("0", p.modLoc("block/" + beltMaterial + "/belt"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_end", Create.asResource("block/belt/end"))
                .texture("0", p.modLoc("block/belt_overlay/empty"))
                .renderType(CUTOUT);

        p.models().withExistingParent(beltMaterial + "_belt_end_bottom", Create.asResource("block/belt/end_bottom"))
                .texture("1", p.modLoc("block/" + beltMaterial + "/belt_offset"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_end_bottom", Create.asResource("block/belt/end_bottom"))
                .texture("1", p.modLoc("block/belt_overlay/empty"))
                .renderType(CUTOUT);


        p.models().withExistingParent(beltMaterial + "_belt_middle", Create.asResource("block/belt/middle"))
                .texture("0", p.modLoc("block/" + beltMaterial + "/belt"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_middle", Create.asResource("block/belt/middle"))
                .texture("0", p.modLoc("block/belt_overlay/empty"))
                .renderType(CUTOUT);

        p.models().withExistingParent(beltMaterial + "_belt_middle_bottom", Create.asResource("block/belt/middle_bottom"))
                .texture("1", p.modLoc("block/" + beltMaterial + "/belt_offset"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_middle_bottom", Create.asResource("block/belt/middle_bottom"))
                .texture("1", p.modLoc("block/belt_overlay/empty"))
                .renderType(CUTOUT);


        p.models().withExistingParent(beltMaterial + "_belt_particle", Create.asResource("block/belt/particle"))
                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt"));


        p.models().withExistingParent(beltMaterial + "_belt_start", Create.asResource("block/belt/start"))
                .texture("0", p.modLoc("block/" + beltMaterial + "/belt"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_start", Create.asResource("block/belt/start"))
                .texture("0", p.modLoc("block/belt_overlay/empty"))
                .renderType(CUTOUT);

        p.models().withExistingParent(beltMaterial + "_belt_start_bottom", Create.asResource("block/belt/start_bottom"))
                .texture("1", p.modLoc("block/" + beltMaterial + "/belt_offset"))
                .renderType(CUTOUT_MIPPED);

        p.models().withExistingParent("belt_overlay_start_bottom", Create.asResource("block/belt/start_bottom"))
                .texture("1", p.modLoc("block/belt_overlay/empty"))
                .renderType(CUTOUT);


        p.getVariantBuilder(c.getEntry()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(getModel(c, p, state))
                .rotationX((getXRotation(state) + 360) % 360)
                .rotationY((getYRotation(state) + 360) % 360)
                .build());
    }
}
