package electrolyte.greate.compat.gtceu.client.data.models;

import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder.ModelInitializer;
import com.simibubi.create.Create;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static electrolyte.greate.GreateValues.TM;

public class GreateMachineModels {

    public static ModelInitializer createMillstoneModel(int tier) {
        return (c, p, b) -> b.forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(p.models().withExistingParent(c.getName() + "_inner", Create.asResource("block/millstone/inner"))
                        .texture("5", p.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/millstone"))
                        .texture("particle", p.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/axis"))
                        .texture("1_0", p.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/axis"))
                        .texture("1_1", p.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/axis_top")))
                .modelFile(p.models().withExistingParent(c.getName(), Create.asResource("block/millstone/block")))
                .build(), RecipeLogic.STATUS_PROPERTY);
    }

    public static <P extends MetaMachineItem> NonNullBiConsumer<DataGenContext<Item, P>, RegistrateItemModelProvider> tieredMillstoneItemModel(int tier) {
        return (ctx, provider) -> {
            provider.withExistingParent(ctx.getName(), Create.asResource("block/millstone/item"))
                    .texture("5", provider.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/millstone"));
        };
    }

    public static ModelInitializer createSawModel() {
        return ((ctx, prov, builder) -> {
            new TieredSawGenerator().generateModel(ctx, prov, builder);
        });
    }


    public static <P extends MetaMachineItem> NonNullBiConsumer<DataGenContext<Item, P>, RegistrateItemModelProvider> tieredSawItemModel(int tier) {
        return (ctx, prov) -> {
            prov.withExistingParent(ctx.getName(), Create.asResource("block/mechanical_saw/item"))
                    .texture("stonecutter_saw", prov.modLoc("block/" + TM[tier].getResourceLocation().getPath() + "/saw"));
        };
    }
}
