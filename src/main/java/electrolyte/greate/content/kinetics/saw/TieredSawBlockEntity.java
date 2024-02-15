package electrolyte.greate.content.kinetics.saw;

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

public class TieredSawBlockEntity extends SawBlockEntity implements ITieredKineticBlockEntity {

    private int tier;
    private SmartFluidTankBehaviour inputTank;
    private LazyOptional<IFluidHandler> fluidCapability;

    public TieredSawBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        tier = ((ITieredBlock) state.getBlock()).getTier();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        if(canProcess()) {
            inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, 16000, false);
            behaviours.add(inputTank);

            fluidCapability = LazyOptional.of(() -> {
                LazyOptional<? extends IFluidHandler> inputCap = inputTank.getCapability();
                return new CombinedTankWrapper(inputCap.orElse(null));
            });
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
        if(canProcess()) {
            IFluidHandler fluid = fluidCapability.orElse(new FluidTank(0));
            LangBuilder mb = Lang.translate("generic.unit.millibuckets");
            FluidStack fluidStack = fluid.getFluidInTank(0);
            if(! fluidStack.isEmpty()) {
                Lang.builder(Greate.MOD_ID).translate("gui.goggles.saw_contents").forGoggles(tooltip);
                Lang.text("")
                        .add(Lang.fluidName(fluidStack)
                                .add(Lang.text(" ")).style(ChatFormatting.GRAY)
                                .add(Lang.number(fluidStack.getAmount()).add(mb).style(ChatFormatting.BLUE)))
                        .forGoggles(tooltip, 1);
            } else {
                tooltip.remove(0);
            }
        }
        return true;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if(fluidCapability != null) {
            fluidCapability.invalidate();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if(cap == ForgeCapabilities.FLUID_HANDLER && side != Direction.DOWN) {
            return fluidCapability.cast();
        }
        return super.getCapability(cap, side);
    }


}
