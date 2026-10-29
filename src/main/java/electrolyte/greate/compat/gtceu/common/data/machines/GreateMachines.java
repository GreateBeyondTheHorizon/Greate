package electrolyte.greate.compat.gtceu.common.data.machines;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.client.data.models.GreateMachineModels;
import electrolyte.greate.compat.gtceu.client.renderer.machine.GreateDynamicRenderHelper;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeModifiers;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;
import electrolyte.greate.compat.gtceu.common.data.blocks.GreateBlocks;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachineBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part.GreatePartAbility;
import electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part.StressPartMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part.TieredKineticBoxMachineBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.saw.TieredSawMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.saw.TieredSawMachineBlock;
import electrolyte.greate.content.kinetics.saw.TieredSawMovementBehaviour;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import electrolyte.greate.foundation.data.GreateTagGen;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.UHV;
import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.OVERLAY_ITEM_HATCH_INPUT;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.OVERLAY_ITEM_HATCH_OUTPUT;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;

public class GreateMachines {

    public static void register() {}

    public static final MachineDefinition[] MILLSTONES = registerTieredMachines("millstone", false,
            (properties, definition) -> new TieredMillstoneMachineBlock(definition),
            (info, tier) -> new TieredMillstoneMachine(info),
            (tier, builder) -> builder
                    .langValue(FormattingUtil.toEnglishName(GreateValues.TM[tier].getName()) + " Millstone")
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeModifier(GreateRecipeModifiers.OC_NON_PERFECT)
                    .addOutputLimit(ItemRecipeCapability.CAP, switch(tier) {
                        case 0 -> 1;
                        case 1 -> 2;
                        case 2 -> 3;
                        default -> 4;
                    })
                    .blockBuilder(b -> b
                            .transform(GStress.setImpact(0.5 * (tier + 1))) //TODO: change
                            .properties(p -> p.mapColor(MapColor.METAL)))
                    .model(GreateMachineModels.createMillstoneModel(tier)
                            .andThen(b -> b.addDynamicRenderer(GreateDynamicRenderHelper::createMillstoneCogRender)))
                    .itemBuilder(b -> b.model(GreateMachineModels.tieredMillstoneItemModel(tier)))
                    .register(),
            GTValues.tiersBetween(ULV, UHV),
            GreateRecipeTypes.MILLING);

    public static final MachineDefinition[] SAWS = registerTieredMachines("mechanical_saw", false,
            (properties, definition) -> new TieredSawMachineBlock(definition),
            (def, tier) -> new TieredSawMachine(def),
            (tier, builder) -> builder
                    .langValue(FormattingUtil.toEnglishName(GreateValues.TM[tier].getName()) + " Mechanical Saw")
                    .rotationState(RotationState.NONE)
                    .recipeModifier(GreateRecipeModifiers.OC_NON_PERFECT)
                    .modelProperty(SawBlock.FACING, Direction.UP)
                    .modelProperty(SawBlock.FLIPPED)
                    .modelProperty(DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE)
                    .blockBuilder(b -> b
                            .transform(GStress.setImpact(0.75 * (tier + 1)))
                            .properties(p -> p.mapColor(MapColor.PODZOL))
                            .onRegister(r -> movementBehaviour(new TieredSawMovementBehaviour())))//todo:check
                    .model(GreateMachineModels.createSawModel(tier)
                                    .andThen(b -> b.addDynamicRenderer(GreateDynamicRenderHelper::createSawRender)))
                    .itemBuilder(b -> b
                            .model(GreateMachineModels.tieredSawItemModel(tier))
                            .tag(GreateTagGen.MECHANICAL_SAWS))
                    .register(),
            GTValues.tiersBetween(ULV, UHV),
            GreateRecipeTypes.CUTTING, GreateRecipeTypes.KINETIC_STONECUTTING);

