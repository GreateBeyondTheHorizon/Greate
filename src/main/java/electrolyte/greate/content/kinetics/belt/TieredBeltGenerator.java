package electrolyte.greate.content.kinetics.belt;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class TieredBeltGenerator {

    protected int getXRotation(BlockState state) {
        Direction direction = state.getValue(BeltBlock.HORIZONTAL_FACING);
        BeltSlope slope = state.getValue(BeltBlock.SLOPE);
        return slope == BeltSlope.VERTICAL ? 90
                : slope == BeltSlope.SIDEWAYS && direction.getAxisDirection() == AxisDirection.NEGATIVE ? 180 : 0;
    }

    protected int getYRotation(BlockState state) {
        Boolean casing = state.getValue(BeltBlock.CASING);
        BeltSlope slope = state.getValue(BeltBlock.SLOPE);

        boolean flip = slope == BeltSlope.UPWARD;
        boolean rotate = casing && slope == BeltSlope.VERTICAL;
        Direction direction = state.getValue(BeltBlock.HORIZONTAL_FACING);
        return horizontalAngle(direction) + (flip ? 180 : 0) + (rotate ? 90 : 0);
    }

    protected int horizontalAngle(Direction direction) {
        if (direction.getAxis()
                .isVertical())
            return 0;
        return (int) direction.toYRot();
    }

    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        Boolean casing = state.getValue(BeltBlock.CASING);

        if (!casing)
            return new UncheckedModelFile(prov.modLoc("block/" + ctx.getName() + "_particle"));

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

        //todo: check
        //String path = "block/" + ctx.getName() + (casing ? "_casing/" : "/");
        String path = "block/belt" + (casing ? "_casing/" : "/");
        String slopeName = slope.getSerializedName();
        String partName = part.getSerializedName();

        if (diagonal)
            slopeName = "diagonal";

        ResourceLocation location = prov.modLoc(path + slopeName + "_" + partName);
        if(casing) return new UncheckedModelFile(Create.asResource(path + slopeName + "_" + partName));
        return new UncheckedModelFile(location);
    }

    public <T extends TieredBeltBlock> void generate(DataGenContext<Block, T> c, RegistrateBlockstateProvider p) {
        ItemStack shaft = TieredBeltBlock.BELTS.get(c.get()).get(0);
        String shaftMaterial = shaft.toString().substring(2, shaft.toString().length() - 6);
        ItemStack shaft1 = TieredBeltBlock.BELTS.get(c.get()).get(1);
        String shaftMaterial1 = shaft1.toString().substring(2, shaft1.toString().length() - 6);
        String beltMaterial = c.getName().substring(0, c.getName().length() - 5);
        p.getVariantBuilder(c.getEntry())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(p.models().withExistingParent(c.getName() + "_" + shaftMaterial + "_pulley", Create.asResource("block/belt_pulley"))
                                .texture("0", p.modLoc("block/" + shaftMaterial + "/axis"))
                                .texture("1", p.modLoc("block/" + shaftMaterial + "/axis_top")))
                        .modelFile(p.models().withExistingParent(c.getName() + "_" + shaftMaterial1 + "_pulley", Create.asResource("block/belt_pulley"))
                                .texture("0", p.modLoc("block/" + shaftMaterial1 + "/axis"))
                                .texture("1", p.modLoc("block/" + shaftMaterial1 + "/axis_top")))

                        .modelFile(p.models().withExistingParent(c.getName() + "_diagonal_end", Create.asResource("block/belt/diagonal_end"))
                                .texture("0", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal")))
                        .modelFile(p.models().withExistingParent(c.getName() + "_diagonal_middle", Create.asResource("block/belt/diagonal_middle"))
                                .texture("0", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal")))
                        .modelFile(p.models().withExistingParent(c.getName() + "_diagonal_start", Create.asResource("block/belt/diagonal_start"))
                                .texture("0", p.modLoc("block/" + beltMaterial + "/belt_diagonal"))
                                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt_diagonal")))

                        .modelFile(p.models().withExistingParent(c.getName() + "_end", Create.asResource("block/belt/end"))
                                .texture("0", p.modLoc("block/" + beltMaterial + "/belt")))
                        .modelFile(p.models().withExistingParent(c.getName() + "_end_bottom", Create.asResource("block/belt/end_bottom"))
                                .texture("1", p.modLoc("block/" + beltMaterial + "/belt_offset")))

                        .modelFile(p.models().withExistingParent(c.getName() + "_middle", Create.asResource("block/belt/middle"))
                                .texture("0", p.modLoc("block/" + beltMaterial + "/belt")))
                        .modelFile(p.models().withExistingParent(c.getName() + "_middle_bottom", Create.asResource("block/belt/middle_bottom"))
                                .texture("1", p.modLoc("block/" + beltMaterial + "/belt_offset")))

                        .modelFile(p.models().withExistingParent(c.getName() + "_particle", Create.asResource("block/belt/particle"))
                                .texture("particle", p.modLoc("block/" + beltMaterial + "/belt")))

                        .modelFile(p.models().withExistingParent(c.getName() + "_start", Create.asResource("block/belt/start"))
                                .texture("0", p.modLoc("block/" + beltMaterial + "/belt")))
                        .modelFile(p.models().withExistingParent(c.getName() + "_start_bottom", Create.asResource("block/belt/start_bottom"))
                                .texture("1", p.modLoc("block/" + beltMaterial + "/belt_offset")))

                        .modelFile(getModel(c, p, state))
                        .rotationX((getXRotation(state) + 360) % 360)
                        .rotationY((getYRotation(state) + 360) % 360)
                        .build());
    }
}
