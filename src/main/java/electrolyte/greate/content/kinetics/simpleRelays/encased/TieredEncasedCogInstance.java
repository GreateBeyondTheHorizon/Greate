package electrolyte.greate.content.kinetics.simpleRelays.encased;

import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredEncasedCogwheel;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredHalfShaft;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class TieredEncasedCogInstance extends KineticBlockEntityInstance<TieredKineticBlockEntity> {

    private boolean large;
    protected RotatingData rotatingModel;
    protected Optional<RotatingData> rotatingTopShaft;
    protected Optional<RotatingData> rotatingBottomShaft;
    protected PartialModel halfShaftModel, model;

    public static TieredEncasedCogInstance small(MaterialManager manager, TieredKineticBlockEntity be) {
        return new TieredEncasedCogInstance(manager, be, false);
    }

    public static TieredEncasedCogInstance large(MaterialManager manager, TieredKineticBlockEntity be) {
        return new TieredEncasedCogInstance(manager, be, true);
    }

    public TieredEncasedCogInstance(MaterialManager materialManager, TieredKineticBlockEntity blockEntity, boolean large) {
        super(materialManager, blockEntity);
        this.large = large;
        this.halfShaftModel = ((ITieredHalfShaft) blockState.getBlock()).getHalfShaft();
        this.model = ((ITieredEncasedCogwheel) blockState.getBlock()).getCogwheelModel();
    }

    @Override
    public void init() {
        rotatingModel = setup(getCogModel().createInstance());

        Block block = blockState.getBlock();
        if(!(block instanceof IRotate def)) return;

        rotatingTopShaft = Optional.empty();
        rotatingBottomShaft = Optional.empty();
        for(Direction d : Iterate.directionsInAxis(axis)) {
            if(!def.hasShaftTowards(blockEntity.getLevel(), blockEntity.getBlockPos(), blockState, d)) continue;
        RotatingData data = setup(getRotatingMaterial().getModel(halfShaftModel, blockState, d).createInstance());
            if(large) {
                data.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos));
            }
            if(d.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
                rotatingTopShaft = Optional.of(data);
            } else {
                rotatingBottomShaft = Optional.of(data);
            }
        }
    }

    @Override
    public void update() {
        updateRotation(rotatingModel);
        rotatingTopShaft.ifPresent(this::updateRotation);
        rotatingBottomShaft.ifPresent(this::updateRotation);
    }

    @Override
    public void updateLight() {
        relight(pos, rotatingModel);
        rotatingTopShaft.ifPresent(d -> relight(pos, d));
        rotatingBottomShaft.ifPresent(d -> relight(pos, d));
    }

    @Override
    protected void remove() {
        rotatingModel.delete();
        rotatingTopShaft.ifPresent(InstanceData::delete);
        rotatingBottomShaft.ifPresent(InstanceData::delete);
    }

    protected Instancer<RotatingData> getCogModel() {
        BlockState refState = blockEntity.getBlockState();
        Direction facing = Direction.fromAxisAndDirection(refState.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
        PartialModel partialModel = model;
        return getRotatingMaterial().getModel(partialModel, refState, facing, () -> {
            PoseStack stack = new PoseStack();
            TransformStack.cast(stack)
                    .centre()
                    .rotateToFace(facing)
                    .multiply(Vector3f.XN.rotationDegrees(90))
                    .unCentre();
            return stack;
        });
    }
}
