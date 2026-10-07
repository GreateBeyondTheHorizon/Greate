package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import electrolyte.greate.content.kinetics.base.KineticBlockEntityAccessor;
import electrolyte.greate.content.kinetics.waterWheel.WaterWheelBreaker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(KineticBlockEntity.class)
public abstract class MixinKineticBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation, KineticBlockEntityAccessor {

    @Shadow
    @Nullable
    public Long network;

    public MixinKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Unique private int greate_waterWheelCount = 0;
    @Unique private int greate_largeWaterWheelCount = 0;


    @Unique
    @Override
    public int greate_getLargeWaterWheelCount() {
        return greate_largeWaterWheelCount;
    }

    @Unique
    @Override
    public int greate_getWaterWheelCount() {
        return greate_waterWheelCount;
    }

    @Unique
    @Override
    public void greate_setLargeWaterWheelCount(int count) {
        this.greate_largeWaterWheelCount = count;
        this.sendData();
    }

    @Unique
    @Override
    public void greate_setWaterWheelCount(int count) {
        this.greate_waterWheelCount = count;
        this.sendData();
    }

    @Inject(method = "read", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getCompound(Ljava/lang/String;)Lnet/minecraft/nbt/CompoundTag;", ordinal = 2), remap = false)
    private void great_read(CompoundTag compound, boolean clientPacket, CallbackInfo ci) {
        CompoundTag networkTag = compound.getCompound("Network");
        this.greate_largeWaterWheelCount = networkTag.getInt("LargeWaterWheelCount");
        this.greate_waterWheelCount = networkTag.getInt("WaterWheelCount");
        if(((KineticBlockEntity) (Object) this) instanceof WaterWheelBlockEntity &&
                (this.greate_largeWaterWheelCount > WaterWheelBreaker.getMaxLargeWaterWheelLimit() ||
                        this.greate_waterWheelCount > WaterWheelBreaker.getMaxWaterWheelLimit())) {
            WaterWheelBreaker.WHEELS_TO_BREAK.put(networkTag.getLong("Id"), this.getBlockPos());
        }
    }

    @Inject(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;put(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;", ordinal = 2), remap = false)
    private void greate_write(CompoundTag compound, boolean clientPacket, CallbackInfo ci, @Local(name = "networkTag") CompoundTag networkTag) {
        networkTag.putInt("WaterWheelCount", this.greate_waterWheelCount);
        networkTag.putInt("LargeWaterWheelCount", this.greate_largeWaterWheelCount);
    }
}
