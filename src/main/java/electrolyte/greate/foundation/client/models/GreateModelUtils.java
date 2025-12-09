package electrolyte.greate.foundation.client.models;

import com.simibubi.create.content.kinetics.belt.BeltSlope;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.data.models.blockstates.VariantProperties.Rotation;

public class GreateModelUtils {

    //Belts
	protected static Rotation getXRotation(Direction direction, BeltSlope slope) {
		return Rotation.valueOf("R" + ((slope == BeltSlope.VERTICAL ? 90
                : slope == BeltSlope.SIDEWAYS && direction.getAxisDirection() == AxisDirection.NEGATIVE ? 180 : 0) + 360) % 360);
	}

	protected static Rotation getYRotation(Direction direction, BeltSlope slope, Boolean casing) {
		boolean flip = slope == BeltSlope.UPWARD;
		boolean rotate = casing && slope == BeltSlope.VERTICAL;
		return Rotation.valueOf("R" + ((horizontalAngle(direction) + (flip ? 180 : 0) + (rotate ? 90 : 0)) + 360) % 360);
	}

    private static int horizontalAngle(Direction direction) {
		if (direction.getAxis().isVertical()) return 0;
		return (int) direction.toYRot();
	}
}
