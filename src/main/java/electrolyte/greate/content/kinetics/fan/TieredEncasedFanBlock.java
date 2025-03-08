package electrolyte.greate.content.kinetics.fan;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TieredEncasedFanBlock extends EncasedFanBlock implements IBE<EncasedFanBlockEntity>, ITieredBlock {

    private int tier;
    private PartialModel[] models;

    public TieredEncasedFanBlock(Properties properties, PartialModel... models) {
        super(properties);
        this.models = models;
    }

    @Override
    public BlockEntityType<? extends EncasedFanBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_FAN.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    public PartialModel[] getModels() {
        return models;
    }
}
