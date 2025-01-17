package sirttas.elementalcraft.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.RegistryObject;
import sirttas.elementalcraft.api.name.ECNames;
import sirttas.elementalcraft.container.IContainerBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractECContainerBlockEntity extends AbstractECBlockEntity implements Clearable, IContainerBlockEntity, ContainerListener {

	private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createHandler);

	protected AbstractECContainerBlockEntity(RegistryObject<? extends BlockEntityType<?>> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
		this.clearContent();
		super.onDataPacket(net, packet);
	}

	@Nonnull
	protected IItemHandler createHandler() {
		return new InvWrapper(this.getInventory());
	}

	@Override
	public void clearContent() {
		this.getInventory().clearContent();
	}

	@Override
	public void load(@Nonnull CompoundTag compound) {
		super.load(compound);
		Container inv = getInventory();

		if (inv instanceof INBTSerializable nbtInv && compound.contains(ECNames.INVENTORY)) {
			nbtInv.deserializeNBT(compound.get(ECNames.INVENTORY));
		}
	}

	@Override
	public void saveAdditional(@Nonnull CompoundTag compound) {
		super.saveAdditional(compound);
		Container inv = getInventory();

		if (inv instanceof INBTSerializable nbtInv) {
			compound.put(ECNames.INVENTORY, nbtInv.serializeNBT());
		}
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
			return getItemHandler().cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public LazyOptional<IItemHandler> getItemHandler() {
		return itemHandler;
	}

	@Override
	public void containerChanged(@Nonnull Container invBasic) {
		this.setChanged();
	}
}
