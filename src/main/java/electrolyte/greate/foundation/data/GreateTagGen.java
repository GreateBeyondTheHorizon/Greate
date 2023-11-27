package electrolyte.greate.foundation.data;

import electrolyte.greate.registry.GreateTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class GreateTagGen extends ItemTagsProvider {

    public GreateTagGen(PackOutput pOutput, CompletableFuture<Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        getOrCreateRawBuilder(GreateTags.forgeItemTag("plates/tungstensteel"))
                .addOptionalTag(new ResourceLocation("forge", "plates/tungsten_steel"));
        getOrCreateRawBuilder(GreateTags.forgeItemTag("nuggets/tungstensteel"))
                .addOptionalTag(new ResourceLocation("forge", "nuggets/tungsten_steel"));
        getOrCreateRawBuilder(GreateTags.forgeItemTag("storage_blocks/tungstensteel"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/tungsten_steel"));
    }

    public static class GreateBlockTagGen extends BlockTagsProvider {

        public GreateBlockTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(Provider pProvider) {}
    }
}
