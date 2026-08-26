package electrolyte.greate.foundation.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class GreateTagGen {

    private static final TagKey<Item> HIDE_FROM_RECIPE_VIEWERS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "hidden_from_recipe_viewers"));

    public static class GreateBlockTagGen extends BlockTagsProvider {

        public GreateBlockTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(Provider pProvider) {}
    }

    public static class GreateItemTagGen extends ItemTagsProvider {

        public GreateItemTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> tagLookup, String modId, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, tagLookup, modId, existingFileHelper);
        }

        @Override
        protected void addTags(Provider pProvider) {
            this.tag(HIDE_FROM_RECIPE_VIEWERS)
                    .add(AllBlocks.ANDESITE_ENCASED_SHAFT.asItem())
                    .add(AllBlocks.BRASS_ENCASED_SHAFT.asItem())
                    .add(AllBlocks.ANDESITE_ENCASED_COGWHEEL.asItem())
                    .add(AllBlocks.ANDESITE_ENCASED_LARGE_COGWHEEL.asItem())
                    .add(AllBlocks.BRASS_ENCASED_COGWHEEL.asItem())
                    .add(AllBlocks.BRASS_ENCASED_LARGE_COGWHEEL.asItem())
                    .add(AllItems.BELT_CONNECTOR.asItem())
                    .add(AllBlocks.COGWHEEL.asItem())
                    .add(AllBlocks.LARGE_COGWHEEL.asItem())
                    .add(AllBlocks.MILLSTONE.asItem())
                    .add(AllBlocks.CRUSHING_WHEEL.asItem())
                    .add(AllBlocks.GEARBOX.asItem())
                    .add(AllBlocks.MECHANICAL_PRESS.asItem())
                    .add(AllBlocks.MECHANICAL_MIXER.asItem())
                    .add(AllBlocks.MECHANICAL_SAW.asItem())
                    .add(AllBlocks.ENCASED_FAN.asItem())
                    .add(AllItems.WHISK.asItem())
                    .add(AllItems.PROPELLER.asItem())
                    .add(AllItems.VERTICAL_GEARBOX.asItem())
                    .add(AllBlocks.SHAFT.asItem());

            this.tag(CustomTags.WHEAT_GRAINS)
                    .addOptional(GTCEu.id("wheat_dust"))
                    .replace(true);
        }
    }
}
