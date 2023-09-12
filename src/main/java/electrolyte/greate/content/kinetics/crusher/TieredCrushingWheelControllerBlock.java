package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

import static com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock.VALID;

public class TieredCrushingWheelControllerBlock extends DirectionalBlock implements IBE<TieredCrushingWheelControllerBlockEntity>, ITieredBlock {

    private TIER tier;
    private final Block crushingWheel;
    public static Map<Block, Block> MAP = new HashMap<>();

    public TieredCrushingWheelControllerBlock(Properties properties, Block crushingWheel) {
        super(properties);
        this.crushingWheel = crushingWheel;
        MAP.put(crushingWheel, this);
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pContext) {
        return false;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(VALID);
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if(!pState.getValue(VALID)) return;
        Direction dir = pState.getValue(FACING);
        Axis axis = dir.getAxis();
        checkEntityForProcessing(pLevel, pPos, pEntity);
        withBlockEntityDo(pLevel, pPos, be -> {
            if(be.processingEntity == pEntity) {
                pEntity.makeStuckInBlock(pState, new Vec3(axis == Axis.X ? (double) 0.05F : 0.25D, axis == Axis.Y ? (double) 0.05F : 0.25D, axis == Axis.Z ? (double) 0.05F : 0.25D));
            }
        });
    }

    public void checkEntityForProcessing(Level level, BlockPos pos, Entity entity) {
        TieredCrushingWheelControllerBlockEntity be = getBlockEntity(level, pos);
        if(be == null) return;
        if(be.crushingSpeed == 0) return;
        CompoundTag tag = entity.getPersistentData();
        if(tag.contains("BypassCrushingWheel")) {
            if(pos.equals(NbtUtils.readBlockPos(tag.getCompound("BypassCrushingWheel")))) return;
        }
        if(be.isOccupied()) return;
        boolean isPlayer = entity instanceof Player;
        if(isPlayer && ((Player) entity).isCreative()) return;
        if(isPlayer && entity.level().getDifficulty() == Difficulty.PEACEFUL) return;
        be.startCrushing(entity);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if(!pState.getValue(VALID)) return;
        if(pRandom.nextInt(1) != 0) return;
        double d0 = (float) pPos.getX() + pRandom.nextFloat();
        double d1 = (float) pPos.getY() + pRandom.nextFloat();
        double d2 = (float) pPos.getZ() + pRandom.nextFloat();
        pLevel.addParticle(ParticleTypes.CRIT, d0, d1, d2, 0D, 0D, 0D);
    }

    public void updateSpeed(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        withBlockEntityDo(pLevel, pPos, be -> {
            if(!pState.getValue(VALID)) {
                if(be.crushingSpeed != 0) {
                    be.crushingSpeed = 0;
                    be.sendData();
                }
                return;
            }
            for(Direction dir : Iterate.directions) {
                BlockState state = pLevel.getBlockState(pPos.relative(dir));
                if(crushingWheel != state.getBlock()) continue;
                if(state.getValue(BlockStateProperties.AXIS) == dir.getAxis()) continue;
                BlockEntity adjBE = pLevel.getBlockEntity(pPos.relative(dir));
                if(!(adjBE instanceof TieredCrushingWheelBlockEntity tcwbe)) continue;
                be.crushingSpeed = Math.abs(tcwbe.getSpeed() / 50F);
                be.sendData();
                tcwbe.award(AllAdvancements.CRUSHING_WHEEL);
                if(tcwbe.getSpeed() > 255) {
                    tcwbe.award(AllAdvancements.CRUSHER_MAXED);
                }
                break;
            }
        });
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape standardShape = AllShapes.CRUSHING_WHEEL_CONTROLLER_COLLISION.get(pState.getValue(FACING));
        if(!pState.getValue(VALID)) return standardShape;
        if(!(pContext instanceof EntityCollisionContext ecc)) return standardShape;
        Entity entity = ecc.getEntity();
        if(entity == null) return standardShape;
        CompoundTag tag = entity.getPersistentData();
        if(tag.contains("BypassCrushingWheel")) {
            if(pPos.equals(NbtUtils.readBlockPos(tag.getCompound("BypassCrushingWheel")))) {
                if(pState.getValue(FACING) != Direction.UP) {
                    return Shapes.empty();
                }
            }
        }
        TieredCrushingWheelControllerBlockEntity be = getBlockEntity(pLevel, pPos);
        if(be != null && be.processingEntity == entity) return Shapes.empty();
        return standardShape;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(!pState.hasBlockEntity() || pState.getBlock() == pNewState.getBlock()) return;
        withBlockEntityDo(pLevel, pPos, be -> ItemHelper.dropContents(pLevel, pPos, be.inventory));
        pLevel.removeBlockEntity(pPos);
    }

    @Override
    public Class<TieredCrushingWheelControllerBlockEntity> getBlockEntityClass() {
        return TieredCrushingWheelControllerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TieredCrushingWheelControllerBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_CRUSHING_WHEEL_CONTROLLER.get();
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public TIER getTier() {
        return tier;
    }

    @Override
    public void setTier(TIER tier) {
        this.tier = tier;
    }
}
