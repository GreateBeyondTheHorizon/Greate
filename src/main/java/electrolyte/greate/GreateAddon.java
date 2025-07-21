package electrolyte.greate;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import electrolyte.greate.content.gtceu.machines.GreateRecipeTypes;
import electrolyte.greate.foundation.data.recipe.GreateCraftingComponents;
import electrolyte.greate.registry.GreateTagPrefixes;
import net.minecraft.data.recipes.RecipeOutput;

@GTAddon(Greate.MOD_ID)
public class GreateAddon implements IGTAddon {

	@Override
	public GTRegistrate getRegistrate() {
		return GreateRegistries.REGISTRATE;
	}

	@Override
	public void gtInitComplete() {
		Greate.LOGGER.info("Greate GT addon initialized!");
		GreateTagPrefixes.register();
	}

	@Override
	public void addRecipes(RecipeOutput provider) {
		GreateRecipeTypes.register();
		GreateCraftingComponents.register();
		//GreateRecipes.register(provider);
	}
}
