package electrolyte.greate.blocks.andesite;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import electrolyte.greate.registry.ModBlocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GirderEncasedAndesiteShaft extends GirderEncasedShaftBlock {
    public GirderEncasedAndesiteShaft(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        InteractionResult onWrenched = super.onWrenched(state, context);
        Player player = context.getPlayer();
        if(onWrenched == InteractionResult.SUCCESS && player != null && !player.isCreative()) {
            player.getInventory().placeItemBackInInventory(ModBlocks.ANDESITE_SHAFT.asStack());
        }
        return onWrenched;
    }

    @Override
    public Class<KineticBlockEntity> getBlockEntityClass() {
        return super.getBlockEntityClass();
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return super.getBlockEntityType();
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity be) {
        return ItemRequirement.of(ModBlocks.ANDESITE_SHAFT.getDefaultState(), be)
                .union(ItemRequirement.of(AllBlocks.METAL_GIRDER.getDefaultState(), be));
    }
}
