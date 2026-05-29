package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredBeltBlockEntity extends BeltBlockEntity implements ITieredKineticBlockEntity {

    private Material shaftMaterial;

    public TieredBeltBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if(shaftMaterial != null) compound.putString("ShaftMaterial", this.shaftMaterial.toString());
    }

    @Override
    public void writeSafe(CompoundTag tag) {
        super.writeSafe(tag);
        if(shaftMaterial != null) tag.putString("ShaftMaterial", shaftMaterial.toString());
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if(compound.contains("ShaftMaterial")) this.shaftMaterial =
                GTCEuAPI.materialManager.getMaterial(compound.getString("ShaftMaterial"));
    }

    public Material getShaftMaterial() {
        return shaftMaterial;
    }

    public void setShaftMaterial(Material shaftMaterial) {
        this.shaftMaterial = shaftMaterial;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if(getShaftMaterial() == null || getShaftMaterial() == GTMaterials.NULL) return false;
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, shaftMaterial, capacity, stress);
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        notifyUpdate();
    }

    @Override
    public float getMaxCapacityFromBlock(Block block) {
        return GreateValues.getMaxCapacityFromMaterial(shaftMaterial);
    }
}
