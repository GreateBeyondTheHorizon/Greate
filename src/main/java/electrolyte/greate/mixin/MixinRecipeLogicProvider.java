package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.machine.trait.MachineTraitType;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.integration.jade.provider.MachineTraitProvider;
import com.gregtechceu.gtceu.integration.jade.provider.RecipeLogicProvider;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachineBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Mixin(RecipeLogicProvider.class)
public abstract class MixinRecipeLogicProvider extends MachineTraitProvider<RecipeLogic, CompoundTag> {

    protected MixinRecipeLogicProvider(ResourceLocation uid, MachineTraitType<RecipeLogic> type) {
        super(uid, type);
    }

    @Inject(method = "addTooltip(Lnet/minecraft/nbt/CompoundTag;Lsnownee/jade/api/ITooltip;Lnet/minecraft/world/entity/player/Player;Lsnownee/jade/api/BlockAccessor;Lnet/minecraft/world/level/block/entity/BlockEntity;Lsnownee/jade/api/config/IPluginConfig;)V", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate_addTooltip(CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block, BlockEntity blockEntity, IPluginConfig config, CallbackInfo ci) {
        if(block.getBlockState().getBlock() instanceof TieredKineticMachineBlock) {
            ci.cancel();
            return;
        }
    }
}
