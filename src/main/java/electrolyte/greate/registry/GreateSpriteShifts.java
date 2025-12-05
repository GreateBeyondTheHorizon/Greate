package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GreateSpriteShifts {

    public static final Object2ObjectOpenHashMap<TieredBeltBlock, Map<DyeColor, SpriteShiftEntry>>
            DYED_BELTS = new Object2ObjectOpenHashMap<>(),
            DYED_DIAGONAL_BELTS = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectOpenHashMap<TieredBeltBlock, List<SpriteShiftEntry>> BELT_SPRITES = new Object2ObjectOpenHashMap<>();

    public static void populateMaps(Material beltMaterial) {
        TieredBeltBlock belt = (TieredBeltBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Greate.MOD_ID,beltMaterial.getName() + "_belt"));
        BELT_SPRITES.put(belt, List.of(
                get(beltMaterial.getName() + "/belt", beltMaterial.getName() + "/belt_scroll"),
                get(beltMaterial.getName() + "/belt_offset", beltMaterial.getName() + "/belt_scroll"),
                get(beltMaterial.getName() + "/belt_diagonal", beltMaterial.getName() + "/belt_diagonal_scroll"),
                get( "belt_overlay/empty", "belt_overlay/empty_scroll")));
        EnumMap<DyeColor, SpriteShiftEntry> COLOR_MAP = new EnumMap<>(DyeColor.class);
        EnumMap<DyeColor, SpriteShiftEntry> DIAGONAL_MAP = new EnumMap<>(DyeColor.class);
        for(DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            COLOR_MAP.put(color, get("belt_overlay/empty", "belt_overlay/" + id));
            DIAGONAL_MAP.put(color, get("belt_overlay/empty", "belt_overlay/" + id + "_diagonal"));
            DYED_BELTS.put(belt, COLOR_MAP);
            DYED_DIAGONAL_BELTS.put(belt, DIAGONAL_MAP);
        }
    }

    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(Greate.id("block/" + originalLocation), Greate.id("block/" + targetLocation));
    }
}
