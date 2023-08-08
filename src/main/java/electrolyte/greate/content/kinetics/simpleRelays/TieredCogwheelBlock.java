package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import electrolyte.greate.GreateEnums;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TieredCogwheelBlock extends CogWheelBlock implements ITieredBlock {

    boolean isLarge;
    TIER tier;

    protected TieredCogwheelBlock(boolean large, Properties properties) {
        super(large, properties);
        this.isLarge = large;
    }

    public static TieredCogwheelBlock small(Properties properties) {
        return new TieredCogwheelBlock(false, properties);
    }

    public static TieredCogwheelBlock large(Properties properties) {
        return new TieredCogwheelBlock(true, properties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        //todo: break when lower tier shaft is connected to higher tier shaft
        return isValidCogwheelPosition(ICogWheel.isLargeCog(pState), pLevel, pPos, pState.getValue(AXIS));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.isShiftKeyDown() || !pPlayer.mayBuild()) return InteractionResult.PASS;
        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        InteractionResult result = tryEncase(pState, pLevel, pPos, heldItem, pPlayer, pHand, pHit);
        if(result.consumesAction()) return result;
        return InteractionResult.PASS;
    }

    @Override
    public GreateEnums.TIER getTier() {
        return tier;
    }

    @Override
    public void setTier(GreateEnums.TIER tier) {
        this.tier = tier;
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_KINETIC.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.shaft_capacity").append(Component.literal(String.valueOf(tier.getStressCapacity())).withStyle(tier.getTierColor())).append(" (").append(Component.literal(tier.getName()).withStyle(tier.getTierColor())).append(")").withStyle(ChatFormatting.DARK_GRAY));
    }
}
