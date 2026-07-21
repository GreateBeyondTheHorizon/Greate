package electrolyte.greate.compat.gtceu.client.data.models;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder.ModelInitializer;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static electrolyte.greate.GreateValues.TM;

public class GreateMachineModels {

    public static ModelInitializer createMillstoneModel() {
        return ((ctx, provider, builder) -> {
           ResourceLocation parentModel = ModelLocationUtils.getModelLocation(AllBlocks.MILLSTONE.get()).withSuffix("/block");
           builder.forAllStatesModelsExcept(state ->
                   provider.models().nested().parent(provider.models().getExistingFile(parentModel)), RecipeLogic.STATUS_PROPERTY);
        });
    }

    public static <P extends MetaMachineItem> NonNullBiConsumer<DataGenContext<Item, P>, RegistrateItemModelProvider> tieredMillstoneItemModel(int tier) {
        return (ctx, provider) -> {
            Material material = tier > 9 ? GTMaterials.Neutronium : TM[tier];
            provider.withExistingParent(ctx.getName(), Create.asResource("block/millstone/item"))
                    .texture("5", provider.modLoc("block/" + material.getName() + "/millstone"));
        };
    }

    public static ModelInitializer createSawModel() {
        return ((ctx, prov, builder) -> {
            new TieredSawGenerator().generateModel(ctx, prov, builder);
        });
    }


    public static <P extends MetaMachineItem> NonNullBiConsumer<DataGenContext<Item, P>, RegistrateItemModelProvider> tieredSawItemModel(int tier) {
        return (ctx, prov) -> {
            Material material = tier > 9 ? GTMaterials.Neutronium : TM[tier];
            prov.withExistingParent(ctx.getName(), Create.asResource("block/mechanical_saw/item"))
                    .texture("stonecutter_saw", prov.modLoc("block/" + material.getName() + "/saw"));
        };
    }
}


