package electrolyte.greate.content.kinetics.gearbox;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class TieredGearboxBlock extends GearboxBlock implements ITieredBlock, ITieredPartialModel {

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
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        if(state.getValue(AXIS).isVertical()) {
            return super.getCloneItemStack(state, target, level, pos, player);
        }
        return TieredVerticalGearboxItem.MAP.get(this).getDefaultInstance();
    }

    @Override
    public BlockEntityType<? extends GearboxBlockEntity> getBlockEntityType() {
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
        ITieredBlock.super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
