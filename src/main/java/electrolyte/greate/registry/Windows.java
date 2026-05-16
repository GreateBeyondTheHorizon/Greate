package electrolyte.greate.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.GlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.WindowBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.GlassPaneCTBehaviour;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import electrolyte.greate.Greate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

import java.util.function.Function;
import java.util.function.Supplier;

import static electrolyte.greate.GreateRegistries.REGISTRATE;

public class Windows {

    public static final BlockEntry<WindowBlock>
			RUBBER_WINDOW = woodenWindowBlock(GTBlocks.RUBBER_TYPE, GTBlocks.RUBBER_PLANK),
			TREATED_WOOD_WINDOW = woodenWindowBlock(GTBlocks.TREATED_WOOD_TYPE, GTBlocks.TREATED_WOOD_PLANK);
	public static BlockEntry<ConnectedGlassPaneBlock>
			RUBBER_GLASS_PANE = woodenWindowPane(GTBlocks.RUBBER_TYPE, RUBBER_WINDOW),
			TREATED_WOOD_PANE = woodenWindowPane(GTBlocks.TREATED_WOOD_TYPE, TREATED_WOOD_WINDOW);

    public static void register() {}

    public static BlockEntry<WindowBlock> woodenWindowBlock(WoodType woodType, Supplier<Block> planksBlock) {
		return woodenWindowBlock(woodType, planksBlock, true);
	}

    public static BlockEntry<WindowBlock> woodenWindowBlock(WoodType woodType, Supplier<Block> planksBlock, boolean translucent) {
		String woodName = woodType.name().split(":")[1];
		String name = woodName + "_window";
		NonNullFunction<String, ResourceLocation> end_texture =
			$ -> GTCEu.id("block/" + woodName + "_planks");
		NonNullFunction<String, ResourceLocation> side_texture = n -> Greate.id("block/palettes/" + n);
		return windowBlock(name, () -> GreateSpriteShifts.getWoodenSpriteShift(GTCEu.id(woodName + "_planks")),
				translucent, end_texture, side_texture, () -> planksBlock.get().defaultMapColor()).register();
	}

	public static BlockBuilder<WindowBlock, GTRegistrate> windowBlock(String name,
																	  Supplier<CTSpriteShiftEntry> ct,
																	  boolean translucent,
																	  NonNullFunction<String, ResourceLocation> endTexture, NonNullFunction<String, ResourceLocation> sideTexture,
																	  Supplier<MapColor> color) {
		return REGISTRATE.block(name, p -> new WindowBlock(p, translucent))
			.onRegister(CreateRegistrate.connectedTextures(() -> new HorizontalCTBehaviour(ct.get())))
			.initialProperties(() -> Blocks.GLASS)
			.properties(p -> p.mapColor(color.get())
                    .isValidSpawn((blockState, blockGetter, pos, entityType) -> false)
                    .isRedstoneConductor((blockState, blockGetter, pos) -> false)
                    .isSuffocating((blockState, blockGetter, pos) -> false)
                    .isViewBlocking((blockState, blockGetter, pos) -> false))
			.loot(RegistrateBlockLootTables::dropWhenSilkTouch)
			.blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
				.cubeColumn(c.getName(), sideTexture.apply(c.getName()), endTexture.apply(c.getName())).renderType(RenderType.translucent().name)))
			.tag(BlockTags.IMPERMEABLE)
			.simpleItem();
	}

	public static BlockEntry<ConnectedGlassPaneBlock> woodenWindowPane(WoodType woodType, Supplier<? extends Block> parent) {
		String woodName = woodType.name().split(":")[1];
		String name = woodName + "_window";
		ResourceLocation topTexture = GTCEu.id("block/" + woodName + "_planks");
		ResourceLocation sideTexture = Greate.id("block/palettes/" + name);
		return connectedGlassPane(name, parent, () -> GreateSpriteShifts.getWoodenSpriteShift(GTCEu.id(woodName + "_planks")), sideTexture,
			sideTexture, topTexture).register();
	}

	private static BlockBuilder<ConnectedGlassPaneBlock, GTRegistrate> connectedGlassPane(String name,
																							  Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift, ResourceLocation sideTexture,
																							  ResourceLocation itemSideTexture, ResourceLocation topTexture) {
		NonNullConsumer<? super ConnectedGlassPaneBlock> connectedTextures = CreateRegistrate.connectedTextures(() -> new GlassPaneCTBehaviour(ctshift.get()));
		String CGPparents = "block/connected_glass_pane/";
		String prefix = name + "_pane_";

		Function<RegistrateBlockstateProvider, ModelFile> post =
			getPaneModelProvider(CGPparents, prefix, "post", sideTexture, topTexture),
			side = getPaneModelProvider(CGPparents, prefix, "side", sideTexture, topTexture),
			sideAlt = getPaneModelProvider(CGPparents, prefix, "side_alt", sideTexture, topTexture),
			noSide = getPaneModelProvider(CGPparents, prefix, "noside", sideTexture, topTexture),
			noSideAlt = getPaneModelProvider(CGPparents, prefix, "noside_alt", sideTexture, topTexture);

		NonNullBiConsumer<DataGenContext<Block, ConnectedGlassPaneBlock>, RegistrateBlockstateProvider> stateProvider =
			(c, p) -> p.paneBlock(c.get(), post.apply(p), side.apply(p), sideAlt.apply(p), noSide.apply(p),
				noSideAlt.apply(p));

		return glassPane(name, parent, itemSideTexture, topTexture, ConnectedGlassPaneBlock::new,
				connectedTextures, stateProvider);
	}

	private static Function<RegistrateBlockstateProvider, ModelFile> getPaneModelProvider(String CGPparents, String prefix, String partial, ResourceLocation sideTexture, ResourceLocation topTexture) {
		return p -> p.models()
			.withExistingParent(prefix + partial, Create.asResource(CGPparents + partial))
			.texture("pane", sideTexture).renderType(RenderType.cutoutMipped().name)
			.texture("edge", topTexture).renderType(RenderType.translucent().name);
	}

	private static <G extends GlassPaneBlock> BlockBuilder<G, GTRegistrate> glassPane(String name,
																						  Supplier<? extends Block> parent, ResourceLocation sideTexture, ResourceLocation topTexture,
																						  NonNullFunction<Properties, G> factory, NonNullConsumer<? super G> connectedTextures,
																						  NonNullBiConsumer<DataGenContext<Block, G>, RegistrateBlockstateProvider> stateProvider) {
		name += "_pane";
		ItemBuilder<BlockItem, BlockBuilder<G, GTRegistrate>> itemBuilder = REGISTRATE.block(name, factory)
			.onRegister(connectedTextures)
			.initialProperties(() -> Blocks.GLASS_PANE)
			.properties(p -> p.mapColor(parent.get().defaultMapColor()))
			.blockstate(stateProvider)
			.loot(RegistrateBlockLootTables::dropWhenSilkTouch)
			.item();

		itemBuilder.tag(Tags.Items.GLASS_PANES);

		BlockBuilder<G, GTRegistrate> blockBuilder = itemBuilder
			.model((c, p) -> p.generated(c, sideTexture).renderType(RenderType.translucent().name))
			.build();

		blockBuilder.tag(Tags.Blocks.GLASS_PANES);

		return blockBuilder;
	}
}
