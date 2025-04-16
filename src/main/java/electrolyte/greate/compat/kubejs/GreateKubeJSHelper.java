package electrolyte.greate.compat.kubejs;

import dev.latvian.mods.kubejs.item.ingredient.TagContext;
import dev.latvian.mods.kubejs.server.KubeJSReloadListener;

public class GreateKubeJSHelper {

    public static void kubeStuff() {
        TagContext.INSTANCE.setValue(TagContext.fromLoadResult(KubeJSReloadListener.resources.tagManager.getResult()));
    }
}
