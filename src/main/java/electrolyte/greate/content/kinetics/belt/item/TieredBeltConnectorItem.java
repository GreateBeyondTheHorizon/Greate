package electrolyte.greate.content.kinetics.belt.item;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.utility.VecHelper;
import electrolyte.greate.content.kinetics.belt.ITieredBelt;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.TieredBracketedKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import electrolyte.greate.registry.Belts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class TieredBeltConnectorItem extends BlockItem implements ITieredBelt {
    private Material beltMaterial;

    public TieredBeltConnectorItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public String getDescriptionId() {
        return getOrCreateDescriptionId();
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        if(player != null && player.isShiftKeyDown()) {
            pContext.getItemInHand().setTag(null);
            return InteractionResult.SUCCESS;
        }
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        boolean validAxis = validateAxis(level, pos);
        if(level.isClientSide) return validAxis ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        CompoundTag tag = pContext.getItemInHand().getOrCreateTag();
        BlockPos firstPulley = null;
        if(tag.contains("FirstPulley")) {
            firstPulley = NbtUtils.readBlockPos(tag.getCompound("FirstPulley"));
            if(!validateAxis(level, firstPulley) || !firstPulley.closerThan(pos,
                    GConfigUtility.getBeltLengthFromMaterial(((TieredBeltConnectorItem) pContext.getItemInHand().getItem()).getBeltMaterial()) * 2)) {
                tag.remove("FirstPulley");
                pContext.getItemInHand().setTag(tag);
            }
        }

        if(!validAxis || player == null) return InteractionResult.FAIL;
        if(tag.contains("FirstPulley")) {
            if(!canConnect(level, firstPulley, pos, pContext.getItemInHand())) return InteractionResult.FAIL;
            if(firstPulley != null && !firstPulley.equals(pos)) {
                createBelts(level, firstPulley, pos);
                AllAdvancements.BELT.awardTo(player);
                if(!player.isCreative()) {
                    pContext.getItemInHand().shrink(1);
                }
            }

            if(!pContext.getItemInHand().isEmpty()) {
                pContext.getItemInHand().setTag(null);
                player.getCooldowns().addCooldown(this, 5);
            }
            return InteractionResult.SUCCESS;
        }
        tag.put("FirstPulley", NbtUtils.writeBlockPos(pos));
        pContext.getItemInHand().setTag(tag);
        player.getCooldowns().addCooldown(this, 5);
        return InteractionResult.SUCCESS;
    }

    public void createBelts(Level level, BlockPos start, BlockPos end) {
        level.playSound(null, BlockPos.containing(VecHelper.getCenterOf(start.offset(end)).scale(0.5F)), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.5F, 1F);
        BeltSlope slope = getSlopeBetween(start, end);
        Direction facing = getFacingFromTo(start, end);
        BlockPos diff = end.subtract(start);
        if(diff.getX() == diff.getZ()) {
            facing = Direction.get(facing.getAxisDirection(), level.getBlockState(start).getValue(BlockStateProperties.AXIS) == Axis.X ? Axis.Z : Axis.X);
        }

        List<BlockPos> beltsToCreate = getBeltChainBetween(start, end, slope, facing);
        BlockState state = Block.byItem(this).defaultBlockState();
        boolean failed = false;
        int tier = -1;
        ItemStack shaftType = null;
        for(BlockPos pos : beltsToCreate) {
            BlockState existingState = level.getBlockState(pos);
            if(existingState.getDestroySpeed(level, pos) == -1) {
                failed = true;
                break;
            }

            BeltPart part = pos.equals(start) ? BeltPart.START : pos.equals(end) ? BeltPart.END : BeltPart.MIDDLE;
            BlockState shaftState = level.getBlockState(pos);
            boolean pulley = shaftState.getBlock() instanceof TieredShaftBlock;
            if(pulley) {
                tier = (((TieredShaftBlock) shaftState.getBlock()).getTier());
                shaftType = shaftState.getBlock().asItem().getDefaultInstance();
            }
            ((TieredBeltBlock) state.getBlock()).setShaftType(shaftType);
            ((TieredBeltBlock) state.getBlock()).setTier(tier);
            if(part == BeltPart.MIDDLE && pulley) part = BeltPart.PULLEY;
            if(pulley && shaftState.getValue(AbstractShaftBlock.AXIS) == Axis.Y) slope = BeltSlope.SIDEWAYS;
            if(!existingState.canBeReplaced()) level.destroyBlock(pos, false);
            KineticBlockEntity.switchToBlockState(level, pos, ProperWaterloggedBlock.withWater(level, state
                    .setValue(BeltBlock.SLOPE, slope)
                    .setValue(BeltBlock.PART, part)
                    .setValue(BeltBlock.HORIZONTAL_FACING, facing), pos));
            if(level.getBlockEntity(pos) instanceof TieredBeltBlockEntity be) {
                be.setShaftType(shaftType);
                be.setTier(tier);
            }
        }

        if(!failed) return;
        for(BlockPos pos : beltsToCreate) {
            if(level.getBlockState(pos).getBlock() == state.getBlock()) level.destroyBlock(pos, false);
        }
    }

    private static Direction getFacingFromTo(BlockPos start, BlockPos end) {
        Axis beltAxis = start.getX() == end.getX() ? Axis.Z : Axis.X;
        BlockPos diff = end.subtract(start);
        AxisDirection dir;
        if(diff.getX() == 0 && diff.getZ() == 0) {
            dir = diff.getY() > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE;
        } else {
            dir = beltAxis.choose(diff.getX(), 0, diff.getZ()) > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE;
        }
        return Direction.get(dir, beltAxis);
    }

    private static BeltSlope getSlopeBetween(BlockPos start, BlockPos end) {
        BlockPos diff = end.subtract(start);
        if(diff.getY() != 0) {
            if(diff.getZ() != 0 || diff.getX() != 0) {
                return diff.getY() > 0 ? BeltSlope.UPWARD : BeltSlope.DOWNWARD;
            }
            return BeltSlope.VERTICAL;
        }
        return BeltSlope.HORIZONTAL;
    }

    private static List<BlockPos> getBeltChainBetween(BlockPos start, BlockPos end, BeltSlope slope, Direction dir) {
        List<BlockPos> positions = new LinkedList<>();
        int limit = 1000;
        BlockPos current = start;
        do {
            positions.add(current);
            if(slope == BeltSlope.VERTICAL) {
                current = current.above(dir.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1);
                continue;
            }
            current = current.relative(dir);
            if(slope != BeltSlope.HORIZONTAL) current = current.above(slope == BeltSlope.UPWARD ? 1 : -1);
        } while(!current.equals(end) && limit-- > 0);
        positions.add(end);
        return positions;
    }

    public static boolean canConnect(Level level, BlockPos first, BlockPos second, ItemStack heldStack) {
        if(!level.isLoaded(first) || !level.isLoaded(second)) return false;
        if(!(heldStack.getItem() instanceof TieredBeltConnectorItem tbci)) return false;
        if(!second.closerThan(first, GConfigUtility.getBeltLengthFromMaterial(tbci.getBeltMaterial()))) return false;
        BlockPos diff = second.subtract(first);
        Axis shaftAxis = level.getBlockState(first).getValue(BlockStateProperties.AXIS);
        int x = diff.getX();
        int y = diff.getY();
        int z = diff.getZ();
        int sames = ((Math.abs(x) == Math.abs(y)) ? 1 : 0) +
                ((Math.abs(y) == Math.abs(z)) ? 1 : 0) + ((Math.abs(x) == Math.abs(z)) ? 1 : 0);
        if(shaftAxis.choose(x, y, z) != 0) return false;
        if(sames != 1) return false;
        if(shaftAxis != level.getBlockState(second).getValue(BlockStateProperties.AXIS)) return false;
        if(shaftAxis == Axis.Y && x != 0 && z != 0) return false;
        BlockEntity be = level.getBlockEntity(first);
        BlockEntity be2 = level.getBlockEntity(second);
        if(!(be instanceof TieredBracketedKineticBlockEntity kbe)) return false;
        if(!(be2 instanceof TieredBracketedKineticBlockEntity kbe2)) return false;
        if(level.getBlockState(first).getBlock() != level.getBlockState(second).getBlock()) return false;
        List<Block> validShafts = Belts.VALID_SHAFTS.get(Block.byItem(heldStack.getItem()));
        if(!validShafts.contains(level.getBlockState(first).getBlock())) return false;
        if(!validShafts.contains(level.getBlockState(second).getBlock())) return false;
        float speed = kbe.getTheoreticalSpeed();
        float speed2 = kbe2.getTheoreticalSpeed();

        if(Math.signum(speed) != Math.signum(speed2) && speed != 0 && speed2 != 0) return false;

        BlockPos step = BlockPos.containing(Math.signum(diff.getX()), Math.signum(diff.getY()), Math.signum(diff.getZ()));
        int limit = 1000;
        for(BlockPos pos = first.offset(step); !pos.equals(second) && limit-- > 0; pos = pos.offset(step)) {
            BlockState blockState = level.getBlockState(pos);
            if(blockState.getBlock() instanceof TieredBeltBlock && blockState.getValue(AbstractSimpleShaftBlock.AXIS) == shaftAxis) continue;
            if(!blockState.canBeReplaced()) return false;
        }
        return true;
    }

    public static boolean validateAxis(Level level, BlockPos pos) {
        if(!level.isLoaded(pos)) return false;
        if(!(level.getBlockState(pos).getBlock() instanceof TieredShaftBlock)) return false;
        return true;
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
