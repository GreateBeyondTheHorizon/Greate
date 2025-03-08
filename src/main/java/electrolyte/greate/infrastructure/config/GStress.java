package electrolyte.greate.infrastructure.config;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.createmod.catnip.config.ConfigBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.HashMap;
import java.util.Map;

public class GStress extends ConfigBase {

    private static final Object2DoubleMap<ResourceLocation> DEFAULT_IMPACTS = new Object2DoubleOpenHashMap<>();
    private static final Object2DoubleMap<ResourceLocation> DEFAULT_CAPACITIES = new Object2DoubleOpenHashMap<>();

    private final Map<ResourceLocation, ConfigValue<Double>> capacities = new HashMap<>();
    private final Map<ResourceLocation, ConfigValue<Double>> impacts = new HashMap<>();

    @Override
    public void registerAll(Builder builder) {
        //TODO: fix
        /*builder.comment("." + Comments.su + Comments.impact).push("impact");
        TieredBlockMaterials.MATERIAL_FOR_BLOCK.forEach(pair -> {
            ResourceLocation r = pair.getFirst();
            Material blockMaterial = pair.getSecond();
            for(Material material : GreateValues.TM) {
                if(material.equals(blockMaterial) && DEFAULT_IMPACTS.containsKey(r)) {
                    double impact = DEFAULT_IMPACTS.getDouble(r);
                    builder.push(material.getName());
                    getImpacts().put(r, builder.define(r.getPath(), impact));
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
                    builder.push(beltMaterial.toString().charAt(0) + beltMaterial.toString().substring(1).toLowerCase());
                    DEFAULT_IMPACTS.put(r, builder.define(r.getPath(), impact));
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
                    DEFAULT_CAPACITIES.put(r, builder.define(r.getPath(), capacity));
                    builder.pop();
                }
            }
        });
        builder.pop();*/
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
