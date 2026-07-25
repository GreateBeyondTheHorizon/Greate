package electrolyte.greate.compat.gtceu.machine.kinetic.saw;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.gregtechceu.gtceu.api.transfer.fluid.CustomFluidTank;
import com.gregtechceu.gtceu.client.model.machine.MachineRenderState;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.utils.ISubscription;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.KineticMachineRecipeLogic;
import lombok.Getter;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
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

    @SaveField
    @SyncToClient
    @Getter
    private ItemStack renderedImportStack;
    @SaveField
    @SyncToClient
    @Getter
    private NonNullList<ItemStack> renderedExportStacks;

    @Nullable
    protected ISubscription importFluidSub;
    @Nullable
    protected ISubscription importItemSub;
    @Nullable
    protected ISubscription exportItemSub;

    @SyncToClient
    private ItemStack playEvent = ItemStack.EMPTY;
    @SyncToClient
    private boolean shouldPlaySound = false;

    private final boolean canRunRecipes = getBlockState().getValue(SawBlock.FACING) == Direction.UP;

    //todo: add bucket clicking io and sponge clearing
    public TieredSawMachine(BlockEntityCreationInfo info, int tier) {
        super(info, tier, new TieredSawMachineBlockEntity(info.type(), info.pos(), info.state()));
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
        this.renderedImportStack = ItemStack.EMPTY;
        this.renderedExportStacks = NonNullList.of(ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.updateStoredFluidFromCache();
        this.importFluidSub = this.importTank.addChangedListener(this::onFluidChanged);
        this.importItemSub = this.importItems.addChangedListener(this::onImportItemChanged);
        this.exportItemSub = this.exportItems.addChangedListener(this::onExportItemChanged);
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if(this.importFluidSub != null) {
            this.importFluidSub.unsubscribe();
            this.importFluidSub = null;
        }
        if(this.importItemSub != null) {
            this.importItemSub.unsubscribe();
            this.importItemSub = null;
        }
        if(this.exportItemSub != null) {
            this.exportItemSub.unsubscribe();
            this.exportItemSub = null;
        }
    }

    private void onFluidChanged() {
        if(!this.isRemote()) {
            this.syncDataHolder.markClientSyncFieldDirty("containedFluid");
            this.updateStoredFluidFromCache();
        }
    }

    private void onImportItemChanged() {
        if(!this.isRemote()) {
            this.syncDataHolder.markClientSyncFieldDirty("renderedImportStack");
            this.updateStoredImportItemFromCache();
        }
    }

    private void onExportItemChanged() {
        if(!this.isRemote()) {
            this.syncDataHolder.markClientSyncFieldDirty("renderedExportStacks");
            this.updateStoredExportItemFromCache();
        }
    }

    private void updateStoredFluidFromCache() {
        FluidStack cachedFluid = this.importTank.getFluidInTank(0);
        this.containedFluid = cachedFluid.isEmpty() ? FluidStack.EMPTY : cachedFluid;
    }

    private void updateStoredImportItemFromCache() {
        ItemStack cachedStack = this.importItems.getStackInSlot(0);
        this.renderedImportStack = cachedStack.isEmpty() ? ItemStack.EMPTY : this.importItems.getStackInSlot(0);
    }

    private void updateStoredExportItemFromCache() {
        NonNullList<ItemStack> cachedExportStack = NonNullList.create();
        for(int i = 0; i < this.exportItems.getSlots(); i++) {
            if(!this.exportItems.getStackInSlot(i).isEmpty()) {
                cachedExportStack.add(this.exportItems.getStackInSlot(i));
            }
        }
        this.renderedExportStacks = cachedExportStack;
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
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        this.playEvent = renderedImportStack;
        this.getSyncDataHolder().markClientSyncFieldDirty("playEvent");
        return super.beforeWorking(recipe);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        this.shouldPlaySound = true;
        this.getSyncDataHolder().markClientSyncFieldDirty("shouldPlaySound");
        this.kineticMachineTrait.getKineticBlockEntity().award(AllAdvancements.SAW_PROCESSING);
    }

    @Override
    public void clientTick() {
        if(getSpeed() == 0 || !canRunRecipes) return;
        if(!renderedImportStack.isEmpty() && this.getRecipeLogic().isActive()) {
            spawnParticles(renderedImportStack);
        }
		if(shouldPlaySound && ((KineticMachineRecipeLogic) this.getRecipeLogic()).postEffects()) {
			boolean isWood = false;
			Item item = renderedImportStack.getItem();
			if (item instanceof BlockItem) {
				Block block = ((BlockItem) item).getBlock();
				isWood = block.getSoundType(block.defaultBlockState(), level, worldPosition, null) == SoundType.WOOD;
			}
			spawnEventParticles(playEvent);
            shouldPlaySound = false;
            playEvent = ItemStack.EMPTY;
            if(!isWood)
                AllSoundEvents.SAW_ACTIVATE_STONE.playAt(level, worldPosition, 3, 1, true);
            else
                AllSoundEvents.SAW_ACTIVATE_WOOD.playAt(level, worldPosition, 3, 1, true);

            return;
		}
        super.clientTick();
    }

    protected void spawnParticles(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return;

		ParticleOptions particleData = null;
		float speed = 1;
		if (stack.getItem() instanceof BlockItem)
			particleData = new BlockParticleOption(ParticleTypes.BLOCK, ((BlockItem) stack.getItem()).getBlock()
				.defaultBlockState());
		else {
			particleData = new ItemParticleOption(ParticleTypes.ITEM, stack);
			speed = .125f;
		}

		RandomSource r = level.random;
		Vec3 vec = getItemMovementVec();
		Vec3 pos = VecHelper.getCenterOf(this.worldPosition);
		float offset = this.getRecipeLogic().getProgress() != 0 ? ((float) this.getRecipeLogic().getProgress() / this.getRecipeLogic().getLastRecipe().duration) - 1.0f : 0;
		offset = -offset;
        offset /= 2;
		/*if (inventory.appliedRecipe)
			offset -= .5f;*/
		level.addParticle(particleData, pos.x() + -vec.x * offset, pos.y() + .45f, pos.z() + -vec.z * offset,
			-vec.x * speed, r.nextFloat() * speed, -vec.z * speed);
	}

    public Vec3 getItemMovementVec() {
		boolean alongX = !getRenderState().getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE);
		int offset = getSpeed() < 0 ? -1 : 1;
		return new Vec3(offset * (alongX ? 1 : 0), 0, offset * (alongX ? 0 : -1));
	}

    protected void spawnEventParticles(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return;

		ParticleOptions particleData = null;
		if (stack.getItem() instanceof BlockItem)
			particleData = new BlockParticleOption(ParticleTypes.BLOCK, ((BlockItem) stack.getItem()).getBlock()
				.defaultBlockState());
		else
			particleData = new ItemParticleOption(ParticleTypes.ITEM, stack);

		RandomSource r = level.random;
		Vec3 v = VecHelper.getCenterOf(this.worldPosition)
			.add(0, 5 / 16f, 0);
		for (int i = 0; i < 10; i++) {
			Vec3 m = VecHelper.offsetRandomly(new Vec3(0, 0.25f, 0), r, .125f);
			level.addParticle(particleData, v.x, v.y, v.z, m.x, m.y, m.y);
		}
	}
}
