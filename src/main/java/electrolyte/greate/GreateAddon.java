package electrolyte.greate;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import electrolyte.greate.content.gtceu.machines.GreateRecipeTypes;
import electrolyte.greate.foundation.data.recipe.GreateCraftingComponent;
import electrolyte.greate.foundation.data.recipe.GreateRecipes;
import electrolyte.greate.infrastructure.config.GreateConfigs;
import electrolyte.greate.registry.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.fml.ModLoadingContext;

import java.util.function.Consumer;

@GTAddon
public class GreateAddon implements IGTAddon {
	@Override
	public GTRegistrate getRegistrate() {
		return GreateRegistries.REGISTRATE;
	}

	@Override
	public void initializeAddon() {
		Shafts.register();
		Belts.register();
		Cogwheels.register();
		CrushingWheels.register();
		EncasedFans.register();
		Gearboxes.register();
		Girders.register();
		MechanicalCrafters.register();
		MechanicalPresses.register();
		MechanicalMixers.register();
		Millstones.register();
		Saws.register();
		Pumps.register();
		ModBlockEntityTypes.register();
		ModItems.register();
		GreateConfigs.register(ModLoadingContext.get());
		Greate.LOGGER.info("Greate GT addon initialized!");
	}

	@Override
	public String addonModId() {
		return Greate.MOD_ID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> provider) {
		GreateRecipeTypes.register();
		GreateCraftingComponent.init();
		GreateRecipes.init(provider);
	}

	@Override
	public void registerTagPrefixes() {
		GreateTagPrefixes.register();
	}


}
