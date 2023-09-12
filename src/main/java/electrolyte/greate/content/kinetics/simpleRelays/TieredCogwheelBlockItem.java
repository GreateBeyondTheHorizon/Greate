package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.function.Predicate;

import static com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock.AXIS;

public class TieredCogwheelBlockItem extends BlockItem {

    boolean large;

    private final int placementHelperID;
    private final int integratedCogHelperID;

    public TieredCogwheelBlockItem(TieredCogwheelBlock pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        large = pBlock.isLarge;

        placementHelperID = PlacementHelpers.register(large ? new LargeCogHelper() : new SmallCogHelper());
        integratedCogHelperID = PlacementHelpers.register(large ? new IntegratedLargeCogHelper() : new IntegratedSmallCogHelper());
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        IPlacementHelper helper = PlacementHelpers.get(placementHelperID);
        Player player = context.getPlayer();
        BlockHitResult ray = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, true);
        if (helper.matchesState(state) && player != null && !player.isShiftKeyDown()) {
            return helper.getOffset(player, world, state, pos, ray)
                    .placeInWorld(world, this, player, context.getHand(), ray);
        }

        if (integratedCogHelperID != -1) {
            helper = PlacementHelpers.get(integratedCogHelperID);

            if (helper.matchesState(state) && player != null && !player.isShiftKeyDown()) {
                return helper.getOffset(player, world, state, pos, ray)
                        .placeInWorld(world, this, player, context.getHand(), ray);
            }
        }

        return super.onItemUseFirst(stack, context);
    }

    @MethodsReturnNonnullByDefault
    private static class SmallCogHelper extends CogwheelBlockItem.DiagonalCogHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isSmallCogItem).and(ICogWheel::isDedicatedCogItem);
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            if (hitOnShaft(state, ray))
                return PlacementOffset.fail();

            if (!ICogWheel.isLargeCog(state)) {
                Direction.Axis axis = ((IRotate) state.getBlock()).getRotationAxis(state);
                List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), axis);

                for (Direction dir : directions) {
                    BlockPos newPos = pos.relative(dir);

                    if (!TieredCogwheelBlock.isValidCogwheelPosition(false, world, newPos, axis))
                        continue;

                    if (!world.getBlockState(newPos).canBeReplaced())
                        continue;

                    return PlacementOffset.success(newPos, s -> s.setValue(AXIS, axis));

                }

                return PlacementOffset.fail();
            }

            return super.getOffset(player, world, state, pos, ray);
        }
    }

    @MethodsReturnNonnullByDefault
    private static class LargeCogHelper extends CogwheelBlockItem.DiagonalCogHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isLargeCogItem).and(ICogWheel::isDedicatedCogItem);
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            if (hitOnShaft(state, ray))
                return PlacementOffset.fail();

            if (ICogWheel.isLargeCog(state)) {
                Direction.Axis axis = ((IRotate) state.getBlock()).getRotationAxis(state);
                Direction side = IPlacementHelper.orderedByDistanceOnlyAxis(pos, ray.getLocation(), axis)
                        .get(0);
                List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), axis);
                for (Direction dir : directions) {
                    BlockPos newPos = pos.relative(dir)
                            .relative(side);

                    if (!TieredCogwheelBlock.isValidCogwheelPosition(true, world, newPos, dir.getAxis()))
                        continue;

                    if (!world.getBlockState(newPos).canBeReplaced())
                        continue;

                    return PlacementOffset.success(newPos, s -> s.setValue(AXIS, dir.getAxis()));
                }

                return PlacementOffset.fail();
            }

            return super.getOffset(player, world, state, pos, ray);
        }
    }

    @MethodsReturnNonnullByDefault
    public static class IntegratedLargeCogHelper implements IPlacementHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isLargeCogItem).and(ICogWheel::isDedicatedCogItem);
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> !ICogWheel.isDedicatedCogWheel(s.getBlock()) && ICogWheel.isSmallCog(s);
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            Direction face = ray.getDirection();
            Direction.Axis newAxis;

            if (state.hasProperty(HorizontalKineticBlock.HORIZONTAL_FACING))
                newAxis = state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING)
                        .getAxis();
            else if (state.hasProperty(DirectionalKineticBlock.FACING))
                newAxis = state.getValue(DirectionalKineticBlock.FACING)
                        .getAxis();
            else if (state.hasProperty(RotatedPillarKineticBlock.AXIS))
                newAxis = state.getValue(RotatedPillarKineticBlock.AXIS);
            else
                newAxis = Direction.Axis.Y;

            if (face.getAxis() == newAxis)
                return PlacementOffset.fail();

            List<Direction> directions =
                    IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), face.getAxis(), newAxis);

            for (Direction d : directions) {
                BlockPos newPos = pos.relative(face)
                        .relative(d);

                if (!world.getBlockState(newPos).canBeReplaced())
                    continue;

                if (!TieredCogwheelBlock.isValidCogwheelPosition(false, world, newPos, newAxis))
                    return PlacementOffset.fail();

                return PlacementOffset.success(newPos, s -> s.setValue(TieredCogwheelBlock.AXIS, newAxis));
            }

            return PlacementOffset.fail();
        }

    }

    @MethodsReturnNonnullByDefault
    public static class IntegratedSmallCogHelper implements IPlacementHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isSmallCogItem).and(ICogWheel::isDedicatedCogItem);
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> !ICogWheel.isDedicatedCogWheel(s.getBlock()) && ICogWheel.isSmallCog(s);
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            Direction face = ray.getDirection();
            Direction.Axis newAxis;

            if (state.hasProperty(HorizontalKineticBlock.HORIZONTAL_FACING))
                newAxis = state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING)
                        .getAxis();
            else if (state.hasProperty(DirectionalKineticBlock.FACING))
                newAxis = state.getValue(DirectionalKineticBlock.FACING)
                        .getAxis();
            else if (state.hasProperty(RotatedPillarKineticBlock.AXIS))
                newAxis = state.getValue(RotatedPillarKineticBlock.AXIS);
            else
                newAxis = Direction.Axis.Y;

            if (face.getAxis() == newAxis)
                return PlacementOffset.fail();

            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), newAxis);

            for (Direction d : directions) {
                BlockPos newPos = pos.relative(d);

                if (!world.getBlockState(newPos).canBeReplaced())
                    continue;

                if (!TieredCogwheelBlock.isValidCogwheelPosition(false, world, newPos, newAxis))
                    return PlacementOffset.fail();

                return PlacementOffset.success()
                        .at(newPos)
                        .withTransform(s -> s.setValue(TieredCogwheelBlock.AXIS, newAxis));
            }

            return PlacementOffset.fail();
        }

    }
}
