package electrolyte.greate.content.kinetics.waterWheel;

import electrolyte.greate.infrastructure.config.GreateConfigs;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;

public class WaterWheelBreaker {

    public static final Object2ObjectOpenHashMap<Long, BlockPos> WHEELS_TO_BREAK = new Object2ObjectOpenHashMap<>();

    public static Integer getMaxWaterWheelLimit() {
        return GreateConfigs.server().kinetics.maxWaterWheelCount.get() == -1 ? Integer.MAX_VALUE : GreateConfigs.server().kinetics.maxWaterWheelCount.get();
    }

    public static Integer getMaxLargeWaterWheelLimit() {
        return GreateConfigs.server().kinetics.maxLargeWaterWheelCount.get() == -1 ? Integer.MAX_VALUE : GreateConfigs.server().kinetics.maxLargeWaterWheelCount.get();
    }
}
