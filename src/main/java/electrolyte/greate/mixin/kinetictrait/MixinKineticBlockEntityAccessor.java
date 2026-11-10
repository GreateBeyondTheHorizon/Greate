package electrolyte.greate.mixin.kinetictrait;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(KineticBlockEntity.class)
public interface MixinKineticBlockEntityAccessor {

    @Accessor(value = "stress", remap = false) float getStress();
    @Accessor(value = "stress", remap = false) void setStress(float stress);
    @Accessor(value = "capacity", remap = false) float getCapacity();
    @Accessor(value = "capacity", remap = false) void setCapacity(float capacity);
    @Accessor(value = "networkSize", remap = false) int getSize();
    @Accessor(value = "networkSize", remap = false) void setSize(int capacity);
    @Accessor(value = "lastStressApplied", remap = false) float getStressApplied();
    @Accessor(value = "lastStressApplied", remap = false) void setStressApplied(float stress);
    @Accessor(value = "lastCapacityProvided", remap = false) float getCapacityProvided();
    @Accessor(value = "lastCapacityProvided", remap = false) void setCapacityProvided(float capacity);
    @Accessor(value = "network", remap = false) void setNetworkId(Long networkId);
    @Accessor(value = "source", remap = false) void setSourcePos(BlockPos sourcePos);

    @Invoker(value = "addStressImpactStats", remap = false) void addStressStats(List<Component> tooltip, float stressAtBase);
}
