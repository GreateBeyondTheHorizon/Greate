package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.*;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity.CasingType;
import com.simibubi.create.content.kinetics.belt.BeltSlicer.Feedback;
import com.simibubi.create.content.kinetics.belt.transport.BeltInventory;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.mixin.MixinBeltBlockEntityAccessor;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

import static electrolyte.greate.registry.GreateTagPrefixes.beltConnector;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class TieredBeltSlicer {

    public static InteractionResult useConnector(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, Feedback feedback) {
        BeltBlockEntity controllerBE = BeltHelper.getControllerBE(level, pos);
        if(controllerBE == null)
            return InteractionResult.PASS;
        int beltLength = controllerBE.beltLength;
        Material beltMaterial = ((TieredBeltConnectorItem) player.getItemInHand(hand).getItem()).getBeltMaterial();
        if(beltLength == beltMaterial.getProperty(GreatePropertyKeys.BELT).getMaxLength())
            return InteractionResult.FAIL;

        BlockPos beltVector = BlockPos.containing(BeltHelper.getBeltVector(state));
        BeltPart beltPart = state.getValue(BeltBlock.PART);
        Direction direction = state.getValue(BeltBlock.HORIZONTAL_FACING);
        List<BlockPos> beltChain = BeltBlock.getBeltChain(level, controllerBE.getBlockPos());
        boolean creative = player.isCreative();
        if(!hoveringEnd(state, hitResult))
            return InteractionResult.PASS;

        BlockPos next = beltPart == BeltPart.START ? pos.subtract(beltVector) : pos.offset(beltVector);
        BeltBlockEntity mergedController = null;
        int mergedBeltLength = 0;

        BlockState nextState = level.getBlockState(next);
        if(!nextState.canBeReplaced()) {
            if(!(state.getBlock() instanceof TieredBeltBlock))
                return InteractionResult.FAIL;
            if(!(beltStatesCompatible(state, nextState)))
                return InteractionResult.FAIL;

            mergedController = BeltHelper.getControllerBE(level, next);
            if(mergedController == null)
                return InteractionResult.FAIL;
            if(mergedController.beltLength + beltLength > beltMaterial.getProperty(GreatePropertyKeys.BELT).getMaxLength())
                return InteractionResult.FAIL;

            mergedBeltLength = mergedController.beltLength;

            if(!level.isClientSide) {
                boolean flipBelt = direction != nextState.getValue(BeltBlock.HORIZONTAL_FACING);
                Optional<DyeColor> color = controllerBE.color;
                for(BlockPos blockPos : BeltBlock.getBeltChain(level, mergedController.getBlockPos())) {
                    BeltBlockEntity belt = BeltHelper.getSegmentBE(level, blockPos);
                    if(belt == null)
                        continue;
                    belt.detachKinetics();
                    belt.invalidateItemHandler();
                    belt.beltLength = 0;
                    belt.color = color;
                    if(flipBelt) {
                        level.setBlock(blockPos, flipBelt(level.getBlockState(blockPos)), Block.UPDATE_ALL | Block.UPDATE_MOVE_BY_PISTON);
                    }
                }

                if(flipBelt && ((MixinBeltBlockEntityAccessor) mergedController).getInventoryField() != null) {
                    List<TransportedItemStack> transportedItems = ((MixinBeltBlockEntityAccessor) mergedController).getInventoryField().getTransportedItems();
                    for(TransportedItemStack transportedStack : transportedItems) {
                        transportedStack.beltPosition = mergedBeltLength - transportedStack.beltPosition;
                        transportedStack.prevBeltPosition = mergedBeltLength - transportedStack.prevBeltPosition;
                    }
                }
            }
        }

        if(!level.isClientSide) {
            for(BlockPos blockPos : beltChain) {
                BeltBlockEntity belt = BeltHelper.getSegmentBE(level, blockPos);
                if(belt == null)
                    continue;
                belt.detachKinetics();
                belt.invalidateItemHandler();
                belt.beltLength = 0;
            }

            BeltInventory inventory = ((MixinBeltBlockEntityAccessor) controllerBE).getInventoryField();
            KineticBlockEntity.switchToBlockState(level, pos, state.setValue(BeltBlock.PART, BeltPart.MIDDLE));

            if(mergedController == null) {
                level.setBlock(next, ProperWaterloggedBlock.withWater(level, state.setValue(BeltBlock.CASING, false), next), Block.UPDATE_ALL | Block.UPDATE_MOVE_BY_PISTON);
                BeltBlockEntity segmentBE = BeltHelper.getSegmentBE(level, next);
                if(segmentBE != null) {
                    segmentBE.color = controllerBE.color;
                }

                level.playSound(null, pos, SoundEvents.WOOL_PLACE, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 0.5F, 1F);
                if(beltPart == BeltPart.START && segmentBE != null && inventory != null) {
                    segmentBE.setController(next);
                    for(TransportedItemStack transportedStack : inventory.getTransportedItems()) {
                        transportedStack.beltPosition += 1;
                        segmentBE.getInventory().addItem(transportedStack);
                    }
                }
            } else {
                BeltInventory mergedInventory = ((MixinBeltBlockEntityAccessor) mergedController).getInventoryField();
                level.playSound(null, pos, SoundEvents.WOOL_HIT, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 0.5F, 1.3F);
                BeltBlockEntity segmentBE = BeltHelper.getSegmentBE(level, next);
                KineticBlockEntity.switchToBlockState(level, next, state.setValue(BeltBlock.CASING, segmentBE != null && segmentBE.casing != CasingType.NONE).setValue(BeltBlock.PART, BeltPart.MIDDLE));
                if(!creative) {
                    player.getInventory().placeItemBackInInventory(ChemicalHelper.get(shaft, ((TieredBeltBlockEntity) controllerBE).getShaftMaterial()).copyWithCount(2));
                    player.getInventory().placeItemBackInInventory(ChemicalHelper.get(beltConnector, beltMaterial));
                }

                BlockPos search = controllerBE.getBlockPos();
                for(int i = 0; i < 10000; i++) {
                    BlockState blockState = level.getBlockState(search);
                    if(!(blockState.getBlock() instanceof TieredBeltBlock))
                        break;
                    if(blockState.getValue(BeltBlock.PART) != BeltPart.START) {
                        search = search.subtract(beltVector);
                        continue;
                    }

                    BeltBlockEntity newController = BeltHelper.getSegmentBE(level, search);
                    if(newController != controllerBE && inventory != null) {
                        newController.setController(search);
                        ((MixinBeltBlockEntityAccessor) controllerBE).setInventoryField(null);
                        for(TransportedItemStack transportedStack : inventory.getTransportedItems()) {
                            transportedStack.beltPosition += mergedBeltLength;
                            newController.getInventory().addItem(transportedStack);
                        }
                    }

                    if(newController != mergedController && mergedInventory != null) {
                        newController.setController(search);
                        ((MixinBeltBlockEntityAccessor) mergedController).setInventoryField(null);
                        for(TransportedItemStack transportedStack : mergedInventory.getTransportedItems()) {
                            if(newController == controllerBE) {
                                transportedStack.beltPosition += beltLength;
                            }
                            newController.getInventory().addItem(transportedStack);
                        }
                    }

                    break;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private static boolean hoveringEnd(BlockState state, BlockHitResult hitResult) {
        BeltPart part = state.getValue(BeltBlock.PART);
        if(part == BeltPart.MIDDLE || part == BeltPart.PULLEY) return false;

        Vec3 beltVector = BeltHelper.getBeltVector(state);
        Vec3 centerOf = VecHelper.getCenterOf(hitResult.getBlockPos());
        Vec3 subtract = hitResult.getLocation().subtract(centerOf);

        return subtract.dot(beltVector) > 0 == (part == BeltPart.END);
    }

    private static boolean beltStatesCompatible(BlockState state, BlockState nextState) {
        if(state.getBlock() != nextState.getBlock()) return false;
        Direction direction = state.getValue(BeltBlock.HORIZONTAL_FACING);
        BeltSlope slope = state.getValue(BeltBlock.SLOPE);
        Direction direction2 = nextState.getValue(BeltBlock.HORIZONTAL_FACING);
        BeltSlope slope2 = nextState.getValue(BeltBlock.SLOPE);

        switch(slope) {
            case UPWARD:
                if(slope2 == BeltSlope.DOWNWARD) return direction == direction2.getOpposite();
                return slope2 == slope && direction == direction2;
            case DOWNWARD:
                if(slope2 == BeltSlope.UPWARD) return direction == direction2.getOpposite();
                return slope2 == slope && direction == direction2;
            default:
                return slope2 == slope && direction2.getAxis() == direction.getAxis();
        }
    }

    private static BlockState flipBelt(BlockState state) {
        Direction direction = state.getValue(BeltBlock.HORIZONTAL_FACING);
        BeltSlope slope = state.getValue(BeltBlock.SLOPE);
        BeltPart part = state.getValue(BeltBlock.PART);

        if(slope == BeltSlope.UPWARD) {
            state = state.setValue(BeltBlock.SLOPE, BeltSlope.DOWNWARD);
        } else if(slope == BeltSlope.DOWNWARD) {
            state = state.setValue(BeltBlock.SLOPE, BeltSlope.UPWARD);
        }

        if(part == BeltPart.END) {
            state = state.setValue(BeltBlock.PART, BeltPart.START);
        } else if(part == BeltPart.START) {
            state = state.setValue(BeltBlock.PART, BeltPart.END);
        }

        return state.setValue(BeltBlock.HORIZONTAL_FACING, direction.getOpposite());
    }
}
