package electrolyte.greate;

import com.gregtechceu.gtceu.api.GTCEuAPI.RegisterEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import electrolyte.greate.compat.gtceu.api.capability.recipe.GreateRecipeTypes;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;
import electrolyte.greate.compat.gtceu.machine.GreateMultiblockMachines;
import electrolyte.greate.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GreateRegistries {
	public static final GTRegistrate REGISTRATE = GTRegistrate.create(Greate.MOD_ID);

	@SubscribeEvent
	public static void registerMaterials(MaterialEvent event) {
		GreateMaterials.register();
	}

	@SubscribeEvent
	public static void registerMachines(RegisterEvent<ResourceLocation, MachineDefinition> event) {
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
		ModItems.register();
		GreateMultiblockMachines.register();
		GreateMachines.register();
	}

	@SubscribeEvent
	public static void registerRecipeTypes(RegisterEvent<ResourceLocation, GTRecipeType> event) {
		GreateRecipeTypes.register();
	}

	@SubscribeEvent
	public static void registerRecipeConditions(RegisterEvent<ResourceLocation, RecipeConditionType> event) {
		//GreateRecipeConditions.register();
	}
}
