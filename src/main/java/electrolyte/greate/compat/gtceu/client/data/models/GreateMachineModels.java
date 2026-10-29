package electrolyte.greate.compat.gtceu.client.data.models;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder.ModelInitializer;
import com.simibubi.create.Create;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import electrolyte.greate.Greate;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.GreateBlockStateGen.CUTOUT_MIPPED;

public class GreateMachineModels {

    public static ModelInitializer createMillstoneModel(int tier) {
        return (ctx, provider, builder) -> builder.forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(provider.models().withExistingParent(ctx.getName() + "_inner", Create.asResource("block/millstone/inner"))
                        .texture("5", provider.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/millstone"))
                        .texture("particle", provider.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/axis"))
                        .texture("1_0", provider.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/axis"))
                        .texture("1_1", provider.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/axis_top")))
                .modelFile(provider.models().withExistingParent(ctx.getName(), Create.asResource("block/millstone/block")))
                .build(), RecipeLogic.STATUS_PROPERTY);
    }

    public static <P extends MetaMachineItem> NonNullBiConsumer<DataGenContext<Item, P>, RegistrateItemModelProvider> tieredMillstoneItemModel(int tier) {
        return (ctx, provider) -> {
            provider.withExistingParent(ctx.getName(), Create.asResource("block/millstone/item"))
                    .texture("5", provider.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/millstone"));
        };
    }

    public static ModelInitializer createSawModel(int tier) {
        return ((ctx, prov, builder) -> new TieredSawGenerator().generateModel(ctx, prov, builder, tier));
    }


    public static <P extends MetaMachineItem> NonNullBiConsumer<DataGenContext<Item, P>, RegistrateItemModelProvider> tieredSawItemModel(int tier) {
        return (ctx, prov) -> prov.withExistingParent(ctx.getName(), Create.asResource("block/mechanical_saw/item"))
                .texture("stonecutter_saw", prov.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/saw"));
    }

    public static ModelInitializer createKineticBoxModel(int tier, boolean output) {
        return ((context, provider, builder) -> {
            String parentModel = "block/machine/part/kinetic" + (output ? "_output_" : "_input_") + "box";
            String material = GTValues.VN[tier].toLowerCase();
            builder.forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(provider.models().withExistingParent(context.getName(), Greate.id(parentModel)).renderType(CUTOUT_MIPPED)
                            .texture("bottom", GTCEu.id("block/casings/voltage/" + material + "/bottom"))
                            .texture("top", GTCEu.id("block/casings/voltage/" + material + "/top"))
                            .texture("side", GTCEu.id("block/casings/voltage/" + material + "/side"))
                            .texture("particle", GTCEu.id("block/casings/voltage/" + material + "/side")))
                    .build());
        });
    }
}
