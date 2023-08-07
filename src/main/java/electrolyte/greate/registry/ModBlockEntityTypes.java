package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.content.kinetics.simpleRelays.TieredBracketedKineticBlockEntity;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity> TIERED_SHAFT = REGISTRATE
            .blockEntity("tiered_shaft", TieredBracketedKineticBlockEntity::new)
            .instance(() -> BracketedKineticBlockEntityInstance::new, false)
            .validBlocks(ModBlocks.STEEL_SHAFT, ModBlocks.ALUMINIUM_SHAFT, ModBlocks.STAINLESS_STEEL_SHAFT,
                    ModBlocks.TITANIUM_SHAFT, ModBlocks.TUNGSTENSTEEL_SHAFT, ModBlocks.PALLADIUM_SHAFT,
                    ModBlocks.NAQUADAH_SHAFT, ModBlocks.DARMSTADTIUM_SHAFT, ModBlocks.NEUTRONIUM_SHAFT)
            .renderer(() -> BracketedKineticBlockEntityRenderer::new)
            .register();

    public static void register() {}
}
