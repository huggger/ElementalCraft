package sirttas.elementalcraft.mixin;

import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import sirttas.elementalcraft.item.spell.StaffItem;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
	
	protected MixinPlayerEntity(EntityType<? extends LivingEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@SuppressWarnings("rawtypes")
	@Redirect(method = "attack(Lnet/minecraft/entity/Entity;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", shift = At.Shift.AFTER))
	private boolean isHoldingStaff$attack(final Object item, final Class target) {
		return item instanceof SwordItem || isHoldingStaff();
	}
	
	@Redirect(method = "attack(Lnet/minecraft/entity/Entity;)V", 
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getBoundingBox()Lnet/minecraft/util/math/AxisAlignedBB;"))
	public AxisAlignedBB getBoundingBoxStaff$attack(Entity targetEntity) {
		if (isHoldingStaff()) {
			return this.getBoundingBox().inflate(1, 0, 1);
		}
		return targetEntity.getBoundingBox();
	}
	
	private boolean isHoldingStaff() {
		return this.getMainHandItem().getItem() instanceof StaffItem;
	}
}