package electrolyte.greate.mixin;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BasinBlockEntity.class)
public abstract class MixinBasinBlockEntity extends SmartBlockEntity {

    @Shadow(remap = false) public SmartFluidTankBehaviour inputTank;
    @Shadow(remap = false) protected SmartFluidTankBehaviour outputTank;
    @Shadow(remap = false) private boolean contentsChanged;
    @Shadow(remap = false) protected IItemHandlerModifiable itemCapability;
    @Shadow(remap = false) protected IFluidHandler fluidCapability;

    @Shadow(remap = false) public abstract boolean isEmpty();

    public MixinBasinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "addBehaviours", at = @At("RETURN"), remap = false)
    private void greate$addBehaviors(List<BlockEntityBehaviour> behaviours, CallbackInfo ci) {
        behaviours.remove(inputTank);
        behaviours.remove(outputTank);
        inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 2, 16000, true).whenFluidUpdates(() -> contentsChanged = true);
        outputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 2, 16000, true).whenFluidUpdates(() -> contentsChanged = true).forbidInsertion();
        behaviours.add(inputTank);
        behaviours.add(outputTank);
    }

    @Inject(method = "addToGoggleTooltip", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate$addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {
        CreateLang.translate("gui.goggles.basin_contents").forGoggles(tooltip);
        if(itemCapability == null) itemCapability = new ItemStackHandler();
        if(fluidCapability == null) fluidCapability = new FluidTank(0);
        boolean isEmpty = true;

        for(int i = 0; i < itemCapability.getSlots(); i++) {
            ItemStack stackInSlot = itemCapability.getStackInSlot(i);
            if(stackInSlot.isEmpty()) continue;
            CreateLang.text("")
                    .add(Component.translatable(stackInSlot.getHoverName().getString()).withStyle(ChatFormatting.GRAY))
                    .add(CreateLang.text(" x" + stackInSlot.getCount()).style(ChatFormatting.GREEN))
                    .forGoggles(tooltip, 1);
            isEmpty = false;
        }

        LangBuilder mb = CreateLang.translate("generic.unit.millibuckets");
        for(int i = 0; i < fluidCapability.getTanks(); i++) {
            FluidStack fluidStack = fluidCapability.getFluidInTank(i);
            if(fluidStack.isEmpty()) continue;
            CreateLang.text("")
                    .add(CreateLang.fluidName(fluidStack)
                            .add(CreateLang.text(" ")).style(ChatFormatting.GRAY)
                            .add(CreateLang.number(fluidStack.getAmount()).add(mb).style(ChatFormatting.BLUE)))
                    .forGoggles(tooltip, 1);
            isEmpty = false;
        }

        if(isEmpty) {
            tooltip.remove(0);
        }
        cir.setReturnValue(true);
    }
}
