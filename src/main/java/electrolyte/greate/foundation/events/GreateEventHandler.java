package electrolyte.greate.foundation.events;

import electrolyte.greate.Greate;
import electrolyte.greate.foundation.data.recipe.GreateRuntimeRecipes;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber(modid = Greate.MOD_ID, bus = Bus.GAME)
public class GreateEventHandler {

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(TieredRecipeFinder.LISTENER);
        event.addListener(GreateRuntimeRecipes.LISTENER);
    }
}
