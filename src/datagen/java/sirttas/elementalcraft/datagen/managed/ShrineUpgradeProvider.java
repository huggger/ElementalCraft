package sirttas.elementalcraft.datagen.managed;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import sirttas.dpanvil.api.data.AbstractManagedDataBuilderProvider;
import sirttas.dpanvil.api.predicate.block.IBlockPosPredicate;
import sirttas.elementalcraft.ElementalCraft;
import sirttas.elementalcraft.block.ECBlocks;
import sirttas.elementalcraft.block.shrine.upgrade.ShrineUpgrade;
import sirttas.elementalcraft.block.shrine.upgrade.ShrineUpgrade.BonusType;
import sirttas.elementalcraft.block.shrine.upgrade.ShrineUpgrades;
import sirttas.elementalcraft.data.predicate.block.shrine.HasShrineUpgradePredicate;
import sirttas.elementalcraft.tag.ECTags;

import javax.annotation.Nonnull;

public class ShrineUpgradeProvider extends AbstractManagedDataBuilderProvider<ShrineUpgrade, ShrineUpgrade.Builder> {
	private static final IBlockPosPredicate ACCELERATION_PREDICATE = IBlockPosPredicate.match(ECTags.Blocks.SHRINES_UPGRADABLES_ACCELERATION)
			.or(IBlockPosPredicate.match(ECBlocks.VACUUM_SHRINE.get()).and(new HasShrineUpgradePredicate(ShrineUpgrades.PICKUP)));
	private static final IBlockPosPredicate PROTECTION_PREDICATE = IBlockPosPredicate.match(ECTags.Blocks.SHRINES_UPGRADABLES_PROTECTION)
			.or(IBlockPosPredicate.match(ECBlocks.VACUUM_SHRINE.get()).and(new HasShrineUpgradePredicate(ShrineUpgrades.VORTEX)));
	private static final IBlockPosPredicate STRENGTH_PREDICATE = IBlockPosPredicate.match(ECTags.Blocks.SHRINES_UPGRADABLES_STRENGTH)
			.or(IBlockPosPredicate.match(ECBlocks.SPRING_SHRINE.get()).and(new HasShrineUpgradePredicate(ShrineUpgrades.FILLING)));
	
	public ShrineUpgradeProvider(DataGenerator generator) {
		super(generator, ElementalCraft.SHRINE_UPGRADE_MANAGER, ShrineUpgrade.Builder::toJson);
	}

