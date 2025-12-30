package electrolyte.greate.foundation.events;

import com.simibubi.create.foundation.pack.DynamicPack;
import com.simibubi.create.foundation.pack.DynamicPackSource;
import electrolyte.greate.Greate;
import electrolyte.greate.foundation.data.GreateDynamicTags;
import electrolyte.greate.foundation.data.recipe.GreateRuntimeRecipes;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
class GreateForgeEventHandler {

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(TieredRecipeFinder.LISTENER);
        event.addListener(GreateRuntimeRecipes.LISTENER);
    }
}

@EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
class GreateModEventHandler {

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if(event.getPackType() == PackType.SERVER_DATA) {
            DynamicPack dynamicPack = new DynamicPack(Greate.id("dynamic_data").toString(), PackType.SERVER_DATA);
            GreateDynamicTags.generateDynamicTags(dynamicPack);
            event.addRepositorySource(new DynamicPackSource(
                    "greate:dynamic_data",
                    PackType.SERVER_DATA,
                    Position.BOTTOM,
                    dynamicPack
            ));
        }
    }
}
