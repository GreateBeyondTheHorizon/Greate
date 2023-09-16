package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.AllDamageTypes;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.List;

public class TieredCrushingWheelBlockEntity extends TieredKineticBlockEntity implements ITieredKineticBlockEntity {

    public TieredCrushingWheelBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        setLazyTickRate(20);
    }

    @Override
    public double getMaxCapacity() {
        return this.getTier().getStressCapacity();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.CRUSHING_WHEEL, AllAdvancements.CRUSHER_MAXED);
    }

    @Override
    public void onSpeedChanged(float previousSpeed) {
        super.onSpeedChanged(previousSpeed);
        fixControllers();
    }

    public void fixControllers() {
        for(Direction d : Iterate.directions) {
            ((TieredCrushingWheelBlock) getBlockState().getBlock()).updateControllers(getBlockState(), getLevel(), getBlockPos(), d);
        }
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        fixControllers();
    }

    public static int fortunateCrushing(DamageSource source, LivingEntity entity, int currentLevel, boolean recentlyHit) {
        if(!AllDamageTypes.CRUSH.is(source)) return 0;
        return 2;
    }

    public static boolean handleCrushedDrops(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops, int lootingLevel, boolean recentlyHit) {
        if(!AllDamageTypes.CRUSH.is(source)) return false;
        for(ItemEntity itemEntity : drops) {
            itemEntity.setDeltaMovement(Vec3.ZERO);
        }
        return false;
    }
}
