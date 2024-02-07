package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.content.kinetics.base.TieredKineticEffectHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredKineticBlockEntity extends KineticBlockEntity implements ITieredKineticBlockEntity {

    protected TIER tier;
    public TieredKineticEffectHandler effects;

    public TieredKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.tier = ((ITieredBlock) state.getBlock()).getTier();
        effects = new TieredKineticEffectHandler(this);
    }

    public TIER getTier() {
        return tier;
    }

    @Override
    public void tick() {
        effects.tick();
        super.tick();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity);
    }
}
