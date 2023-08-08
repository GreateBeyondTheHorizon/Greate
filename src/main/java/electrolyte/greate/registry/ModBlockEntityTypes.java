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
            .validBlocks(Shafts.ANDESITE_SHAFT, Shafts.STEEL_SHAFT, Shafts.ALUMINIUM_SHAFT, Shafts.STAINLESS_STEEL_SHAFT,
                    Shafts.TITANIUM_SHAFT, Shafts.TUNGSTENSTEEL_SHAFT, Shafts.PALLADIUM_SHAFT, Shafts.NAQUADAH_SHAFT,
                    Shafts.DARMSTADTIUM_SHAFT, Shafts.NEUTRONIUM_SHAFT, Cogwheels.ANDESITE_COGWHEEL, Cogwheels.LARGE_ANDESITE_COGWHEEL,
                    Cogwheels.STEEL_COGWHEEL, Cogwheels.LARGE_STEEL_COGWHEEL, Cogwheels.ALUMINIUM_COGWHEEL, Cogwheels.LARGE_ALUMINIUM_COGWHEEL,
                    Cogwheels.STAINLESS_STEEL_COGWHEEL, Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL, Cogwheels.TITANIUM_COGWHEEL, Cogwheels.LARGE_TITANIUM_COGWHEEL,
                    Cogwheels.TUNGSTENSTEEL_COGWHEEL, Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL, Cogwheels.PALLADIUM_COGWHEEL, Cogwheels.LARGE_PALLADIUM_COGWHEEL,
                    Cogwheels.NAQUADAH_COGWHEEL, Cogwheels.LARGE_NAQUADAH_COGWHEEL, Cogwheels.DARMSTADTIUM_COGWHEEL, Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL,
                    Cogwheels.NEUTRONIUM_COGWHEEL, Cogwheels.LARGE_NEUTRONIUM_COGWHEEL
                    )
            .renderer(() -> BracketedKineticBlockEntityRenderer::new)
            .register();

    public static void register() {}
}
