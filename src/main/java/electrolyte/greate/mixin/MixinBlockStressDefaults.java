package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BlockStressDefaults.class)
public class MixinBlockStressDefaults {

    // Force all stress entries to update to the new defaults on worlds that used Create before installing this mod.
    @Shadow @Final public static final int FORCED_UPDATE_VERSION = 3;
    @Shadow @Final public static Map<ResourceLocation, Double> DEFAULT_CAPACITIES;
    @Unique private static final Map<ResourceLocation, Double> greate_NEW_DEFAULT_CAPACITIES = Map.ofEntries(
            Map.entry(new ResourceLocation("create:hand_crank"), 1.),
            Map.entry(new ResourceLocation("create:copper_valve_handle"), 1.),
            Map.entry(new ResourceLocation("create:water_wheel"), 1.),
            Map.entry(new ResourceLocation("create:large_water_wheel"), 8.),
            Map.entry(new ResourceLocation("create:windmill_bearing"), 128.),
            Map.entry(new ResourceLocation("create:steam_engine"), 256.),
            Map.entry(new ResourceLocation("create:creative_motor"), 2097152.)
    );

    @Inject(method = "setDefaultCapacity", at = @At("RETURN"), remap = false)
    private static void greate_setDefaultCapacity(ResourceLocation blockId, double capacity, CallbackInfo ci) {
        if(greate_NEW_DEFAULT_CAPACITIES.containsKey(blockId)) {
            var newCapacity = greate_NEW_DEFAULT_CAPACITIES.get(blockId);
            DEFAULT_CAPACITIES.put(blockId, newCapacity);
        }
    }
}
