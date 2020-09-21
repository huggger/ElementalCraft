package sirttas.elementalcraft.spell.air;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import sirttas.elementalcraft.ElementType;
import sirttas.elementalcraft.config.ECConfig;
import sirttas.elementalcraft.spell.ISelfCastedSpell;
import sirttas.elementalcraft.spell.Spell;

public class SpellItemPull extends Spell implements ISelfCastedSpell {

	public static final String NAME = "item_pull";

	public SpellItemPull() {
		super(Properties.create(Spell.Type.UTILITY).elementType(ElementType.AIR).cooldown(ECConfig.CONFIG.itemPullCooldown.get()).consumeAmount(ECConfig.CONFIG.itemPullConsumeAmount.get()));
	}

	@Override
	public ActionResultType castOnSelf(Entity sender) {
		Vector3d pos = sender.getPositionVec();

		sender.getEntityWorld().getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(ECConfig.CONFIG.itemPullRange.get())).stream()
				.forEach(i -> i.setPosition(pos.x, pos.y, pos.z));
		return ActionResultType.SUCCESS;
	}
}