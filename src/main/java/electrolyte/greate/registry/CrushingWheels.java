package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
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

    public static BlockEntry<TieredCrushingWheelBlock>[] CRUSHING_WHEELS = new BlockEntry[10];
    public static BlockEntry<TieredCrushingWheelBlock>
            ANDESITE_CRUSHING_WHEEL,
            STEEL_CRUSHING_WHEEL,
            ALUMINIUM_CRUSHING_WHEEL,
            STAINLESS_STEEL_CRUSHING_WHEEL,
            TITANIUM_CRUSHING_WHEEL,
            TUNGSTENSTEEL_CRUSHING_WHEEL,
            PALLADIUM_CRUSHING_WHEEL,
            NAQUADAH_CRUSHING_WHEEL,
            DARMSTADTIUM_CRUSHING_WHEEL,
            NEUTRONIUM_CRUSHING_WHEEL;

    public static BlockEntry<TieredCrushingWheelControllerBlock>[] CRUSHING_WHEEL_CONTROLLERS = new BlockEntry[10];
    public static BlockEntry<TieredCrushingWheelControllerBlock>
            ANDESITE_CRUSHING_WHEEL_CONTROLLER,
            STEEL_CRUSHING_WHEEL_CONTROLLER,
            ALUMINIUM_CRUSHING_WHEEL_CONTROLLER,
            STAINLESS_STEEL_CRUSHING_WHEEL_CONTROLLER,
            TITANIUM_CRUSHING_WHEEL_CONTROLLER,
            TUNGSTENSTEEL_CRUSHING_WHEEL_CONTROLLER,
            PALLADIUM_CRUSHING_WHEEL_CONTROLLER,
            NAQUADAH_CRUSHING_WHEEL_CONTROLLER,
            DARMSTADTIUM_CRUSHING_WHEEL_CONTROLLER,
            NEUTRONIUM_CRUSHING_WHEEL_CONTROLLER;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        CRUSHING_WHEELS[ULV] = ANDESITE_CRUSHING_WHEEL = crushingWheel(ULV, 0.5);
        CRUSHING_WHEELS[LV] = STEEL_CRUSHING_WHEEL = crushingWheel(LV, 1.0);
        CRUSHING_WHEELS[MV] = ALUMINIUM_CRUSHING_WHEEL = crushingWheel( MV, 1.5);
        CRUSHING_WHEELS[HV] = STAINLESS_STEEL_CRUSHING_WHEEL = crushingWheel(HV, 2.0);
        CRUSHING_WHEELS[EV] = TITANIUM_CRUSHING_WHEEL = crushingWheel(EV, 2.5);
        CRUSHING_WHEELS[IV] = TUNGSTENSTEEL_CRUSHING_WHEEL = crushingWheel(IV, 3.0);
        CRUSHING_WHEELS[LuV] = PALLADIUM_CRUSHING_WHEEL = crushingWheel(LuV, 3.5);
        CRUSHING_WHEELS[ZPM] = NAQUADAH_CRUSHING_WHEEL = crushingWheel(ZPM, 4.0);
        CRUSHING_WHEELS[UV] = DARMSTADTIUM_CRUSHING_WHEEL = crushingWheel(UV, 4.5);
        CRUSHING_WHEELS[UHV] = NEUTRONIUM_CRUSHING_WHEEL = crushingWheel(UHV, 5.0);

        CRUSHING_WHEEL_CONTROLLERS[ULV] = ANDESITE_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(ULV);
        CRUSHING_WHEEL_CONTROLLERS[LV] = STEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(LV);
        CRUSHING_WHEEL_CONTROLLERS[MV] = ALUMINIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(MV);
        CRUSHING_WHEEL_CONTROLLERS[HV] = STAINLESS_STEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(HV);
        CRUSHING_WHEEL_CONTROLLERS[EV] = TITANIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(EV);
        CRUSHING_WHEEL_CONTROLLERS[IV] = TUNGSTENSTEEL_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(IV);
        CRUSHING_WHEEL_CONTROLLERS[LuV] = PALLADIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(LuV);
        CRUSHING_WHEEL_CONTROLLERS[ZPM] = NAQUADAH_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(ZPM);
        CRUSHING_WHEEL_CONTROLLERS[UV] = DARMSTADTIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(UV);
        CRUSHING_WHEEL_CONTROLLERS[UHV] = NEUTRONIUM_CRUSHING_WHEEL_CONTROLLER = crushingWheelController(UHV);
    }

    private static BlockEntry<TieredCrushingWheelBlock> crushingWheel(int tier, double stressImpact) {
        return crushingWheel(tier, TM[tier], stressImpact);
    }

    public static BlockEntry<TieredCrushingWheelBlock> crushingWheel(int tier, Material material, double stressImpact) {
        return REGISTRATE
                .block(material.getName() + "_crushing_wheel", TieredCrushingWheelBlock::new)
                .properties(p -> p.mapColor(MapColor.METAL))
                .initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(TagGen.pickaxeOnly())
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(GreateBuilderTransformers.tieredCrushingWheel())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    private static BlockEntry<TieredCrushingWheelControllerBlock> crushingWheelController(int tier) {
        return crushingWheelController(tier, TM[tier], CRUSHING_WHEELS[tier]);
    }

    public static BlockEntry<TieredCrushingWheelControllerBlock> crushingWheelController(int tier, Material material, BlockEntry<TieredCrushingWheelBlock> crushingWheel) {
        return REGISTRATE
                .block(material.getName() + "_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, crushingWheel.get()))
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
}
