package electrolyte.greate.content.kinetics;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

public class TieredBlockMaterials {

    public static final ArrayList<Pair<ResourceLocation, String>> MATERIAL_FOR_BLOCK = new ArrayList<>();
    public static final ArrayList<Pair<ResourceLocation, String>> BELT_TYPE_FOR_BLOCK = new ArrayList<>();

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setMaterialTypeForBlock(String materialType) {
        return b -> {
            MATERIAL_FOR_BLOCK.add(new Pair<>(new ResourceLocation(b.getOwner().getModid(), b.getName()), materialType));
            return b;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setBeltTypeForBlock(String beltType) {
        return b -> {
            BELT_TYPE_FOR_BLOCK.add(new Pair<>(new ResourceLocation(b.getOwner().getModid(), b.getName()), beltType));
            return b;
        };
    }
}
