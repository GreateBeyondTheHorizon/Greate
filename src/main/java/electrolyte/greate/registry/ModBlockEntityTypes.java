package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.be.TieredBracketedKineticBlockEntity;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity.ANDESITE> ANDESITE_SHAFT = REGISTRATE
            .blockEntity("andesite_shaft", TieredBracketedKineticBlockEntity.ANDESITE::new)
            .instance(() -> BracketedKineticBlockEntityInstance::new, false)
            .validBlocks(ModBlocks.ANDESITE_SHAFT)
            .renderer(() -> BracketedKineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity.BRASS> BRASS_SHAFT = REGISTRATE
            .blockEntity("brass_shaft", TieredBracketedKineticBlockEntity.BRASS::new)
            .instance(() -> BracketedKineticBlockEntityInstance::new, false)
            .validBlocks(ModBlocks.BRASS_SHAFT)
            .renderer(() -> BracketedKineticBlockEntityRenderer::new)
            .register();

    public static void register() {}
}
