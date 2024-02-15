package electrolyte.greate.content.kinetics.gearbox;

import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredGearboxBlockEntity extends GearboxBlockEntity implements ITieredKineticBlockEntity {

    private int tier;

    public TieredGearboxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        tier = ((TieredGearboxBlock) state.getBlock()).getTier();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
    }
}
