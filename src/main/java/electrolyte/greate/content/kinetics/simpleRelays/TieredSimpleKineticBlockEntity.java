package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import electrolyte.greate.GreateValues.TIER;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredSimpleKineticBlockEntity extends SimpleKineticBlockEntity implements ITieredKineticBlockEntity {

    private TIER tier;

    public TieredSimpleKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        tier = ((ITieredBlock) state.getBlock()).getTier();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity);
    }
}
