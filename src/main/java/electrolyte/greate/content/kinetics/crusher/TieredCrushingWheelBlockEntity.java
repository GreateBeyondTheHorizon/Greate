package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredCrushingWheelBlockEntity extends CrushingWheelBlockEntity implements ITieredKineticBlockEntity {

    private int tier;

    public TieredCrushingWheelBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        tier = ((TieredCrushingWheelBlock) state.getBlock()).getTier();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
    }
}
