package electrolyte.greate.foundation.client.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ExtendedDelegatedModel extends DelegatedModel {

    private RenderType renderType;
    private Map<String, ResourceLocation> textures;

    public ExtendedDelegatedModel(ResourceLocation pParent, RenderType renderType) {
        super(pParent);
        this.renderType = renderType;
    }

    public ExtendedDelegatedModel(ResourceLocation pParent, Map<String, ResourceLocation> textures) {
        super(pParent);
        this.textures = textures;
    }

    public ExtendedDelegatedModel(ResourceLocation pParent, RenderType renderType, Map<String, ResourceLocation> textures) {
        super(pParent);
        this.renderType = renderType;
        this.textures = textures;
    }

    @Override
    public JsonElement get() {
        JsonObject parent = super.get().getAsJsonObject();

        if(renderType != null) {
            parent.addProperty("render_type", renderType.name);
        }

        if(textures != null) {
            JsonObject texturesObj = new JsonObject();
            textures.forEach((s, resourceLocation) -> texturesObj.addProperty(s, resourceLocation.toString()));
            parent.add("textures", texturesObj);
        }
        return parent;
    }
}
