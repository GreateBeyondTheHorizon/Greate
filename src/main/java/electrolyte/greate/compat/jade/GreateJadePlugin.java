package electrolyte.greate.compat.jade;

import electrolyte.greate.content.kinetics.saw.TieredSawBlock;
import electrolyte.greate.content.kinetics.saw.TieredSawBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class GreateJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(TieredSawProvider.INSTANCE, TieredSawBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(TieredSawProvider.INSTANCE, TieredSawBlock.class);
    }
}
