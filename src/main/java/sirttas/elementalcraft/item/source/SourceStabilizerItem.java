package sirttas.elementalcraft.item.source;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import sirttas.elementalcraft.api.source.ISourceInteractable;
import sirttas.elementalcraft.block.entity.BlockEntityHelper;
import sirttas.elementalcraft.block.source.SourceBlockEntity;
import sirttas.elementalcraft.item.ECItem;
import sirttas.elementalcraft.property.ECProperties;

public class SourceStabilizerItem extends ECItem implements ISourceInteractable {

	public static final String NAME = "source_stabilizer";
	
	public SourceStabilizerItem() {
		super(ECProperties.Items.ITEM_UNSTACKABLE);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		ItemStack stack = context.getItemInHand();
		Player player = context.getPlayer();
		
		return BlockEntityHelper.getBlockEntityAs(level, pos, SourceBlockEntity.class)
				.map(source -> {
					if (!source.isStabalized() && source.isAnalyzed()) {
						source.setStabalized(true);
						if (!player.isCreative()) {
							stack.shrink(1);
							if (stack.isEmpty()) {
								player.setItemInHand(context.getHand(), ItemStack.EMPTY);
							}
						}
						return InteractionResult.SUCCESS;
					}
					return InteractionResult.PASS;
				}).orElse(InteractionResult.PASS);
	}
	
}