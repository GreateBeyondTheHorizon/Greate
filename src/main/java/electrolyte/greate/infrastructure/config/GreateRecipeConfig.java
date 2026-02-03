package electrolyte.greate.infrastructure.config;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.Configurable.Comment;
import dev.toma.configuration.config.Configurable.Range;
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
    @Range(min = 0)
    @Comment({"Multiplier used for calculating how many ticks should initially be removed in fan processing recipes, based on how fast the fan is spinning."})
    public final float fanSpeedMultiplier = 0.75f;

    @Configurable
    @Synchronized
    @Comment({"Should certain Create items that have a GTCEu counterpart also be used in recipes?", "If false, GTCEu items will only be used."})
    public boolean useCreateItemsInRecipes = true;

    @Configurable
    @Synchronized
    @Comment({"Should recipes from create that conflict with GTCEu recipes be disabled?", "[WARNING]: This will not cover every single recipe conflict, only common conflicts (like components (plates/sheets)), or ore processing conflicts."})
    public boolean disableConflictingRecipes = false;


    @Configurable
    @Synchronized
    @Comment({"Should tiered encased fans process item entities?"})
    public boolean processItemEntitiesWithFan = true;

    @Configurable
    @Synchronized
    @Comment({"Recipe types that should not be copied to Greate machines. Ex. 'gtceu:macerator' for macerator recipes"})
    public String[] ignoredRecipeTypes = {};
}
