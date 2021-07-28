package sirttas.elementalcraft.entity.boss.earthgolem;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ObjectHolder;
import sirttas.elementalcraft.api.ElementalCraftApi;
import sirttas.elementalcraft.entity.boss.AbstractECBossEntity;
import sirttas.elementalcraft.entity.goal.CastSpellGoal;
import sirttas.elementalcraft.spell.SpellTickManager;
import sirttas.elementalcraft.spell.Spells;

public class EarthGolemEntity extends AbstractECBossEntity {

	public static final String NAME = "earthgolem";
	@ObjectHolder(ElementalCraftApi.MODID + ":" + NAME) public static final EntityType<EarthGolemEntity> TYPE = null;

	public EarthGolemEntity(EntityType<EarthGolemEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public static AttributeSupplier.Builder getAttributeModifier() {
		return AbstractECBossEntity.getAttributeModifier().add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.KNOCKBACK_RESISTANCE, 5.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new AttackGoal(this));
		this.goalSelector.addGoal(3, new CastSpellGoal(this, Spells.GRAVEL_FALL));
		this.goalSelector.addGoal(3, new CastSpellGoal(this, Spells.FIRE_BALL));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean ret = super.hurt(source, amount);

		if (ret && source instanceof IndirectEntityDamageSource && this.random.nextInt(10) > 2) {
			Spells.STONE_WALL.castOnSelf(this);
			SpellTickManager.getInstance(getCommandSenderWorld()).setCooldown(this, Spells.STONE_WALL);
		}
		return ret;
	}

	private static class AttackGoal extends MeleeAttackGoal {

		public AttackGoal(EarthGolemEntity creature) {
			super(creature, 0, true);
		}

		@Override
		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return this.mob.getBbWidth() * 1.25F * this.mob.getBbWidth() * 1.25F + attackTarget.getBbWidth();
		}
	}
}
