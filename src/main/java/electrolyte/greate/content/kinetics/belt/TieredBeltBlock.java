package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement.ItemUseType;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import electrolyte.greate.registry.GreateSpriteShifts;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

import static electrolyte.greate.registry.GreateTagPrefixes.beltConnector;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class TieredBeltBlock extends BeltBlock implements ITieredBelt {

    private Material beltMaterial;

    public TieredBeltBlock(Properties properties) {
        super(properties);
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
                drops.add(ChemicalHelper.get(shaft, tbe.getShaftMaterial()));
            }
        }
        return drops;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        IBE.onRemove(pState, pLevel, pPos, pNewState);
        Material shaftMaterial = GTMaterials.NULL;
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
                if(shaftMaterial == GTMaterials.NULL) {
                    shaftMaterial = beltBE.getShaftMaterial();
                }
                if(beltBE.isController()) {
                    beltBE.getInventory().ejectAll();
                }
                hasPulley = beltBE.hasPulley();

                pLevel.removeBlockEntity(currentPos);
                BlockState shaftState = ChemicalHelper.getBlock(shaft, shaftMaterial).defaultBlockState().setValue(AXIS, getRotationAxis(currentState));
                pLevel.setBlock(currentPos, ProperWaterloggedBlock.withWater(pLevel, hasPulley ? shaftState : Blocks.AIR.defaultBlockState(), currentPos), Block.UPDATE_ALL);
                pLevel.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, currentPos, Block.getId(currentState));
            }
        }
    }

    @Override
    public BlockEntityType<? extends TieredBeltBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_BELT.get();
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        List<ItemStack> required = new ArrayList<>();
        if(state.getValue(PART) != BeltPart.MIDDLE) {
            Material shaftMaterial = ((TieredBeltBlockEntity) blockEntity).getShaftMaterial();
            required.add(ChemicalHelper.get(shaft, shaftMaterial));
        }
        if(state.getValue(PART) == BeltPart.START)
            required.add(ChemicalHelper.get(beltConnector, beltMaterial));
        if(required.isEmpty()) return ItemRequirement.NONE;
        return new ItemRequirement(ItemUseType.CONSUME, required);
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
}
