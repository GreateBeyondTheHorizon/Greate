package electrolyte.greate.foundation.events;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.Greate;
import electrolyte.greate.infrastructure.config.GreateConfigs;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.ChatFormatting;

public class GreateEventHandler {

    public static Event<ItemTooltipCallback> ITEM_TOOLTTIP_EVENT = EventFactory.createArrayBacked(ItemTooltipCallback.class,
            (l) -> (stack, flag, componentList) -> {
                if(GreateConfigs.client().warningTooltip.get()) {
                    if(stack.getItem() == AllBlocks.SHAFT.get().asItem() ||
                            stack.getItem() == AllBlocks.COGWHEEL.get().asItem() ||
                            stack.getItem() == AllBlocks.LARGE_COGWHEEL.get().asItem() ||
                            stack.getItem() == AllBlocks.CRUSHING_WHEEL.get().asItem() ||
                            stack.getItem() == AllItems.BELT_CONNECTOR.get() ||
                            stack.getItem() == AllBlocks.MECHANICAL_PRESS.get().asItem()) {
                        componentList.add(Lang.builder(Greate.MOD_ID).translate("old_create_items_warning.1").component().withStyle(ChatFormatting.RED));
                        componentList.add(Lang.builder(Greate.MOD_ID).translate("old_create_items_warning.2").component().withStyle(ChatFormatting.RED));
                    }
                }
            });
}