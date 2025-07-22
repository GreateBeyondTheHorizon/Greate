package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
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
import net.minecraft.world.ItemInteractionResult;
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
    private static boolean greate$canTransportObjects(BlockEntry<BeltBlock> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "updateEntityAfterFallOn", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    private static boolean greate$updateEntityAfterFallOn(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "isBlockCoveringBelt", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate$isBlockCoveringBelt(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof CrushingWheelControllerBlock;
    }

    @WrapOperation(method = "getBeltChain", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate$getBeltChain(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "initBelt", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate$initBelt(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @Inject(method = "useItemOn", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/belt/BeltSlicer;useConnector(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;Lcom/simibubi/create/content/kinetics/belt/BeltSlicer$Feedback;)Lnet/minecraft/world/ItemInteractionResult;"), cancellable = true)
    private void greate$isConnector(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock) {
            cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        }
    }

    @Inject(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"), cancellable = true)
    private void greate$isShaft(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock) {
            cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        }
    }

    @Inject(method = "useItemOn", at = @At(value = "RETURN", ordinal = 14), cancellable = true)
    private void greate$use(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            ItemStack heldItem = player.getItemInHand(hand);
            boolean isConnector = heldItem.getItem() instanceof TieredBeltConnectorItem;
            boolean isShaft = Block.byItem(heldItem.getItem()) instanceof TieredShaftBlock;
            if(isConnector) {
                if(((TieredBeltConnectorItem) heldItem.getItem()).getBeltMaterial() == ((TieredBeltBlock) level.getBlockState(pos).getBlock()).getBeltMaterial()) {
                    cir.setReturnValue(TieredBeltSlicer.useConnector(state, level, pos, player, hand, hitResult, new Feedback()));
                    return;
                }
            }
            if(isShaft) {
                if(heldItem.is(ChemicalHelper.get(shaft, TM[tbb.getTier()]).getItem())) {
                    if(state.getValue(PART) != BeltPart.MIDDLE) {
                        cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
                        return;
                    }
                    if(level.isClientSide) {
                        cir.setReturnValue(ItemInteractionResult.SUCCESS);
                        return;
                    }
                    if(!player.isCreative()) heldItem.shrink(1);
                    KineticBlockEntity.switchToBlockState(level, pos, state.setValue(PART, BeltPart.PULLEY));
                    cir.setReturnValue(ItemInteractionResult.SUCCESS);
                }
            }
        }
    }

    @Inject(method = "onWrenched", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V"), cancellable = true)
    private void greate$onWrenched(BlockState state, UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            context.getPlayer().getInventory().placeItemBackInInventory(ChemicalHelper.get(shaft, TM[tbb.getTier()]));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
