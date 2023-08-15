package electrolyte.greate.content.decoration.girder;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

public class GreateGirderBlockStateGenerator {

    public static void blockStateWithShaft(DataGenContext<Block, TieredGirderEncasableBlock> c, RegistrateBlockstateProvider p) {
        MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());

        builder.part()
                .modelFile(p.models().withExistingParent(c.getName(), p.modLoc("block/base/metal_girder/block")))
                .rotationY(90)
                .addModel()
                .condition(TieredGirderEncasableBlock.HORIZONTAL_AXIS, Axis.X)
                .end();

        builder.part()
                .modelFile(p.models().withExistingParent(c.getName(), p.modLoc("block/base/metal_girder/block")))
                .rotationY(0)
                .addModel()
                .condition(TieredGirderEncasableBlock.HORIZONTAL_AXIS, Axis.Z)
                .end();

        builder.part()
                .modelFile(p.models().withExistingParent(c.getName() + "_top", p.modLoc("block/base/metal_girder/block_top")))
                .addModel()
                .condition(TieredGirderEncasableBlock.TOP, true)
                .end();

        builder.part()
                .modelFile(p.models().withExistingParent(c.getName() + "_bottom", p.modLoc("block/base/metal_girder/block_bottom")))
                .addModel()
                .condition(TieredGirderEncasableBlock.BOTTOM, true)
                .end();
    }
}
