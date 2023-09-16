package electrolyte.greate.content.kinetics.gearbox;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.fabricmc.fabric.api.block.BlockPickInteractionAware;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class TieredGearboxBlock extends RotatedPillarKineticBlock implements IBE<TieredGearboxBlockEntity>, ITieredBlock, ITieredPartialModel, BlockPickInteractionAware {

    private TIER tier;
    private PartialModel partialModel;

    public TieredGearboxBlock(Properties properties, PartialModel partialModel) {
        super(properties);
        this.partialModel = partialModel;
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, Builder pBuilder) {
        if(pState.getValue(AXIS).isVertical()) {
            return super.getDrops(pState, pBuilder);
        }
        return List.of(TieredVerticalGearboxItem.MAP.get(this).getDefaultInstance());
    }

    @Override
    public ItemStack getPickedStack(BlockState state, BlockGetter level, BlockPos pos, Player player, HitResult hit) {
        if(state.getValue(AXIS).isVertical()) {
            return super.getCloneItemStack(level, pos, state);
        }
        return TieredVerticalGearboxItem.MAP.get(this).getDefaultInstance();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(AXIS, Axis.Y);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() != state.getValue(AXIS);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public Class<TieredGearboxBlockEntity> getBlockEntityClass() {
        return TieredGearboxBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TieredGearboxBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_GEARBOX.get();
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
    public PartialModel getPartialModel() {
        return partialModel;
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.capacity").append(Component.literal(String.valueOf(tier.getStressCapacity())).withStyle(tier.getTierColor())).append(" (").append(Component.literal(tier.getName()).withStyle(tier.getTierColor())).append(")").withStyle(ChatFormatting.DARK_GRAY));
    }
}
