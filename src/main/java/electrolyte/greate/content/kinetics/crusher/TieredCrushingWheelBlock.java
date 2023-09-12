package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.CrushingWheels;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

import static com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock.VALID;

public class TieredCrushingWheelBlock extends RotatedPillarKineticBlock implements IBE<TieredCrushingWheelBlockEntity>, ITieredBlock {

    private TIER tier;

    public TieredCrushingWheelBlock(Properties properties) {
        super(properties);
        CrushingWheels.CRUSHING_WHEELS.add(this);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.CRUSHING_WHEEL_COLLISION_SHAPE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        for(Direction dir : Iterate.directions) {
            if(dir.getAxis() == pState.getValue(AXIS)) continue;
            if(TieredCrushingWheelControllerBlock.MAP.get(this) == pLevel.getBlockState(pPos.relative(dir)).getBlock()) {
                pLevel.removeBlock(pPos.relative(dir), pIsMoving);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public void updateControllers(BlockState state, Level level, BlockPos pos, Direction direction) {
        if(direction.getAxis() == state.getValue(AXIS)) return;
        if(level == null) return;
        BlockPos controllerPos = pos.relative(direction);
        BlockPos otherWheelPos = pos.relative(direction, 2);
        boolean controllerExists = TieredCrushingWheelControllerBlock.MAP.get(this) == level.getBlockState(controllerPos).getBlock();
        boolean controllerIsValid = controllerExists && level.getBlockState(controllerPos).getValue(VALID);
        Direction controllerOldDirection = controllerExists ? level.getBlockState(controllerPos).getValue(CrushingWheelControllerBlock.FACING) : null;
        boolean controllerShouldExist = false;
        boolean controllerShouldBeValid = false;
        Direction controllerNewDirection = Direction.DOWN;
        BlockState otherState = level.getBlockState(otherWheelPos);
        if(this == otherState.getBlock()) {
            controllerShouldExist = true;
            TieredCrushingWheelBlockEntity be = getBlockEntity(level, pos);
            TieredCrushingWheelBlockEntity otherBE = getBlockEntity(level, otherWheelPos);

            if(be != null && otherBE != null && (be.getSpeed() > 0) != (otherBE.getSpeed() > 0) && be.getSpeed() != 0) {
                Axis wheelAxis = state.getValue(AXIS);
                Axis sideAxis = direction.getAxis();
                int controllerADO = Math.round(Math.signum(be.getSpeed()))  * direction.getAxisDirection().getStep();
                Vec3 controllerDirVec = new Vec3(wheelAxis == Axis.X ? 1 : 0, wheelAxis == Axis.Y ? 1 : 0, wheelAxis == Axis.Z ? 1 : 0).cross(
                        new Vec3(sideAxis == Axis.X ? 1 : 0, sideAxis == Axis.Y ? 1 : 0, sideAxis == Axis.Z ? 1 : 0));
                controllerNewDirection = Direction.getNearest(controllerDirVec.x * controllerADO, controllerDirVec.y * controllerADO, controllerDirVec.z * controllerADO);
                controllerShouldBeValid = true;
            }
            if(otherState.getValue(AXIS) != state.getValue(AXIS)) controllerShouldExist = false;
        }
        if(!controllerShouldExist) {
            if(controllerExists) level.setBlockAndUpdate(controllerPos, Blocks.AIR.defaultBlockState());
            return;
        }

        if(!controllerExists) {
            if(!level.getBlockState(controllerPos).canBeReplaced()) return;
            level.setBlockAndUpdate(controllerPos, TieredCrushingWheelControllerBlock.MAP.get(this).defaultBlockState()
                    .setValue(VALID, controllerShouldBeValid)
                    .setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
        } else if(controllerIsValid != controllerShouldBeValid || controllerOldDirection != controllerNewDirection) {
            level.setBlockAndUpdate(controllerPos, level.getBlockState(controllerPos)
                    .setValue(VALID, controllerShouldBeValid)
                    .setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
        }

        ((TieredCrushingWheelControllerBlock) TieredCrushingWheelControllerBlock.MAP.get(this)).updateSpeed(level.getBlockState(controllerPos), level, controllerPos);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if(pEntity.getY() < pPos.getY() + 1.25F || !pEntity.onGround()) return;
        float speed = getBlockEntityOptional(pLevel, pPos).map(TieredCrushingWheelBlockEntity::getSpeed).orElse(0F);
        double x = 0, z = 0;
        if(pState.getValue(AXIS) == Axis.X) {
            z = speed / 20F;
            x += (pPos.getZ() + 0.5F - pEntity.getZ()) * 0.1F;
        }
        if(pState.getValue(AXIS) == Axis.Z) {
            x = speed / -20F;
            z += (pPos.getZ() + 0.5F - pEntity.getZ()) * 0.1F;
        }
        pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(x, 0, z));
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        for (Direction direction : Iterate.directions) {
            BlockPos pos = pPos.relative(direction);
            BlockState state = pLevel.getBlockState(pos);
            Axis stateAxis = pState.getValue(AXIS);
            if (TieredCrushingWheelControllerBlock.MAP.get(this) == state.getBlock() && direction.getAxis() != stateAxis) return false;
            if (this != state.getBlock()) continue;
            if (state.getValue(AXIS) != stateAxis || stateAxis != direction.getAxis()) return false;
        }
        return true;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(AXIS);
    }

    @Override
    public float getParticleInitialRadius() {
        return 1F;
    }

    @Override
    public float getParticleTargetRadius() {
        return 1.125F;
    }

    @Override
    public Class<TieredCrushingWheelBlockEntity> getBlockEntityClass() {
        return TieredCrushingWheelBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TieredCrushingWheelBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_CRUSHING_WHEEL.get();
    }

    @Override
    public TIER getTier() {
        return tier;
    }

    @Override
    public void setTier(TIER tier) {
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.capacity").append(Component.literal(String.valueOf(tier.getStressCapacity())).withStyle(tier.getTierColor())).append(" (").append(Component.literal(tier.getName()).withStyle(tier.getTierColor())).append(")").withStyle(ChatFormatting.DARK_GRAY));
    }
}
