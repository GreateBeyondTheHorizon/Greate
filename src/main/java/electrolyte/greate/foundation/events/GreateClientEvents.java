package electrolyte.greate.foundation.events;

import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class GreateClientEvents {

    public static void onTick(Minecraft client) {
        if(!isGameActive()) return;
        Level level = Minecraft.getInstance().level;

        TieredBeltConnectorHandler.tick();
    }

    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(GreateClientEvents::onTick);
        ItemTooltipCallback.EVENT.register(GreateEventHandler.ITEM_TOOLTTIP_EVENT.invoker());
    }
}
