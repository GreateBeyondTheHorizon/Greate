package electrolyte.greate;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import electrolyte.greate.content.gtceu.machines.GreateRecipeTypes;
import electrolyte.greate.foundation.data.recipe.GreateCraftingComponent;
import electrolyte.greate.foundation.data.recipe.GreateRecipes;
import electrolyte.greate.registry.GreateTagPrefixes;
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
