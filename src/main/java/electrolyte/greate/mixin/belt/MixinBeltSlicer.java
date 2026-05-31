package electrolyte.greate.mixin.belt;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.BeltSlicer;
import com.simibubi.create.content.kinetics.belt.BeltSlicer.Feedback;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlockEntity;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.registry.GreateTagPrefixes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeltSlicer.class)
public class MixinBeltSlicer {

    @Inject(method = "useWrench", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;removeBlockEntity(Lnet/minecraft/core/BlockPos;)V", remap = true), remap = false)
    private static void greate_useWrench(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, Feedback feedBack, CallbackInfoReturnable<InteractionResult> cir) {
        if(world.getBlockEntity(pos) instanceof TieredBeltBlockEntity tbbe) {
            player.getInventory().placeItemBackInInventory(
                    ChemicalHelper.get(GreateTagPrefixes.shaft, tbbe.getShaftMaterial()));
        }
    }

    @WrapOperation(method = "useWrench", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V", remap = true), remap = false)
    private static void greate_useWrench(Inventory instance, ItemStack pStack, Operation<Void> original, @Local(argsOnly = true) BlockState state) {
        if(!(state.getBlock() instanceof TieredBeltBlock)) original.call(instance, pStack);
    }

    @WrapOperation(method = "useWrench", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_useWrench(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "useWrench", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"), remap = false)
    private static boolean greate_useWrench(ItemEntry<?> instance, ItemStack itemStack, Operation<Boolean> original, @Local(argsOnly = true) BlockState blockState) {
        if(blockState.getBlock() instanceof TieredBeltBlock tbb) {
            ItemStack beltConnector = ChemicalHelper.get(GreateTagPrefixes.beltConnector, tbb.getBeltMaterial());
            return itemStack.is(beltConnector.getItem());
        } else return original.call(instance, itemStack);
    }

    @WrapOperation(method = "useWrench", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"), remap = false)
    private static boolean greate_useWrench(BlockEntry<?> instance, ItemStack itemStack, Operation<Boolean> original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos) {
        if(level.getBlockEntity(pos) instanceof TieredBeltBlockEntity tbbe) {
            ItemStack shaftStack = ChemicalHelper.get(GreateTagPrefixes.shaft, tbbe.getShaftMaterial());
            return itemStack.is(shaftStack.getItem());
        }
        else return original.call(instance, itemStack);
    }

    @WrapOperation(method = "useConnector", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/belt/item/BeltConnectorItem;maxLength()Ljava/lang/Integer;"), remap = false)
    private static Integer greate_useConnector(Operation<Integer> original, @Local(name = "controllerBE") BeltBlockEntity controllerBE, @Local(argsOnly = true) Player player, @Local(argsOnly = true) InteractionHand hand) {
        if(controllerBE instanceof TieredBeltBlockEntity && player.getItemInHand(hand).getItem() instanceof TieredBeltConnectorItem tbci) {
            return tbci.getBeltMaterial().getProperty(GreatePropertyKeys.BELT).getMaxLength();
        } else return original.call();
    }

    @WrapOperation(method = "useConnector", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_useConnector(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "useConnector", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V", ordinal = 0, remap = true), remap = false)
    private static void greate_useConnector(Inventory instance, ItemStack pStack, Operation<Void> original, @Local(name = "mergedController") BeltBlockEntity mergedController) {
        if(mergedController instanceof TieredBeltBlockEntity tbbe) {
            instance.placeItemBackInInventory(ChemicalHelper.get(GreateTagPrefixes.shaft, tbbe.getShaftMaterial()).copyWithCount(2));
        } else original.call(instance, pStack);
    }

    @WrapOperation(method = "useConnector", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V", ordinal = 1, remap = true), remap = false)
    private static void greate_useConnector(Inventory instance, ItemStack pStack, Operation<Void> original, @Local(argsOnly = true) BlockState state) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            instance.placeItemBackInInventory(ChemicalHelper.get(GreateTagPrefixes.beltConnector, tbb.getBeltMaterial()));
        } else original.call(instance, pStack);
    }

    @Inject(method = "useConnector", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/kinetics/belt/BeltBlockEntity;color:Ljava/util/Optional;", opcode = Opcodes.PUTFIELD), remap = false)
    private static void greate_useConnector(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, Feedback feedBack, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "controllerBE") BeltBlockEntity controllerBE, @Local(name = "segmentBE", ordinal = 2) BeltBlockEntity segmentBE) {
        if(controllerBE instanceof TieredBeltBlockEntity tbbe) {
            ((TieredBeltBlockEntity) segmentBE).setShaftMaterial(tbbe.getShaftMaterial());
        }
    }


    @WrapOperation(method = "tickHoveringInformation", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_tickHoveringInformation(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }
}
