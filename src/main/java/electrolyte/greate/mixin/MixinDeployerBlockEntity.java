package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.base.IRotate.StressImpact;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DeployerBlockEntity.class)
public abstract class MixinDeployerBlockEntity extends KineticBlockEntity {

    @Shadow(remap = false) protected ItemStack heldItem;

    public MixinDeployerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "addToGoggleTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"), remap = false, cancellable = true)
    private void greate$addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {
        if(!heldItem.isEmpty()) {
            CreateLang.translate("tooltip.deployer.contains",
                    Component.translatable(heldItem.getHoverName().getString()), heldItem.getCount()).style(ChatFormatting.GREEN).forGoggles(tooltip);
        }

        float stressAtBase = calculateStressApplied();
        if(StressImpact.isEnabled() && !Mth.equal(stressAtBase, 0)) {
            tooltip.add(Component.empty());
            addStressImpactStats(tooltip, stressAtBase);
        }

        cir.setReturnValue(true);
    }
}
