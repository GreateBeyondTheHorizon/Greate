package electrolyte.greate;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import electrolyte.greate.registry.ModBlockEntityTypes;
import electrolyte.greate.registry.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Greate.MOD_ID)
public class Greate {

    public static final String MOD_ID = "greate";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Greate.MOD_ID);
    public static final LangBuilder LANG = Lang.builder(Greate.MOD_ID);

    public Greate() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        REGISTRATE.registerEventListeners(eventBus);
        ModBlocks.register();
        ModBlockEntityTypes.register();
    }

    public static final CreativeModeTab GREATE_TAB = new CreativeModeTab("greate") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.GOLDEN_APPLE);
        }
    };
}
