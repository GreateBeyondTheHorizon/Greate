package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.content.kinetics.simpleRelays.TieredBracketedKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.TieredBracketedKineticBlockEntityInstance;
import electrolyte.greate.content.kinetics.simpleRelays.TieredBracketedKineticBlockEntityRenderer;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity> TIERED_KINETIC = REGISTRATE
            .blockEntity("tiered_kinetic", TieredBracketedKineticBlockEntity::new)
            .instance(() -> TieredBracketedKineticBlockEntityInstance::new, false)
            .validBlocks(Shafts.ANDESITE_SHAFT, Shafts.STEEL_SHAFT, Shafts.ALUMINIUM_SHAFT, Shafts.STAINLESS_STEEL_SHAFT,
                    Shafts.TITANIUM_SHAFT, Shafts.TUNGSTENSTEEL_SHAFT, Shafts.PALLADIUM_SHAFT, Shafts.NAQUADAH_SHAFT,
                    Shafts.DARMSTADTIUM_SHAFT, Shafts.NEUTRONIUM_SHAFT, Cogwheels.ANDESITE_COGWHEEL, Cogwheels.LARGE_ANDESITE_COGWHEEL,
                    Cogwheels.STEEL_COGWHEEL, Cogwheels.LARGE_STEEL_COGWHEEL, Cogwheels.ALUMINIUM_COGWHEEL, Cogwheels.LARGE_ALUMINIUM_COGWHEEL,
                    Cogwheels.STAINLESS_STEEL_COGWHEEL, Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL, Cogwheels.TITANIUM_COGWHEEL, Cogwheels.LARGE_TITANIUM_COGWHEEL,
                    Cogwheels.TUNGSTENSTEEL_COGWHEEL, Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL, Cogwheels.PALLADIUM_COGWHEEL, Cogwheels.LARGE_PALLADIUM_COGWHEEL,
                    Cogwheels.NAQUADAH_COGWHEEL, Cogwheels.LARGE_NAQUADAH_COGWHEEL, Cogwheels.DARMSTADTIUM_COGWHEEL, Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL,
                    Cogwheels.NEUTRONIUM_COGWHEEL, Cogwheels.LARGE_NEUTRONIUM_COGWHEEL
                    )
            .renderer(() -> TieredBracketedKineticBlockEntityRenderer::new)
            .register();
    public static final BlockEntityEntry<TieredKineticBlockEntity> TIERED_ENCASED_SHAFT = REGISTRATE
            .blockEntity("tiered_encased_shaft", TieredKineticBlockEntity::new)
            .instance(() -> ShaftInstance::new, false)
            .validBlocks(Shafts.ANDESITE_ENCASED_ANDESITE_SHAFT, Shafts.BRASS_ENCASED_ANDESITE_SHAFT, Shafts.ANDESITE_ENCASED_STEEL_SHAFT, Shafts.BRASS_ENCASED_STEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_ALUMINIUM_SHAFT, Shafts.BRASS_ENCASED_ALUMINIUM_SHAFT, Shafts.ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT, Shafts.BRASS_ENCASED_STAINLESS_STEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_TITANIUM_SHAFT, Shafts.BRASS_ENCASED_TITANIUM_SHAFT, Shafts.ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT, Shafts.BRASS_ENCASED_TUNGSTENSTEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_PALLADIUM_SHAFT, Shafts.BRASS_ENCASED_PALLADIUM_SHAFT, Shafts.ANDESITE_ENCASED_NAQUADAH_SHAFT, Shafts.BRASS_ENCASED_NAQUADAH_SHAFT,
                    Shafts.ANDESITE_ENCASED_DARMSTADTIUM_SHAFT, Shafts.BRASS_ENCASED_DARMSTADTIUM_SHAFT, Shafts.ANDESITE_ENCASED_NEUTRONIUM_SHAFT, Shafts.BRASS_ENCASED_NEUTRONIUM_SHAFT)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static void register() {}
}
