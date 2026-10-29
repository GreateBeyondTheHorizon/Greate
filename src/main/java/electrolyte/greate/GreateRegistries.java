package electrolyte.greate;

import com.gregtechceu.gtceu.api.GTCEuAPI.RegisterEvent;
import com.gregtechceu.gtceu.api.addon.events.KJSRecipeKeyEvent;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.mojang.datafixers.util.Pair;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipModifier;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;
import electrolyte.greate.compat.gtceu.common.data.blocks.GreateBlocks;
import electrolyte.greate.compat.gtceu.common.data.machines.GreateMachines;
import electrolyte.greate.compat.kubejs.GreateRecipeComponents;
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
	public static void registerMaterialBlocks(PostMaterialEvent event) {
		Shafts.register();
		Belts.register();
		ChainConveyors.register();
		Cogwheels.register();
		CrushingWheels.register();
		EncasedFans.register();
		Gearboxes.register();
		Girders.register();
		GreateBlocks.register();
		MechanicalPresses.register();
		MechanicalMixers.register();
		Pumps.register();
		Windows.register();
		ModBlockEntityTypes.register();
	}

	@SubscribeEvent
	public static void registerMachines(RegisterEvent<ResourceLocation, MachineDefinition> event) {
		GreateMachines.register();
	}

	@SubscribeEvent
	public static void registerRecipeTypes(RegisterEvent<ResourceLocation, GTRecipeType> event) {
		GreateRecipeTypes.register();
	}

	@SubscribeEvent
	public static void registerTagPrefixes(RegisterEvent<ResourceLocation, TagPrefix> event) {
		GreateTagPrefixes.register();
	}

	@SubscribeEvent
	public static void registerRecipeCapabilities(RegisterEvent<ResourceLocation, RecipeCapability<?>> event) {
		GTRegistries.RECIPE_CAPABILITIES.register(StressRecipeCapability.STRESS_CAPABILITY.id, StressRecipeCapability.STRESS_CAPABILITY);
	}

	@SubscribeEvent
	public static void registerKJSRecipeKeys(KJSRecipeKeyEvent event) {
		event.registerKey(StressRecipeCapability.STRESS_CAPABILITY, Pair.of(GreateRecipeComponents.STRESS_IN, GreateRecipeComponents.STRESS_OUT));
	}
}
