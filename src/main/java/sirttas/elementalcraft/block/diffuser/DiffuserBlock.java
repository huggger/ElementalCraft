package sirttas.elementalcraft.block.diffuser;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sirttas.elementalcraft.block.AbstractECEntityBlock;
import sirttas.elementalcraft.block.entity.BlockEntityHelper;
import sirttas.elementalcraft.particle.ParticleHelper;

public class DiffuserBlock extends AbstractECEntityBlock {

	public static final String NAME = "diffuser";

	private static final VoxelShape BASE_1 = Block.box(6D, 0D, 6D, 10D, 4D, 10D);
	private static final VoxelShape BASE_2 = Block.box(0D, 1D, 0D, 16D, 3D, 16D);
	private static final VoxelShape PILLAR = Block.box(7D, 4D, 7D, 9D, 11D, 9D);

	private static final VoxelShape SIDE_PILLAR_1 = Block.box(1D, 0D, 1D, 3D, 6D, 3D);
	private static final VoxelShape SIDE_PILLAR_2 = SIDE_PILLAR_1.move(12D / 16, 0D, 0D);
	private static final VoxelShape SIDE_PILLAR_3 = SIDE_PILLAR_1.move(0D, 0D, 12D /16);
	private static final VoxelShape SIDE_PILLAR_4 = SIDE_PILLAR_1.move(12D / 16, 0D, 12D / 16);

	private static final VoxelShape SHAPE = Shapes.or(BASE_1, BASE_2, PILLAR, SIDE_PILLAR_1, SIDE_PILLAR_2, SIDE_PILLAR_3, SIDE_PILLAR_4);

	@Override
	public DiffuserBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DiffuserBlockEntity(pos, state);
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createECTicker(level, type, DiffuserBlockEntity.TYPE, DiffuserBlockEntity::tick);
	}
	
	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	@Deprecated
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return BlockEntityHelper.isValidContainer(state.getBlock(), world, pos.below());
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
		BlockEntityHelper.getTileEntityAs(world, pos, DiffuserBlockEntity.class).filter(DiffuserBlockEntity::hasDiffused)
				.ifPresent(e -> ParticleHelper.createElementFlowParticle(e.getTankElementType(), world, Vec3.atCenterOf(pos), Direction.UP, 1, rand));
	}
}