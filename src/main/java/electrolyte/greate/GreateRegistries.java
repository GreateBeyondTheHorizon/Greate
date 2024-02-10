package electrolyte.greate;

import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import electrolyte.greate.registry.GreateMaterials;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GreateRegistries {
	public static final GTRegistrate REGISTRATE = GTRegistrate.create(Greate.MOD_ID);

	@SubscribeEvent
	public static void registerMaterials(MaterialEvent event) {
		GreateMaterials.init();
		GreateValues.init();
	}
}
