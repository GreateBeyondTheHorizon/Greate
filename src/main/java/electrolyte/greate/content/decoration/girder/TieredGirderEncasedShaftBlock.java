package electrolyte.greate.content.decoration.girder;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import electrolyte.greate.content.decoration.encasing.IGirderEncasedBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

import static electrolyte.greate.registry.GreateTagPrefixes.shaft;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class TieredGirderEncasedShaftBlock extends GirderEncasedShaftBlock implements ITieredBlock, ITieredShaftBlock, IGirderEncasedBlock {

    private final Supplier<Block> shaftBlock;
    private int tier;

    public TieredGirderEncasedShaftBlock(Properties properties, Material material) {
        super(properties);
        this.shaftBlock = () -> ChemicalHelper.getBlock(shaft, material);
    }

    @Override
    public BlockEntityType<? extends TieredKineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_ENCASED_SHAFT.get();
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        return ItemRequirement.of(shaftBlock.get().defaultBlockState(), blockEntity)
                .union(ItemRequirement.of(AllBlocks.METAL_GIRDER.getDefaultState(), blockEntity));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        if(target instanceof BlockHitResult) {
            return ((BlockHitResult) target).getDirection().getAxis() == getRotationAxis(state) ? getShaft().asItem().getDefaultInstance() : AllBlocks.METAL_GIRDER.asStack();
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public Block getShaft() {
        return shaftBlock.get();
    }

    @Override
    public void handleEncasing(BlockState state, Level level, BlockPos pos, ItemStack heldItem, Player player, InteractionHand hand) {
        KineticBlockEntity.switchToBlockState(level, pos, defaultBlockState()
                .setValue(WATERLOGGED, state.getValue(WATERLOGGED))
                .setValue(HORIZONTAL_AXIS, state.getValue(AXIS)));
    }
}
