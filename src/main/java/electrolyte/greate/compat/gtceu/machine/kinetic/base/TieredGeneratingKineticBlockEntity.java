package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredGeneratingKineticBlockEntity extends GeneratingKineticBlockEntity implements ITieredKineticBlockEntity {

    @Getter
    @Setter
    private float recipeRPM, recipeStress = 0;

    public TieredGeneratingKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, ((ITieredBlock) getBlockState().getBlock()).getMaterial(), speed);
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        notifyUpdate();
    }

    @Override
    public void initialize() {
        super.initialize();
        if(!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) {
            updateGeneratedRotation();
        }
    }

    @Override
    public float calculateAddedStressCapacity() {
        if(this.getRecipeStress() == 0 || this.getRecipeRPM() == 0) return 0;
        this.lastCapacityProvided = this.getRecipeStress() / this.getRecipeRPM();
        return this.getRecipeStress() / this.getRecipeRPM();
    }

    @Override
    public boolean isSource() {
        return this.getRecipeStress() != 0 && this.getRecipeRPM() != 0;
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        recipeRPM = compound.getFloat("recipeSpeed");
        recipeStress = compound.getFloat("recipeStress");

    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putFloat("recipeSpeed", recipeRPM);
        compound.putFloat("recipeStress", recipeStress);
        super.write(compound, clientPacket);
    }

    @Override
    public float getGeneratedSpeed() {
        return recipeRPM;
    }
}
