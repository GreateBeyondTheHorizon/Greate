package electrolyte.greate.content.decoration.girder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.HorizontalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.foundation.block.IBE;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.decoration.encasing.IGirderEncasedBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class TieredGirderEncasableBlock extends HorizontalAxisKineticBlock implements IBE<TieredKineticBlockEntity>, SimpleWaterloggedBlock, IWrenchable, ISpecialBlockItemRequirement, ITieredBlock, ITieredShaftBlock, IGirderEncasedBlock {

    public static final BooleanProperty TOP = GirderBlock.TOP;
    public static final BooleanProperty BOTTOM = GirderBlock.BOTTOM;
    private final Supplier<Block> shaft;
    private TIER tier;

    public TieredGirderEncasableBlock(Properties properties, Supplier<Block> shaft) {
        super(properties);
        this.shaft = shaft;
        registerDefaultState(super.defaultBlockState()
                .setValue(WATERLOGGED, false)
                .setValue(TOP, false)
                .setValue(BOTTOM, false));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(TOP, BOTTOM, WATERLOGGED));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.GIRDER_BEAM_SHAFT.get(pState.getValue(HORIZONTAL_AXIS));
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.or(super.getBlockSupportShape(pState, pReader, pPos), AllShapes.EIGHT_VOXEL_POLE.get(Axis.Y));
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return AllBlocks.METAL_GIRDER.getDefaultState()
                .setValue(WATERLOGGED, originalState.getValue(WATERLOGGED))
                .setValue(GirderBlock.X, originalState.getValue(HORIZONTAL_AXIS) == Axis.Z)
                .setValue(GirderBlock.Z, originalState.getValue(HORIZONTAL_AXIS) == Axis.X)
                .setValue(GirderBlock.AXIS, originalState.getValue(HORIZONTAL_AXIS) == Axis.X ? Axis.Z : Axis.X)
                .setValue(GirderBlock.BOTTOM, originalState.getValue(BOTTOM))
                .setValue(GirderBlock.TOP, originalState.getValue(TOP));
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        InteractionResult onWrenched = super.onWrenched(state, context);
        Player player = context.getPlayer();
        if(onWrenched == InteractionResult.SUCCESS && player != null && !player.isCreative()) {
            player.getInventory().placeItemBackInInventory(shaft.get().asItem().getDefaultInstance());
        }
        return onWrenched;
    }

    @Override
    public BlockEntityType<? extends TieredKineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_ENCASED_SHAFT.get();
    }

    @Override
    public Class<TieredKineticBlockEntity> getBlockEntityClass() {
        return TieredKineticBlockEntity.class;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if(pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        Property<Boolean> updateProperty = pDirection == Direction.UP ? TOP : BOTTOM;
        if(pDirection.getAxis().isVertical()) {
            if(pLevel.getBlockState(pCurrentPos.relative(pDirection))
                    .getBlockSupportShape(pLevel, pCurrentPos.relative(pDirection))
                    .isEmpty()) {
                pState = pState.setValue(updateProperty, false);
                return GirderBlock.updateVerticalProperty(pLevel, pCurrentPos, pState, updateProperty, pNeighborState, pDirection);
            }
        }
        return pState;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fLuidState = level.getFluidState(pos);
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(WATERLOGGED, Boolean.valueOf(fLuidState.getType() == Fluids.WATER));
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        return ItemRequirement.of(shaft.get().defaultBlockState(), blockEntity)
                .union(ItemRequirement.of(AllBlocks.METAL_GIRDER.getDefaultState(), blockEntity));
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
    public Block getShaft() {
        return shaft.get();
    }

    @Override
    public void handleEncasing(BlockState state, Level level, BlockPos pos, ItemStack heldItem, Player player, InteractionHand hand) {
        KineticBlockEntity.switchToBlockState(level, pos, defaultBlockState()
                .setValue(WATERLOGGED, state.getValue(WATERLOGGED))
                .setValue(HORIZONTAL_AXIS, state.getValue(AXIS)));
    }
}
