package electrolyte.greate.content.kinetics.mixer;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogInstance;
import net.minecraft.core.Direction.Axis;

public class TieredMechanicalMixerInstance extends TieredEncasedCogInstance implements DynamicInstance {

    private final RotatingData mixerHead;
    private final OrientedData mixerPole;
    private final TieredMechanicalMixerBlockEntity mixer;

    public TieredMechanicalMixerInstance(MaterialManager materialManager, TieredMechanicalMixerBlockEntity blockEntity) {
        super(materialManager, blockEntity, false);
        this.mixer = blockEntity;
        mixerHead = materialManager.defaultCutout()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(((TieredMechanicalMixerBlock) this.mixer.getBlockState().getBlock()).getPartialModel(), blockState)
                .createInstance();
        mixerHead.setRotationAxis(Axis.Y);
        mixerPole = getOrientedMaterial()
                .getModel(AllPartialModels.MECHANICAL_MIXER_POLE, blockState)
                .createInstance();

        float renderedHeadOffset = getRenderedHeadOffset();

        transformPole(renderedHeadOffset);
        transformHead(renderedHeadOffset);
    }

    @Override
    protected Instancer<RotatingData> getCogModel() {
        return materialManager.defaultSolid()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(((TieredMechanicalMixerBlock) this.mixer.getBlockState().getBlock()).getCogwheelModel(), blockEntity.getBlockState());
    }

    @Override
    public void beginFrame() {
        float renderedHeadOffset = getRenderedHeadOffset();
        transformPole(renderedHeadOffset);
        transformHead(renderedHeadOffset);
    }

    private void transformHead(float renderedHeadOffset) {
        float speed = mixer.getRenderedHeadRotationSpeed(AnimationTickHolder.getPartialTicks());

        mixerHead.setPosition(getInstancePosition())
                .nudge(0, -renderedHeadOffset, 0)
                .setRotationalSpeed(speed * 2);
    }

    private void transformPole(float renderedHeadOffset) {
        mixerPole.setPosition(getInstancePosition())
                .nudge(0, -renderedHeadOffset, 0);
    }

    private float getRenderedHeadOffset() {
        return mixer.getRenderedHeadOffset(AnimationTickHolder.getPartialTicks());
    }

    @Override
    public void updateLight() {
        super.updateLight();

        relight(pos.below(), mixerHead);
        relight(pos, mixerPole);
    }

    @Override
    protected void remove() {
        super.remove();
        mixerHead.delete();
        mixerPole.delete();
    }
}
