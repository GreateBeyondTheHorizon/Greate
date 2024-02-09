package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashMap;
import java.util.Map;

public class TieredCrushingWheelControllerBlock extends CrushingWheelControllerBlock implements ITieredBlock {

    private int tier;
    private final Block crushingWheel;
    public static Map<Block, Block> MAP = new HashMap<>();

    public TieredCrushingWheelControllerBlock(Properties properties, Block crushingWheel) {
        super(properties);
        this.crushingWheel = crushingWheel;
        MAP.put(crushingWheel, this);
    }

    @Override
    public void updateSpeed(BlockState state, LevelAccessor level, BlockPos pos) {
        withBlockEntityDo(level, pos, be -> {
            if(!state.getValue(VALID)) {
                if(be.crushingspeed != 0) {
                    be.crushingspeed = 0;
                    be.sendData();
                }
                return;
            }

            for(Direction dir : Iterate.directions) {
                BlockState neighbor = level.getBlockState(pos.relative(dir));
                if(!(neighbor.getBlock() instanceof TieredCrushingWheelBlock)) continue;
                if(neighbor.getValue(BlockStateProperties.AXIS) == dir.getAxis()) continue;
                BlockEntity adjBE = level.getBlockEntity(pos.relative(dir));
                if(!(adjBE instanceof CrushingWheelBlockEntity cwbe)) continue;
                be.crushingspeed = Math.abs(cwbe.getSpeed() / 50f);
                be.sendData();
                cwbe.award(AllAdvancements.CRUSHING_WHEEL);
                if(cwbe.getSpeed() > 255) {
                    cwbe.award(AllAdvancements.CRUSHER_MAXED);
                }
                break;
            }
        });
    }

    @Override
    public BlockEntityType<? extends CrushingWheelControllerBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_CRUSHING_WHEEL_CONTROLLER.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }
}
