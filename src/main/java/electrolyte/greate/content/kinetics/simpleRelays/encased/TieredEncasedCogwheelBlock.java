package electrolyte.greate.content.kinetics.simpleRelays.encased;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

import static electrolyte.greate.registry.GreateTagPrefixes.cogwheel;
import static electrolyte.greate.registry.GreateTagPrefixes.largeCogwheel;

public class TieredEncasedCogwheelBlock extends EncasedCogwheelBlock implements ITieredBlock {

    private int tier;
    private final Supplier<Block> cogwheelBlock;

    public static TieredEncasedCogwheelBlock small(Properties properties, Supplier<Block> casing, Material mat) {
        return new TieredEncasedCogwheelBlock(properties, false, casing, () -> ChemicalHelper.getBlock(cogwheel, mat));
    }

    public static TieredEncasedCogwheelBlock large(Properties properties, Supplier<Block> casing, Material mat) {
        return new TieredEncasedCogwheelBlock(properties, true, casing, () -> ChemicalHelper.getBlock(largeCogwheel, mat));
    }

    public TieredEncasedCogwheelBlock(Properties properties, boolean isLarge, Supplier<Block> casing, Supplier<Block> cogwheel) {
        super(properties, isLarge, casing);
        this.cogwheelBlock = cogwheel;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        if(target instanceof BlockHitResult) {
            return ((BlockHitResult) target).getDirection().getAxis() != getRotationAxis(state) ?
                    getCogWheel().asItem().getDefaultInstance() :
                    getCasing().asItem().getDefaultInstance();
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        if(context.getLevel().isClientSide) return InteractionResult.SUCCESS;
        context.getLevel().levelEvent(2001, context.getClickedPos(), Block.getId(state));
        KineticBlockEntity.switchToBlockState(context.getLevel(), context.getClickedPos(), getCogWheel().defaultBlockState().setValue(AXIS, state.getValue(AXIS)));
        return InteractionResult.SUCCESS;
    }

    public Block getCogWheel() {
        return cogwheelBlock.get();
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        return ItemRequirement.of(getCogWheel().defaultBlockState(), blockEntity);
    }

    @Override
    public BlockEntityType<? extends SimpleKineticBlockEntity> getBlockEntityType() {
        return isLarge ? ModBlockEntityTypes.TIERED_ENCASED_LARGE_COGWHEEL.get() : ModBlockEntityTypes.TIERED_ENCASED_COGWHEEL.get();
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }
}
