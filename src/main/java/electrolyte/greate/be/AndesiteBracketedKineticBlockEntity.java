package electrolyte.greate.be;

import com.simibubi.create.content.contraptions.ITransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AndesiteBracketedKineticBlockEntity extends SimpleKineticBlockEntity implements ITransformableBlockEntity {

    private float maxCapacity;

    public AndesiteBracketedKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        maxCapacity = 512;
    }

    public float getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new BracketedBlockEntityBehaviour(this, state -> state.getBlock() instanceof AbstractSimpleShaftBlock));
        super.addBehaviours(behaviours);
    }

    @Override
    public void transform(StructureTransform transform) {
        BracketedBlockEntityBehaviour bracketBehaviour = getBehaviour(BracketedBlockEntityBehaviour.TYPE);
        if(bracketBehaviour != null) {
            bracketBehaviour.transformBracket(transform);
        }
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        maxCapacity = compound.getFloat("MaxCapacity");
        if (compound.contains("Network")) {
            overStressed =  capacity > maxCapacity;
        }
        super.read(compound, clientPacket);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putFloat("MaxCapacity", maxCapacity);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("gui.goggles.kinetic_stats").forGoggles(tooltip);
        Lang.text("Shaft Kinetic Capacity:").style(ChatFormatting.GRAY).forGoggles(tooltip);
        Lang.number(capacity).style(ChatFormatting.AQUA).add(Lang.text("su")).space().add(Lang.text("/").space().add(Lang.number(maxCapacity)).add(Lang.text("su").space().add(Lang.text("at current shaft tier").style(ChatFormatting.DARK_GRAY)))).forGoggles(tooltip, 1);
        return true;
    }
}
