package electrolyte.greate.compat.gtceu.machine.kinetic.saw;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.gregtechceu.gtceu.api.transfer.fluid.CustomFluidTank;
import com.gregtechceu.gtceu.client.model.machine.MachineRenderState;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.utils.ISubscription;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import lombok.Getter;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class TieredSawMachine extends TieredKineticWorkableMachine {

    @SaveField
    public final NotifiableItemStackHandler importItems, exportItems;

    @SaveField
    public final NotifiableFluidTank importTank;

    @SaveField
    @SyncToClient
    @Getter
    private FluidStack containedFluid;

    @Nullable
    protected ISubscription exportFluidSubs;

    //todo: add bucket clicking io and sponge clearing
    public TieredSawMachine(BlockEntityCreationInfo info, int tier) {
        super(info, tier, new TieredSawMachineBlockEntity(info.type(), info.pos(), info.state()));
        boolean canRunRecipes = getBlockState().getValue(SawBlock.FACING) == Direction.UP;
        this.importItems = canRunRecipes ?
                attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxInputs(ItemRecipeCapability.CAP), IO.IN)) :
                attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxInputs(ItemRecipeCapability.CAP), IO.NONE));
        this.exportItems = canRunRecipes ?
                attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxOutputs(ItemRecipeCapability.CAP), IO.OUT)) :
                attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxOutputs(ItemRecipeCapability.CAP), IO.NONE));
        CustomFluidTank EMPTY_TANK = new CustomFluidTank(GTMachineUtils.defaultTankSizeFunction.apply(this.getTier()),
                p -> p.getFluid().equals(Fluids.EMPTY));
        this.importTank = canRunRecipes ?
                attachTrait(new NotifiableFluidTank(getRecipeType().getMaxInputs(FluidRecipeCapability.CAP),
                        GTMachineUtils.defaultTankSizeFunction.apply(this.getTier()),
                        IO.IN, IO.IN)) :
                attachTrait(new NotifiableFluidTank(Collections.singletonList(EMPTY_TANK), IO.NONE));
        this.containedFluid = FluidStack.EMPTY;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.updateStoredFluidFromCache();
        this.exportFluidSubs = this.importTank.addChangedListener(this::onFluidChanged);
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if(this.exportFluidSubs != null) {
            this.exportFluidSubs.unsubscribe();
            this.exportFluidSubs = null;
        }
    }

    private void onFluidChanged() {
        if(!this.isRemote()) {
            this.syncDataHolder.markClientSyncFieldDirty("containedFluid");
            this.updateStoredFluidFromCache();
        }
    }

    private void updateStoredFluidFromCache() {
        FluidStack cachedFluid = this.importTank.getFluidInTank(0);
        this.containedFluid = cachedFluid.isEmpty() ? FluidStack.EMPTY : cachedFluid;
    }

    @Override
    public @NotNull MachineRenderState getRenderState() {
        MachineRenderState machineRenderState = super.getRenderState();
        BlockState blockState = getLevel().getBlockState(getBlockPos());
        if(blockState.hasProperty(SawBlock.FACING)) {
            machineRenderState = machineRenderState.setValue(SawBlock.FACING, blockState.getValue(SawBlock.FACING));
        }
        if(blockState.hasProperty(SawBlock.FLIPPED)) {
            machineRenderState = machineRenderState.setValue(SawBlock.FLIPPED, blockState.getValue(SawBlock.FLIPPED));
        }
        if(blockState.hasProperty(SawBlock.AXIS_ALONG_FIRST_COORDINATE)) {
            machineRenderState = machineRenderState.setValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE, blockState.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE));
        }
        return machineRenderState;
    }

    @Override
    public void onMachineDestroyed() {
        super.onMachineDestroyed();
        importItems.dropInventoryInWorld();
        exportItems.dropInventoryInWorld();
    }

    @Override
    public void afterWorking() {
        this.kineticMachineTrait.getKineticBlockEntity().award(AllAdvancements.SAW_PROCESSING);
    }
}
