package electrolyte.greate.compat.gtceu.client.renderer.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.compat.gtceu.machine.kinetic.saw.TieredSawMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.platform.ForgeCatnipServices;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;
import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.GreatePartialModels.*;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SawRender extends DynamicRender<TieredSawMachine, SawRender> {

    public static final Codec<SawRender> CODEC = Codec.unit(SawRender::new);
    public static final DynamicRenderType<TieredSawMachine, SawRender> TYPE = new DynamicRenderType<>(CODEC);
    private int tier;
    @Override
    public DynamicRenderType<TieredSawMachine, SawRender> getType() {
        return TYPE;
    }

    @Override
    public void render(TieredSawMachine machine, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        KineticBlockEntity be = machine.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
        tier = ((ITieredBlock) be.getBlockState().getBlock()).getTier();
        renderBlade(machine, ms, bufferSource, light);
        renderItems(machine, partialTicks, ms, bufferSource, light, overlay);
        if(machine.getRenderState().getValue(FACING) == Direction.UP) {
            renderFluid(machine, ms, bufferSource, light);
        }
        FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, bufferSource, light, overlay);

        renderShaft(be, machine, ms, bufferSource, light);
    }

    private void renderBlade(TieredSawMachine be, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        BlockState state = be.getBlockState();
        PartialModel sawModel;
        float speed = be.getSpeed();
        boolean rotate = false;
        if(SawBlock.isHorizontal(state)) {
            if(speed > 0) {
                sawModel = MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE_MODELS[tier];
            } else if(speed < 0) {
                sawModel = MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED_MODELS[tier];
            } else {
                sawModel = MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE_MODELS[tier];
            }
        } else {
            if(speed > 0) {
                sawModel = MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE_MODELS[tier];
            } else if(speed < 0) {
                sawModel = MECHANICAL_SAW_BLADE_VERTICAL_REVERSED_MODELS[tier];
            } else {
                sawModel = MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE_MODELS[tier];
            }

            if(state.getValue(AXIS_ALONG_FIRST_COORDINATE)) rotate = true;
        }

        SuperByteBuffer superByteBuffer = CachedBuffers.partialFacing(sawModel, state);
        if(rotate) {
            superByteBuffer.rotateCentered(AngleHelper.rad(90), Direction.UP);
        }
        superByteBuffer.color(0xFFFFFF)
                .light(light)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    private void renderFluid(TieredSawMachine be, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        FluidStack renderedFluid = be.getContainedFluid();
        if(renderedFluid.isEmpty()) return;

        final float xMin = 7 / 16f;
        final float xMax = 9 / 16f;
        final float yMin = 13 / 16f;
        final float yMax = 13.01f / 16f;
        final float zMin = 2 / 16f;
        final float zMax = 14 / 16f;
        if(be.getBlockState().getValue(AXIS_ALONG_FIRST_COORDINATE)) {
            ForgeCatnipServices.FLUID_RENDERER.renderFluidBox(renderedFluid, xMin, yMin, zMin, xMax, yMax, zMax, bufferSource, poseStack, light, false, false);
        } else {
            ForgeCatnipServices.FLUID_RENDERER.renderFluidBox(renderedFluid, zMin, yMin, xMin, zMax, yMax, xMax, bufferSource, poseStack, light, false, false);
        }
    }

    private void renderShaft(KineticBlockEntity be, TieredSawMachine machine, PoseStack ms, MultiBufferSource buffer, int light) {
		KineticBlockEntityRenderer.renderRotatingBuffer(be, getRotatedModel(machine, machine.getBlockState()), ms,
			buffer.getBuffer(RenderType.solid()), light);
	}

    private SuperByteBuffer getRotatedModel(TieredSawMachine be, BlockState state) {
        if(state.getValue(FACING).getAxis().isHorizontal()) {
            return CachedBuffers.partialFacing(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/shaft_half"), state.rotate(be.getLevel(), be.getBlockPos(), Rotation.CLOCKWISE_180));
        }
        return CachedBuffers.block(KineticBlockEntityRenderer.KINETIC_BLOCK, getRenderedBlockState(be));
    }

	private BlockState getRenderedBlockState(TieredSawMachine be) {
        return ChemicalHelper.getBlock(shaft, TM[tier]).defaultBlockState().setValue(BlockStateProperties.AXIS, getRotationAxisOf(be));
    }

    private Direction.Axis getRotationAxisOf(TieredSawMachine be) {
        return ((IRotate) be.getBlockState()
			.getBlock()).getRotationAxis(be.getBlockState());
    }

    private void renderItems(TieredSawMachine be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		if(be.getRenderState().getValue(SawBlock.FACING) != Direction.UP) return;
        if(be.getSpeed() == 0) return;
		if(be.getRenderedImportStack().isEmpty()) return;
        if(!be.getRecipeLogic().isActive()) return;

		boolean alongZ = !be.getBlockState().getValue(AXIS_ALONG_FIRST_COORDINATE);

        float offset = getOffset(be, partialTicks, alongZ);

		ms.pushPose();
		if (alongZ) ms.mulPose(Axis.YP.rotationDegrees(90));
		ms.translate(0.5, 0, offset);
		ms.translate(alongZ ? -1 : 0, 0, 0);


        ItemStack stack = be.getRenderedImportStack();
        if (stack.isEmpty()) return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel modelWithOverrides = itemRenderer.getModel(stack, be.getLevel(), null, 0);
        boolean blockItem = modelWithOverrides.isGui3d();

        ms.pushPose();
        ms.translate(0, blockItem ? .925f : 13f / 16f, 0);

        boolean box = PackageItem.isPackage(stack);
        if (box) {
            ms.translate(0, 4 / 16f, 0);
            ms.scale(1.5f, 1.5f, 1.5f);
        } else ms.scale(.5f, .5f, .5f);

        if (!box) ms.mulPose(Axis.XP.rotationDegrees(90));

        itemRenderer.render(stack, ItemDisplayContext.FIXED, false, ms, buffer, light, overlay, modelWithOverrides);
        ms.popPose();

		ms.popPose();
	}

    private static float getOffset(TieredSawMachine be, float partialTicks, boolean alongZ) {
        float progress = be.getRecipeLogic().getProgress();
        int duration = be.getRecipeLogic().getLastRecipe().duration;
        boolean moving = duration != 0 && progress != 0;
        float offset = moving ? (progress / duration) : 1.0f;
        float processingSpeed = Mth.clamp(Math.abs(be.getSpeed()) / 32, 1, 128);
        /*if (moving) {
            offset = Mth.clamp(offset + ((- partialTicks + .5f) * processingSpeed) / progress, 0.125f, 1f);
            *//*if (! be.inventory.appliedRecipe) //todo: fix (probably check recipe type)
                offset += 1;*//*
            offset /= 2;
        }*/

        if (be.getSpeed() == 0) offset = .5f;
        if (be.getSpeed() > 0 ^ alongZ) offset = 1 - offset;
        return offset;
    }
}
