package electrolyte.greate.content.kinetics.simpleRelays.encased;

import com.simibubi.create.content.decoration.encasing.EncasedBlock;
import com.simibubi.create.content.kinetics.base.AbstractEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.foundation.block.IBE;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

public class TieredEncasedShaftBlock extends AbstractEncasedShaftBlock implements IBE<TieredKineticBlockEntity>, ISpecialBlockItemRequirement, EncasedBlock, ITieredBlock, ITieredShaftBlock {

    private final Supplier<Block> casing;
    private final Supplier<Block> shaft;
    private TIER tier;

    public TieredEncasedShaftBlock(Properties properties, Supplier<Block> casing, Supplier<Block> shaft) {
        super(properties);
        this.casing = casing;
        this.shaft = shaft;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pTab, NonNullList<ItemStack> pItems) {}

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        if(context.getLevel().isClientSide) return InteractionResult.SUCCESS;
        context.getLevel().levelEvent(2001, context.getClickedPos(), Block.getId(state));
        KineticBlockEntity.switchToBlockState(context.getLevel(), context.getClickedPos(), getShaft().defaultBlockState().setValue(AXIS, state.getValue(AXIS)));
        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        if(target instanceof HitResult) {
            return ((BlockHitResult) target).getDirection().getAxis() == getRotationAxis(state) ? getShaft().asItem().getDefaultInstance() : getCasing().asItem().getDefaultInstance();
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        return ItemRequirement.of(getShaft().defaultBlockState(), blockEntity);
    }

    @Override
    public Class<TieredKineticBlockEntity> getBlockEntityClass() {
        return TieredKineticBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TieredKineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_ENCASED_SHAFT.get();
    }

    @Override
    public Block getCasing() {
        return casing.get();
    }

    @Override
    public Block getShaft() {
        return shaft.get();
    }

    @Override
    public void handleEncasing(BlockState state, Level level, BlockPos pos, ItemStack heldItem, Player player, InteractionHand hand, BlockHitResult ray) {
        KineticBlockEntity.switchToBlockState(level, pos, defaultBlockState().setValue(RotatedPillarKineticBlock.AXIS, state.getValue(RotatedPillarKineticBlock.AXIS)));
    }

    @Override
    public TIER getTier() {
        return this.tier;
    }

    @Override
    public void setTier(TIER tier) {
        this.tier = tier;
    }
}
