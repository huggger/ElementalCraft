package sirttas.elementalcraft.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import sirttas.elementalcraft.tag.ECTags;

public class ECItemTagsProvider extends ItemTagsProvider {

	public ECItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider) {
		super(generatorIn, blockTagsProvider);
	}

	@Override
	protected void registerTags() {
		this.func_240521_a_/* copy */(BlockTags.SLABS, ItemTags.SLABS);
		this.func_240521_a_/* copy */(BlockTags.STAIRS, ItemTags.STAIRS);
		this.func_240521_a_/* copy */(BlockTags.WALLS, ItemTags.WALLS);
		this.func_240521_a_/* copy */(BlockTags.FENCES, ItemTags.FENCES);
		this.func_240521_a_/* copy */(Tags.Blocks.ORES, Tags.Items.ORES);

		this.func_240521_a_/* copy */(ECTags.Blocks.PUREROCKS, ECTags.Items.PUREROCKS);

		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_SWORDS).func_240534_a_/* add */(Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.field_234754_kI_/* NETHERITE_SWORD */);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_PICKAXES).func_240534_a_/* add */(Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE,
				Items.field_234756_kK_/* NETHERITE_PICKAXE */);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_AXES).func_240534_a_/* add */(Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.field_234757_kL_/* NETHERITE_AXE */);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_SHOVELS);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_HOES);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_SHILDS);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_BOWS).func_240534_a_/* add */(Items.BOW);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_CROSSBOWS).func_240534_a_/* add */(Items.CROSSBOW);

		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_HELMETS).func_240534_a_/* add */(Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET,
				Items.field_234763_ls_/* NETHERITE_HELMET */);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_CHESTPLATES).func_240534_a_/* add */(Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE,
				Items.field_234764_lt_/* NETHERITE_CHESTPLATE */);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_LEGGINGS).func_240534_a_/* add */(Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS,
				Items.field_234765_lu_/* NETHERITE_LEGGINGS */);
		func_240522_a_/* getBuilder */(ECTags.Items.INFUSABLE_BOOTS).func_240534_a_/* add */(Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS, Items.field_234766_lv_/* NETHERITE_BOOTS */);
	}
}
