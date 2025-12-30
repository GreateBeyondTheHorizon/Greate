package electrolyte.greate;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTCEuAPI.RegisterEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipModifier;
import electrolyte.greate.content.gtceu.machines.GreateMultiblockMachines;
import electrolyte.greate.content.gtceu.machines.GreateRecipeTypes;
import electrolyte.greate.foundation.data.GreateRegistrate;
import electrolyte.greate.foundation.item.GreateKineticStats;
import electrolyte.greate.registry.*;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Greate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GreateRegistries {
	public static final GreateRegistrate REGISTRATE = GreateRegistrate.create(Greate.MOD_ID)
			.setTooltipModifierFactory(i ->
					new ItemDescription.Modifier(i, Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(GreateKineticStats.create(i))));

	@SubscribeEvent
	public static void registerMaterials(MaterialEvent event) {
		GreateMaterials.register();
	}

	@SubscribeEvent
	public static void registerCustomMaterials(MaterialRegistryEvent event) {
		GTCEuAPI.materialManager.createRegistry(Greate.MOD_ID);
	}

	@SubscribeEvent
	public static void registerMaterialBlocks(PostMaterialEvent event) {
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
	}

	@SubscribeEvent
	public static void registerMachines(RegisterEvent<ResourceLocation, MachineDefinition> event) {
		GreateMultiblockMachines.register();
	}

	@SubscribeEvent
	public static void registerRecipeTypes(RegisterEvent<ResourceLocation, GTRecipeType> event) {
		GreateRecipeTypes.register();
	}
}
