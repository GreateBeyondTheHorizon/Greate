package electrolyte.greate;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.CustomLoaderBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.util.Map;

public class ObjModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T> {

    private ResourceLocation modelLocation;
    private Boolean flipV;

    public static <T extends ModelBuilder<T>> ObjModelBuilder<T> begin(T parent, ExistingFileHelper efh) {
        return new ObjModelBuilder<>(parent, efh);
    }

    protected ObjModelBuilder(T parent, ExistingFileHelper existingFileHelper) {
        super(new ResourceLocation("porting_lib", "obj"), parent, existingFileHelper);
    }

    public ObjModelBuilder<T> modelLocation(ResourceLocation modelLoc) {
        Preconditions.checkNotNull(modelLoc, "Model location cannot be null!");
        Preconditions.checkArgument(existingFileHelper.exists(modelLoc, PackType.CLIENT_RESOURCES),
                "OBJ Model %s does not exist in any known resource pack(s)!", modelLoc);
        this.modelLocation = modelLoc;
        return this;
    }

    public ObjModelBuilder<T> flipV(boolean flipV) {
        this.flipV = flipV;
        return this;
    }

    @Override
    public JsonObject toJson(JsonObject json) {
        json.addProperty("porting_lib:loader", loaderId.toString());
        if(!visibility.isEmpty()) {
            JsonObject visibilityObj = new JsonObject();
            for(Map.Entry<String, Boolean> entry : visibility.entrySet()) {
                visibilityObj.addProperty(entry.getKey(), entry.getValue());
            }
            json.add("visibility", visibilityObj);
        }
        json.addProperty("model", modelLocation.toString());

        if(flipV != null) {
            json.addProperty("flip_v", flipV);
        }
        return json;
    }
}
