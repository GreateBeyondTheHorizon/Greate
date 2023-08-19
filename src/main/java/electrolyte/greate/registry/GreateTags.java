package electrolyte.greate.registry;

import com.simibubi.create.AllTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class GreateTags extends AllTags {

    public static <T> TagKey<T> modTag(IForgeRegistry<T> registry, ResourceLocation tagLocation) {
        return optionalTag(registry, tagLocation);
    }

    public static TagKey<Block> gregtechBlockTag(String location) {
        return modTag(ForgeRegistries.BLOCKS, new ResourceLocation("gtceu", location));
    }

    public static TagKey<Item> gregtechItemTag(String location) {
        return modTag(ForgeRegistries.ITEMS, new ResourceLocation("gtceu", location));
    }

    public static TagKey<Fluid> gregtechFluidTag(String location) {
        return modTag(ForgeRegistries.FLUIDS, new ResourceLocation("gtceu", location));
    }

    public static TagKey<Block> modBlockTag(ResourceLocation tagLocation) {
        return modTag(ForgeRegistries.BLOCKS, tagLocation);
    }

    public static TagKey<Item> modItemTag(ResourceLocation tagLocation) {
        return modTag(ForgeRegistries.ITEMS, tagLocation);
    }

    public static TagKey<Fluid> modFluidTag(ResourceLocation tagLocation) {
        return modTag(ForgeRegistries.FLUIDS, tagLocation);
    }
}
