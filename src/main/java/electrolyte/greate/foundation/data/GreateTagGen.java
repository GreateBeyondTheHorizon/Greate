package electrolyte.greate.foundation.data;

import electrolyte.greate.registry.GreateTags;
import io.github.fabricators_of_create.porting_lib.tags.data.ItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class GreateTagGen extends ItemTagProvider {
    public GreateTagGen(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    /*
    Dear GT Devs,
    WHY ARE SOME TUNGSTENSTEEL ITEMS/BLOCKS NAMED 'TUNGSTENSTEEL_*' AND OTHERS 'TUNGSTEN_STEEL_*' ????
    pls fix
    ty <3
     */
    @Override
    protected void addTags(Provider arg) {
        getOrCreateTagBuilder(GreateTags.forgeItemTag("tungstensteel_plates"))
                .addOptionalTag(new ResourceLocation("c", "tungsten_steel_plates"));
        getOrCreateTagBuilder(GreateTags.forgeItemTag("tungstensteel_nuggets"))
                .addOptionalTag(new ResourceLocation("c", "tungsten_steel_nuggets"));
    }
}