    public static final MachineDefinition[] KINETIC_INPUT_BOXES = registerTieredMachines("kinetic_input_box", false,
            ((properties, machineDefinition) -> new TieredKineticBoxMachineBlock(machineDefinition, GreateBlocks.KINETIC_INPUT_BOX)),
            (info, tier) -> new StressPartMachine(info, tier, IO.IN, new TieredKineticBlockEntity(info.type(), info.pos(), info.state())),
            (tier, builder) -> builder
                    .langValue(FormattingUtil.toEnglishName(GreateValues.TM[tier].getName()) + " Kinetic Input Box")
                    .rotationState(RotationState.ALL)
                    .blockProp(BlockBehaviour.Properties::dynamicShape)
                    .blockProp(BlockBehaviour.Properties::noOcclusion)
                    .blockBuilder(b -> b.transform(GStress.setNoImpact())) //todo: dynamic based on recipe
                    .colorOverlayTieredHullModel(OVERLAY_ITEM_HATCH_INPUT, "overlay_pipe", "overlay_pipe_in_emissive")
                    .tooltips(Component.translatable("greate.tooltip.kinetic_input_box"))
                    .abilities(GreatePartAbility.INPUT_KINETIC)
                    .model(GreateMachineModels.createKineticBoxModel(tier,false)
                            .andThen(b -> b.addDynamicRenderer(GreateDynamicRenderHelper::createKineticBoxShaftRender)))
                    .register(),
            GTValues.tiersBetween(ULV, UHV));

    public static final MachineDefinition[] KINETIC_OUTPUT_BOXES = registerTieredMachines("kinetic_output_box", false,
            ((properties, machineDefinition) -> new TieredKineticBoxMachineBlock(machineDefinition, GreateBlocks.KINETIC_OUTPUT_BOX)),
            (info, tier) -> new StressPartMachine(info, tier, IO.OUT, new TieredKineticBlockEntity(info.type(), info.pos(), info.state())),
            (tier, builder) -> builder
                    .langValue(FormattingUtil.toEnglishName(GreateValues.TM[tier].getName()) + " Kinetic Output Box")
                    .rotationState(RotationState.ALL)
                    .blockProp(BlockBehaviour.Properties::dynamicShape)
                    .blockProp(BlockBehaviour.Properties::noOcclusion)
                    .blockBuilder(b -> b.transform(GStress.setNoImpact())) //todo: dynamic based on recipe
                    .colorOverlayTieredHullModel(OVERLAY_ITEM_HATCH_OUTPUT, "overlay_pipe", "overlay_pipe_out_emissive")
                    .tooltips(Component.translatable("greate.tooltip.kinetic_output_box"))
                    .abilities(GreatePartAbility.OUTPUT_KINETIC)
                    .model(GreateMachineModels.createKineticBoxModel(tier,true)
                            .andThen(b -> b.addDynamicRenderer(GreateDynamicRenderHelper::createKineticBoxShaftRender)))
                    .register(),
            GTValues.tiersBetween(ULV, UHV));

    public static MachineDefinition[] registerTieredMachines(String name, boolean tempName,
                                                             BiFunction<Properties, MachineDefinition, MetaMachineBlock> blockFactory,
                                                             BiFunction<BlockEntityCreationInfo, Integer, MetaMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition, MetaMachine, ?>, MachineDefinition> builder,
                                                             int[] tiers,
                                                             GTRecipeType... recipeTypes) {
        MachineDefinition[] definitions = new MachineDefinition[tiers.length];
        for(int tier : tiers) {
            String machineName = !tempName ? GreateValues.TM[tier].getName().toLowerCase(Locale.ROOT) + "_" + name : GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name;
            var register = GreateRegistries.REGISTRATE
                    .machine(machineName, //todo: back to GreateValues.TM[tier].getName().toLowerCase(Locale.ROOT) + "_" + name once all machines have been converted
                            MachineDefinition::new,
                            blockFactory,
                            MetaMachineItem::new,
                            info -> factory.apply(info, tier))
                    .tier(tier)
                    .recipeTypes(recipeTypes)
                    .hasBER(true);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }
}
