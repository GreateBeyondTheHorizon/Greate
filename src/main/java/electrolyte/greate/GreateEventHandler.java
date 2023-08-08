package electrolyte.greate;

import electrolyte.greate.registry.ModBlocks;
import electrolyte.greate.GreateEnums.TIER;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GreateEventHandler {

    @SubscribeEvent
    public static void assignShaftTiers(AddReloadListenerEvent event) {
        ModBlocks.ANDESITE_SHAFT.get().setTier(TIER.ULTRA_LOW);
        ModBlocks.STEEL_SHAFT.get().setTier(TIER.LOW);
        ModBlocks.ALUMINIUM_SHAFT.get().setTier(TIER.MEDIUM);
        ModBlocks.STAINLESS_STEEL_SHAFT.get().setTier(TIER.HIGH);
        ModBlocks.TITANIUM_SHAFT.get().setTier(TIER.EXTREME);
        ModBlocks.TUNGSTENSTEEL_SHAFT.get().setTier(TIER.INSANE);
        ModBlocks.PALLADIUM_SHAFT.get().setTier(TIER.LUDICRIOUS);
        ModBlocks.NAQUADAH_SHAFT.get().setTier(TIER.ZPM);
        ModBlocks.DARMSTADTIUM_SHAFT.get().setTier(TIER.ULTIMATE);
        ModBlocks.NEUTRONIUM_SHAFT.get().setTier(TIER.ULTIMATE_HIGH);

        ModBlocks.ANDESITE_COGWHEEL.get().setTier(TIER.ULTRA_LOW);
        ModBlocks.LARGE_ANDESITE_COGWHEEL.get().setTier(TIER.ULTRA_LOW);
        ModBlocks.STEEL_COGWHEEL.get().setTier(TIER.LOW);
        ModBlocks.LARGE_STEEL_COGWHEEL.get().setTier(TIER.LOW);
        ModBlocks.ALUMINIUM_COGWHEEL.get().setTier(TIER.MEDIUM);
        ModBlocks.LARGE_ALUMINIUM_COGWHEEL.get().setTier(TIER.MEDIUM);
        ModBlocks.STAINLESS_STEEL_COGWHEEL.get().setTier(TIER.HIGH);
        ModBlocks.LARGE_STAINLESS_STEEL_COGWHEEL.get().setTier(TIER.HIGH);
        ModBlocks.TITANIUM_COGWHEEL.get().setTier(TIER.EXTREME);
        ModBlocks.LARGE_TITANIUM_COGWHEEL.get().setTier(TIER.EXTREME);
        ModBlocks.TUNGSTENSTEEL_COGWHEEL.get().setTier(TIER.INSANE);
        ModBlocks.LARGE_TUNGSTENSTEEL_COGWHEEL.get().setTier(TIER.INSANE);
        ModBlocks.PALLADIUM_COGWHEEL.get().setTier(TIER.LUDICRIOUS);
        ModBlocks.LARGE_PALLADIUM_COGWHEEL.get().setTier(TIER.LUDICRIOUS);
        ModBlocks.NAQUADAH_COGWHEEL.get().setTier(TIER.ZPM);
        ModBlocks.LARGE_NAQUADAH_COGWHEEL.get().setTier(TIER.ZPM);
        ModBlocks.DARMSTADTIUM_COGWHEEL.get().setTier(TIER.ULTIMATE);
        ModBlocks.LARGE_DARMSTADTIUM_COGWHEEL.get().setTier(TIER.ULTIMATE);
        ModBlocks.NEUTRONIUM_COGWHEEL.get().setTier(TIER.ULTIMATE_HIGH);
        ModBlocks.LARGE_NEUTRONIUM_COGWHEEL.get().setTier(TIER.ULTIMATE_HIGH);
    }
}
