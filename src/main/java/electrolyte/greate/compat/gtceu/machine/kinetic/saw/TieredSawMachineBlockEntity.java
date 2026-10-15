package electrolyte.greate.compat.gtceu.machine.kinetic.saw;

import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawFilterSlot;
import com.simibubi.create.content.kinetics.saw.TreeCutter;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.AbstractBlockBreakQueue;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredBlockBreakingKineticMachineBlockEntity;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class TieredSawMachineBlockEntity extends TieredBlockBreakingKineticMachineBlockEntity {

	protected FilteringBehaviour filtering;
	public ScrollValueBehaviour targetCircuit;

    public TieredSawMachineBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		super.addBehaviours(behaviours);
		behaviours.add(new DirectBeltInputBehaviour(this).allowingBeltFunnels());
		filtering = new FilteringBehaviour(this, new SawFilterSlot()).forRecipes();
		behaviours.add(filtering);
		targetCircuit = new ScrollValueBehaviour(Lang.builder(Greate.MOD_ID).translate("tooltip.circuit_number").component(),
				this, new CircuitValueBoxTransform());
		targetCircuit.between(0, 32);
		behaviours.add(targetCircuit);
		registerAwardables(behaviours, AllAdvancements.SAW_PROCESSING);
	}

    @Override
	public void tick() {
		if (shouldRun() && ticksUntilNextProgress < 0)
			destroyNextTick();
		super.tick();
	}

    @Override
	protected boolean shouldRun() {
		return getBlockState().getValue(SawBlock.FACING)
			.getAxis()
			.isHorizontal();
	}

	@Override
	protected BlockPos getBreakingPos() {
		return getBlockPos().relative(getBlockState().getValue(SawBlock.FACING));
	}

	@Override
	public void onBlockBroken(BlockState stateToBreak) {
		Optional<AbstractBlockBreakQueue> dynamicTree =
			TreeCutter.findDynamicTree(stateToBreak.getBlock(), breakingPos);
		if (dynamicTree.isPresent()) {
			dynamicTree.get()
				.destroyBlocks(level, null, this::dropItemFromCutTree);
			return;
		}

		super.onBlockBroken(stateToBreak);
		TreeCutter.findTree(level, breakingPos, stateToBreak)
			.destroyBlocks(level, null, this::dropItemFromCutTree);
	}

	public void dropItemFromCutTree(BlockPos pos, ItemStack stack) {
		float distance = (float) Math.sqrt(pos.distSqr(breakingPos));
		Vec3 dropPos = VecHelper.getCenterOf(pos);
		ItemEntity entity = new ItemEntity(level, dropPos.x, dropPos.y, dropPos.z, stack);
		entity.setDeltaMovement(Vec3.atLowerCornerOf(breakingPos.subtract(this.worldPosition))
			.scale(distance / 20f));
		level.addFreshEntity(entity);
	}

	@Override
	public boolean canBreak(BlockState stateToBreak, float blockHardness) {
		boolean sawable = isSawable(stateToBreak);
		return super.canBreak(stateToBreak, blockHardness) && sawable;
	}

	public static boolean isSawable(BlockState stateToBreak) {
		if (stateToBreak.is(BlockTags.SAPLINGS))
			return false;
		if (TreeCutter.isLog(stateToBreak) || (stateToBreak.is(BlockTags.LEAVES)))
			return true;
		if (TreeCutter.isRoot(stateToBreak))
			return true;
		Block block = stateToBreak.getBlock();
		if (block instanceof BambooStalkBlock)
			return true;
		if (block instanceof StemGrownBlock)
			return true;
		if (block instanceof CactusBlock)
			return true;
		if (block instanceof SugarCaneBlock)
			return true;
		if (block instanceof KelpPlantBlock)
			return true;
		if (block instanceof KelpBlock)
			return true;
		if (block instanceof ChorusPlantBlock)
			return true;
		if (TreeCutter.canDynamicTreeCutFrom(block))
			return true;
		return false;
	}

	private class CircuitValueBoxTransform extends ValueBoxTransform.Sided {

		@Override
		public Sided fromSide(Direction direction) {
			return super.fromSide(direction);
		}

		@Override
		public boolean shouldRender(LevelAccessor level, BlockPos pos, BlockState state) {
			if(state.getValue(SawBlock.FACING) != Direction.UP) return false;
			return super.shouldRender(level, pos, state);
		}

		@Override
		public boolean testHit(LevelAccessor level, BlockPos pos, BlockState state, Vec3 localHit) {
			if(state.getValue(SawBlock.FACING) != Direction.UP) return false;
			boolean t = super.testHit(level, pos, state, localHit);
			return localHit.y < 0.75 && t;
		}

		@Override
		protected Vec3 getSouthLocation() {
			return VecHelper.voxelSpace(8, 6f, 15.5f);
		}

		@Override
		protected boolean isSideActive(BlockState state, Direction direction) {
			if(state.getValue(SawBlock.FACING) != Direction.UP) return false;
			if(direction.getAxis().isVertical()) return false;
			return !((TieredSawMachineBlock) state.getBlock()).hasShaftTowards(level, getBlockPos(), state, direction);
		}
	}
}
