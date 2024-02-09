package electrolyte.greate;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GreateRegistries {
	public static final GTRegistrate REGISTRATE = GTRegistrate.create(Greate.MOD_ID);
}
