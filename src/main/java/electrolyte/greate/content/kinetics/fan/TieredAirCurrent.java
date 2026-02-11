package electrolyte.greate.content.kinetics.fan;

import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.AirCurrentSound;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes.TieredSplashingType;
import electrolyte.greate.mixin.MixinAirCurrentInvoker;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class TieredAirCurrent extends AirCurrent {

    int machineTier;

    public TieredAirCurrent(IAirCurrentSource source, int tier) {
        super(source);
        this.machineTier = tier;
    }

    @Override
    public void tickAffectedHandlers() {
        for(Pair<TransportedItemStackHandlerBehaviour, FanProcessingType> pair : affectedItemHandlers) {
            TransportedItemStackHandlerBehaviour handler = pair.getKey();
            Level level = handler.getWorld();
            FanProcessingType processingType = pair.getRight();
            if(processingType == null) continue;
            handler.handleProcessingOnAllItems(transported -> {
                if(level.isClientSide) {
                    if(processingType instanceof TieredSplashingType tst) {
                        tst.spawnProcessingParticles(level, handler.getWorldPositionOf(transported), (TieredEncasedFanBlockEntity) level.getBlockEntity(source.getAirCurrentPos()));
                    } else processingType.spawnProcessingParticles(level, handler.getWorldPositionOf(transported));
                    return TransportedResult.doNothing();
                }
                TransportedResult applyProcessing = TieredFanProcessing.applyProcessing(source.getSpeed(), transported, level, processingType, machineTier, (TieredEncasedFanBlockEntity) level.getBlockEntity(getAirCurrentPos()));
                if(!applyProcessing.doesNothing() && source instanceof EncasedFanBlockEntity fan)
                    fan.award(AllAdvancements.FAN_PROCESSING);
                return applyProcessing;
            });
        }
    }

    @Override
    protected void tickAffectedEntities(Level level) {
        for(Iterator<Entity> iterator = caughtEntities.iterator(); iterator.hasNext(); ) {
            Entity entity = iterator.next();
            if(! entity.isAlive() || ! entity.getBoundingBox().intersects(bounds) || isPlayerCreativeFlying(entity)) {
                iterator.remove();
                continue;
            }

            Vec3i flow = (pushing ? direction : direction.getOpposite()).getNormal();
            float speed = Math.abs(source.getSpeed());
            float sneakModifier = entity.isShiftKeyDown() ? 4096f : 512f;
            double entityDistance = VecHelper.alignedDistanceToFace(entity.position(), source.getAirCurrentPos(), direction);
            double entityDistanceOld = entity.position().distanceTo(VecHelper.getCenterOf(source.getAirCurrentPos()));
            float acceleration = (float) (speed / sneakModifier / (entityDistanceOld / maxDistance));
            Vec3 previousMotion = entity.getDeltaMovement();
            float maxAcceleration = 5;

            double xIn = Mth.clamp(flow.getX() * acceleration - previousMotion.x, - maxAcceleration, maxAcceleration);
            double yIn = Mth.clamp(flow.getY() * acceleration - previousMotion.y, - maxAcceleration, maxAcceleration);
            double zIn = Mth.clamp(flow.getZ() * acceleration - previousMotion.z, - maxAcceleration, maxAcceleration);

            entity.setDeltaMovement(previousMotion.add(new Vec3(xIn, yIn, zIn).scale(1 / 8f)));
            entity.fallDistance = 0;
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                    () -> () -> enableClientPlayerSound(entity, Mth.clamp(speed / 128f * .4f, 0.01f, .4f)));

            if(entity instanceof ServerPlayer sp) {
                sp.connection.aboveGroundTickCount = 0;
            }

            FanProcessingType processingType = getTypeAt((float) entityDistance);
            if(processingType == null) continue;

            if(entity instanceof ItemEntity itemEntity) {
                if(level != null && level.isClientSide) {
                    if(processingType instanceof TieredSplashingType tst) {
                        tst.spawnProcessingParticles(level, entity.position(), (TieredEncasedFanBlockEntity) level.getBlockEntity(source.getAirCurrentPos()));
                    } else processingType.spawnProcessingParticles(level, entity.position());
                    continue;
                }
                if(TieredFanProcessing.canProcess(itemEntity, processingType, machineTier, level.getBlockEntity(getAirCurrentPos())))
                    if(TieredFanProcessing.applyProcessing(source.getSpeed(), itemEntity, processingType, machineTier, (TieredEncasedFanBlockEntity) level.getBlockEntity(getAirCurrentPos()))
                            && source instanceof EncasedFanBlockEntity fan)
                        fan.award(AllAdvancements.FAN_PROCESSING);
                continue;
            }

            if(level != null)
                processingType.affectEntity(entity, level);
        }
    }

    @Override
    public void findAffectedHandlers() {
		Level world = source.getAirCurrentWorld();
		BlockPos start = source.getAirCurrentPos();
		affectedItemHandlers.clear();
		int limit = ((MixinAirCurrentInvoker) this).callGetLimit();
		for (int i = 0; i <= limit; i++) {
			FanProcessingType segmentType = getTypeAt(i - 1);
			for (int offset : Iterate.zeroAndOne) {
				BlockPos pos = start.relative(direction, i).below(offset);
				TransportedItemStackHandlerBehaviour behaviour =
					BlockEntityBehaviour.get(world, pos, TransportedItemStackHandlerBehaviour.TYPE);
				if (behaviour != null) {
					FanProcessingType type = FanProcessingType.getAt(world, pos);
					if (type == null)
						type = segmentType;
					affectedItemHandlers.add(Pair.of(behaviour, type));
				}
				if (direction.getAxis().isVertical()) break;
			}
		}
	}

    private @NotNull BlockPos getAirCurrentPos() {
        return source.getAirCurrentPos();
    }

    @OnlyIn(Dist.CLIENT)
    private static AirCurrentSound flyingSound;
    private static boolean isClientPlayerInAirCurrent;

    @OnlyIn(Dist.CLIENT)
    private static void enableClientPlayerSound(Entity e, float maxVolume) {
        if(e != Minecraft.getInstance()
                .getCameraEntity())
            return;

        isClientPlayerInAirCurrent = true;

        float pitch = (float) Mth.clamp(e.getDeltaMovement()
                .length() * .5f, .5f, 2f);

        if(flyingSound == null || flyingSound.isStopped()) {
            flyingSound = new TieredAirCurrentSound(SoundEvents.ELYTRA_FLYING, pitch);
            Minecraft.getInstance()
                    .getSoundManager()
                    .play(flyingSound);
        }
        flyingSound.setPitch(pitch);
        flyingSound.fadeIn(maxVolume);
    }

    @OnlyIn(Dist.CLIENT)
    public static void tickClientPlayerSounds() {
        if(! isClientPlayerInAirCurrent && flyingSound != null)
            if(flyingSound.isFaded())
                flyingSound.stopSound();
            else
                flyingSound.fadeOut();
        isClientPlayerInAirCurrent = false;
    }

    private static class TieredAirCurrentSound extends AirCurrentSound {
        protected TieredAirCurrentSound(SoundEvent p_i46532_1_, float pitch) {
            super(p_i46532_1_, pitch);
        }
    }
}


