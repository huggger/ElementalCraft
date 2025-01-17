package sirttas.elementalcraft.block.shrine.upgrade.horizontal;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sirttas.elementalcraft.block.shrine.upgrade.ShrineUpgrades;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FortuneShrineUpgradeBlock extends AbstractHorizontalShrineUpgradeBlock {

	public static final String NAME = "shrine_upgrade_fortune";

	private static final VoxelShape CORE_NORTH = Block.box(6D, 6D, 4D, 10D, 10D, 8D);
	private static final VoxelShape PILAR_NORTH = Block.box(7D, 2D, 5D, 9D, 6D, 7D);
	private static final VoxelShape BASE_1_NORTH = Block.box(5D, 0D, 3D, 11D, 1D, 9D);
	private static final VoxelShape BASE_2_NORTH = Block.box(6D, 1D, 4D, 10D, 2D, 8D);
	private static final VoxelShape PIPE_NORTH = Block.box(7D, 7D, 0D, 9D, 9D, 4D);

	private static final VoxelShape CORE_SOUTH = Block.box(6D, 6D, 8D, 10D, 10D, 12D);
	private static final VoxelShape PILAR_SOUTH = Block.box(7D, 2D, 9D, 9D, 6D, 11D);
	private static final VoxelShape BASE_1_SOUTH = Block.box(5D, 0D, 7D, 11D, 1D, 13D);
	private static final VoxelShape BASE_2_SOUTH = Block.box(6D, 1D, 8D, 10D, 2D, 12D);
	private static final VoxelShape PIPE_SOUTH = Block.box(7D, 7D, 12D, 9D, 9D, 16D);

	private static final VoxelShape CORE_WEST = Block.box(4D, 6D, 6D, 8D, 10D, 10D);
	private static final VoxelShape PILAR_WEST = Block.box(5D, 2D, 7D, 7D, 6D, 9D);
	private static final VoxelShape BASE_1_WEST = Block.box(3D, 0D, 5D, 9D, 1D, 11D);
	private static final VoxelShape BASE_2_WEST = Block.box(4D, 1D, 6D, 8D, 2D, 10D);
	private static final VoxelShape PIPE_WEST = Block.box(0D, 7D, 7D, 4D, 9D, 9D);

	private static final VoxelShape CORE_EAST = Block.box(8D, 6D, 6D, 12D, 10D, 10D);
	private static final VoxelShape PILAR_EAST = Block.box(9D, 2D, 7D, 11D, 6D, 9D);
	private static final VoxelShape BASE_1_EAST = Block.box(7D, 0D, 5D, 13D, 1D, 11D);
	private static final VoxelShape BASE_2_EAST = Block.box(8D, 1D, 6D, 12D, 2D, 10D);
	private static final VoxelShape PIPE_EAST = Block.box(12D, 7D, 7D, 16D, 9D, 9D);

	private static final VoxelShape SHAPE_NORTH = Shapes.or(CORE_NORTH, PILAR_NORTH, BASE_1_NORTH, BASE_2_NORTH, PIPE_NORTH);
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(CORE_SOUTH, PILAR_SOUTH, BASE_1_SOUTH, BASE_2_SOUTH, PIPE_SOUTH);
	private static final VoxelShape SHAPE_WEST = Shapes.or(CORE_WEST, PILAR_WEST, BASE_1_WEST, BASE_2_WEST, PIPE_WEST);
	private static final VoxelShape SHAPE_EAST = Shapes.or(CORE_EAST, PILAR_EAST, BASE_1_EAST, BASE_2_EAST, PIPE_EAST);

	public FortuneShrineUpgradeBlock() {
		super(ShrineUpgrades.FORTUNE);
	}
	
	@Nonnull
    @Override
	@Deprecated
	public VoxelShape getShape(BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_SOUTH;
			case WEST -> SHAPE_WEST;
			case EAST -> SHAPE_EAST;
			default -> SHAPE_NORTH;
		};
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable BlockGetter worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
		tooltip.add(Component.translatable("enchantment.minecraft.fortune").append(Component.literal(" ")).append(Component.translatable("enchantment.level.1"))
				.withStyle(ChatFormatting.BLUE));
		super.appendHoverText(stack, worldIn, tooltip, flag);
	}

}
