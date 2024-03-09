package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.DivingBootsItem;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.*;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity.CasingType;
import com.simibubi.create.content.kinetics.belt.BeltSlicer.Feedback;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.BeltMovementHandler.TransportedEntityInfo;
import com.simibubi.create.content.kinetics.belt.transport.BeltTunnelInteractionHandler;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.simibubi.create.content.logistics.funnel.FunnelBlock;
import com.simibubi.create.content.logistics.tunnel.BeltTunnelBlock;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement.ItemUseType;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.utility.Iterate;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.registry.Belts;
import electrolyte.greate.registry.GreateSpriteShifts;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class TieredBeltBlock extends BeltBlock implements ITieredBlock, ITieredBelt {

    private ItemStack shaftType;
    private int tier;
    private Material beltMaterial;

    public TieredBeltBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return this.asItem().getDefaultInstance();
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, Builder pBuilder) {
        List<ItemStack> drops = super.getDrops(pState, pBuilder);
        BlockEntity be = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if(be instanceof TieredBeltBlockEntity tbe && tbe.hasPulley()) {
            drops.removeIf(s -> s.is(AllBlocks.SHAFT.asItem()));
            drops.addAll(Block.byItem(tbe.getShaftType().getItem()).getDrops(pState, pBuilder));
        }
        return drops;
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        super.updateEntityAfterFallOn(pLevel, pEntity);
        BlockPos entityPos = pEntity.blockPosition();
        BlockPos beltPos = null;
        if(pLevel.getBlockState(entityPos).getBlock() == this) {
            beltPos = entityPos;
        } else if(pLevel.getBlockState(entityPos.below()).getBlock() == this) {
            beltPos = entityPos.below();
        }
        if(beltPos == null)
            return;
        if(! (pLevel instanceof Level))
            return;
        entityInside(pLevel.getBlockState(beltPos), (Level) pLevel, beltPos, pEntity);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if(! canTransportObjects(pState))
            return;
        if(pEntity instanceof Player player) {
            if(player.isShiftKeyDown())
                return;
            if(player.getAbilities().flying)
                return;
        }
        if(DivingBootsItem.isWornBy(pEntity))
            return;
        BeltBlockEntity beltBE = BeltHelper.getSegmentBE(pLevel, pPos);
        if(beltBE == null)
            return;
        if(pEntity instanceof ItemEntity itemEntity && pEntity.isAlive()) {
            if(pLevel.isClientSide)
                return;
            if(pEntity.getDeltaMovement().y > 0)
                return;
            if(! pEntity.isAlive())
                return;
            if(BeltTunnelInteractionHandler.getTunnelOnPosition(pLevel, pPos) != null)
                return;
            withBlockEntityDo(pLevel, pPos, be -> {
                IItemHandler handler = be.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
                if(handler == null)
                    return;
                ItemStack remainder = handler.insertItem(0, itemEntity.getItem().copy(), false);
                if(remainder.isEmpty())
                    itemEntity.discard();
            });
            return;
        }
        BeltBlockEntity controllerBE = BeltHelper.getControllerBE(pLevel, pPos);
        if(controllerBE == null || controllerBE.passengers == null)
            return;
        if(controllerBE.passengers.containsKey(pEntity)) {
            TransportedEntityInfo info = controllerBE.passengers.get(pEntity);
            if(info.getTicksSinceLastCollision() != 0 || pPos.equals(pEntity.blockPosition())) {
                info.refresh(pPos, pState);
            }
        } else {
            controllerBE.passengers.put(pEntity, new TransportedEntityInfo(pPos, pState));
            pEntity.setOnGround(true);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.isShiftKeyDown() || ! pPlayer.mayBuild()) {
            return InteractionResult.PASS;
        }
        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        boolean isWrench = AllItems.WRENCH.isIn(heldItem);
        boolean isConnector = heldItem.getItem() instanceof TieredBeltConnectorItem;
        boolean isShaft = Block.byItem(heldItem.getItem()) instanceof TieredShaftBlock;
        boolean isDye = heldItem.is(Tags.Items.DYES);
        boolean hasWater = GenericItemEmptying.emptyItem(pLevel, heldItem, true).getFirst().getFluid().isSame(Fluids.WATER);
        boolean isHand = heldItem.isEmpty() && pHand == InteractionHand.MAIN_HAND;
        if(isDye || hasWater) {
            return onBlockEntityUse(pLevel, pPos, be -> be.applyColor(DyeColor.getColor(heldItem)) ? InteractionResult.SUCCESS : InteractionResult.PASS);
        }
        if(isConnector) {
            if(((TieredBeltConnectorItem) heldItem.getItem()).getBeltMaterial() == ((TieredBeltBlock) pLevel.getBlockState(pPos).getBlock()).getBeltMaterial()) {
                return BeltSlicer.useConnector(pState, pLevel, pPos, pPlayer, pHand, pHit, new Feedback());
            }
        }
        if(isWrench) {
            return BeltSlicer.useWrench(pState, pLevel, pPos, pPlayer, pHand, pHit, new Feedback());
        }

        BeltBlockEntity beltBE = BeltHelper.getSegmentBE(pLevel, pPos);
        if(beltBE == null)
            return InteractionResult.PASS;
        if(isHand) {
            BeltBlockEntity controllerBE = beltBE.getControllerBE();
            if(controllerBE == null)
                return InteractionResult.PASS;
            if(pLevel.isClientSide)
                return InteractionResult.SUCCESS;
            MutableBoolean success = new MutableBoolean(false);
            controllerBE.getInventory().applyToEachWithin(beltBE.index + 0.5F, 0.55F, (s) -> {
                pPlayer.getInventory().placeItemBackInInventory(s.stack);
                success.setTrue();
                return TransportedResult.removeItem();
            });
            if(success.isTrue()) {
                pLevel.playSound(null, pPos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, 1F + new Random().nextFloat());
            }
        }
        if(isShaft && heldItem.is(((TieredBeltBlockEntity)beltBE).getShaftType().getItem())) {
            if(pState.getValue(PART) != BeltPart.MIDDLE) return InteractionResult.PASS;
            if(pLevel.isClientSide) return InteractionResult.SUCCESS;
            if(!pPlayer.isCreative()) heldItem.shrink(1);
            KineticBlockEntity.switchToBlockState(pLevel, pPos, pState.setValue(PART, BeltPart.PULLEY));
            return InteractionResult.SUCCESS;
        }
        if(AllBlocks.BRASS_CASING.isIn(heldItem)) {
            withBlockEntityDo(pLevel, pPos, be -> be.setCasingType(CasingType.BRASS));
            updateCoverProperty(pLevel, pPos, pLevel.getBlockState(pPos));
            return InteractionResult.SUCCESS;
        }

        if(AllBlocks.ANDESITE_CASING.isIn(heldItem)) {
            withBlockEntityDo(pLevel, pPos, be -> be.setCasingType(CasingType.ANDESITE));
            updateCoverProperty(pLevel, pPos, pLevel.getBlockState(pPos));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        if(state.getValue(CASING)) {
            if(level.isClientSide)
                return InteractionResult.SUCCESS;
            withBlockEntityDo(level, pos, be -> be.setCasingType(CasingType.NONE));
            return InteractionResult.SUCCESS;
        }

        if(state.getValue(PART) == BeltPart.PULLEY) {
            if(level.isClientSide)
                return InteractionResult.SUCCESS;
            KineticBlockEntity.switchToBlockState(level, pos, state.setValue(PART, BeltPart.MIDDLE));
            if(player != null && ! player.isCreative()) {
                TieredBeltBlockEntity beltBE = (TieredBeltBlockEntity) level.getBlockEntity(pos);
                player.getInventory().placeItemBackInInventory(beltBE.getShaftType());
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static void initBelt(Level level, BlockPos pos) {
        if(level.isClientSide) return;
        if(level instanceof ServerLevel serverLevel && serverLevel.getChunkSource().getGenerator() instanceof DebugLevelSource) return;
        BlockState state = level.getBlockState(pos);
        if(!(state.getBlock() instanceof TieredBeltBlock)) return;
        int limit = 1000;
        BlockPos currentPos = pos;
        while(limit-- > 0) {
            BlockState currentState = level.getBlockState(currentPos);
            if(!(currentState.getBlock() instanceof TieredBeltBlock)) {
                level.destroyBlock(pos, true);
                return;
            }
            BlockPos nextPos = nextSegmentPosition(currentState, currentPos, false);
            if(nextPos == null) break;
            if(!level.isLoaded(nextPos)) return;
            currentPos = nextPos;
        }

        int index = 0;
        List<BlockPos> beltChain = getBeltChain(level, currentPos);
        if(beltChain.size() < 2) {
            level.destroyBlock(currentPos, true);
            return;
        }

        for(BlockPos beltPos : beltChain) {
            BlockEntity be = level.getBlockEntity(beltPos);
            BlockState currentState = level.getBlockState(beltPos);
            if(be instanceof TieredBeltBlockEntity beltBE && currentState.getBlock() instanceof TieredBeltBlock) {
                beltBE.setController(currentPos);
                beltBE.beltLength = beltChain.size();
                beltBE.index = index;
                beltBE.attachKinetics();
                beltBE.setChanged();
                beltBE.sendData();
                if(beltBE.isController() && ! BeltBlock.canTransportObjects(currentState))
                    beltBE.getInventory().ejectAll();
            } else {
                level.destroyBlock(currentPos, true);
                return;
            }
            index++;
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        if(pLevel.isClientSide)
            return;
        if(pState.getBlock() == pNewState.getBlock())
            return;
        if(pIsMoving)
            return;

        for(boolean forward : Iterate.trueAndFalse) {
            BlockPos currentPos = nextSegmentPosition(pState, pPos, forward);
            if(currentPos == null)
                continue;
            BlockState currentState = pLevel.getBlockState(currentPos);
            if(currentState.getBlock() != this)
                continue;
            boolean hasPulley = false;
            BlockEntity be = pLevel.getBlockEntity(currentPos);
            if(be instanceof TieredBeltBlockEntity beltBE) {
                if(beltBE.isController()) {
                    beltBE.getInventory().ejectAll();
                }
                hasPulley = beltBE.hasPulley();
            }

            TieredBeltBlockEntity tbbe = (TieredBeltBlockEntity) be;
            BlockState shaftState = Block.byItem(tbbe.getShaftType().getItem()).defaultBlockState()
                    .setValue(AXIS, getRotationAxis(currentState));
            pLevel.removeBlockEntity(currentPos);
            pLevel.setBlock(currentPos, ProperWaterloggedBlock.withWater(pLevel, hasPulley ? shaftState : Blocks.AIR.defaultBlockState(), currentPos), 3);
            pLevel.levelEvent(2001, currentPos, Block.getId(currentState));
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        if(pDirection.getAxis().isHorizontal())
            updateTunnelConnections(pLevel, pCurrentPos.above());
        if(pDirection == Direction.UP)
            updateCoverProperty(pLevel, pCurrentPos, pState);
        return pState;
    }

    @Override
    public void updateCoverProperty(LevelAccessor level, BlockPos pos, BlockState state) {
        if(level.isClientSide())
            return;
        if(state.getValue(CASING) && state.getValue(SLOPE) == BeltSlope.HORIZONTAL) {
            withBlockEntityDo(level, pos, be -> be.setCovered(isBlockCoveringBelt(level, pos.above())));
        }
    }

    public static boolean isBlockCoveringBelt(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        VoxelShape shape = state.getCollisionShape(level, pos);
        if(shape.isEmpty()) return false;
        AABB aabb = shape.bounds();
        if(aabb.getXsize() < 0.5f || aabb.getZsize() < 0.5f) return false;
        if(aabb.minY  > 0) return false;
        if(state.getBlock() instanceof CrushingWheelControllerBlock) return false;
        if(state.getBlock() instanceof TieredCrushingWheelControllerBlock) return false;
        if(FunnelBlock.isFunnel(state) && FunnelBlock.getFunnelFacing(state) != Direction.UP) return false;
        if(state.getBlock() instanceof BeltTunnelBlock) return false;
        return true;
    }

    private void updateTunnelConnections(LevelAccessor level, BlockPos pos) {
        Block tunnelBlock = level.getBlockState(pos).getBlock();
        if(tunnelBlock instanceof BeltTunnelBlock btb) {
            btb.updateTunnel(level, pos);
        }
    }

    public static List<BlockPos> getBeltChain(Level level, BlockPos controllerPos) {
        List<BlockPos> positions = new LinkedList<>();
        BlockState state = level.getBlockState(controllerPos);
        if(!(state.getBlock() instanceof TieredBeltBlock)) return positions;
        int limit = 1000;
        BlockPos currentPos = controllerPos;
        while(limit-- > 0 && currentPos != null) {
            BlockState currentState = level.getBlockState(currentPos);
            if(!(currentState.getBlock() instanceof TieredBeltBlock)) break;
            positions.add(currentPos);
            currentPos = nextSegmentPosition(currentState, currentPos, true);
        }
        return positions;
    }

    @Override
    public BlockEntityType<? extends TieredBeltBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_BELT.get();
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        List<ItemStack> required = new ArrayList<>();
        if(state.getValue(PART) != BeltPart.MIDDLE)
            required.add(Belts.VALID_SHAFTS.get(this).stream().filter(s -> s.asItem().getDefaultInstance().is(state.getBlock().asItem())).findFirst().get().asItem().getDefaultInstance());
        if(state.getValue(PART) == BeltPart.START)
            required.add(this.asItem().getDefaultInstance());
        if(required.isEmpty())
            return ItemRequirement.NONE;
        return new ItemRequirement(ItemUseType.CONSUME, required);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    public ItemStack getShaftType() {
        return shaftType;
    }

    public void setShaftType(ItemStack shaftType) {
        this.shaftType = shaftType;
    }

    public void validShafts(List<BlockEntry<TieredShaftBlock>> shafts) {
        List<Block> shaftList = new ArrayList<>();
        shafts.forEach(s -> shaftList.add(s.get()));
        Belts.VALID_SHAFTS.put(this, shaftList);
    }

    public void setupBeltModel() {
        GreateSpriteShifts.populateMaps(this);
    }

    @Override
    public Material getBeltMaterial() {
        return beltMaterial;
    }

    @Override
    public void setBeltMaterial(Material material) {
        this.beltMaterial = material;
    }
}
