package sirttas.elementalcraft.block.shrine.upgrade.unidirectional;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sirttas.elementalcraft.block.shrine.upgrade.AbstractShrineUpgradeBlock;

public class PickupShrineUpgradeBlock extends AbstractShrineUpgradeBlock {

	public static final String NAME = "shrine_upgrade_pickup";

	private static final VoxelShape BASE = Block.box(6D, -1D, 6D, 10D, 10D, 10D);
	private static final VoxelShape PIPE_1 = Block.box(7D, 7D, 3D, 9D, 9D, 6D);
	private static final VoxelShape PIPE_2 = Block.box(7D, 7D, 10D, 9D, 9D, 13D);
	private static final VoxelShape PIPE_3 = Block.box(3D, 7D, 7D, 6D, 9D, 9D);
	private static final VoxelShape PIPE_4 = Block.box(10D, 7D, 7D, 13D, 9D, 9D);

	private static final VoxelShape SHAPE = Shapes.or(BASE, PIPE_1, PIPE_2, PIPE_3, PIPE_4);

	@Override
	public Direction getFacing(BlockState state) {
		return Direction.DOWN;
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent("tooltip.elementalcraft.pickup").withStyle(ChatFormatting.BLUE));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
}
