package electrolyte.greate;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import electrolyte.greate.content.gtceu.machines.GreateMultiblockMachines;
import electrolyte.greate.content.gtceu.machines.GreateRecipeTypes;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes;
import electrolyte.greate.foundation.data.recipe.GreateCraftingComponents;
import electrolyte.greate.registry.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = Greate.MOD_ID)
public class GreateRegistries {

    @SubscribeEvent
    public static void registerEvent(RegisterEvent event) {
        if(event.getRegistryKey() == GTRegistries.MATERIAL_REGISTRY) {
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
            GreateCraftingComponents.register();

			//TODO: check
			if(LogicalSide.CLIENT.isClient()) {
				//GreatePartialModels.register();
			}
        }

        event.register(GTRegistries.TAG_PREFIX_REGISTRY, helper -> GreateTagPrefixes.register());

        if(event.getRegistry() == GTRegistries.RECIPE_TYPE_REGISTRY) {
            GreateRecipeTypes.register();
        }

        if(event.getRegistry() == GTRegistries.MACHINE_REGISTRY) {
            GreateMultiblockMachines.register();
        }

		GreateFanProcessingTypes.register();
	}
}
