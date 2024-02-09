package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;

import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlock;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class CrushingWheels {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredCrushingWheelBlock> CRUSHING_WHEELS = new ArrayList<>();

    public static final BlockEntry<TieredCrushingWheelBlock> ANDESITE_CRUSHING_WHEEL = crushingWheel("andesite_crushing_wheel", ULV, TM[0], 0.5);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> ANDESITE_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("andesite_crushing_wheel_controller", ULV, ANDESITE_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> STEEL_CRUSHING_WHEEL = crushingWheel("steel_crushing_wheel", LV, TM[1], 1.0);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> STEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("steel_crushing_wheel_controller", LV, STEEL_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> ALUMINIUM_CRUSHING_WHEEL = crushingWheel("aluminium_crushing_wheel", MV, TM[2], 1.5);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> ALUMINIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("aluminium_crushing_wheel_controller", MV, ALUMINIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> STAINLESS_STEEL_CRUSHING_WHEEL = crushingWheel("stainless_steel_crushing_wheel", HV, TM[3], 2.0);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> STAINLESS_STEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("stainless_steel_crushing_wheel_controller", HV, STAINLESS_STEEL_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> TITANIUM_CRUSHING_WHEEL = crushingWheel("titanium_crushing_wheel", EV, TM[4], 2.5);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> TITANIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("titanium_crushing_wheel_controller", EV, TITANIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> TUNGSTENSTEEL_CRUSHING_WHEEL = crushingWheel("tungstensteel_crushing_wheel", IV, TM[5], 3.0);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> TUNGSTENSTEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("tungstensteel_crushing_wheel_controller", IV, TUNGSTENSTEEL_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> PALLADIUM_CRUSHING_WHEEL = crushingWheel("palladium_crushing_wheel", LuV, TM[6], 3.5);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> PALLADIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("palladium_crushing_wheel_controller", LuV, PALLADIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> NAQUADAH_CRUSHING_WHEEL = crushingWheel("naquadah_crushing_wheel", ZPM, TM[7], 4.0);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> NAQUADAH_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("naquadah_crushing_wheel_controller", ZPM, NAQUADAH_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> DARMSTADTIUM_CRUSHING_WHEEL = crushingWheel("darmstadtium_crushing_wheel", UV, TM[8], 4.5);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> DARMSTADTIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("darmstadtium_crushing_wheel_controller", UV, DARMSTADTIUM_CRUSHING_WHEEL);
    public static final BlockEntry<TieredCrushingWheelBlock> NEUTRONIUM_CRUSHING_WHEEL = crushingWheel("neutronium_crushing_wheel", UHV, TM[9], 5.0);
    public static final BlockEntry<TieredCrushingWheelControllerBlock> NEUTRONIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController("neutronium_crushing_wheel_controller", UHV, NEUTRONIUM_CRUSHING_WHEEL);

    public static BlockEntry<TieredCrushingWheelBlock> crushingWheel(String name, int tier, String materialType, double stressImpact) {
        return REGISTRATE
                .block(name, TieredCrushingWheelBlock::new)
                .properties(p -> p.mapColor(MapColor.METAL))
                .initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(TagGen.pickaxeOnly())
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(GreateBuilderTransformers.tieredCrushingWheel())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static BlockEntry<TieredCrushingWheelControllerBlock> crushingWheelController(String name, int tier, BlockEntry<TieredCrushingWheelBlock> crushingWheel) {
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
