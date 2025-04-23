package electrolyte.greate.infrastructure.config;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.createmod.catnip.config.ConfigBase;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

public class GStress extends ConfigBase {

    private static final Object2DoubleMap<ResourceLocation> DEFAULT_IMPACTS = new Object2DoubleOpenHashMap<>();
    private static final Object2DoubleMap<ResourceLocation> DEFAULT_CAPACITIES = new Object2DoubleOpenHashMap<>();

    private final Map<ResourceLocation, ConfigValue<Double>> capacities = new HashMap<>();
    private final Map<ResourceLocation, ConfigValue<Double>> impacts = new HashMap<>();

    @Override
    public void registerAll(Builder builder) {
        builder.comment("." + Comments.su + Comments.impact).push("impact");
        TieredBlockMaterials.MATERIAL_FOR_BLOCK.forEach(pair -> {
            ResourceLocation r = pair.getFirst();
            Material blockMaterial = pair.getSecond();
            for(Material material : GreateValues.TM) {
                if(material.equals(blockMaterial) && DEFAULT_IMPACTS.containsKey(r)) {
                    double impact = DEFAULT_IMPACTS.getDouble(r);
                    builder.push(material.getName());
                    this.impacts.put(r, builder.define(r.getPath(), impact));
                    builder.pop();
                }
            }
        });
        TieredBlockMaterials.MATERIAL_FOR_BELT_BLOCK.forEach(pair -> {
            ResourceLocation r = pair.getFirst();
            Material blockBeltMaterial = pair.getSecond();
            for(Material beltMaterial : GreateValues.BM) {
                if(beltMaterial.equals(blockBeltMaterial) && DEFAULT_IMPACTS.containsKey(r)) {
                    double impact = DEFAULT_IMPACTS.getDouble(r);
                    builder.push(beltMaterial.getName());
                    this.impacts.put(r, builder.define(r.getPath(), impact));
                    builder.pop();
                }
            }
        });
        builder.pop();
        builder.comment("." + Comments.su + Comments.capacity).push("capacity");
        TieredBlockMaterials.MATERIAL_FOR_BLOCK.forEach(pair -> {
            ResourceLocation r = pair.getFirst();
            Material blockMaterial = pair.getSecond();
            for(Material material : GreateValues.TM) {
                if(material.equals(blockMaterial) && DEFAULT_CAPACITIES.containsKey(r)) {
                    double capacity = DEFAULT_CAPACITIES.getDouble(r);
                    builder.push(material.getName());
                    this.capacities.put(r, builder.define(r.getPath(), capacity));
                    builder.pop();
                }
            }
        });
        builder.pop();
    }

    @Nullable
    public DoubleSupplier getImpact(Block block) {
        ResourceLocation loc = CatnipServices.REGISTRIES.getKeyOrThrow(block);
        ConfigValue<Double> impact = this.impacts.get(loc);
        return impact == null ? null : impact::get;
    }

    @Nullable
    public DoubleSupplier getCapacity(Block block) {
        ResourceLocation loc = CatnipServices.REGISTRIES.getKeyOrThrow(block);
        ConfigValue<Double> impact = this.capacities.get(loc);
        return impact == null ? null : impact::get;
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setCapacity(double capacity) {
        return b -> {
            ResourceLocation id = Greate.id(b.getName());
            DEFAULT_CAPACITIES.put(id, capacity);
            return b;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setImpact(double impact) {
        return b -> {
            ResourceLocation id = Greate.id(b.getName());
            DEFAULT_IMPACTS.put(id, impact);
            return b;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setNoImpact() {
        return setImpact(0);
    }

    @Override
    public String getName() {
        return "stressValues.v2";
    }

    private static class Comments {
        static String su = "[in Stress Units]";
        static String impact = "Configure the individual stress impact of mechanical blocks. Note that this cost is doubled for every speed increase it receives";
        static String capacity = "Configure how much stress a source can accommodate for.";
    }
}
