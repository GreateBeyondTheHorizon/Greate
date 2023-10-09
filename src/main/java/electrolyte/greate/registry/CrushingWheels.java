package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlock;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class CrushingWheels {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredCrushingWheelBlock> CRUSHING_WHEELS = new ArrayList<>();

    public static final BlockEntry<TieredCrushingWheelBlock> ANDESITE_CRUSHING_WHEEL = crushingWheel("andesite_crushing_wheel", TIER.ULTRA_LOW, Greate.CONFIG.ULS.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> ANDESITE_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("andesite_crushing_wheel_controller", TIER.ULTRA_LOW, ANDESITE_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> STEEL_CRUSHING_WHEEL = crushingWheel("steel_crushing_wheel", TIER.LOW, Greate.CONFIG.LS.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> STEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("steel_crushing_wheel_controller", TIER.LOW, STEEL_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> ALUMINIUM_CRUSHING_WHEEL = crushingWheel("aluminium_crushing_wheel", TIER.MEDIUM, Greate.CONFIG.MS.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> ALUMINIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("aluminium_crushing_wheel_controller", TIER.MEDIUM, ALUMINIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> STAINLESS_STEEL_CRUSHING_WHEEL = crushingWheel("stainless_steel_crushing_wheel", TIER.HIGH, Greate.CONFIG.HS.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> STAINLESS_STEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("stainless_steel_crushing_wheel_controller", TIER.HIGH, STAINLESS_STEEL_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> TITANIUM_CRUSHING_WHEEL = crushingWheel("titanium_crushing_wheel", TIER.EXTREME, Greate.CONFIG.ES.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> TITANIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("titanium_crushing_wheel_controller", TIER.EXTREME, TITANIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> TUNGSTENSTEEL_CRUSHING_WHEEL = crushingWheel("tungstensteel_crushing_wheel", TIER.INSANE, Greate.CONFIG.IS.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> TUNGSTENSTEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("tungstensteel_crushing_wheel_controller", TIER.INSANE, TUNGSTENSTEEL_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> PALLADIUM_CRUSHING_WHEEL = crushingWheel("palladium_crushing_wheel", TIER.LUDICRIOUS, Greate.CONFIG.LUS.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> PALLADIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("palladium_crushing_wheel_controller", TIER.LUDICRIOUS, PALLADIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> NAQUADAH_CRUSHING_WHEEL = crushingWheel("naquadah_crushing_wheel", TIER.ZPM, Greate.CONFIG.ZPM.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> NAQUADAH_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("naquadah_crushing_wheel_controller", TIER.ZPM, NAQUADAH_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> DARMSTADTIUM_CRUSHING_WHEEL = crushingWheel("darmstadtium_crushing_wheel", TIER.ULTIMATE, Greate.CONFIG.US.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> DARMSTADTIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("darmstadtium_crushing_wheel_controller", TIER.ULTIMATE, DARMSTADTIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> NEUTRONIUM_CRUSHING_WHEEL = crushingWheel("neutronium_crushing_wheel", TIER.ULTIMATE_HIGH, Greate.CONFIG.UHS.CRUSHING_WHEEL_IMPACT);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> NEUTRONIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("neutronium_crushing_wheel_controller", TIER.ULTIMATE_HIGH, NEUTRONIUM_CRUSHING_WHEEL);

    public static BlockEntry<TieredCrushingWheelBlock> crushingWheel(String name, TIER tier, double stressImpact) {
        return REGISTRATE
                .block(name, TieredCrushingWheelBlock::new)
                .properties(p -> p.mapColor(MapColor.METAL))
                .initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(TagGen.pickaxeOnly())
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(GreateBuilderTransformers.tieredCrushingWheel())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static BlockEntry<TieredCrushingWheelControllerBlock> crushingWheelController(String name, TIER tier, BlockEntry<TieredCrushingWheelBlock> crushingWheel) {
        return REGISTRATE
                .block(name, p -> new TieredCrushingWheelControllerBlock(p, crushingWheel.get()))
                .properties(p -> p.mapColor(MapColor.STONE))
                .properties(p -> p.noOcclusion()
                        .noLootTable()
                        .air()
                        .noCollission()
                        .pushReaction(PushReaction.BLOCK))
                .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
