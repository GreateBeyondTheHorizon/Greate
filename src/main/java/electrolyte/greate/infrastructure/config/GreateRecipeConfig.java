package electrolyte.greate.infrastructure.config;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.Configurable.Comment;
import dev.toma.configuration.config.Configurable.Synchronized;
import electrolyte.greate.Greate;

@Config(id = Greate.MOD_ID, filename = Greate.MOD_ID + "-recipes")
public class GreateRecipeConfig {

    @Configurable
    @Synchronized
    @Comment({"Enable/Disable wire coating recipes in the crafting grid/packer/assembler.", "If disabled, wire coating recipes will only be accessible via the spout & wire coating factory."})
    public boolean enableGTWireCoatingRecipes = true;

    @Configurable
    @Synchronized
    @Comment({"Enable/Disable harder recipes for various Create blocks & items."})
    public boolean enableHardCreateRecipes = false;

    @Configurable
    @Synchronized
    @Comment({"Recipe types that should not be copied to Greate machines. Ex. 'gtceu:macerator' for macerator recipes"})
    public String[] ignoredRecipeTypes = {};
}
