package electrolyte.greate.compat.gtceu.common.data.blocks;

import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.util.entry.BlockEntry;

import static electrolyte.greate.GreateRegistries.REGISTRATE;

public class GreateBlocks {

    public static void register() {}

    public static final BlockEntry<KineticBoxBlock> KINETIC_INPUT_BOX = REGISTRATE
            .block("kinetic_input_box", KineticBoxBlock::new)
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
				.forAllStatesExcept(BlockStateGen.mapToAir(p)))
            .register();

    public static final BlockEntry<KineticBoxBlock> KINETIC_OUTPUT_BOX = REGISTRATE
            .block("kinetic_output_box", KineticBoxBlock::new)
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
				.forAllStatesExcept(BlockStateGen.mapToAir(p)))
            .register();
}
