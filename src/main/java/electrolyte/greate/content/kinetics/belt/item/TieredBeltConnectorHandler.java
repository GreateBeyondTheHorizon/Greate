package electrolyte.greate.content.kinetics.belt.item;

import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TieredBeltConnectorHandler {

    private static Random rand = new Random();

    public static void tick() {
        Player player = Minecraft.getInstance().player;
        Level level = Minecraft.getInstance().level;
        if(player == null || level == null) return;
        if(Minecraft.getInstance().screen != null) return;

        for(InteractionHand hand : InteractionHand.values()) {
            ItemStack heldStack = player.getItemInHand(hand);
            if(!(heldStack.getItem() instanceof TieredBeltConnectorItem tbci)) continue;
            if(!heldStack.hasTag()) continue;

            CompoundTag tag = heldStack.getTag();
            if(!tag.contains("FirstPulley")) continue;

            BlockPos first = NbtUtils.readBlockPos(tag.getCompound("FirstPulley"));
            if(!level.getBlockState(first).hasProperty(BlockStateProperties.AXIS)) continue;

            Axis axis = level.getBlockState(first).getValue(BlockStateProperties.AXIS);
            HitResult hitResult = Minecraft.getInstance().hitResult;
            if(hitResult == null || !(hitResult instanceof BlockHitResult)) {
                if(rand.nextInt(50) == 0) {
                    level.addParticle(new DustParticleOptions(new Vector3f(0.3f, 0.9f, 0.5f), 1),
                            first.getX() + 0.5f + randomOffset(0.25f),
                            first.getY() + 0.5f + randomOffset(0.25f),
                            first.getZ() + 0.5f + randomOffset(0.25f), 0, 0, 0);
                }
                return;
            }

            BlockPos selected = ((BlockHitResult) hitResult).getBlockPos();
            if(level.getBlockState(selected).canBeReplaced()) return;
            if(!(level.getBlockState(selected).getBlock() instanceof TieredShaftBlock)) {
                selected = selected.relative(((BlockHitResult) hitResult).getDirection());
            }
            if(!selected.closerThan(first, GConfigUtility.getBeltLengthFromMaterial(tbci.getBeltMaterial()))) return;

            boolean canConnect = TieredBeltConnectorItem.validateAxis(level, selected) && TieredBeltConnectorItem.canConnect(level, first, selected, heldStack);
            Vec3 start = Vec3.atLowerCornerOf(first);
            Vec3 end = Vec3.atLowerCornerOf(selected);
            Vec3 actualDiff = end.subtract(start);
            end = end.subtract(axis.choose(actualDiff.x, 0, 0), axis.choose(0, actualDiff.y, 0), axis.choose(0, 0, actualDiff.z));
            Vec3 diff = end.subtract(start);
            double x = Math.abs(diff.x);
            double y = Math.abs(diff.y);
            double z = Math.abs(diff.z);
            float length = (float) Math.max(x, Math.max(y, z));
            Vec3 step = diff.normalize();
            int sames = ((x == y) ? 1 : 0) + ((y == z) ? 1 : 0) + ((z == x) ? 1 : 0);
            if(sames == 0) {
                List<Vec3> validDiffs = new LinkedList<>();
                for(int i = -1; i <= 1; i++)
                    for(int j = -1; j <= 1; j++)
                        for(int k = -1; k <= 1; k++) {
                            if(axis.choose(i, j, k) != 0) continue;
                            if(axis == Axis.Y && i != 0 && k != 0) continue;
                            if(i == 0 && j == 0 && k == 0) continue;
                            validDiffs.add(new Vec3(i, j, k));
                        }

                int closestIndex = 0;
                float closest = Float.MAX_VALUE;
                for(Vec3 validDiff : validDiffs) {
                    double distanceTo = step.distanceTo(validDiff);
                    if(distanceTo < closest) {
                        closest = (float) distanceTo;
                        closestIndex = validDiffs.indexOf(validDiff);
                    }
                }
                step = validDiffs.get(closestIndex);
            }

            if(axis == Axis.Y && step.x != 0 && step.z != 0) return;
            step = new Vec3(Math.signum(step.x), Math.signum(step.y), Math.signum(step.z));
            for(float f = 0; f < length; f += .0625f) {
                Vec3 position = start.add(step.scale(f));
                if(rand.nextInt(10) == 0) {
                    level.addParticle(new DustParticleOptions(new Vector3f(canConnect ? 0.3f : 0.9f, canConnect ? 0.9f : 0.3f, 0.5f), 1),
                            position.x + 0.5f, position.y + 0.5f, position.z + 0.5f, 0, 0, 0);
                }
            }
            return;
        }
    }

    private static float randomOffset(float range) {
        return (rand.nextFloat() - 0.5f) * 2 * range;
    }
}
