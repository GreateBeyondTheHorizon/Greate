package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredSimpleKineticBlockEntity extends SimpleKineticBlockEntity implements ITieredKineticBlockEntity {

    private TIER tier;
    private double networkMaxCapacity;

    public TieredSimpleKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        tier = ((ITieredBlock) state.getBlock()).getTier();
    }

    @Override
    public double getMaxCapacity() {
        return GConfigUtility.getMaxCapacityFromTier(tier);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        if(compound.contains("Network")) {
            CompoundTag networkTag = compound.getCompound("Network");
            this.networkMaxCapacity = networkTag.getDouble("MaxCapacity");
        }
        super.read(compound, clientPacket);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if(hasNetwork()) {
            CompoundTag networkTag = compound.getCompound("Network");
            networkTag.putDouble("MaxCapacity", this.networkMaxCapacity);
        }
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize, double networkMaxCapacity) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        this.networkMaxCapacity = networkMaxCapacity;
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity);
    }
}
