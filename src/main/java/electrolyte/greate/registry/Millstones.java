package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import net.minecraft.world.level.material.MaterialColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static electrolyte.greate.Greate.REGISTRATE;

public class Millstones {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredMillstoneBlock> TEST_MILLSTONE = REGISTRATE
            .block("test_millstone", TieredMillstoneBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .transform(BlockStressDefaults.setImpact(4.0))
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {}
}
