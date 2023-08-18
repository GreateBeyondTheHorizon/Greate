package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.IRotate.SpeedLevel;
import com.simibubi.create.content.kinetics.base.IRotate.StressImpact;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class TieredGeneratingKineticBlockEntity extends TieredKineticBlockEntity {

    public boolean reActivateSource;

    public TieredGeneratingKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    protected void notifyStressCapacityChange(float capacity) {
        getOrCreateNetwork().updateCapacityFor(this, capacity);
    }

    @Override
    public void removeSource() {
        if(hasSource() && isSource()) {
            reActivateSource = true;
        }
        super.removeSource();
    }

    @Override
    public void setSource(BlockPos source) {
        super.setSource(source);
        BlockEntity be = level.getBlockEntity(source);
        if(!(be instanceof TieredKineticBlockEntity sourceBE)) return;
        if(reActivateSource && Math.abs(sourceBE.getSpeed()) >= Math.abs(getGeneratedSpeed())) {
            reActivateSource = false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(reActivateSource) {
            updateGeneratedRotation();
            reActivateSource = false;
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if (!StressImpact.isEnabled()) return added;

        float stressBase = calculateAddedStressCapacity();
        if (Mth.equal(stressBase, 0)) return added;

        Lang.translate("gui.goggles.generator_stats")
                .forGoggles(tooltip);
        Lang.translate("tooltip.capacityProvided")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        float speed = getTheoreticalSpeed();
        if (speed != getGeneratedSpeed() && speed != 0) {
            stressBase *= getGeneratedSpeed() / speed;
        }
        speed = Math.abs(speed);

        float stressTotal = stressBase * speed;

        Lang.number(stressTotal)
                .translate("generic.unit.stress")
                .style(ChatFormatting.AQUA)
                .space()
                .add(Lang.translate("gui.goggles.at_current_speed")
                        .style(ChatFormatting.DARK_GRAY))
                .forGoggles(tooltip, 1);

        return true;
    }

    public void updateGeneratedRotation() {
        float speed = getGeneratedSpeed();
        float prevSpeed = this.speed;

        if (level == null || level.isClientSide) return;

        if (prevSpeed != speed) {
            if (!hasSource()) {
                SpeedLevel levelBefore = SpeedLevel.of(this.speed);
                SpeedLevel levelafter = SpeedLevel.of(speed);
                if (levelBefore != levelafter)
                    effects.queueRotationIndicators();
            }
            applyNewSpeed(prevSpeed, speed);
        }

        if (hasNetwork() && speed != 0) {
            KineticNetwork network = getOrCreateNetwork();
            notifyStressCapacityChange(calculateAddedStressCapacity());
            getOrCreateNetwork().updateStressFor(this, calculateStressApplied());
            network.updateStress();
        }

        onSpeedChanged(prevSpeed);
        sendData();
    }

    public void applyNewSpeed(float prevSpeed, float speed) {
        if (speed == 0) {
            if (hasSource()) {
                notifyStressCapacityChange(0);
                getOrCreateNetwork().updateStressFor(this, calculateStressApplied());
                return;
            }
            detachKinetics();
            setSpeed(0);
            setNetwork(null);
            return;
        }

        if (prevSpeed == 0) {
            setSpeed(speed);
            setNetwork(createNetworkID());
            attachKinetics();
            return;
        }

        if(hasSource()) {

            if(Math.abs(prevSpeed) >= Math.abs(speed)) {
                if(Math.signum(prevSpeed) != Math.signum(speed))
                    level.destroyBlock(worldPosition, true);
                return;
            }

            detachKinetics();
            setSpeed(speed);
            source = null;
            setNetwork(createNetworkID());
            attachKinetics();
            return;
        }

        detachKinetics();
        setSpeed(speed);
        attachKinetics();
    }

    public Long createNetworkID() {
        return worldPosition.asLong();
    }
}
