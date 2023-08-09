package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredKineticBlockEntity extends SimpleKineticBlockEntity implements ITieredKineticBlockEntity {

    private double networkMaxCapacity;
    private double networkCurrentCapacity;
    private GreateEnums.TIER tier;

    public TieredKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.tier = ((ITieredBlock) state.getBlock()).getTier();
    }

    @Override
    public double getShaftMaxCapacity() {
        return tier.getStressCapacity();
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if(compound.contains("Network")) {
            CompoundTag networkTag = compound.getCompound("Network");
            this.networkMaxCapacity = networkTag.getDouble("MaxCapacity");
            this.networkCurrentCapacity = networkTag.getDouble("Capacity");
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
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize, double networkMaxCapacity) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        this.networkMaxCapacity = networkMaxCapacity;
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("gui.goggles.kinetic_stats").forGoggles(tooltip);
        Lang.builder(Greate.MOD_ID).translate("tooltip.capacity").style(ChatFormatting.GRAY).forGoggles(tooltip);
        Lang.number(this.networkCurrentCapacity).style(ChatFormatting.AQUA).add(Lang.text("su")).space().add(Lang.text("/").space().add(Lang.number(tier.getStressCapacity())).add(Lang.text("su").space().add(Lang.text("at current shaft tier").style(ChatFormatting.DARK_GRAY)))).forGoggles(tooltip, 1);
        return true;
    }
}
