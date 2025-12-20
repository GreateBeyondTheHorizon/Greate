package electrolyte.greate.foundation.client.models;

import com.simibubi.create.content.kinetics.belt.BeltSlope;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.data.models.blockstates.VariantProperties.Rotation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class GreateModelUtils {

	protected static Rotation getBeltXRotation(Direction direction, BeltSlope slope) {
		return Rotation.valueOf("R" + ((slope == BeltSlope.VERTICAL ? 90
                : slope == BeltSlope.SIDEWAYS && direction.getAxisDirection() == AxisDirection.NEGATIVE ? 180 : 0) + 360) % 360);
	}

	protected static Rotation getBeltYRotation(Direction direction, BeltSlope slope, Boolean casing) {
		boolean flip = slope == BeltSlope.UPWARD;
		boolean rotate = casing && slope == BeltSlope.VERTICAL;
		return Rotation.valueOf("R" + ((horizontalAngle(direction) + (flip ? 180 : 0) + (rotate ? 90 : 0)) + 360) % 360);
	}

    private static int horizontalAngle(Direction direction) {
		if (direction.getAxis().isVertical()) return 0;
		return (int) direction.toYRot();
	}

	public static PartialModel getPartialModel(Block block, String suffix) {
		ITieredBlock tieredBlock = (ITieredBlock) block;
        String material = tieredBlock.getMaterial().getName();
        ResourceLocation model = Greate.id("block/" + material + suffix);
		return PartialModel.of(model);
	}
}
