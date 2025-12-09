package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement.ItemUseType;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.foundation.client.models.BeltModel;
import electrolyte.greate.registry.GreateSpriteShifts;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

import static electrolyte.greate.registry.GreateTagPrefixes.beltConnector;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class TieredBeltBlock extends BeltBlock implements ITieredBlock, ITieredBelt {

    private int tier;
    private Material beltMaterial;
    private Material shaftMaterial;

    public TieredBeltBlock(Properties properties) {
        super(properties);
        if(GTCEu.isClientSide()) {
            BeltModel.create(this);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return ChemicalHelper.get(beltConnector, beltMaterial);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, Builder pBuilder) {
        List<ItemStack> drops = super.getDrops(pState, pBuilder);
        BlockEntity be = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if(be instanceof TieredBeltBlockEntity tbe) {
            if(tbe.hasPulley()) {
                drops.removeIf(s -> s.is(AllBlocks.SHAFT.asItem()));
                drops.add(ChemicalHelper.get(shaft, getShaftMaterial()));
            }
        }
        return drops;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        if(pLevel.isClientSide) return;
        if(pState.getBlock() == pNewState.getBlock()) return;
        if(pIsMoving) return;

        for(boolean forward : Iterate.trueAndFalse) {
            BlockPos currentPos = nextSegmentPosition(pState, pPos, forward);
            if(currentPos == null) continue;
            BlockState currentState = pLevel.getBlockState(currentPos);
            if(currentState.getBlock() != this) continue;
            boolean hasPulley = false;
            BlockEntity be = pLevel.getBlockEntity(currentPos);
            if(be instanceof TieredBeltBlockEntity beltBE) {
                if(beltBE.isController()) {
                    beltBE.getInventory().ejectAll();
                }
                hasPulley = beltBE.hasPulley();
            }

            pLevel.removeBlockEntity(currentPos);
            BlockState shaftState = ChemicalHelper.getBlock(shaft, getShaftMaterial()).defaultBlockState().setValue(AXIS, getRotationAxis(currentState));
            pLevel.setBlock(currentPos, ProperWaterloggedBlock.withWater(pLevel, hasPulley ? shaftState : Blocks.AIR.defaultBlockState(), currentPos), 3);
            pLevel.levelEvent(2001, currentPos, Block.getId(currentState));
        }
    }

    @Override
    public BlockEntityType<? extends TieredBeltBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_BELT.get();
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        List<ItemStack> required = new ArrayList<>();
        if(state.getValue(PART) != BeltPart.MIDDLE)
            required.add(ChemicalHelper.get(shaft, getShaftMaterial()));
        if(state.getValue(PART) == BeltPart.START)
            required.add(ChemicalHelper.get(beltConnector, beltMaterial));
        if(required.isEmpty())
            return ItemRequirement.NONE;
        return new ItemRequirement(ItemUseType.CONSUME, required);
    }

    public static void initBelt(Level world, BlockPos pos) {
		if (world.isClientSide) return;
		if (world instanceof ServerLevel && ((ServerLevel) world).getChunkSource().getGenerator() instanceof DebugLevelSource) return;

		BlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof TieredBeltBlock)) return;

		int limit = 1000;
		BlockPos currentPos = pos;
		while (limit-- > 0) {
			BlockState currentState = world.getBlockState(currentPos);
			if (!(currentState.getBlock() instanceof TieredBeltBlock)) {
				world.destroyBlock(pos, true);
				return;
			}
			BlockPos nextSegmentPosition = nextSegmentPosition(currentState, currentPos, false);
			if (nextSegmentPosition == null) break;
			if (!world.isLoaded(nextSegmentPosition)) return;
			currentPos = nextSegmentPosition;
		}

		int index = 0;
		List<BlockPos> beltChain = getBeltChain(world, currentPos);
		if (beltChain.size() < 2) {
			world.destroyBlock(currentPos, true);
			return;
		}

		for (BlockPos beltPos : beltChain) {
			BlockEntity blockEntity = world.getBlockEntity(beltPos);
			BlockState currentState = world.getBlockState(beltPos);

			if (blockEntity instanceof TieredBeltBlockEntity be && currentState.getBlock() instanceof TieredBeltBlock tbb) {
				be.setController(currentPos);
				be.beltLength = beltChain.size();
				be.index = index;
                be.setShaftMaterial(tbb.getShaftMaterial());
				be.attachKinetics();
				be.setChanged();
				be.sendData();

				if (be.isController() && !canTransportObjects(currentState))
					be.getInventory().ejectAll();
			} else {
				world.destroyBlock(currentPos, true);
				return;
			}
			index++;
		}

	}

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    public void setupBeltModel(Material beltMaterial) {
        GreateSpriteShifts.populateMaps(beltMaterial);
    }

    @Override
    public Material getBeltMaterial() {
        return beltMaterial;
    }

    @Override
    public void setBeltMaterial(Material material) {
        this.beltMaterial = material;
    }

    public Material getShaftMaterial() {
        return shaftMaterial;
    }

    public void setShaftMaterial(Material shaftMaterial) {
        this.shaftMaterial = shaftMaterial;
    }
}