	@Override
	public void collectBuilders() {
        builder(ShrineUpgrades.SILK_TOUCH).predicate(miningPredicate(ECTags.Blocks.SHRINES_UPGRADABLES_SILK_TOUCH)).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 2).incompatibleWith(ShrineUpgrades.FORTUNE);
        builder(ShrineUpgrades.FORTUNE).predicate(miningPredicate(ECTags.Blocks.SHRINES_UPGRADABLES_FORTUNE)).max(3).addBonus(BonusType.ELEMENT_CONSUMPTION, 1.3F).incompatibleWith(ShrineUpgrades.SILK_TOUCH);
        builder(ShrineUpgrades.PLANTING).match(ECTags.Blocks.SHRINES_UPGRADABLES_PLANTING).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 3F);
        builder(ShrineUpgrades.BONELESS_GROWTH).match(ECBlocks.GROWTH_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 1.5F).addBonus(BonusType.SPEED, 10F).addBonus(BonusType.CAPACITY, 1.5F).incompatibleWith(ShrineUpgrades.STEM_POLLINATION, ShrineUpgrades.CRYSTAL_GROWTH);
        builder(ShrineUpgrades.PICKUP).match(ECBlocks.VACUUM_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 100F).addBonus(BonusType.SPEED, 20F).addBonus(BonusType.RANGE, 0.5F).incompatibleWith(ShrineUpgrades.VORTEX, ShrineUpgrades.STRENGTH);
		builder(ShrineUpgrades.VORTEX).match(ECBlocks.VACUUM_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 2F).addBonus(BonusType.RANGE, 0.75F).incompatibleWith(ShrineUpgrades.PICKUP);
		builder(ShrineUpgrades.NECTAR).match(ECBlocks.SWEET_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 5F).addBonus(BonusType.SPEED, 2F).incompatibleWith(ShrineUpgrades.STRENGTH);
		builder(ShrineUpgrades.MYSTICAL_GROVE).match(ECBlocks.GROVE_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 2F).addBonus(BonusType.SPEED, 2F);
		builder(ShrineUpgrades.STEM_POLLINATION).match(ECBlocks.GROWTH_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 4F).addBonus(BonusType.SPEED, 3F).addBonus(BonusType.RANGE, 0.5F).incompatibleWith(ShrineUpgrades.BONELESS_GROWTH, ShrineUpgrades.CRYSTAL_GROWTH);
		builder(ShrineUpgrades.FILLING).match(ECBlocks.SPRING_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 3F).addBonus(BonusType.SPEED, 2F);
		builder(ShrineUpgrades.SPRINGALINE).match(ECBlocks.BUDDING_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 2F).addBonus(BonusType.SPEED, 2F).addBonus(BonusType.CAPACITY, 2F);
		builder(ShrineUpgrades.CRYSTAL_HARVEST).match(ECTags.Blocks.SHRINES_UPGRADABLES_CRYSTAL_HARVEST).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 1.5F).addBonus(BonusType.RANGE, 0.5F);
		builder(ShrineUpgrades.CRYSTAL_GROWTH).match(ECBlocks.GROWTH_SHRINE.get()).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 50F).addBonus(BonusType.SPEED, 30F).incompatibleWith(ShrineUpgrades.BONELESS_GROWTH, ShrineUpgrades.STEM_POLLINATION);

		builder(ShrineUpgrades.ACCELERATION).predicate(ACCELERATION_PREDICATE).addBonus(BonusType.SPEED, 0.5F);
		builder(ShrineUpgrades.RANGE).match(ECTags.Blocks.SHRINES_UPGRADABLES_RANGE).addBonus(BonusType.RANGE, 1.5F).addBonus(BonusType.ELEMENT_CONSUMPTION, 1.2F).addBonus(BonusType.SPEED, 1.2F);
		builder(ShrineUpgrades.PROTECTION).predicate(PROTECTION_PREDICATE).max(1).addBonus(BonusType.ELEMENT_CONSUMPTION, 3F);
		builder(ShrineUpgrades.CAPACITY).match(ECTags.Blocks.SHRINES).max(1).addBonus(BonusType.CAPACITY, 5F).addBonus(BonusType.ELEMENT_CONSUMPTION, 1.1F).addBonus(BonusType.SPEED, 1.1F);
		builder(ShrineUpgrades.EFFICIENCY).match(ECTags.Blocks.SHRINES).addBonus(BonusType.CAPACITY, 0.9F).addBonus(BonusType.ELEMENT_CONSUMPTION, 0.5F);
		builder(ShrineUpgrades.STRENGTH).predicate(STRENGTH_PREDICATE).addBonus(BonusType.STRENGTH, 2F).addBonus(BonusType.ELEMENT_CONSUMPTION, 1.2F).incompatibleWith(ShrineUpgrades.NECTAR, ShrineUpgrades.PICKUP);
		builder(ShrineUpgrades.OPTIMIZATION).match(ECTags.Blocks.SHRINES).max(2).addBonus(BonusType.CAPACITY, 1.25F).addBonus(BonusType.ELEMENT_CONSUMPTION, 0.75F).addBonus(BonusType.SPEED, 0.8F);
	}

	private IBlockPosPredicate miningPredicate(TagKey<Block> tag) {
		return IBlockPosPredicate.match(tag)
				.or(IBlockPosPredicate.match(ECBlocks.BUDDING_SHRINE.get()).and(new HasShrineUpgradePredicate(ShrineUpgrades.CRYSTAL_HARVEST)));
	}
	
	protected ShrineUpgrade.Builder builder(ResourceKey<ShrineUpgrade> key) {
		var builder = ShrineUpgrade.Builder.create();
		
		add(key, builder);
		return builder;
	}
	
	@Nonnull
    @Override
	public String getName() {
		return "ElementalCraft Shrines Upgrades";
	}
}
