package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.registry.GreateTagPrefixes;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

import static com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock.isShaftValid;

@Mixin(targets = "com/simibubi/create/content/kinetics/steamEngine/SteamEngineBlock$PlacementHelper")
public abstract class MixinSteamEngineBlock$PlacementHelper {

    @Inject(method = "getItemPredicate", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate$getItemPredicate(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(i -> Block.byItem(i.getItem()) instanceof ShaftBlock);
    }

    @Inject(method = "getOffset", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate$getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray, CallbackInfoReturnable<PlacementOffset> cir) {
        Block shaftType = Block.byItem(player.getMainHandItem().getItem());
        if (shaftType instanceof TieredShaftBlock) {
            BlockPos shaftPos = SteamEngineBlock.getShaftPos(state, pos);
            BlockState shaft = shaftType.defaultBlockState();
            for (Direction dir : Direction.orderedByNearest(player)) {
                shaft = shaft.setValue(ShaftBlock.AXIS, dir.getAxis());
                if (isShaftValid(state, shaft)) break;
            }

            BlockState newState = world.getBlockState(shaftPos);
            if (!newState.canBeReplaced()) {
                cir.setReturnValue(PlacementOffset.fail());
                return;
            }
            Axis axis = shaft.getValue(ShaftBlock.AXIS);
            cir.setReturnValue(PlacementOffset.success(shaftPos, s -> {
                Material mat = ChemicalHelper.getMaterialEntry(shaftType).material();
                return BlockHelper.copyProperties(s,
                        (world.isClientSide ? ChemicalHelper.getBlock(GreateTagPrefixes.shaft, mat) : ChemicalHelper.getBlock(GreateTagPrefixes.poweredShaft, mat)).defaultBlockState())
                        .setValue(PoweredShaftBlock.AXIS, axis);
            }));
        }
    }
}
