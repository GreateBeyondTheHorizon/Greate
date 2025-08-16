package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlicer.Feedback;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltSlicer;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.kinetics.belt.BeltBlock.PART;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

@Mixin(BeltBlock.class)
public abstract class MixinBeltBlock {

    @WrapOperation(method = "canTransportObjects", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_canTransportObjects(BlockEntry<BeltBlock> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "updateEntityAfterFallOn", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    private static boolean greate_updateEntityAfterFallOn(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "isBlockCoveringBelt", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_isBlockCoveringBelt(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof CrushingWheelControllerBlock;
    }

    @WrapOperation(method = "getBeltChain", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_getBeltChain(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "initBelt", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_initBelt(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/belt/BeltSlicer;useConnector(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;Lcom/simibubi/create/content/kinetics/belt/BeltSlicer$Feedback;)Lnet/minecraft/world/InteractionResult;"), cancellable = true)
    private void greate_isConnector(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"), cancellable = true)
    private void greate_isShaft(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "use", at = @At(value = "RETURN", ordinal = 14), cancellable = true)
    private void greate_use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            ItemStack heldItem = player.getItemInHand(handIn);
            boolean isConnector = heldItem.getItem() instanceof TieredBeltConnectorItem;
            boolean isShaft = Block.byItem(heldItem.getItem()) instanceof TieredShaftBlock;
            if(isConnector) {
                if(((TieredBeltConnectorItem) heldItem.getItem()).getBeltMaterial() == ((TieredBeltBlock) world.getBlockState(pos).getBlock()).getBeltMaterial()) {
                    cir.setReturnValue(TieredBeltSlicer.useConnector(state, world, pos, player, handIn, hit, new Feedback()));
                    return;
                }
            }
            if(isShaft) {
                if(heldItem.is(ChemicalHelper.get(shaft, TM[tbb.getTier()]).getItem())) {
                    if(state.getValue(PART) != BeltPart.MIDDLE) {
                        cir.setReturnValue(InteractionResult.PASS);
                        return;
                    }
                    if(world.isClientSide) {
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    }
                    if(!player.isCreative()) heldItem.shrink(1);
                    KineticBlockEntity.switchToBlockState(world, pos, state.setValue(PART, BeltPart.PULLEY));
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }

    @Inject(method = "onWrenched", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V"), cancellable = true)
    private void greate_onWrenched(BlockState state, UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            context.getPlayer().getInventory().placeItemBackInInventory(ChemicalHelper.get(shaft, TM[tbb.getTier()]));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
