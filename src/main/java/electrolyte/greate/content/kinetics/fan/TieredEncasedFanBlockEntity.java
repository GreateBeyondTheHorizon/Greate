package electrolyte.greate.content.kinetics.fan;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TieredEncasedFanBlockEntity extends EncasedFanBlockEntity implements IAirCurrentSource, ITieredKineticBlockEntity {

    public TieredEncasedFanBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.airCurrent = new TieredAirCurrent(this, ((TieredEncasedFanBlock) state.getBlock()).getTier());
    }

    @Override
    public boolean renderNormally() {
        return false;
    }
}
