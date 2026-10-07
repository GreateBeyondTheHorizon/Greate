package electrolyte.greate.foundation.events;

import com.simibubi.create.foundation.pack.DynamicPack;
import com.simibubi.create.foundation.pack.DynamicPackSource;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.waterWheel.WaterWheelBreaker;
import electrolyte.greate.foundation.data.GreateDynamicTags;
import electrolyte.greate.foundation.data.recipe.GreateRuntimeRecipes;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
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

    @SubscribeEvent
    public static void levelTick(LevelTickEvent event) {
        if(event.phase == Phase.END && !event.level.isClientSide) {
            if(event.level.getGameTime() % 10 == 0) {
                if(WaterWheelBreaker.WHEELS_TO_BREAK.isEmpty()) return;
                for(BlockPos pos : WaterWheelBreaker.WHEELS_TO_BREAK.values()) {
                    event.level.destroyBlock(pos, true);
                    WaterWheelBreaker.WHEELS_TO_BREAK.values().remove(pos);
                }
            }
        }
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
