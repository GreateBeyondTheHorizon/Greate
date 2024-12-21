package electrolyte.greate.compat.gtceu.machine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.simibubi.create.AllBlocks;
import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.CASING_WATERTIGHT;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static electrolyte.greate.GreateRegistries.REGISTRATE;
import static electrolyte.greate.compat.gtceu.api.capability.recipe.GreateRecipeTypes.WIRE_COATING_RECIPES;

public class GreateMultiblockMachines {

    public static void register() {}

    public static final MultiblockMachineDefinition WIRE_COATING_FACTORY = REGISTRATE
            .multiblock("wire_coating_factory", CoilWorkableElectricMultiblockMachine::new)
            .langValue("Wire Coating Factory")
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.wire_coating")))
            .rotationState(RotationState.ALL)
            .recipeType(WIRE_COATING_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(CASING_WATERTIGHT)
            .pattern(d -> FactoryBlockPattern.start()
                    .aisle("XXXXX", "XXGXX", "XXGXX", "XXGXX", "XXXXX")
                    .aisle("XXXXX", "XA#AX", "XA#AX", "XA#AX", "XXXXX")
                    .aisle("XXXXX", "G#D#G", "G###G", "G#P#G", "XXXXX")
                    .aisle("XXXXX", "XA#AX", "XA#AX", "XA#AX", "XXXXX")
                    .aisle("XXSXX", "XXGXX", "XXGXX", "XXGXX", "XXXXX")
                    .where('S', controller(blocks(d.get())))
                    .where('X', blocks(CASING_WATERTIGHT.get()).setMinGlobalLimited(70)
                            .or(autoAbilities(d.getRecipeTypes()))
                            .or(autoAbilities(true, false, true)))
                    .where('C', blocks(CASING_TITANIUM_GEARBOX.get()))
                    .where('G', blocks(CASING_TEMPERED_GLASS.get()))
                    .where('A', blocks(CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                    .where('#', Predicates.air())
                    .where('D', blocks(AllBlocks.DEPOT.get()))
                    .where('P', blocks(AllBlocks.SPOUT.get()))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/gcym/watertight_casing"),
                    GTCEu.id("block/multiblock/gcym/large_wiremill"))
            .register();
}
