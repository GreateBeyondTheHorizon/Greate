package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticEffectHandler;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KineticBlockEntity.class)
public interface MixinKineticBlockEntityAccessor {

    @Accessor(value = "network", remap = false) Long getNetwork();
    @Accessor(value = "stress", remap = false) float getStress();
    @Accessor(value = "capacity", remap = false) float getCapacity();
    @Accessor(value = "networkSize", remap = false) int getNetworkSize();
    @Accessor(value = "lastStressApplied", remap = false) float getLastStressApplied();
    @Accessor(value = "lastCapacityProvided", remap = false) float getLastCapacityProvided();
    @Accessor(value = "source", remap = false) BlockPos getSource();
    @Accessor(value = "effects", remap = false) KineticEffectHandler getEffects();
}
