package electrolyte.greate;

import electrolyte.greate.registry.ModBlocks;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GreateEventHandler {

    @SubscribeEvent
    public static void assignShaftTiers(AddReloadListenerEvent event) {
        ModBlocks.STEEL_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.LOW);
        ModBlocks.ALUMINIUM_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.MEDIUM);
        ModBlocks.STAINLESS_STEEL_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.HIGH);
        ModBlocks.TITANIUM_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.EXTREME);
        ModBlocks.TUNGSTENSTEEL_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.INSANE);
        ModBlocks.PALLADIUM_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.LUDICRIOUS);
        ModBlocks.NAQUADAH_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.ZPM);
        ModBlocks.DARMSTADTIUM_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.ULTIMATE);
        ModBlocks.NEUTRONIUM_SHAFT.get().setCapacityTier(GreateEnums.CAPACITY_TIER.ULTIMATE_HIGH);
    }
}
