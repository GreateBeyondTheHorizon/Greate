package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.TorquePropagator;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(TorquePropagator.class)
public interface MixinTorquePropagatorAccessor {

    @Accessor(value = "networks", remap = false) static Map<LevelAccessor, Map<Long, KineticNetwork>> getNetworks() { throw new IllegalStateException("Mixin did not apply!"); }
}
