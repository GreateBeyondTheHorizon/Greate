package electrolyte.greate;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlockEntity;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEntityLootEvents;

public class CommonEvents {

    public static void register() {
        LivingEntityLootEvents.DROPS.register(TieredCrushingWheelBlockEntity::handleCrushedDrops);
        LivingEntityLootEvents.LOOTING_LEVEL.register(CrushingWheelBlockEntity::crushingIsFortunate);
    }
}
