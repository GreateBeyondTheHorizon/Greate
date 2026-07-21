package electrolyte.greate.compat.gtceu.client.renderer.machine;

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;

public class GreateDynamicRenderHelper {

    public static DynamicRender<?, ?> createMillstoneCogRender() {
        return new MillstoneCogRender();
    }

    public static DynamicRender<?, ?> createSawRender() {
        return new SawRender();
    }
}
