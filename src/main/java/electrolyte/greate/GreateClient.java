package electrolyte.greate;

import electrolyte.greate.foundation.events.GreateClientEvents;
import electrolyte.greate.registry.GreatePartialModels;
import net.fabricmc.api.ClientModInitializer;

public class GreateClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GreatePartialModels.register();
        GreateClientEvents.register();
    }
}
