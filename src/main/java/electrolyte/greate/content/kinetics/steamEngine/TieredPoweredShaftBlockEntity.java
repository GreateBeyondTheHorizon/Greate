package electrolyte.greate.content.kinetics.steamEngine;

import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredPoweredShaftBlockEntity extends PoweredShaftBlockEntity implements ITieredKineticBlockEntity {

    private double networkMaxCapacity;
    private TIER tier;

    public TieredPoweredShaftBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.tier = ((ITieredBlock) state.getBlock()).getTier();
    }

    @Override
    public double getMaxCapacity() {
        return tier.getStressCapacity();
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (compound.contains("Network")) {
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
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize, double networkMaxCapacity) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        this.networkMaxCapacity = networkMaxCapacity;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if(!tooltip.isEmpty()) {
            Lang.builder().space();
        } else {
            Lang.translate("gui.goggles.kinetic_stats").forGoggles(tooltip);
        }
        Lang.builder(Greate.MOD_ID).translate("tooltip.capacity").style(ChatFormatting.GRAY).forGoggles(tooltip);
        Lang.number(capacity).style(ChatFormatting.AQUA).add(Lang.text("su")).space().add(Lang.text("/").space().add(Lang.number(tier.getStressCapacity())).add(Lang.text("su").space().add(Lang.text("at current shaft tier").style(ChatFormatting.DARK_GRAY)))).forGoggles(tooltip, 1);
        return true;
    }
}
