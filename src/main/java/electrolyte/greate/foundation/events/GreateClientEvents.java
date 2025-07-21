package electrolyte.greate.foundation.events;

import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorHandler;
import electrolyte.greate.content.kinetics.fan.TieredAirCurrent;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(Dist.CLIENT)
public class GreateClientEvents {

    @SubscribeEvent
    public static void onClientTickPre(ClientTickEvent.Pre event) {
        onClientTick(true);
    }

    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        onClientTick(false);
    }

    public static void onClientTick(boolean preEvent) {
        if(!isGameActive()) return;
        if(preEvent) {
        TieredAirCurrent.tickClientPlayerSounds();
        }
        TieredBeltConnectorHandler.tick();
    }

    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }
}
