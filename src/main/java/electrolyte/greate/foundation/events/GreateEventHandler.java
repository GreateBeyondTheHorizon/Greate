package electrolyte.greate.foundation.events;

import electrolyte.greate.Greate;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GreateEventHandler {

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(TieredRecipeFinder.LISTENER);
    }
}
