package electrolyte.greate.compat.gtceu.common.data.machines;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
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
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachineBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.saw.TieredSawMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.saw.TieredSawMachineBlock;
import electrolyte.greate.content.kinetics.saw.TieredSawMovementBehaviour;
import electrolyte.greate.foundation.data.GreateTagGen;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.UHV;
import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;

public class GreateMachines {

    public static void register() {}

    public static MachineDefinition[] MILLSTONES = registerTieredMachines("millstone",
            TieredMillstoneMachineBlock::new,
            TieredMillstoneMachine::new,
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

    public static MachineDefinition[] SAWS = registerTieredMachines("mechanical_saw",
            TieredSawMachineBlock::new,
            TieredSawMachine::new,
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

    public static MachineDefinition[] registerTieredMachines(String name,
                                                             BiFunction<Properties, MachineDefinition, MetaMachineBlock> blockFactory,
                                                             BiFunction<BlockEntityCreationInfo, Integer, TieredKineticWorkableMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition, TieredKineticWorkableMachine, ?>, MachineDefinition> builder,
                                                             int[] tiers,
                                                             GTRecipeType... recipeTypes) {
        MachineDefinition[] definitions = new MachineDefinition[tiers.length];
        for(int tier : tiers) {
            var register = GreateRegistries.REGISTRATE
                    .machine(GreateValues.TM[tier].getName().toLowerCase(Locale.ROOT) + "_" + name,
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
