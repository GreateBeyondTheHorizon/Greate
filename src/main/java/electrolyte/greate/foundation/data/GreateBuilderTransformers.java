package electrolyte.greate.foundation.data;

import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.content.kinetics.simpleRelays.TieredEncasedShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;

public class GreateBuilderTransformers {

    public static <B extends TieredEncasedShaftBlock, P>NonNullUnaryOperator<BlockBuilder<B, P>> tieredEncasedShaft(BlockEntry<TieredShaftBlock> shaftBlock, String casing, String shaft, Supplier<CTSpriteShiftEntry> casingShift) {
        return builder -> encasedBase(builder, shaftBlock::get)
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(casingShift.get())))
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
                        (s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
                .blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
                        .getExistingFile(p.modLoc("block/encased_shaft/block_" + casing + "_" + shaft)), true))
                .item()
                .model(AssetLookup.customBlockItemModel("encased_shaft", "item_" + casing + "_" + shaft))
                .build();
    }

    private static <B extends RotatedPillarKineticBlock, P> BlockBuilder<B, P> encasedBase(BlockBuilder<B, P> b, Supplier<ItemLike> drop) {
        return b.initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(BlockStressDefaults.setNoImpact())
                .loot((p, loot) -> p.dropOther(loot, drop.get()));
    }
}
