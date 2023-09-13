package electrolyte.greate.content.decoration.girder;

import com.simibubi.create.Create;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

public class GreateGirderBlockStateGenerator {

    public static void blockStateWithShaft(DataGenContext<Block, TieredGirderEncasedShaftBlock> c, RegistrateBlockstateProvider p) {
        MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());
        builder.part()
                .modelFile(p.models().withExistingParent(c.getName(), Create.asResource("block/metal_girder_encased_shaft/block")))
                .rotationY(90)
                .addModel()
                .condition(TieredGirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.X)
                .end();

        builder.part()
                .modelFile(p.models().withExistingParent(c.getName(), Create.asResource("block/metal_girder_encased_shaft/block")))
                .rotationY(0)
                .addModel()
                .condition(TieredGirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.Z)
                .end();

        builder.part()
                .modelFile(p.models().withExistingParent(c.getName() + "_top", Create.asResource("block/metal_girder_encased_shaft/block_top")))
                .addModel()
                .condition(TieredGirderEncasedShaftBlock.TOP, true)
                .end();

        builder.part()
                .modelFile(p.models().withExistingParent(c.getName() + "_bottom", Create.asResource("block/metal_girder_encased_shaft/block_bottom")))
                .addModel()
                .condition(TieredGirderEncasedShaftBlock.BOTTOM, true)
                .end();
    }
}
