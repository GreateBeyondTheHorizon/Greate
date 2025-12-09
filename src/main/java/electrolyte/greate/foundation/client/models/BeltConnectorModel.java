package electrolyte.greate.foundation.client.models;

import com.gregtechceu.gtceu.data.pack.GTDynamicResourcePack;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record BeltConnectorModel(TieredBeltConnectorItem beltConnectorItem) {
    private static final Set<BeltConnectorModel> MODELS = new HashSet<>();

    public static void create(TieredBeltConnectorItem beltItem) {
        MODELS.add(new BeltConnectorModel(beltItem));
    }

    public static void reinitModels() {
        for(BeltConnectorModel model : MODELS) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(model.beltConnectorItem);
            GTDynamicResourcePack.addItemModel(itemId, new ExtendedDelegatedModel(new ResourceLocation("item/generated"),
                    Map.of("layer0", Greate.id("item/" + itemId.getPath()))));
        }
    }
}
