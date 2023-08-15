package electrolyte.greate.content.decoration.encasing;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;

public class GirderEncasingRegistry {

    private static final Map<Block, List<Block>> GIRDER_ENCASED_TYPE = new HashMap<>();

    public static <B extends Block & IGirderEncasableBlock, E extends Block & IGirderEncasedBlock> void addVariant(B encasable, E encased) {
        GIRDER_ENCASED_TYPE.computeIfAbsent(encasable, b -> new ArrayList<>()).add(encased);
    }

    public static List<Block> get(Block block) {
        return GIRDER_ENCASED_TYPE.getOrDefault(block, Collections.emptyList());
    }

    public static <B extends Block & IGirderEncasedBlock, P, E extends Block & IGirderEncasableBlock> NonNullUnaryOperator<BlockBuilder<B, P>> addVariantTo(Supplier<E> encasable) {
        return builder -> {
            builder.onRegisterAfter(ForgeRegistries.BLOCKS.getRegistryKey(), b -> addVariant(encasable.get(), b));
            return builder;
        };
    }
}
