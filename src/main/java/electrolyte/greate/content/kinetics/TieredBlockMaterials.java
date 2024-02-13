package electrolyte.greate.content.kinetics;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

public class TieredBlockMaterials {

    public static final ArrayList<Pair<ResourceLocation, Material>> MATERIAL_FOR_BLOCK = new ArrayList<>();
    public static final ArrayList<Pair<ResourceLocation, Material>> MATERIAL_FOR_BELT_BLOCK = new ArrayList<>();

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setMaterialForBlock(Material material) {
        return b -> {
            MATERIAL_FOR_BLOCK.add(new Pair<>(new ResourceLocation(b.getOwner().getModid(), b.getName()), material));
            return b;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setMaterialForBeltBlock(Material material) {
        return b -> {
            MATERIAL_FOR_BELT_BLOCK.add(new Pair<>(new ResourceLocation(b.getOwner().getModid(), b.getName()), material));
            return b;
        };
    }
}
