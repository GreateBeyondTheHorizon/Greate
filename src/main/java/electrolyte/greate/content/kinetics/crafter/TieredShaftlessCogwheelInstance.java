package electrolyte.greate.content.kinetics.crafter;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;

import java.util.function.Supplier;

import static electrolyte.greate.registry.GreatePartialModels.COGWHEEL_SHAFTLESS_MODELS;

public class TieredShaftlessCogwheelInstance extends TieredSingleRotatingInstance<TieredMechanicalCrafterBlockEntity> {
    public TieredShaftlessCogwheelInstance(MaterialManager materialManager, TieredMechanicalCrafterBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        Direction dir = blockState.getValue(TieredMechanicalCrafterBlock.HORIZONTAL_FACING);
        int tier = ((TieredMechanicalCrafterBlock) blockState.getBlock()).getTier();

        return getRotatingMaterial().getModel(COGWHEEL_SHAFTLESS_MODELS[tier], blockState, dir, rotateToFace(dir));
    }

    private Supplier<PoseStack> rotateToFace(Direction direction) {
        return () -> {
            PoseStack poseStack = new PoseStack();
            TransformStack stacker = TransformStack.cast(poseStack).centre();

            if(direction.getAxis() == Axis.X) stacker.rotateZ(90);
            else if(direction.getAxis() == Axis.Z) stacker.rotateX(90);

            stacker.unCentre();
            return poseStack;
        };
    }
}
