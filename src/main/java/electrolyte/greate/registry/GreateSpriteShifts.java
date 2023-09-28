package electrolyte.greate.registry;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.block.render.SpriteShifter;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreateSpriteShifts {

    public static final Map<TieredBeltBlock, Map<DyeColor, SpriteShiftEntry>>
            DYED_BELTS = new HashMap<>(),
            DYED_OFFSET_BELTS = new HashMap<>(),
            DYED_DIAGONAL_BELTS = new HashMap<>();

    public static final Map<TieredBeltBlock, List<SpriteShiftEntry>> BELT_SPRITES = new HashMap<>();

    public static void populateMaps(TieredBeltBlock belt) {
        ResourceLocation blockID = ForgeRegistries.BLOCKS.getKey(belt);
        String beltMaterial = blockID.toString().substring(Greate.MOD_ID.length() + 1, blockID.toString().length() - 5);
        BELT_SPRITES.put(belt, List.of(
                get(beltMaterial + "/belt", beltMaterial + "/belt_scroll"),
                get(beltMaterial + "/belt_offset", beltMaterial + "/belt_scroll"),
                get(beltMaterial + "/belt_diagonal", beltMaterial + "/belt_diagonal_scroll"),
                getCreate("block/belt/brass_belt_casing", "block/belt/andesite_belt_casing"),
                getCreate("block/crafter_thingies", "block/crafter_thingies")));
        EnumMap<DyeColor, SpriteShiftEntry> COLOR_MAP = new EnumMap<>(DyeColor.class);
        EnumMap<DyeColor, SpriteShiftEntry> OFFSET_MAP = new EnumMap<>(DyeColor.class);
        EnumMap<DyeColor, SpriteShiftEntry> DIAGONAL_MAP = new EnumMap<>(DyeColor.class);

        for(DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            COLOR_MAP.put(color, get(beltMaterial + "/belt", beltMaterial + "/" + id + "_scroll"));
            OFFSET_MAP.put(color, get(beltMaterial + "/belt_offset", beltMaterial + "/" + id + "_scroll"));
            DIAGONAL_MAP.put(color, get(beltMaterial + "/belt_diagonal", beltMaterial + "/" + id + "_diagonal_scroll"));
            DYED_BELTS.put(belt, COLOR_MAP);
            DYED_OFFSET_BELTS.put(belt, OFFSET_MAP);
            DYED_DIAGONAL_BELTS.put(belt, DIAGONAL_MAP);
        }
    }

    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(new ResourceLocation(Greate.MOD_ID, "block/" + originalLocation), new ResourceLocation(Greate.MOD_ID, "block/" + targetLocation));
    }

    private static SpriteShiftEntry getCreate(String originalLocation, String targetLocation) {
        return SpriteShifter.get(Create.asResource("block/" + originalLocation), Create.asResource("block/" + targetLocation));
    }
}
