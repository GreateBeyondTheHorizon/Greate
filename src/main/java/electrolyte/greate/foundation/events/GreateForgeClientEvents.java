package electrolyte.greate.foundation.events;

import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorHandler;
import electrolyte.greate.content.kinetics.fan.TieredAirCurrent;
import electrolyte.greate.foundation.client.models.BeltModel;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Greate.MOD_ID, value = Dist.CLIENT, bus = Bus.FORGE)
public class GreateForgeClientEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if(!isGameActive()) return;
        if(event.phase == Phase.START) {
            TieredAirCurrent.tickClientPlayerSounds();
            return;
        }
        TieredBeltConnectorHandler.tick();
    }

    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }
}
@EventBusSubscriber(modid = Greate.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
class GreateModClientEvents {
    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional event) {
        BeltModel.MODEL_LOCATIONS.forEach(event::register);
    }
}
