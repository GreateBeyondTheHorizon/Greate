package electrolyte.greate;

import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.registry.Cogwheels;
import electrolyte.greate.registry.Shafts;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GreateEventHandler {

    @SubscribeEvent
    public static void assignShaftTiers(AddReloadListenerEvent event) {
        Shafts.ANDESITE_SHAFT.get().setTier(TIER.ULTRA_LOW);
        Shafts.STEEL_SHAFT.get().setTier(TIER.LOW);
        Shafts.ALUMINIUM_SHAFT.get().setTier(TIER.MEDIUM);
        Shafts.STAINLESS_STEEL_SHAFT.get().setTier(TIER.HIGH);
        Shafts.TITANIUM_SHAFT.get().setTier(TIER.EXTREME);
        Shafts.TUNGSTENSTEEL_SHAFT.get().setTier(TIER.INSANE);
        Shafts.PALLADIUM_SHAFT.get().setTier(TIER.LUDICRIOUS);
        Shafts.NAQUADAH_SHAFT.get().setTier(TIER.ZPM);
        Shafts.DARMSTADTIUM_SHAFT.get().setTier(TIER.ULTIMATE);
        Shafts.NEUTRONIUM_SHAFT.get().setTier(TIER.ULTIMATE_HIGH);

        Shafts.ANDESITE_ENCASED_ANDESITE_SHAFT.get().setTier(TIER.ULTRA_LOW);
        Shafts.BRASS_ENCASED_ANDESITE_SHAFT.get().setTier(TIER.ULTRA_LOW);
        Shafts.ANDESITE_ENCASED_STEEL_SHAFT.get().setTier(TIER.LOW);
        Shafts.BRASS_ENCASED_STEEL_SHAFT.get().setTier(TIER.LOW);
        Shafts.ANDESITE_ENCASED_ALUMINIUM_SHAFT.get().setTier(TIER.MEDIUM);
        Shafts.BRASS_ENCASED_ALUMINIUM_SHAFT.get().setTier(TIER.MEDIUM);
        Shafts.ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT.get().setTier(TIER.HIGH);
        Shafts.BRASS_ENCASED_STAINLESS_STEEL_SHAFT.get().setTier(TIER.HIGH);
        Shafts.ANDESITE_ENCASED_TITANIUM_SHAFT.get().setTier(TIER.EXTREME);
        Shafts.BRASS_ENCASED_TITANIUM_SHAFT.get().setTier(TIER.EXTREME);
        Shafts.ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT.get().setTier(TIER.INSANE);
        Shafts.BRASS_ENCASED_TUNGSTENSTEEL_SHAFT.get().setTier(TIER.INSANE);
        Shafts.ANDESITE_ENCASED_PALLADIUM_SHAFT.get().setTier(TIER.LUDICRIOUS);
        Shafts.BRASS_ENCASED_PALLADIUM_SHAFT.get().setTier(TIER.LUDICRIOUS);
        Shafts.ANDESITE_ENCASED_NAQUADAH_SHAFT.get().setTier(TIER.ZPM);
        Shafts.BRASS_ENCASED_NAQUADAH_SHAFT.get().setTier(TIER.ZPM);
        Shafts.ANDESITE_ENCASED_DARMSTADTIUM_SHAFT.get().setTier(TIER.ULTIMATE);
        Shafts.BRASS_ENCASED_DARMSTADTIUM_SHAFT.get().setTier(TIER.ULTIMATE);
        Shafts.ANDESITE_ENCASED_NEUTRONIUM_SHAFT.get().setTier(TIER.ULTIMATE_HIGH);
        Shafts.BRASS_ENCASED_NEUTRONIUM_SHAFT.get().setTier(TIER.ULTIMATE_HIGH);

        Cogwheels.ANDESITE_COGWHEEL.get().setTier(TIER.ULTRA_LOW);
        Cogwheels.LARGE_ANDESITE_COGWHEEL.get().setTier(TIER.ULTRA_LOW);
        Cogwheels.STEEL_COGWHEEL.get().setTier(TIER.LOW);
        Cogwheels.LARGE_STEEL_COGWHEEL.get().setTier(TIER.LOW);
        Cogwheels.ALUMINIUM_COGWHEEL.get().setTier(TIER.MEDIUM);
        Cogwheels.LARGE_ALUMINIUM_COGWHEEL.get().setTier(TIER.MEDIUM);
        Cogwheels.STAINLESS_STEEL_COGWHEEL.get().setTier(TIER.HIGH);
        Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL.get().setTier(TIER.HIGH);
        Cogwheels.TITANIUM_COGWHEEL.get().setTier(TIER.EXTREME);
        Cogwheels.LARGE_TITANIUM_COGWHEEL.get().setTier(TIER.EXTREME);
        Cogwheels.TUNGSTENSTEEL_COGWHEEL.get().setTier(TIER.INSANE);
        Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL.get().setTier(TIER.INSANE);
        Cogwheels.PALLADIUM_COGWHEEL.get().setTier(TIER.LUDICRIOUS);
        Cogwheels.LARGE_PALLADIUM_COGWHEEL.get().setTier(TIER.LUDICRIOUS);
        Cogwheels.NAQUADAH_COGWHEEL.get().setTier(TIER.ZPM);
        Cogwheels.LARGE_NAQUADAH_COGWHEEL.get().setTier(TIER.ZPM);
        Cogwheels.DARMSTADTIUM_COGWHEEL.get().setTier(TIER.ULTIMATE);
        Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL.get().setTier(TIER.ULTIMATE);
        Cogwheels.NEUTRONIUM_COGWHEEL.get().setTier(TIER.ULTIMATE_HIGH);
        Cogwheels.LARGE_NEUTRONIUM_COGWHEEL.get().setTier(TIER.ULTIMATE_HIGH);
    }
}
