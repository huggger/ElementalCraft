package sirttas.elementalcraft.item.spell.book;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sirttas.elementalcraft.config.ECConfig;
import sirttas.elementalcraft.item.ECItem;
import sirttas.elementalcraft.property.ECProperties;
import sirttas.elementalcraft.spell.SpellHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SpellBookItem extends ECItem {

	public static final String NAME = "spell_book";
	
	public SpellBookItem() {
		super(ECProperties.Items.ITEM_UNSTACKABLE);
	}

	/**
	 * Called when the equipped item is right clicked.
	 */
	@Nonnull
    @Override
	public InteractionResultHolder<ItemStack> use(@Nonnull Level world, Player player, @Nonnull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		return new InteractionResultHolder<>(open(world, player, stack), stack);
	}
	
	public InteractionResult open(Level world, Player player, ItemStack stack) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		player.openMenu(new ContainerProvider(stack));
		return InteractionResult.CONSUME;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
		SpellHelper.forEachSpell(stack, (spell, count) -> {
			if (count == 1) {
				tooltip.add(Component.empty().append(spell.getDisplayName()).withStyle(ChatFormatting.GRAY));
			} else {
				tooltip.add(Component.literal(count + " ").append(spell.getDisplayName()).withStyle(ChatFormatting.GRAY));
			}
		});
	}


	@Override
	public int getBarWidth(@Nonnull ItemStack stack) {
		return Math.round(ECConfig.COMMON.spellBookMaxSpell.get() - SpellHelper.getSpellCount(stack) * 13F / ECConfig.COMMON.spellBookMaxSpell.get());
	}
	
	private static class ContainerProvider implements MenuProvider {

		private final ItemStack stack;

		private ContainerProvider(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public AbstractContainerMenu createMenu(int id, @Nonnull Inventory inventory, @Nonnull Player palyer) {
			return SpellBookMenu.create(id, inventory, stack);
		}

		@Nonnull
        @Override
		public Component getDisplayName() {
			return stack.getHoverName();
		}

	}

}
