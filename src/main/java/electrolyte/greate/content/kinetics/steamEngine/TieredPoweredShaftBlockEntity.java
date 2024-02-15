package electrolyte.greate.content.kinetics.steamEngine;

import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredPoweredShaftBlockEntity extends PoweredShaftBlockEntity implements ITieredKineticBlockEntity {

    private int tier;

    public TieredPoweredShaftBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.tier = ((ITieredBlock) state.getBlock()).getTier();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
    }
}
