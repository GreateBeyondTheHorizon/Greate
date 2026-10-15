package electrolyte.greate.mixin.kinetictrait;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KineticBlockEntity.class)
public interface MixinKineticBlockEntityAccessor {

    @Accessor(value = "network", remap = false) Long getNetwork();
    @Accessor(value = "stress", remap = false) float getStress();
}
