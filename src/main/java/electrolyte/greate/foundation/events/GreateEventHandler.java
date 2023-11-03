package electrolyte.greate.foundation.events;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.Greate;
import electrolyte.greate.infrastructure.config.GreateConfigs;
import net.minecraft.ChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GreateEventHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if(GreateConfigs.client().warningTooltip.get()) {
            if(event.getItemStack().getItem() == AllBlocks.SHAFT.get().asItem() ||
                    event.getItemStack().getItem() == AllBlocks.COGWHEEL.get().asItem() ||
                    event.getItemStack().getItem() == AllBlocks.LARGE_COGWHEEL.get().asItem() ||
                    event.getItemStack().getItem() == AllBlocks.CRUSHING_WHEEL.get().asItem() ||
                    event.getItemStack().getItem() == AllItems.BELT_CONNECTOR.get().asItem() ||
                    event.getItemStack().getItem() == AllBlocks.MECHANICAL_PRESS.get().asItem()) {
                event.getToolTip().add(Lang.builder(Greate.MOD_ID).translate("old_create_items_warning").component().withStyle(ChatFormatting.RED));
            }
        }
    }
}
