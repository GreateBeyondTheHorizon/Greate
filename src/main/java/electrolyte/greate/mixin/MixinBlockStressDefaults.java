package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BlockStressDefaults.class)
public class MixinBlockStressDefaults {

    @Shadow @Final public static Map<ResourceLocation, Double> DEFAULT_CAPACITIES;

    @Inject(method = "setDefaultCapacity", at = @At("RETURN"), remap = false)
    private static void greate_setDefaultCapacity(ResourceLocation blockId, double capacity, CallbackInfo ci) {
        if(blockId.equals(new ResourceLocation("create:water_wheel"))) {
            DEFAULT_CAPACITIES.put(blockId, 1.0);
        }
    }
}
