package electrolyte.greate.foundation.events;

import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(Dist.CLIENT)
public class GreateClientEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if(!isGameActive()) return;
        Level level = Minecraft.getInstance().level;
        if(event.phase == Phase.START) { return; }

        TieredBeltConnectorHandler.tick();

    }

    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }
}
