package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.mixin.kinetictrait.MixinKineticBlockEntityAccessor;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.Objects;

@MethodsReturnNonnullByDefault
public class KineticMachineTrait extends MachineTrait {

    @SyncToClient
    @Getter
    protected KineticBlockEntity kineticBlockEntity;
    private TickableSubscription kineticsSub;

    @SyncToClient
    @SaveField
    @Getter
    private float speed = 0;

    @SyncToClient
    @SaveField
    @Getter
    private float capacity = 0;

    @SyncToClient
    @SaveField
    private int size = 0;

    @SyncToClient
    @SaveField
    @Nullable
    private Long networkId = null;

    @SyncToClient
    @SaveField
    private float stress = 0;

    @SyncToClient
    @SaveField
    private float lastStressApplied = 0;

    @SyncToClient
    @SaveField
    private float lastCapacityProvided = 0;

    @SyncToClient
    @SaveField
    @Nullable
    private BlockPos sourcePos = null;

    public KineticMachineTrait(KineticBlockEntity kineticBlockEntity) {
        this.kineticBlockEntity = kineticBlockEntity;
    }

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        kineticBlockEntity.setLevel(getLevel());
        kineticBlockEntity.setSpeed(speed);
        ((MixinKineticBlockEntityAccessor) kineticBlockEntity).setCapacity(capacity);
        ((MixinKineticBlockEntityAccessor) kineticBlockEntity).setSize(size);
        ((MixinKineticBlockEntityAccessor) kineticBlockEntity).setNetworkId(networkId);
        ((MixinKineticBlockEntityAccessor) kineticBlockEntity).setStress(stress);
        ((MixinKineticBlockEntityAccessor) kineticBlockEntity).setStressApplied(lastStressApplied);
        ((MixinKineticBlockEntityAccessor) kineticBlockEntity).setCapacityProvided(lastCapacityProvided);
        ((MixinKineticBlockEntityAccessor) kineticBlockEntity).setSourcePos(sourcePos);
        kineticBlockEntity.initialize();

        if(getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateKineticsSubscription));
        }
    }

    @Override
    public void onMachineUnload() {
        if(kineticsSub != null) {
            kineticsSub.unsubscribe();
            kineticsSub = null;
        }
        if(getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, () -> RotationPropagator.handleRemoved(getLevel(), getBlockPos(), kineticBlockEntity)));
        }
        super.onMachineUnload();
    }

    @Override
    public void onMachineDestroyed() {
        this.getKineticBlockEntity().remove();
        super.onMachineDestroyed();
    }

    public void updateKineticsSubscription() {
        kineticsSub = subscribeServerTick(kineticsSub, () -> {
            MixinKineticBlockEntityAccessor kbeAccessor = (MixinKineticBlockEntityAccessor) kineticBlockEntity;
            if(this.kineticBlockEntity.network == null) {
                kbeAccessor.setCapacityProvided(0);
                kbeAccessor.setStressApplied(0);
            }
            kineticBlockEntity.tick();

            if(speed != kineticBlockEntity.getSpeed()) {
                speed = kineticBlockEntity.getSpeed();
                this.getSyncDataHolder().markClientSyncFieldDirty("speed");
            }
            if(capacity != kbeAccessor.getCapacity()) {
                capacity = kbeAccessor.getCapacity();
                this.getSyncDataHolder().markClientSyncFieldDirty("capacity");
            }
            if(size != kbeAccessor.getSize()) {
                size = kbeAccessor.getSize();
                this.getSyncDataHolder().markClientSyncFieldDirty("size");
            }
            if(!Objects.equals(networkId, kineticBlockEntity.network)) {
                networkId = kineticBlockEntity.network;
                this.getSyncDataHolder().markClientSyncFieldDirty("networkId");
            }

            if(stress != kbeAccessor.getStress()) {
                stress = kbeAccessor.getStress();
                this.getSyncDataHolder().markClientSyncFieldDirty("stress");
            }

            if(lastStressApplied != kbeAccessor.getStressApplied()) {
                lastStressApplied = kbeAccessor.getStressApplied();
                this.getSyncDataHolder().markClientSyncFieldDirty("lastStressApplied");
            }

            if(lastCapacityProvided != kbeAccessor.getCapacityProvided()) {
                lastCapacityProvided = kbeAccessor.getCapacityProvided();
                this.getSyncDataHolder().markClientSyncFieldDirty("lastCapacityProvided");
            }

            if(!Objects.equals(sourcePos, kineticBlockEntity.source)) {
                sourcePos = kineticBlockEntity.source;
                this.getSyncDataHolder().markClientSyncFieldDirty("sourcePos");
            }

            getSyncDataHolder().markClientSyncFieldDirty("kineticBlockEntity");
        });
    }
}
