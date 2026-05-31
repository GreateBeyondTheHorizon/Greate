package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.machine.trait.MachineTraitType;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.TieredMillstoneMachineBlockEntity;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;

@MethodsReturnNonnullByDefault
public class KineticMachineTrait extends MachineTrait {

    public static final MachineTraitType<KineticMachineTrait> TYPE = new MachineTraitType<>(KineticMachineTrait.class);

    @SyncToClient
    @Getter
    private KineticBlockEntity kineticBlockEntity;
    private TickableSubscription kineticsSub;

    @Override
    public MachineTraitType<?> getTraitType() {
        return TYPE;
    }

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        kineticBlockEntity = createKineticBlockEntity();
        kineticBlockEntity.setLevel(getLevel());
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

    protected TieredMillstoneMachineBlockEntity createKineticBlockEntity() {
        return new TieredMillstoneMachineBlockEntity(getMachine().getDefinition().getBlockEntityType(), getBlockPos(), getMachine().getBlockState());
    }

    public void updateKineticsSubscription() {
        kineticsSub = subscribeServerTick(kineticsSub, () -> {
            kineticBlockEntity.tick();
            //speed = kineticBlockEntity.getSpeed();
            //if(kineticBlockEntity.hasNetwork()) {
            //    networkTag = new CompoundTag();
            //    networkTag.putLong("Id", ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getNetwork());
            //    networkTag.putFloat("Stress", ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getStress());
            //    networkTag.putFloat("Capacity", ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getCapacity());
            //    networkTag.putInt("Size", ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getNetworkSize());
            //    networkTag.putFloat("AddedStress", ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getLastStressApplied());
            //    networkTag.putFloat("AddedCapacity", ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getLastCapacityProvided());
            //    overStressed = ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getCapacity() < ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getStress() && StressImpact.isEnabled();
            //}
            //if(kineticBlockEntity.hasSource()) {
            //    source = ((MixinKineticBlockEntityAccessor) kineticBlockEntity).getSource();
            //}
            getSyncDataHolder().markClientSyncFieldDirty("kineticBlockEntity");
        });
    }
}
