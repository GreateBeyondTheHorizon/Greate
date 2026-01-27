package electrolyte.greate.content.kinetics.fan;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.fluid.FluidHelper;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.GreatePartialModels.FAN_INNER_MODELS;

public class TieredEncasedFanBlock extends EncasedFanBlock implements IBE<EncasedFanBlockEntity>, ITieredBlock {

    private int tier;
    private PartialModel[] models;

    public TieredEncasedFanBlock(Properties properties) {
        super(properties);
        this.models = new PartialModel[]{
                FAN_INNER_MODELS[tier],
                GreateModelUtils.getPartialModel(this, "/shaft_half")};
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        return onBlockEntityUse(worldIn, pos, be -> {
            if(!heldItem.isEmpty()) {
                if(FluidHelper.tryEmptyItemIntoBE(worldIn, player, handIn, heldItem, be)) {
                    player.playSound(SoundEvents.BUCKET_EMPTY);
                    return InteractionResult.SUCCESS;
                }
                if(FluidHelper.tryFillItemFromBE(worldIn, player, handIn, heldItem, be)) {
                    player.playSound(SoundEvents.BUCKET_FILL);
                    return InteractionResult.SUCCESS;
                }
                if(GenericItemEmptying.canItemBeEmptied(worldIn, heldItem) ||
                        GenericItemFilling.canItemBeFilled(worldIn, heldItem)) return InteractionResult.SUCCESS;
                if(heldItem.getItem().equals(Items.SPONGE) &&
                        !be.getCapability(ForgeCapabilities.FLUID_HANDLER).map(fh -> fh.drain(Integer.MAX_VALUE, FluidAction.EXECUTE))
                                .orElse(FluidStack.EMPTY)
                                .isEmpty()) {
                    player.playSound(SoundEvents.BOTTLE_EMPTY);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
            be.notifyUpdate();
            return InteractionResult.SUCCESS;
        });
    }

    @Override
    public BlockEntityType<? extends EncasedFanBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_FAN.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    public PartialModel[] getModels() {
        return models;
    }

    @Override
    public Material getMaterial() {
        return TM[tier];
    }
}
