package sirttas.elementalcraft.datagen.tag;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import mekanism.tools.common.MekanismTools;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Wearable;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.gear.SilentGear;
import sirttas.elementalcraft.api.ElementalCraftApi;
import sirttas.elementalcraft.item.ECItems;
import sirttas.elementalcraft.item.spell.AbstractSpellHolderItem;
import sirttas.elementalcraft.tag.ECTags;
import vazkii.botania.api.BotaniaAPI;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ECItemTagsProvider extends ItemTagsProvider {

	private static final String MINECRAFT = "minecraft";
	private static final List<String> MOD_IDS = List.of(BotaniaAPI.MODID, MekanismTools.MODID, SilentGear.MOD_ID);

	public ECItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generatorIn, blockTagsProvider, ElementalCraftApi.MODID, existingFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags() {
		copy(BlockTags.SLABS, ItemTags.SLABS);
		copy(BlockTags.STAIRS, ItemTags.STAIRS);
		copy(BlockTags.WALLS, ItemTags.WALLS);
		copy(BlockTags.FENCES, ItemTags.FENCES);
		copy(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES);
		copy(ECTags.Blocks.ORES_INERT_CRYSTAL, ECTags.Items.ORES_INERT_CRYSTAL);
		copy(Tags.Blocks.ORES, Tags.Items.ORES);

		copy(ECTags.Blocks.PUREROCKS, ECTags.Items.PUREROCKS);
		copy(ECTags.Blocks.PIPES, ECTags.Items.PIPES);
		copy(ECTags.Blocks.SHRINES, ECTags.Items.SHRINES);
		copy(ECTags.Blocks.SHRINE_UPGRADES, ECTags.Items.SHRINE_UPGRADES);
		copy(ECTags.Blocks.SMALL_CONTAINER_COMPATIBLES, ECTags.Items.SMALL_CONTAINER_COMPATIBLES);
		copy(ECTags.Blocks.INSTRUMENTS, ECTags.Items.INSTRUMENTS);
		copy(ECTags.Blocks.CONTAINER_TOOLS, ECTags.Items.CONTAINER_TOOLS);

		copy(ECTags.Blocks.STORAGE_BLOCKS_DRENCHED_IRON, ECTags.Items.STORAGE_BLOCKS_DRENCHED_IRON);
		copy(ECTags.Blocks.STORAGE_BLOCKS_SWIFT_ALLOY, ECTags.Items.STORAGE_BLOCKS_SWIFT_ALLOY);
		copy(ECTags.Blocks.STORAGE_BLOCKS_FIREITE, ECTags.Items.STORAGE_BLOCKS_FIREITE);
		copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

		tag(ECTags.Items.FORGE_SWORDS);
		tag(ECTags.Items.FORGE_PICKAXES);
		tag(ECTags.Items.FORGE_AXES);
		tag(ECTags.Items.FORGE_SHOVELS);
		tag(ECTags.Items.FORGE_HOES);
		tag(ECTags.Items.FORGE_SHILDS);
		tag(ECTags.Items.FORGE_BOWS);
		tag(ECTags.Items.FORGE_CROSSBOWS);
		tag(ECTags.Items.FORGE_HELMETS);
		tag(ECTags.Items.FORGE_CHESTPLATES);
		tag(ECTags.Items.FORGE_LEGGINGS);
		tag(ECTags.Items.FORGE_BOOTS);

		tag(ECTags.Items.RECEPTACLES_EMPTY).add(ECItems.EMPTY_RECEPTACLE, ECItems.EMPTY_RECEPTACLE_IMPROVED);
		tag(ECTags.Items.RECEPTACLES_FULL).add(ECItems.RECEPTACLE, ECItems.RECEPTACLE_IMPROVED);
		tag(ECTags.Items.RECEPTACLES_IMPROVED).add(ECItems.EMPTY_RECEPTACLE_IMPROVED, ECItems.RECEPTACLE_IMPROVED);
		tag(ECTags.Items.RECEPTACLES).addTags(ECTags.Items.RECEPTACLES_EMPTY, ECTags.Items.RECEPTACLES_FULL);

		tag(ECTags.Items.SPELL_CAST_TOOLS).add(ECItems.FOCUS, ECItems.STAFF);
		
		tag(ECTags.Items.INFUSABLE_FOCUS).add(ECItems.FOCUS);
		tag(ECTags.Items.INFUSABLE_STAVES).add(ECItems.STAFF);
		addOptionals(tag(ECTags.Items.INFUSABLE_SWORDS).add(getItems(MINECRAFT, SwordItem.class)).addTag(ECTags.Items.FORGE_SWORDS), getItems(MOD_IDS, SwordItem.class));
		addOptionals(tag(ECTags.Items.INFUSABLE_PICKAXES).add(getItems(MINECRAFT, PickaxeItem.class)).addTag(ECTags.Items.FORGE_PICKAXES), getItems(MOD_IDS, PickaxeItem.class));
		addOptionals(tag(ECTags.Items.INFUSABLE_SHOVELS).add(getItems(MINECRAFT, ShovelItem.class)).addTag(ECTags.Items.FORGE_SHOVELS), getItems(MOD_IDS, ShovelItem.class));
		addOptionals(tag(ECTags.Items.INFUSABLE_HOES).add(getItems(MINECRAFT, HoeItem.class)).addTag(ECTags.Items.FORGE_HOES), getItems(MOD_IDS, HoeItem.class));
		addOptionals(tag(ECTags.Items.INFUSABLE_AXES).add(getItems(MINECRAFT, AxeItem.class)).addTag(ECTags.Items.FORGE_AXES), getItems(MOD_IDS, AxeItem.class));
		tag(ECTags.Items.INFUSABLE_SHILDS);
		addOptionals(tag(ECTags.Items.INFUSABLE_BOWS).add(getItems(MINECRAFT, BowItem.class)).addTag(ECTags.Items.FORGE_BOWS), getItems(MOD_IDS, BowItem.class));
		addOptionals(tag(ECTags.Items.INFUSABLE_CROSSBOWS).add(getItems(MINECRAFT, CrossbowItem.class)).addTag(ECTags.Items.FORGE_CROSSBOWS), getItems(MOD_IDS, CrossbowItem.class));
		tag(ECTags.Items.INFUSABLE_FISHING_RODS).add(Items.FISHING_ROD);
		tag(ECTags.Items.INFUSABLE_TRIDENTS).add(Items.TRIDENT);

		addOptionals(tag(ECTags.Items.INFUSABLE_HELMETS).add(getItems(MINECRAFT, ArmorItem.class, forSlot(EquipmentSlot.HEAD))).addTag(ECTags.Items.FORGE_HELMETS), getItems(MOD_IDS, ArmorItem.class, forSlot(EquipmentSlot.HEAD)));
		addOptionals(tag(ECTags.Items.INFUSABLE_CHESTPLATES).add(getItems(MINECRAFT, ArmorItem.class, forSlot(EquipmentSlot.CHEST))).addTag(ECTags.Items.FORGE_CHESTPLATES), getItems(MOD_IDS, ArmorItem.class, forSlot(EquipmentSlot.CHEST)));
		addOptionals(tag(ECTags.Items.INFUSABLE_LEGGINGS).add(getItems(MINECRAFT, ArmorItem.class, forSlot(EquipmentSlot.LEGS))).addTag(ECTags.Items.FORGE_LEGGINGS), getItems(MOD_IDS, ArmorItem.class, forSlot(EquipmentSlot.LEGS)));
		addOptionals(tag(ECTags.Items.INFUSABLE_BOOTS).add(getItems(MINECRAFT, ArmorItem.class, forSlot(EquipmentSlot.FEET))).addTag(ECTags.Items.FORGE_BOOTS), getItems(MOD_IDS, ArmorItem.class, forSlot(EquipmentSlot.FEET)));

		tag(ECTags.Items.SPELL_HOLDERS).add(getItems(AbstractSpellHolderItem.class));
		tag(ECTags.Items.ELEMENTAL_CRYSTALS).add(ECItems.FIRE_CRYSTAL, ECItems.WATER_CRYSTAL, ECItems.EARTH_CRYSTAL, ECItems.AIR_CRYSTAL);
		tag(ECTags.Items.CRYSTALS).add(ECItems.INERT_CRYSTAL, ECItems.CONTAINED_CRYSTAL, ECItems.PURE_CRYSTAL).addTag(ECTags.Items.ELEMENTAL_CRYSTALS);
		tag(ECTags.Items.LENSES).add(ECItems.FIRE_LENSE, ECItems.WATER_LENSE, ECItems.EARTH_LENSE, ECItems.AIR_LENSE);
		
		tag(ECTags.Items.DEFAULT_SHARDS).add(ECItems.FIRE_SHARD, ECItems.WATER_SHARD, ECItems.EARTH_SHARD, ECItems.AIR_SHARD);
		tag(ECTags.Items.POWERFUL_SHARDS).add(ECItems.POWERFUL_FIRE_SHARD, ECItems.POWERFUL_WATER_SHARD, ECItems.POWERFUL_EARTH_SHARD, ECItems.POWERFUL_AIR_SHARD);
		tag(ECTags.Items.FIRE_SHARDS).add(ECItems.FIRE_SHARD, ECItems.POWERFUL_FIRE_SHARD);
		tag(ECTags.Items.WATER_SHARDS).add(ECItems.WATER_SHARD, ECItems.POWERFUL_WATER_SHARD);
		tag(ECTags.Items.EARTH_SHARDS).add(ECItems.EARTH_SHARD, ECItems.POWERFUL_EARTH_SHARD);
		tag(ECTags.Items.AIR_SHARDS).add(ECItems.AIR_SHARD, ECItems.POWERFUL_AIR_SHARD);
		tag(ECTags.Items.SHARDS).addTag(ECTags.Items.DEFAULT_SHARDS).addTag(ECTags.Items.POWERFUL_SHARDS);

		tag(ECTags.Items.INGOTS_DRENCHED_IRON).add(ECItems.DRENCHED_IRON_INGOT);
		tag(ECTags.Items.INGOTS_SWIFT_ALLOY).add(ECItems.SWIFT_ALLOY_INGOT);
		tag(ECTags.Items.INGOTS_FIREITE).add(ECItems.FIREITE_INGOT);
		tag(Tags.Items.INGOTS).addTags(ECTags.Items.INGOTS_DRENCHED_IRON, ECTags.Items.INGOTS_SWIFT_ALLOY, ECTags.Items.INGOTS_FIREITE);

		tag(ECTags.Items.NUGGETS_DRENCHED_IRON).add(ECItems.DRENCHED_IRON_NUGGET);
		tag(ECTags.Items.NUGGETS_SWIFT_ALLOY).add(ECItems.SWIFT_ALLOY_NUGGET);
		tag(ECTags.Items.NUGGETS_FIREITE).add(ECItems.FIREITE_NUGGET);
		tag(Tags.Items.NUGGETS).addTags(ECTags.Items.NUGGETS_DRENCHED_IRON, ECTags.Items.NUGGETS_SWIFT_ALLOY, ECTags.Items.NUGGETS_FIREITE);

		tag(ECTags.Items.PRISTINE_FIRE_GEMS).add(ECItems.PRISTINE_FIRE_GEM);
		tag(ECTags.Items.FINE_FIRE_GEMS).add(ECItems.FINE_FIRE_GEM, ECItems.PRISTINE_FIRE_GEM);
		tag(ECTags.Items.CRUDE_FIRE_GEMS).add(ECItems.CRUDE_FIRE_GEM, ECItems.FINE_FIRE_GEM, ECItems.PRISTINE_FIRE_GEM);
		tag(ECTags.Items.INPUT_FIRE_GEMS).add(ECItems.CRUDE_FIRE_GEM, ECItems.FINE_FIRE_GEM, ECItems.PRISTINE_FIRE_GEM).addTag(Tags.Items.GEMS_DIAMOND);
		tag(ECTags.Items.PRISTINE_WATER_GEMS).add(ECItems.PRISTINE_WATER_GEM);
		tag(ECTags.Items.FINE_WATER_GEMS).add(ECItems.FINE_WATER_GEM, ECItems.PRISTINE_WATER_GEM);
		tag(ECTags.Items.CRUDE_WATER_GEMS).add(ECItems.CRUDE_WATER_GEM, ECItems.FINE_WATER_GEM, ECItems.PRISTINE_WATER_GEM);
		tag(ECTags.Items.INPUT_WATER_GEMS).add(ECItems.CRUDE_WATER_GEM, ECItems.FINE_WATER_GEM, ECItems.PRISTINE_WATER_GEM).addTag(Tags.Items.GEMS_DIAMOND);
		tag(ECTags.Items.PRISTINE_EARTH_GEMS).add(ECItems.PRISTINE_EARTH_GEM);
		tag(ECTags.Items.FINE_EARTH_GEMS).add(ECItems.FINE_EARTH_GEM, ECItems.PRISTINE_EARTH_GEM);
		tag(ECTags.Items.CRUDE_EARTH_GEMS).add(ECItems.CRUDE_EARTH_GEM, ECItems.FINE_EARTH_GEM, ECItems.PRISTINE_EARTH_GEM);
		tag(ECTags.Items.INPUT_EARTH_GEMS).add(ECItems.CRUDE_EARTH_GEM, ECItems.FINE_EARTH_GEM, ECItems.PRISTINE_EARTH_GEM).addTag(Tags.Items.GEMS_DIAMOND);
		tag(ECTags.Items.PRISTINE_AIR_GEMS).add(ECItems.PRISTINE_AIR_GEM);
		tag(ECTags.Items.FINE_AIR_GEMS).add(ECItems.FINE_AIR_GEM, ECItems.PRISTINE_AIR_GEM);
		tag(ECTags.Items.CRUDE_AIR_GEMS).add(ECItems.CRUDE_AIR_GEM, ECItems.FINE_AIR_GEM, ECItems.PRISTINE_AIR_GEM);
		tag(ECTags.Items.INPUT_AIR_GEMS).add(ECItems.CRUDE_AIR_GEM, ECItems.FINE_AIR_GEM, ECItems.PRISTINE_AIR_GEM).addTag(Tags.Items.GEMS_DIAMOND);
		tag(ECTags.Items.INPUT_GEMS).addTags(ECTags.Items.INPUT_FIRE_GEMS, ECTags.Items.INPUT_WATER_GEMS, ECTags.Items.INPUT_EARTH_GEMS, ECTags.Items.INPUT_AIR_GEMS);
		tag(Tags.Items.GEMS).addTags(ECTags.Items.INPUT_GEMS);

		tag(ECTags.Items.RUNE_SLATES).add(ECItems.MINOR_RUNE_SLATE, ECItems.RUNE_SLATE, ECItems.MAJOR_RUNE_SLATE);
		
		tag(ECTags.Items.PIPE_COVER_HIDING).addTag(ECTags.Items.PIPES).add(ECItems.COVER_FRAME, ECItems.PIPE_PRIORITY);
		
		tag(ECTags.Items.STAFF_CRAFT_SWORD).add(Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
		
		tag(ECTags.Items.PURE_ORES_ORE_SOURCE).addTag(Tags.Items.ORES);
		tag(ECTags.Items.PURE_ORES_RAW_METAL_SOURCE).addTag(Tags.Items.RAW_MATERIALS);
		tag(ECTags.Items.PURE_ORES_MOD_PROCESSING_BLACKLIST);
		
		tag(ECTags.Items.GROVE_SHRINE_FLOWERS).addTag(ItemTags.FLOWERS);
		tag(ECTags.Items.GROVE_SHRINE_BLACKLIST).addOptionalTag(new ResourceLocation(BotaniaAPI.MODID, "double_mystical_flowers"))
				.addOptionalTag(new ResourceLocation(BotaniaAPI.MODID, "mystical_flowers")).addOptionalTag(new ResourceLocation(BotaniaAPI.MODID, "special_flowers"))
				.addOptionalTag(new ResourceLocation(BotaniaAPI.MODID, "floating_flowers"));
		tag(ECTags.Items.MYSTICAL_GROVE_FLOWERS).addOptionalTag(new ResourceLocation(BotaniaAPI.MODID, "double_mystical_flowers"))
				.addOptionalTag(new ResourceLocation(BotaniaAPI.MODID, "mystical_flowers"));

		tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ECItems.DRENCHED_IRON_INGOT, ECItems.SWIFT_ALLOY_INGOT, ECItems.FIREITE_INGOT);

		var jewelSocketables = tag(ECTags.Items.JEWEL_SOCKETABLES).addTags(ECTags.Items.FORGE_SWORDS, ECTags.Items.FORGE_SHILDS, ECTags.Items.SPELL_CAST_TOOLS)
				.addTags(ECTags.Items.FORGE_PICKAXES, ECTags.Items.FORGE_AXES, ECTags.Items.FORGE_SHOVELS, ECTags.Items.FORGE_HOES)
				.addTags(ECTags.Items.FORGE_BOWS, ECTags.Items.FORGE_CROSSBOWS)
				.addTags(ECTags.Items.FORGE_HELMETS, ECTags.Items.FORGE_CHESTPLATES, ECTags.Items.FORGE_LEGGINGS, ECTags.Items.FORGE_BOOTS)
				.add(Items.FISHING_ROD, Items.TRIDENT)
				.add(getItems(MINECRAFT, Wearable.class))
				.add(getItems(MINECRAFT, TieredItem.class))
				.add(getItems(MINECRAFT, ProjectileWeaponItem.class));

		addOptionals(jewelSocketables, getItems(MOD_IDS, Wearable.class));
		addOptionals(jewelSocketables, getItems(MOD_IDS, TieredItem.class));
		addOptionals(jewelSocketables, getItems(MOD_IDS, ProjectileWeaponItem.class));
	}

	public TagsProvider.TagAppender<Item> addOptionals(TagsProvider.TagAppender<Item> builder, Item[]... optionals) {
		for (var opt : optionals) {
			addOptionals(builder, opt);
		}
		return builder;
	}

	public TagsProvider.TagAppender<Item> addOptionals(TagsProvider.TagAppender<Item> builder, Item... optionals) {
		Stream.of(optionals).map(Item::getRegistryName).forEach(builder::addOptional);
		return builder;
	}

	protected <T> Item[] getItems(List<String> modIds, Class<T> clazz, Predicate<T> filter) {
		return registry.stream()
				.filter(i -> modIds.contains(i.getRegistryName().getNamespace()) && clazz.isInstance(i))
				.map(clazz::cast)
				.filter(filter)
				.map(Item.class::cast)
				.sorted(Comparator.comparing(Item::getRegistryName))
				.toArray(Item[]::new);
	}

	protected <T> Item[] getItems(String modId, Class<T> clazz, Predicate<T> filter) {
		return getItems(Lists.newArrayList(modId), clazz, filter);
	}

	protected <T> Item[] getItems(List<String> modIds, Class<T> clazz) {
		return getItems(modIds, clazz, Predicates.alwaysTrue());
	}

	protected <T> Item[] getItems(String modId, Class<T> clazz) {
		return getItems(modId, clazz, Predicates.alwaysTrue());
	}

	protected <T> Item[] getItems(Class<T> clazz) {
		return getItems(ElementalCraftApi.MODID, clazz);
	}

	private Predicate<ArmorItem> forSlot(EquipmentSlot slot) {
		return item -> item.getSlot() == slot;
	}
}
