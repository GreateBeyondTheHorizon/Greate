package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.base.TieredKineticEffectHandler;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredKineticBlockEntity extends KineticBlockEntity implements ITieredKineticBlockEntity {

    private double networkMaxCapacity;
    protected TIER tier;
    public TieredKineticEffectHandler effects;

    public TieredKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.tier = ((ITieredBlock) state.getBlock()).getTier();
        effects = new TieredKineticEffectHandler(this);
    }

    @Override
    public double getMaxCapacity() {
        return GConfigUtility.getMaxCapacityFromTier(tier);
    }

    public TIER getTier() {
        return tier;
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if(compound.contains("Network")) {
            CompoundTag networkTag = compound.getCompound("Network");
            this.networkMaxCapacity = networkTag.getDouble("MaxCapacity");
        }
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
    public void tick() {
        effects.tick();
        super.tick();
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
