package electrolyte.greate;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import electrolyte.greate.foundation.data.recipe.GreateRecipes;
import electrolyte.greate.registry.*;
import net.minecraft.data.recipes.FinishedRecipe;

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
		Gearboxes.register();
		Girders.register();
		MechanicalPresses.register();
		MechanicalMixers.register();
		Millstones.register();
		Saws.register();
		Pumps.register();
		ModBlockEntityTypes.register();
		ModItems.register();
		Greate.LOGGER.info("Greate GT addon initialised!");
	}

	@Override
	public String addonModId() {
		return Greate.MOD_ID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> provider) {
		GreateRecipes.init(provider);
	}
}
