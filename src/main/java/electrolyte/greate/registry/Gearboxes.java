package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;

import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlock;
import electrolyte.greate.content.kinetics.gearbox.TieredVerticalGearboxItem;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class Gearboxes {

    // Gearbox
    public static final BlockEntry<TieredGearboxBlock>[] GEARBOXES = new BlockEntry[10];
    public static BlockEntry<TieredGearboxBlock>
            ANDESITE_GEARBOX,
            STEEL_GEARBOX,
            ALUMINIUM_GEARBOX,
            STAINLESS_STEEL_GEARBOX,
            TITANIUM_GEARBOX,
            TUNGSTENSTEEL_GEARBOX,
            PALLADIUM_GEARBOX,
            NAQUADAH_GEARBOX,
            DARMSTADTIUM_GEARBOX,
            NEUTRONIUM_GEARBOX;

    // Vertical gearbox
    public static final ItemEntry<TieredVerticalGearboxItem>[] VERTICAL_GEARBOXES = new ItemEntry[10];
    public static ItemEntry<TieredVerticalGearboxItem>
            ANDESITE_VERTICAL_GEARBOX,
            STEEL_VERTICAL_GEARBOX,
            ALUMINIUM_VERTICAL_GEARBOX,
            STAINLESS_STEEL_VERTICAL_GEARBOX,
            TITANIUM_VERTICAL_GEARBOX,
            TUNGSTENSTEEL_VERTICAL_GEARBOX,
            PALLADIUM_VERTICAL_GEARBOX,
            NAQUADAH_VERTICAL_GEARBOX,
            DARMSTADTIUM_VERTICAL_GEARBOX,
            NEUTRONIUM_VERTICAL_GEARBOX;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        // Gearbox
        GEARBOXES[ULV] = ANDESITE_GEARBOX = gearbox(ULV, GreatePartialModels.ANDESITE_SHAFT_HALF);
        GEARBOXES[LV] = STEEL_GEARBOX = gearbox(LV, GreatePartialModels.STEEL_SHAFT_HALF);
        GEARBOXES[MV] = ALUMINIUM_GEARBOX = gearbox( MV, GreatePartialModels.ALUMINIUM_SHAFT_HALF);
        GEARBOXES[HV] = STAINLESS_STEEL_GEARBOX = gearbox(HV, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF);
        GEARBOXES[EV] = TITANIUM_GEARBOX = gearbox(EV, GreatePartialModels.TITANIUM_SHAFT_HALF);
        GEARBOXES[IV] = TUNGSTENSTEEL_GEARBOX = gearbox(IV, GreatePartialModels.TUNGSTENSTEEL_SHAFT_HALF);
        GEARBOXES[LuV] = PALLADIUM_GEARBOX = gearbox(LuV, GreatePartialModels.PALLADIUM_SHAFT_HALF);
        GEARBOXES[ZPM] = NAQUADAH_GEARBOX = gearbox(ZPM, GreatePartialModels.NAQUADAH_SHAFT_HALF);
        GEARBOXES[UV] = DARMSTADTIUM_GEARBOX = gearbox(UV, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF);
        GEARBOXES[UHV] = NEUTRONIUM_GEARBOX = gearbox(UHV, GreatePartialModels.NEUTRONIUM_SHAFT_HALF);

        // Vertical gearbox
        VERTICAL_GEARBOXES[ULV] = ANDESITE_VERTICAL_GEARBOX = verticalGearbox(ULV);
        VERTICAL_GEARBOXES[LV] = STEEL_VERTICAL_GEARBOX = verticalGearbox(LV);
        VERTICAL_GEARBOXES[MV] = ALUMINIUM_VERTICAL_GEARBOX = verticalGearbox(MV);
        VERTICAL_GEARBOXES[HV] = STAINLESS_STEEL_VERTICAL_GEARBOX = verticalGearbox(HV);
        VERTICAL_GEARBOXES[EV] = TITANIUM_VERTICAL_GEARBOX = verticalGearbox(EV);
        VERTICAL_GEARBOXES[IV] = TUNGSTENSTEEL_VERTICAL_GEARBOX = verticalGearbox(IV);
        VERTICAL_GEARBOXES[LuV] = PALLADIUM_VERTICAL_GEARBOX = verticalGearbox(LuV);
        VERTICAL_GEARBOXES[ZPM] = NAQUADAH_VERTICAL_GEARBOX = verticalGearbox(ZPM);
        VERTICAL_GEARBOXES[UV] = DARMSTADTIUM_VERTICAL_GEARBOX = verticalGearbox(UV);
        VERTICAL_GEARBOXES[UHV] = NEUTRONIUM_VERTICAL_GEARBOX = verticalGearbox(UHV);
    }

    private static BlockEntry<TieredGearboxBlock> gearbox(int tier, PartialModel halfShaftModel) {
        return gearbox(tier, TM[tier], halfShaftModel);
    }

    public static BlockEntry<TieredGearboxBlock> gearbox(int tier, Material material, PartialModel halfShaftModel) {
        return REGISTRATE
                .block(material.getName() + "_gearbox", p -> new TieredGearboxBlock(p, halfShaftModel))
                .initialProperties(SharedProperties::stone)
                .properties(Properties::noOcclusion)
                .properties(p -> p.mapColor(MapColor.PODZOL).pushReaction(PushReaction.PUSH_ONLY))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(GreateBuilderTransformers.tieredGearbox())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
                .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                        (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    private static ItemEntry<TieredVerticalGearboxItem> verticalGearbox(int tier) {
        return verticalGearbox(TM[tier].getName() + "_vertical_gearbox", GEARBOXES[tier]);
    }

    public static ItemEntry<TieredVerticalGearboxItem> verticalGearbox(String name, BlockEntry<TieredGearboxBlock> gearbox) {
        return REGISTRATE
                .item(name, p -> new TieredVerticalGearboxItem(p, gearbox.get()))
                .transform(GreateBuilderTransformers.tieredGearboxVertical())
                .register();
    }
}
