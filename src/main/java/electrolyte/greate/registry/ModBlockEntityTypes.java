package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.content.kinetics.simpleRelays.TieredBracketedKineticBlockEntity;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity> TIERED_KINETIC = REGISTRATE
            .blockEntity("tiered_kinetic", TieredBracketedKineticBlockEntity::new)
            .instance(() -> BracketedKineticBlockEntityInstance::new, false)
            .validBlocks(ModBlocks.ANDESITE_SHAFT, ModBlocks.STEEL_SHAFT, ModBlocks.ALUMINIUM_SHAFT, ModBlocks.STAINLESS_STEEL_SHAFT,
                    ModBlocks.TITANIUM_SHAFT, ModBlocks.TUNGSTENSTEEL_SHAFT, ModBlocks.PALLADIUM_SHAFT, ModBlocks.NAQUADAH_SHAFT,
                    ModBlocks.DARMSTADTIUM_SHAFT, ModBlocks.NEUTRONIUM_SHAFT, ModBlocks.ANDESITE_COGWHEEL, ModBlocks.LARGE_ANDESITE_COGWHEEL,
                    ModBlocks.STEEL_COGWHEEL, ModBlocks.LARGE_STEEL_COGWHEEL, ModBlocks.ALUMINIUM_COGWHEEL, ModBlocks.LARGE_ALUMINIUM_COGWHEEL,
                    ModBlocks.STAINLESS_STEEL_COGWHEEL, ModBlocks.LARGE_STAINLESS_STEEL_COGWHEEL, ModBlocks.TITANIUM_COGWHEEL, ModBlocks.LARGE_TITANIUM_COGWHEEL,
                    ModBlocks.TUNGSTENSTEEL_COGWHEEL, ModBlocks.LARGE_TUNGSTENSTEEL_COGWHEEL, ModBlocks.PALLADIUM_COGWHEEL, ModBlocks.LARGE_PALLADIUM_COGWHEEL,
                    ModBlocks.NAQUADAH_COGWHEEL, ModBlocks.LARGE_NAQUADAH_COGWHEEL, ModBlocks.DARMSTADTIUM_COGWHEEL, ModBlocks.LARGE_DARMSTADTIUM_COGWHEEL,
                    ModBlocks.NEUTRONIUM_COGWHEEL, ModBlocks.LARGE_NEUTRONIUM_COGWHEEL
                    )
            .renderer(() -> BracketedKineticBlockEntityRenderer::new)
            .register();

    public static void register() {}
}
