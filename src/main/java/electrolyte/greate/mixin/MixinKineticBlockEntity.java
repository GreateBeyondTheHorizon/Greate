package electrolyte.greate.mixin;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KineticBlockEntity.class)
public abstract class MixinKineticBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation, IHaveHoveringInformation, ITieredKineticBlockEntity {

    @Shadow(remap = false) public abstract float getSpeed();
    @Shadow(remap = false) protected float speed;
    @Shadow(remap = false) protected float capacity;

    @Shadow(remap = false) public abstract boolean hasNetwork();

    @Unique protected float greate$shaftMaxCapacity;
    @Unique protected float greate$networkMaxCapacity;

    public MixinKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "write", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/SmartBlockEntity;write(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;Z)V"), remap = false)
    private void greate$write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        compound.putFloat("MaxCapacity", getMaxCapacityFromBlock(this.getBlockState().getBlock()));
        if(hasNetwork()) {
            CompoundTag networkTag = compound.getCompound("Network");
            networkTag.putFloat("MaxCapacity", greate$networkMaxCapacity);
            compound.put("Network", networkTag);
        }
    }

    @Inject(method = "read", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/transmission/sequencer/SequencedGearshiftBlockEntity$SequenceContext;fromNBT(Lnet/minecraft/nbt/CompoundTag;)Lcom/simibubi/create/content/kinetics/transmission/sequencer/SequencedGearshiftBlockEntity$SequenceContext;"), remap = false)
    private void greate$read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        greate$shaftMaxCapacity = tag.getFloat("MaxCapacity");
        if(tag.contains("Network")) {
            CompoundTag networkTag = tag.getCompound("Network");
            capacity = networkTag.getFloat("Capacity");
            greate$networkMaxCapacity = networkTag.getFloat("MaxCapacity");
        }
    }

    @Inject(method = "clearKineticInformation", at = @At("RETURN"), remap = false)
    private void greate$clearKineticInformation(CallbackInfo ci) {
        greate$shaftMaxCapacity = Integer.MAX_VALUE;
        greate$networkMaxCapacity = Integer.MAX_VALUE;
    }
}
