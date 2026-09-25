package electrolyte.greate.content.kinetics.chainConveyor;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorBlock;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static electrolyte.greate.GreateValues.TM;

public class TieredChainConveyorBlock extends ChainConveyorBlock implements ITieredBlock {

    private int tier;

    public TieredChainConveyorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public Material getMaterial() {
        return TM[tier];
    }

    @Override
    public BlockEntityType<? extends ChainConveyorBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_CHAIN_CONVEYOR.get();
    }
}
