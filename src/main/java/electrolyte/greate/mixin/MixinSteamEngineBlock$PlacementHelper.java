package electrolyte.greate.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.utility.BlockHelper;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
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

    @Inject(method = "getItemPredicate", at = @At("HEAD"), cancellable = true)
    private void greate_getItemPredicate(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(i -> Block.byItem(i.getItem()) instanceof TieredShaftBlock || Block.byItem(i.getItem()).equals(AllBlocks.SHAFT.get()));
    }

    @Inject(method = "getOffset(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/BlockHitResult;)Lcom/simibubi/create/foundation/placement/PlacementOffset;", at = @At("HEAD"), cancellable = true)
    private void greate_getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray, CallbackInfoReturnable<PlacementOffset> cir) {
        Block shaftType = Block.byItem(player.getMainHandItem().getItem());
        if (shaftType instanceof TieredShaftBlock) {
            BlockPos shaftPos = SteamEngineBlock.getShaftPos(state, pos);
            BlockState shaft = shaftType.defaultBlockState();
            for (Direction dir : Direction.orderedByNearest(player)) {
                shaft = shaft.setValue(ShaftBlock.AXIS, dir.getAxis());
                if (isShaftValid(state, shaft)) {
                    break;
                }
            }

            BlockState newState = world.getBlockState(shaftPos);
            if (!newState.getMaterial().isReplaceable())
                cir.setReturnValue(PlacementOffset.fail());
            Axis axis = shaft.getValue(ShaftBlock.AXIS);
            cir.setReturnValue(PlacementOffset.success(shaftPos,
                    s -> BlockHelper.copyProperties(s, TieredPoweredShaftBlock.SHAFTS.get(((TieredShaftBlock) s.getBlock())).defaultBlockState())
                            .setValue(PoweredShaftBlock.AXIS, axis)));
        }
    }
}
