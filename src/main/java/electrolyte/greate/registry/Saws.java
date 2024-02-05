package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.MATERIAL_TYPE;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.saw.TieredSawBlock;
import electrolyte.greate.content.kinetics.saw.TieredSawGenerator;
import electrolyte.greate.content.kinetics.saw.TieredSawMovementBehaviour;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static electrolyte.greate.Greate.REGISTRATE;

public class Saws {

    public static final ArrayList<TieredSawBlock> SAWS = new ArrayList<>();

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredSawBlock> ANDESITE_SAW = saw("andesite_mechanical_saw", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, Shafts.ANDESITE_SHAFT, 1.0, GreatePartialModels.ANDESITE_SHAFT_HALF,
            GreatePartialModels.ANDESITE_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.ANDESITE_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.ANDESITE_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.ANDESITE_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.ANDESITE_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.ANDESITE_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> STEEL_SAW = saw("steel_mechanical_saw", TIER.LOW, MATERIAL_TYPE.STEEL, Shafts.STEEL_SHAFT, 2.0, GreatePartialModels.STEEL_SHAFT_HALF,
            GreatePartialModels.STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.STEEL_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.STEEL_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.STEEL_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> ALUMINIUM_SAW = saw("aluminium_mechanical_saw", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, Shafts.ALUMINIUM_SHAFT, 3.0, GreatePartialModels.ALUMINIUM_SHAFT_HALF,
            GreatePartialModels.ALUMINIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.ALUMINIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.ALUMINIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.ALUMINIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.ALUMINIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.ALUMINIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> STAINLESS_STEEL_SAW = saw("stainless_steel_mechanical_saw", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, Shafts.STAINLESS_STEEL_SHAFT, 4.0, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF,
            GreatePartialModels.STAINLESS_STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.STAINLESS_STEEL_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> TITANIUM_SAW = saw("titanium_mechanical_saw", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, Shafts.TITANIUM_SHAFT, 5.0, GreatePartialModels.TITANIUM_SHAFT_HALF,
            GreatePartialModels.TITANIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.TITANIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.TITANIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.TITANIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.TITANIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.TITANIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> TUNGSTENSTEEL_SAW = saw("tungstensteel_mechanical_saw", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, Shafts.TUNGSTENSTEEL_SHAFT, 6.0, GreatePartialModels.TUNGSTENSTEEL_SHAFT_HALF,
            GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> PALLADIUM_SAW = saw("palladium_mechanical_saw", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, Shafts.PALLADIUM_SHAFT, 7.0, GreatePartialModels.PALLADIUM_SHAFT_HALF,
            GreatePartialModels.PALLADIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.PALLADIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.PALLADIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.PALLADIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.PALLADIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.PALLADIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> NAQUADAH_SAW = saw("naquadah_mechanical_saw", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, Shafts.NAQUADAH_SHAFT, 8.0, GreatePartialModels.NAQUADAH_SHAFT_HALF,
            GreatePartialModels.NAQUADAH_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.NAQUADAH_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.NAQUADAH_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.NAQUADAH_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.NAQUADAH_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.NAQUADAH_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> DARMSTADTIUM_SAW = saw("darmstadtium_mechanical_saw", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, Shafts.DARMSTADTIUM_SHAFT, 9.0, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF,
            GreatePartialModels.DARMSTADTIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.DARMSTADTIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.DARMSTADTIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.DARMSTADTIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.DARMSTADTIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.DARMSTADTIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);

    public static final BlockEntry<TieredSawBlock> NEUTRONIUM_SAW = saw("neutronium_mechanical_saw", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, Shafts.NEUTRONIUM_SHAFT, 10.0, GreatePartialModels.NEUTRONIUM_SHAFT_HALF,
            GreatePartialModels.NEUTRONIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE, GreatePartialModels.NEUTRONIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED, GreatePartialModels.NEUTRONIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE,
            GreatePartialModels.NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE, GreatePartialModels.NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED, GreatePartialModels.NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE);




    public static BlockEntry<TieredSawBlock> saw(String name, TIER tier, MATERIAL_TYPE materialType, BlockEntry<TieredShaftBlock> shaftBlock, double stressImpact, PartialModel halfShaftModel, PartialModel... sawModels) {
        return REGISTRATE
                .block(name, p -> new TieredSawBlock(p, shaftBlock.get(), halfShaftModel, sawModels))
                .initialProperties(SharedProperties::stone)
                .addLayer(() -> RenderType::cutoutMipped)
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(TagGen.axeOrPickaxe())
                .blockstate(new TieredSawGenerator()::generateModel)
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(movementBehaviour(new TieredSawMovementBehaviour()))
                .onRegister(c -> c.setTier(tier))
                .addLayer(() -> RenderType::cutoutMipped)
                .item()
                .model(GreateBuilderTransformers::tieredSaw)
                .tag(AllItemTags.CONTRAPTION_CONTROLLED.tag).build()
                .register();
    }



    public static void register() {}
}
