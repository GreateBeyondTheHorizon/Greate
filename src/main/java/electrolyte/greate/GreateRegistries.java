package electrolyte.greate;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import electrolyte.greate.content.gtceu.machines.GreateMultiblockMachines;
import electrolyte.greate.content.gtceu.machines.GreateRecipeTypes;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes;
import electrolyte.greate.registry.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = Greate.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class GreateRegistries {
	public static final GTRegistrate REGISTRATE = GTRegistrate.create(Greate.MOD_ID);

	@SubscribeEvent
	public static void registerEvent(RegisterEvent event) {
		event.register(GTRegistries.MATERIAL_REGISTRY.registryKey(),helper -> {
			GreateMaterials.register();
			Shafts.register();
			Belts.register();
			Cogwheels.register();
			CrushingWheels.register();
			EncasedFans.register();
			Gearboxes.register();
			Girders.register();
			MechanicalPresses.register();
			MechanicalMixers.register();
			Millstones.register();
			Saws.register();
			Pumps.register();
			ModBlockEntityTypes.register();

			//TODO: check
			if(LogicalSide.CLIENT.isClient()) {
				GreatePartialModels.register();
			}
		});
		GreateMultiblockMachines.register();
		GreateRecipeTypes.register();
        GreateFanProcessingTypes.register();
	}
}
