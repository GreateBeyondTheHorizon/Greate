package electrolyte.greate.compat.jade;

import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.saw.TieredSawBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum TieredSawProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if(blockAccessor.getBlockEntity() instanceof TieredSawBlockEntity tsbe) {
            iTooltip.add(Component.translatable("greate.tooltip.saw.active_recipe_type", tsbe.formatActiveRecipeType(blockAccessor.getServerData().getString("ActiveRecipeType"))));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Greate.id("saw.active_recipe_type");
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if(blockAccessor.getBlockEntity() instanceof TieredSawBlockEntity tsbe) {
            compoundTag.putString("ActiveRecipeType", tsbe.getActiveRecipeType().toString());
        }
    }
}
