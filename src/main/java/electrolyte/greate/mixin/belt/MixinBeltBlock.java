package electrolyte.greate.mixin.belt;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlockEntity;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.registry.GreateTagPrefixes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

@Mixin(BeltBlock.class)
public abstract class MixinBeltBlock {

    @WrapOperation(method = "canTransportObjects", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_canTransportObjects(BlockEntry<BeltBlock> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "updateEntityAfterFallOn", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0), remap = false)
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

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 0), remap = false)
    private boolean greate_use(ItemEntry<?> instance, ItemStack itemStack, Operation<Boolean> original, @Local(argsOnly = true) Player player, @Local(argsOnly = true) InteractionHand hand) {
        ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        return mainHandStack.is(AllItemTags.WRENCH.tag) ||
                //idk why gtceu wrenches do this
                (hand == InteractionHand.OFF_HAND && offHandStack.is(AllItemTags.WRENCH.tag));
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 1), remap = false)
    private boolean greate_use(ItemEntry<?> instance, ItemStack itemStack, Operation<Boolean> original, @Local(argsOnly = true) BlockState state, @Local(argsOnly = true) Player player) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(!(mainHandStack.getItem() instanceof TieredBeltConnectorItem tbci)) return false;
            return tbci.getBeltMaterial() == tbb.getBeltMaterial();
        } else return original.call(instance, itemStack);
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 0), remap = false)
    private boolean greate_use(BlockEntry<?> instance, ItemStack itemStack, Operation<Boolean> original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) Player player) {
        if(level.getBlockEntity(pos) instanceof TieredBeltBlockEntity tbbe) {
            ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            Material beltShaftMaterial = tbbe.getShaftMaterial();
            if(!(mainHandStack.is(ChemicalHelper.get(GreateTagPrefixes.shaft, beltShaftMaterial).getItem()))) return false;
            return mainHandStack.is(ChemicalHelper.get(shaft, beltShaftMaterial).getItem());
        } else return original.call(instance, itemStack);
    }

    @WrapOperation(method = "onWrenched", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V"))
    private void greate_onWrenched(Inventory inventory, ItemStack pStack, Operation<Void> original, @Local(name = "world") Level world, @Local(argsOnly = true) UseOnContext context) {
        if(world.getBlockEntity(context.getClickedPos()) instanceof TieredBeltBlockEntity tbbe) {
            inventory.placeItemBackInInventory(ChemicalHelper.get(shaft, tbbe.getShaftMaterial()));
        } else original.call(inventory, pStack);
    }
}
