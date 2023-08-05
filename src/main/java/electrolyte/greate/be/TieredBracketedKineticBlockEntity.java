package electrolyte.greate.be;

import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.Greate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredBracketedKineticBlockEntity extends BracketedKineticBlockEntity implements ITieredKineticBlockEntity {

    protected float shaftMaxCapacity;
    private float networkMaxCapacity;
    private boolean overCapacity;

    public TieredBracketedKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, float shaftMaxCapacity) {
        super(type, pos, state);
        this.shaftMaxCapacity = shaftMaxCapacity;
    }

    public static class ANDESITE extends TieredBracketedKineticBlockEntity {

        public ANDESITE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(type, pos, state, 512);
        }
    }

    public static class BRASS extends TieredBracketedKineticBlockEntity {
        public BRASS(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(type, pos, state, 1024);
        }
    }

    @Override
    public float getShaftMaxCapacity() {
        return shaftMaxCapacity;
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if(compound.contains("NetworkCapacity")) {
            CompoundTag tag = compound.getCompound("NetworkCapacity");
            tag.putFloat("MaxCapacity", networkMaxCapacity);
            compound.put("NetworkCapacity", tag);
        }
    }

    //todo: when capacity = maxCapacity, capacity field does not update
    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize, float networkMaxCapacity) {
        updateFromNetwork(maxStress, currentStress, networkSize);
        this.networkMaxCapacity = networkMaxCapacity;
        boolean overCapacity = capacity > networkMaxCapacity;
        setChanged();

        if(overCapacity != this.overCapacity) {
            float prevSpeed = getSpeed();
            this.overCapacity = overCapacity;
            onSpeedChanged(prevSpeed);
            sendData();
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("gui.goggles.kinetic_stats").forGoggles(tooltip);
        Lang.builder(Greate.MOD_ID).translate("tooltip.capacity").style(ChatFormatting.GRAY).forGoggles(tooltip);
        Lang.number(capacity).style(ChatFormatting.AQUA).add(Lang.text("su")).space().add(Lang.text("/").space().add(Lang.number(shaftMaxCapacity)).add(Lang.text("su").space().add(Lang.text("at current shaft tier").style(ChatFormatting.DARK_GRAY)))).forGoggles(tooltip, 1);
        return true;
    }
}
