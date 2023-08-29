package electrolyte.greate;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GreateEventHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if(event.getItemStack().getItem() == AllBlocks.SHAFT.get().asItem() ||
            event.getItemStack().getItem() == AllBlocks.COGWHEEL.get().asItem() ||
            event.getItemStack().getItem() == AllBlocks.LARGE_COGWHEEL.get().asItem() ||
            event.getItemStack().getItem() == AllBlocks.CRUSHING_WHEEL.get().asItem()) {
            event.getToolTip().add(Lang.builder(Greate.MOD_ID).translate("old_create_items_warning").component().withStyle(ChatFormatting.RED));
        }
    }
}
