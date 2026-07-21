package electrolyte.greate.compat.gtceu.common.data.machines;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.compat.gtceu.client.data.models.GreateMachineModels;
import electrolyte.greate.compat.gtceu.client.renderer.machine.GreateDynamicRenderHelper;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeModifiers;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachineBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.saw.TieredSawMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.saw.TieredSawMachineBlock;
import electrolyte.greate.infrastructure.config.GStress;

import java.util.Locale;
import java.util.function.BiFunction;

public class GreateMachines {

    public static void register() {}

    public static MachineDefinition[] MILLSTONES = registerMillstoneMachines("millstone",
            TieredMillstoneMachine::new,
            (tier, builder) -> builder
                    .langValue(GTValues.VNF[tier] + " Millstone")
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeModifier(GreateRecipeModifiers::stressRequired)
                    .register(),
            GreateRecipeTypes.MILLSTONE,
            GTMachineUtils.ALL_TIERS);

    public static MachineDefinition[] SAWS = registerSawMachines("saw",
            TieredSawMachine::new,
            (tier, builder) -> builder
                    .langValue(GTValues.VNF[tier] + " Mechanical Saw")
                    .rotationState(RotationState.NONE)
                    .modelProperty(SawBlock.FACING)
                    .modelProperty(SawBlock.FLIPPED)
                    .modelProperty(DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE)
                    .register(),
            GreateRecipeTypes.SAW,
            GTMachineUtils.ALL_TIERS);

    public static MachineDefinition[] registerMillstoneMachines(String name,
                                                                BiFunction<BlockEntityCreationInfo, Integer, TieredKineticWorkableMachine> factory,
                                                                BiFunction<Integer, MachineBuilder<MachineDefinition, TieredKineticWorkableMachine, ?>, MachineDefinition> builder,
                                                                GTRecipeType recipeType,
                                                                int[] tiers) {
        MachineDefinition[] definitions = new MachineDefinition[GTValues.TIER_COUNT];
        for(int tier : tiers) {
            var register = GreateRegistries.REGISTRATE
                    .machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name,
                            MachineDefinition::new,
                            TieredMillstoneMachineBlock::new,
                            MetaMachineItem::new,
                            info -> factory.apply(info, tier))
                    .blockBuilder(b ->
                            b.transform(GStress.setImpact(0.5 * (tier + 1)))) //TODO: change
                    .model(GreateMachineModels.createMillstoneModel()
                            .andThen(b -> b.addDynamicRenderer(GreateDynamicRenderHelper::createMillstoneCogRender)))
                    .itemBuilder(b -> b.model(GreateMachineModels.tieredMillstoneItemModel(tier)))
                    .tier(tier)
                    .recipeType(recipeType)
                    .hasBER(true);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }

    public static MachineDefinition[] registerSawMachines(String name,
                                                             BiFunction<BlockEntityCreationInfo, Integer, TieredKineticWorkableMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition, TieredKineticWorkableMachine, ?>, MachineDefinition> builder,
                                                             GTRecipeType recipeType, int[] tiers) {
        MachineDefinition[] definitions = new MachineDefinition[GTValues.TIER_COUNT];
        for(int tier : tiers) {
            var register = GreateRegistries.REGISTRATE
                    .machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name,
                            MachineDefinition::new,
                            TieredSawMachineBlock::new,
                            MetaMachineItem::new,
                            info -> factory.apply(info, tier))
                    .blockBuilder(b ->
                            b.transform(GStress.setImpact(0.5 * (tier + 1))))
                    .model(GreateMachineModels.createSawModel()
                            .andThen(b -> b.addDynamicRenderer(GreateDynamicRenderHelper::createSawRender)))
                    .itemBuilder(b -> b.model(GreateMachineModels.tieredSawItemModel(tier)))
                    .tier(tier)
                    .recipeType(recipeType)
                    .hasBER(true);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }
}
