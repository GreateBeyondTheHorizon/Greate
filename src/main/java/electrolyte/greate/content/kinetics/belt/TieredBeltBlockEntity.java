package electrolyte.greate.content.kinetics.belt;

import com.simibubi.create.content.kinetics.belt.*;
import com.simibubi.create.content.kinetics.belt.transport.BeltMovementHandler;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TieredBeltBlockEntity extends BeltBlockEntity implements ITieredKineticBlockEntity {

    private int tier;

    public TieredBeltBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.tier = ((TieredBeltBlock) state.getBlock()).getTier();
    }

    @Override
    public void tick() {
        if(beltLength == 0) {
            TieredBeltBlock.initBelt(level, worldPosition);
        }
        super.tick();
        if(!(level.getBlockState(worldPosition).getBlock() instanceof TieredBeltBlock)) return;
        initializeItemHandler();
        if(!isController()) return;

        invalidateRenderBoundingBox();
        getInventory().tick();

        if(getSpeed() == 0) return;
        if(passengers == null) {
            passengers = new HashMap<>();
        }

        List<Entity> toRemove = new ArrayList<>();
        passengers.forEach((entity, info) -> {
            boolean canBeTransported = BeltMovementHandler.canBeTransported(entity);
            boolean leftTheBelt = info.getTicksSinceLastCollision() > ((getBlockState().getValue(BeltBlock.SLOPE) != BeltSlope.HORIZONTAL) ? 3 : 1);
            if(!canBeTransported || leftTheBelt) {
                toRemove.add(entity);
                return;
            }
            info.tick();
            BeltMovementHandler.transportEntity(this, entity, info);
        });
        toRemove.forEach(passengers::remove);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("Tier", this.tier);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        this.tier = compound.getInt("Tier");
        beltLength = compound.getInt("Length");
    }

    @Override
    public boolean applyColor(DyeColor colorIn) {
        if(colorIn == null) {
            if(color.isEmpty()) return false;
        } else if(color.isPresent() && color.get() == colorIn) return false;
        if(level.isClientSide()) return true;

        for(BlockPos pos : TieredBeltBlock.getBeltChain(level, getController())) {
            BeltBlockEntity bbe = BeltHelper.getSegmentBE(level, pos);
            if(bbe == null) continue;
            bbe.color = Optional.ofNullable(colorIn);
            bbe.setChanged();
            bbe.sendData();
        }
        return true;
    }

    @Override
    public boolean hasPulley() {
        if(!(getBlockState().getBlock() instanceof TieredBeltBlock)) return false;
        return getBlockState().getValue(BeltBlock.PART) != BeltPart.MIDDLE;
    }

    @Override
    protected Direction getBeltFacing() {
        return super.getBeltFacing();
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, this.getTier(), capacity, stress);
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        notifyUpdate();
    }

}
