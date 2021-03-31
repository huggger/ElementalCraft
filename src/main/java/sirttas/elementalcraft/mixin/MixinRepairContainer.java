package sirttas.elementalcraft.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import sirttas.elementalcraft.infusion.tool.ToolInfusion;
import sirttas.elementalcraft.infusion.tool.ToolInfusionHelper;

@Mixin(RepairContainer.class)
public abstract class MixinRepairContainer extends AbstractRepairContainer {

	@Shadow
	@Final
	private IntReferenceHolder maximumCost;
	
	protected MixinRepairContainer(ContainerType<?> type, int i, PlayerInventory inv, IWorldPosCallable callable) {
		super(type, i, inv, callable);
	}

	@Unique
	public ItemStack getLeft() {
		return this.field_234643_d_.getStackInSlot(0);
	}

	@Unique
	public ItemStack getRight() {
		return this.field_234643_d_.getStackInSlot(1);
	}

	@Unique
	public ItemStack getOutput() {
		return this.field_234642_c_.getStackInSlot(0);
	}
	
	@Inject(method = "updateRepairOutput()V", at = @At("RETURN"))
	public void onUpdateRepairOutput(CallbackInfo ci) {
		ToolInfusion left = ToolInfusionHelper.getInfusion(getLeft());
		ToolInfusion right = ToolInfusionHelper.getInfusion(getRight());
		ItemStack output = getOutput();

		if (!output.isEmpty()) {
			if (left != null) {
				ToolInfusionHelper.setInfusion(output, left);
			} else if (right != null) {
				if (!ToolInfusionHelper.getInfusion(output).equals(right)) {
					maximumCost.set(maximumCost.get() + 4);
				}
				ToolInfusionHelper.setInfusion(output, right);
			} else {
				ToolInfusionHelper.removeInfusion(output);
			}
		}
	}
}
