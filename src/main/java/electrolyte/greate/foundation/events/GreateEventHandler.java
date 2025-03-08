package electrolyte.greate.foundation.events;

import com.gregtechceu.gtceu.config.ConfigHolder;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.machines.GreateMultiblockMachines;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import electrolyte.greate.infrastructure.config.GreateConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GreateEventHandler {

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(TieredRecipeFinder.LISTENER);
    }

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        if(event.getItemStack().is(GreateMultiblockMachines.WIRE_COATING_FACTORY.getItem()) && !ConfigHolder.INSTANCE.recipes.hardMiscRecipes && GreateConfigs.client().enableWireFactoryWarning.get()) {
            event.getToolTip().add(Component.translatable("greate.multiblock.wire_coating_factory.warning.tooltip").withStyle(ChatFormatting.RED));
        }
    }
}
