package electrolyte.greate.compat.gtceu.common.data.machines;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.compat.gtceu.api.machine.multiblock.GreatePartAbility;
import electrolyte.greate.compat.gtceu.client.renderer.machine.SplitShaftTieredHullMachineRenderer;
import electrolyte.greate.compat.gtceu.common.block.TieredKineticMachineBlock;
import electrolyte.greate.compat.gtceu.common.blockentity.TieredKineticMachineBlockEntity;
import electrolyte.greate.compat.gtceu.common.machine.TieredKineticMachineDefinition;
import electrolyte.greate.compat.gtceu.common.machine.multiblock.part.TieredKineticPartMachine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.VLVH;
import static com.gregtechceu.gtceu.api.GTValues.VLVT;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.ELECTRIC_TIERS;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;

public class GreateMachines {
    public static final TieredKineticMachineDefinition[] KINETIC_INPUT_BOX = registerTieredMachines("kinetic_input_box",
            (tier, id) -> new TieredKineticMachineDefinition(id, false, GTValues.V[tier]).setFrontRotation(true),
            (holder, tier) -> new TieredKineticPartMachine(holder, tier, IO.IN),
            (tier, builder) -> builder
                    .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName("kinetic_input_box"), VLVT[tier]))
                    .rotationState(RotationState.ALL)
                    .blockProp(BlockBehaviour.Properties::dynamicShape)
                    .blockProp(BlockBehaviour.Properties::noOcclusion)
                    .abilities(GreatePartAbility.INPUT_KINETIC)
                    .renderer(() -> new SplitShaftTieredHullMachineRenderer(tier, Greate.id("block/machine/part/kinetic_input_box")))
                    .register(),
            ELECTRIC_TIERS);

    public static final TieredKineticMachineDefinition[] KINETIC_OUTPUT_BOX = registerTieredMachines("kinetic_output_box",
            (tier, id) -> new TieredKineticMachineDefinition(id, true, GTValues.V[tier]).setFrontRotation(true),
            (holder, tier) -> new TieredKineticPartMachine(holder, tier, IO.OUT),
            (tier, builder) -> builder
                    .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName("kinetic_output_box"), VLVT[tier]))
                    .rotationState(RotationState.ALL)
                    .blockProp(BlockBehaviour.Properties::dynamicShape)
                    .blockProp(BlockBehaviour.Properties::noOcclusion)
                    .abilities(GreatePartAbility.OUTPUT_KINETIC)
                    .renderer(() -> new SplitShaftTieredHullMachineRenderer(tier, Greate.id("block/machine/part/kinetic_output_box")))
                    .register(),
            ELECTRIC_TIERS);

    public static TieredKineticMachineDefinition[] registerTieredMachines(String name,
                                                                          BiFunction<Integer, ResourceLocation, TieredKineticMachineDefinition> definitionFactory,
                                                                          BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                                          BiFunction<Integer, MachineBuilder<TieredKineticMachineDefinition>, TieredKineticMachineDefinition> builder,
                                                                          int... tiers) {
        TieredKineticMachineDefinition[] definitions = new TieredKineticMachineDefinition[GTValues.TIER_COUNT];
        for(int tier : tiers) {
            var register = GreateRegistries.REGISTRATE.machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name,
                    id -> definitionFactory.apply(tier, id),
                    holder -> factory.apply(holder, tier),
                    TieredKineticMachineBlock::new,
                    MetaMachineItem::new,
                    TieredKineticMachineBlockEntity::create)
                    .tier(tier)
                    .hasTESR(true);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }

    public static void register() {
        /*BlockStressValues.IMPACTS.registerProvider(Greate.MOD_ID, new IStressValueProvider() {
            @Override
            public double getImpact(Block block) {
                if(block instanceof IMachineBlock machineBlock && machineBlock.getDefinition() instanceof TieredKineticMachineDefinition def) {
                    if(!def.isSource()) return def.getTorque();
                }
                return 0;
            }

            @Override
            public double getCapacity(Block block) {
                if(block instanceof IMachineBlock machineBlock && machineBlock.getDefinition() instanceof TieredKineticMachineDefinition def) {
                    if(def.isSource()) return def.getTorque();
                }
                return 0;
            }

            @Override
            public boolean hasImpact(Block block) {
                if(block instanceof IMachineBlock machineBlock && machineBlock.getDefinition() instanceof TieredKineticMachineDefinition def) {
                    return !def.isSource();
                }
                return false;
            }

            @Override
            public boolean hasCapacity(Block block) {
                if(block instanceof IMachineBlock machineBlock && machineBlock.getDefinition() instanceof TieredKineticMachineDefinition def) {
                    return def.isSource();
                }
                return false;
            }

            @Override
            public @Nullable Couple<Integer> getGeneratedRPM(Block block) {
                return null;
            }
        });*/
    }
}
