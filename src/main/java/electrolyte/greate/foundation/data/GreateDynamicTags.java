package electrolyte.greate.foundation.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.serialization.JsonOps;
import com.simibubi.create.foundation.pack.DynamicPack;
import electrolyte.greate.Greate;
import electrolyte.greate.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;

import java.util.ArrayList;


public class GreateDynamicTags {

    public static void generateDynamicTags(DynamicPack dynamicPack) {
        Greate.LOGGER.info("Generating Dynamic Tags...");
        final Multimap<ResourceLocation, TagEntry> TAGS = HashMultimap.create();
        for(var entry : Belts.BELTS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Cogwheels.COGWHEELS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Cogwheels.ANDESITE_ENCASED_COGWHEELS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Cogwheels.BRASS_ENCASED_COGWHEELS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Cogwheels.LARGE_COGWHEELS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Cogwheels.ANDESITE_ENCASED_LARGE_COGWHEELS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Cogwheels.BRASS_ENCASED_LARGE_COGWHEELS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Girders.GIRDERS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Gearboxes.GEARBOXES.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Shafts.SHAFTS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Shafts.POWERED_SHAFTS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Shafts.ANDESITE_ENCASED_SHAFTS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }
        for(var entry : Shafts.BRASS_ENCASED_SHAFTS.values()) {
            TAGS.put(BlockTags.MINEABLE_WITH_PICKAXE.location(), TagEntry.element(entry.getId()));
            TAGS.put(BlockTags.MINEABLE_WITH_AXE.location(), TagEntry.element(entry.getId()));
        }

        TAGS.asMap().forEach((key, value) -> {
            TagFile file = new TagFile(new ArrayList<>(value), false);
            dynamicPack.put(key.withPrefix("tags/blocks/"), TagFile.CODEC.encodeStart(JsonOps.INSTANCE, file).result().orElseThrow());
        });
        Greate.LOGGER.info("Finished generating {} dynamic tags.", TAGS.size());
    }
}
