package sirttas.elementalcraft.block.shrine.budding;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.Lazy;
import sirttas.elementalcraft.block.ECBlocks;
import sirttas.elementalcraft.block.entity.ECBlockEntityTypes;
import sirttas.elementalcraft.block.shrine.AbstractShrineBlockEntity;
import sirttas.elementalcraft.block.shrine.ore.OreShrineBlockEntity;
import sirttas.elementalcraft.block.shrine.properties.ShrineProperties;
import sirttas.elementalcraft.block.shrine.upgrade.ShrineUpgrades;

import java.util.List;

public class BuddingShrineBlockEntity extends AbstractShrineBlockEntity {

	public static final ResourceKey<ShrineProperties> PROPERTIES_KEY = createKey(BuddingShrineBlock.NAME);

	protected static final List<Direction> UPGRADE_DIRECTIONS = List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

	private static final Lazy<List<Block>> AMETHYSTS = Lazy.of(() -> List.of(Blocks.SMALL_AMETHYST_BUD, Blocks.MEDIUM_AMETHYST_BUD, Blocks.LARGE_AMETHYST_BUD, Blocks.AMETHYST_CLUSTER));
	private static final Lazy<List<Block>> SPRINGALINES = Lazy.of(() -> List.of(ECBlocks.SMALL_SPRINGALINE_BUD.get(), ECBlocks.MEDIUM_SPRINGALINE_BUD.get(), ECBlocks.LARGE_SPRINGALINE_BUD.get(), ECBlocks.SPRINGALINE_CLUSTER.get()));

	public BuddingShrineBlockEntity(BlockPos pos, BlockState state) {
		super(ECBlockEntityTypes.BUDDING_SHRINE, pos, state, PROPERTIES_KEY);
	}

	@Override
	public AABB getRangeBoundingBox() {
		return new AABB(above());
	}

	private BlockPos above() {
		return this.worldPosition.above();
	}

	@Override
	protected boolean doPeriod() {
		return switch (this.getBlockState().getValue(BuddingShrineBlock.CRYSTAL_TYPE)) {
			case SPRINGALINE -> grow(SPRINGALINES.get());
			default -> grow(AMETHYSTS.get());
		};
	}

	private boolean grow(List<Block> blocks) {
		var state = this.level.getBlockState(above());

		if (BuddingAmethystBlock.canClusterGrowAtState(state)) {
			setBud(blocks.get(0), state);
			return true;
		}
		var it = blocks.iterator();
		while (it.hasNext()) {
			if (state.is(it.next())) {
				if (it.hasNext()) {
					setBud(it.next(), state);
					return true;
				} else if (this.level instanceof ServerLevel serverLevel && this.hasUpgrade(ShrineUpgrades.CRYSTAL_HARVEST)) {
					var above = worldPosition.above();
					
					OreShrineBlockEntity.harvest(serverLevel, above, this, Blocks.AIR.defaultBlockState());
					return true;
				}
			}
		}
		return false;
	}

	private void setBud(Block block, BlockState state) {
		this.level.setBlockAndUpdate(above(), block.defaultBlockState().setValue(AmethystClusterBlock.FACING, Direction.UP).setValue(AmethystClusterBlock.WATERLOGGED,
				state.getFluidState().getType() == Fluids.WATER));
	}

	@Override
	public List<Direction> getUpgradeDirections() {
		return UPGRADE_DIRECTIONS;
	}
}
