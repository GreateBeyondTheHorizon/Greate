package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.block.FluidPipeBlock;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.simibubi.create.content.fluids.PipeAttachmentModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.fluids.TieredFluidPipeBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static electrolyte.greate.Greate.REGISTRATE;

public class Pipes {

	static {
		REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
	}

	public static Table<TagPrefix, Material, BlockEntry<TieredFluidPipeBlock>> FLUID_PIPE_BLOCKS;
	private static final ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredFluidPipeBlock>> FLUID_PIPE_BLOCKS_BUILDER = ImmutableTable.builder();

//	public static void init() {
	static {
		Greate.LOGGER.debug("Generating Greate Fluid Pipe Blocks from GTCEu Fluid Pipes...");
		Greate.LOGGER.debug(GTBlocks.FLUID_PIPE_BLOCKS.toString());
		GTBlocks.FLUID_PIPE_BLOCKS.rowMap().forEach((prefix, map) ->
				map.forEach((material, block) ->
						registerFluidPipeBlock(prefix, material, block.get())));
		FLUID_PIPE_BLOCKS = FLUID_PIPE_BLOCKS_BUILDER.build();
		Greate.LOGGER.debug("Generating Greate Fluid Pipe Blocks from GTCEu Fluid Pipes... Complete!");
	}

//	private static void generateFluidPipeBlocks() {
//		GTCEu.LOGGER.debug("Generating GTCEu Fluid Pipe Blocks...");
//		for (var fluidPipeType : FluidPipeType.values()) {
//			for (MaterialRegistry registry : GTCEuAPI.materialManager.getRegistries()) {
//				GTRegistrate registrate = registry.getRegistrate();
//				for (Material material : registry.getAllMaterials()) {
//					if (allowFluidPipeBlock(material, fluidPipeType)) {
//						registerFluidPipeBlock(material, fluidPipeType, registrate);
//					}
//				}
//			}
//		}
//		FLUID_PIPE_BLOCKS = FLUID_PIPE_BLOCKS_BUILDER.build();
//		GTCEu.LOGGER.debug("Generating GTCEu Fluid Pipe Blocks... Complete!");
//	}

	// HOW IT IS REGISTERED IN GT
//	private static void registerFluidPipeBlock(Material material, FluidPipeType fluidPipeType, GTRegistrate registrate) {
//		var entry = registrate.block("%s_%s_fluid_pipe".formatted(material.getName(), fluidPipeType.name), p -> new FluidPipeBlock(p, fluidPipeType, material))
//				.initialProperties(() -> Blocks.IRON_BLOCK)
//				.properties(p -> {
//					if (doMetalPipe(material)) {
//						p.sound(GTSoundTypes.METAL_PIPE);
//					}
//					return p.dynamicShape().noOcclusion().noLootTable();
//				})
//				.transform(unificationBlock(fluidPipeType.tagPrefix, material))
//				.blockstate(NonNullBiConsumer.noop())
//				.setData(ProviderType.LANG, NonNullBiConsumer.noop())
//				.setData(ProviderType.LOOT, NonNullBiConsumer.noop())
//				.addLayer(() -> RenderType::cutoutMipped)
//				.color(() -> MaterialPipeBlock::tintedColor)
//				.item(MaterialPipeBlockItem::new)
//				.model(NonNullBiConsumer.noop())
//				.color(() -> MaterialPipeBlockItem::tintColor)
//				.build()
//				.register();
//		FLUID_PIPE_BLOCKS_BUILDER.put(fluidPipeType.tagPrefix, material, entry);
//	}

//	public static final BlockEntry<TieredFluidPipeBlock> FLUID_PIPE = fluidPipe("fluid_pipe", GreateFluidPipeType.NORMAL, GTMaterials.Gold);
//
//	public static BlockEntry<TieredFluidPipeBlock> fluidPipe(String name, GreateFluidPipeType fluidPipeType, Material material) {
//		return REGISTRATE
//				.block(name, p -> new TieredFluidPipeBlock(p, fluidPipeType, material))
//				// Create
//				.initialProperties(SharedProperties::copperMetal)
//				.properties(BlockBehaviour.Properties::forceSolidOn)
//				.transform(pickaxeOnly())
//				.blockstate(GreateBlockStateGen.pipe())
//				.onRegister(CreateRegistrate.blockModel(() -> PipeAttachmentModel::new))
//				.color(() -> TieredFluidPipeBlock::tintedColor)
//				.item()
//				.transform(customItemModel())
//				// GT
//
//
//				.register();
//	}

	public static void registerFluidPipeBlock(TagPrefix tagPrefix, Material material, FluidPipeBlock fluidPipeBlock) {
		//GreateFluidPipeType fluidPipeType = GreateFluidPipeType.from(fluidPipeBlock.pipeType);
		var entry = REGISTRATE
				.block(tagPrefix + "_fluid_pipe", p -> new TieredFluidPipeBlock(p, fluidPipeBlock.pipeType, material))
				// Create
				.initialProperties(SharedProperties::copperMetal)
				.properties(BlockBehaviour.Properties::forceSolidOn)
				.transform(pickaxeOnly())
				.blockstate(GreateBlockStateGen.pipe())
				.onRegister(CreateRegistrate.blockModel(() -> PipeAttachmentModel::new))
				.color(() -> TieredFluidPipeBlock::tintedColor)
				.item()
				.transform(customItemModel())
				// GT

				.register();
		FLUID_PIPE_BLOCKS_BUILDER.put(fluidPipeBlock.pipeType.tagPrefix, material, entry);
	}
}
