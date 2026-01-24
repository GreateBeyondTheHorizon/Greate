package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.data.recipe.WoodTypeEntry;
import com.gregtechceu.gtceu.data.recipe.misc.WoodMachineRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(WoodMachineRecipes.class)
public interface MixinWoodMachineRecipesAccessor {

    @Accessor(value = "DEFAULT_ENTRIES", remap = false) static List<WoodTypeEntry> getDefaultEntries() { throw new IllegalStateException("Mixin did not apply!"); }
}
