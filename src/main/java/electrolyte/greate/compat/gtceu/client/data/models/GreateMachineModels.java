package electrolyte.greate.compat.gtceu.client.data.models;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder.ModelInitializer;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.lowdragmc.lowdraglib.test.TestJava.B;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.TieredMillstoneMachineBlock;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static electrolyte.greate.GreateValues.TM;

public class GreateMachineModels {

    public static ModelInitializer createMillstoneModel() {
        return ((context, provider, builder) -> {
           ResourceLocation parentModel = ModelLocationUtils.getModelLocation(AllBlocks.MILLSTONE.get()).withSuffix("/block");
           builder.forAllStatesModels(state ->
                   provider.models().nested().parent(provider.models().getExistingFile(parentModel)));
        });
    }

    public static <P extends MetaMachineItem> NonNullBiConsumer<DataGenContext<Item, P>, RegistrateItemModelProvider> tieredMillstoneItemModel(int tier) {
        return (c, p) -> {
            Material material = tier > 9 ? GTMaterials.Neutronium : TM[tier];
            p.withExistingParent(c.getName(), Create.asResource("block/millstone/item"))
                    .texture("5", p.modLoc("block/" + material.getName() + "/millstone"));
        };
    }
}
