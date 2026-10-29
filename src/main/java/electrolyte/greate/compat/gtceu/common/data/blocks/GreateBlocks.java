package electrolyte.greate.compat.gtceu.common.data.blocks;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import static electrolyte.greate.GreateRegistries.REGISTRATE;

public class GreateBlocks {

    public static void register() {}

    public static final BlockEntry<KineticBoxBlock> KINETIC_INPUT_BOX = REGISTRATE
            .block("kinetic_input_box", KineticBoxBlock::new)
            .blockstate(NonNullBiConsumer.noop())
            .register();

    public static final BlockEntry<KineticBoxBlock> KINETIC_OUTPUT_BOX = REGISTRATE
            .block("kinetic_output_box", KineticBoxBlock::new)
            .blockstate(NonNullBiConsumer.noop())
            .register();
}
