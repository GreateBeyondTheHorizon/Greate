package electrolyte.greate;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.utility.Lang;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.ChatFormatting;

public class GreateEventHandler {

    public static Event<ItemTooltipCallback> ITEM_TOOLTTIP_EVENT = EventFactory.createArrayBacked(ItemTooltipCallback.class,
            (l) -> (stack, flag, componentList) -> {
                if(stack.getItem() == AllBlocks.SHAFT.get().asItem() ||
                        stack.getItem() == AllBlocks.COGWHEEL.get().asItem() ||
                        stack.getItem() == AllBlocks.LARGE_COGWHEEL.get().asItem() ||
                        stack.getItem() == AllBlocks.CRUSHING_WHEEL.get().asItem()) {
                    componentList.add(Lang.builder(Greate.MOD_ID).translate("old_create_items_warning").component().withStyle(ChatFormatting.RED));
                }
            });
}
