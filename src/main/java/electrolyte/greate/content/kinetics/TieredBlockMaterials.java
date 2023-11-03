package electrolyte.greate.content.kinetics;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.GreateEnums.BELT_TYPE;
import electrolyte.greate.GreateEnums.MATERIAL_TYPE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

public class TieredBlockMaterials {

    public static final ArrayList<Pair<ResourceLocation, MATERIAL_TYPE>> MATERIAL_FOR_BLOCK = new ArrayList<>();
    public static final ArrayList<Pair<ResourceLocation, BELT_TYPE>> BELT_TYPE_FOR_BLOCK = new ArrayList<>();

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setMaterialTypeForBlock(MATERIAL_TYPE materialType) {
        return b -> {
            MATERIAL_FOR_BLOCK.add(new Pair<>(new ResourceLocation(b.getOwner().getModid(), b.getName()), materialType));
            return b;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setBeltTypeForBlock(BELT_TYPE beltType) {
        return b -> {
            BELT_TYPE_FOR_BLOCK.add(new Pair<>(new ResourceLocation(b.getOwner().getModid(), b.getName()), beltType));
            return b;
        };
    }
}
