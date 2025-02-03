package electrolyte.greate.content.kinetics.simpleRelays.encased;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import com.simibubi.create.foundation.utility.Iterate;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static electrolyte.greate.registry.GreatePartialModels.*;

public class TieredEncasedCogVisual extends KineticBlockEntityVisual<KineticBlockEntity> {

    private final boolean large;
    protected RotatingInstance rotatingModel;
    @Nullable
    protected final RotatingInstance rotatingTopShaft;
    @Nullable
    protected final RotatingInstance rotatingBottomShaft;
    protected int tier;

    public static TieredEncasedCogVisual small(VisualizationContext context, KineticBlockEntity be, float partialTick) {
        return new TieredEncasedCogVisual(context, be, false, partialTick);
    }

    public static TieredEncasedCogVisual large(VisualizationContext context, KineticBlockEntity be, float partialTick) {
        return new TieredEncasedCogVisual(context, be, true, partialTick);
    }

    public TieredEncasedCogVisual(VisualizationContext context, KineticBlockEntity blockEntity, boolean large, float partialTick) {
        super(context, blockEntity, partialTick);
        this.large = large;
        this.tier = ((ITieredBlock) blockState.getBlock()).getTier();

        var instancer = instancerProvider().instancer(AllInstanceTypes.ROTATING, getCogModel());
        rotatingModel = setup(instancer.createInstance());

        RotatingInstance rotatingTopShaft = null;
        RotatingInstance rotatingBottomShaft = null;

        Block block = blockState.getBlock();
        if(block instanceof IRotate def) {
            for(Direction d : Iterate.directionsInAxis(axis)) {
                if(!def.hasShaftTowards(blockEntity.getLevel(), blockEntity.getBlockPos(), blockState, d)) continue;
                RotatingInstance data = setup(instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(SHAFT_HALF_MODELS[tier], d)).createInstance());
                if(large) {
                    data.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos));
                }
                if(d.getAxisDirection() == AxisDirection.POSITIVE) {
                    rotatingTopShaft = data;
                } else {
                    rotatingBottomShaft = data;
                }
            }
        }

        this.rotatingTopShaft = rotatingTopShaft;
        this.rotatingBottomShaft = rotatingBottomShaft;
    }

    @Override
    public void update(float partialTick) {
        updateRotation(rotatingModel);
        if(rotatingTopShaft != null) updateRotation(rotatingTopShaft);
        if(rotatingBottomShaft != null) updateRotation(rotatingBottomShaft);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(rotatingModel, rotatingTopShaft, rotatingBottomShaft);
    }

    @Override
    protected void _delete() {
        rotatingModel.delete();
        if(rotatingTopShaft != null) rotatingTopShaft.delete();
        if(rotatingBottomShaft != null) rotatingBottomShaft.delete();
    }

    protected Model getCogModel() {
        BlockState refState = blockEntity.getBlockState();
        Direction facing = Direction.fromAxisAndDirection(refState.getValue(BlockStateProperties.AXIS), AxisDirection.POSITIVE);
        PartialModel cogModel = large ? LARGE_COGWHEEL_SHAFTLESS_MODELS[tier] : COGWHEEL_SHAFTLESS_MODELS[tier];
        return Models.partial(cogModel, facing, TieredEncasedCogVisual::transformCog);
    }

    private static void transformCog(Direction dir, PoseStack stack) {
        TransformStack.of(stack)
                .center()
                .rotateToFace(dir)
                .rotate(Axis.XN.rotationDegrees(90))
                .uncenter();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(rotatingModel);
        consumer.accept(rotatingTopShaft);
        consumer.accept(rotatingBottomShaft);
    }
}
