package sirttas.elementalcraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sirttas.dpanvil.api.data.IDataManager;
import sirttas.dpanvil.api.imc.DataManagerIMC;
import sirttas.dpanvil.api.imc.DataTagIMC;
import sirttas.elementalcraft.api.ElementalCraftApi;
import sirttas.elementalcraft.api.element.storage.IElementStorage;
import sirttas.elementalcraft.api.infusion.tool.ToolInfusion;
import sirttas.elementalcraft.api.rune.Rune;
import sirttas.elementalcraft.api.rune.handler.IRuneHandler;
import sirttas.elementalcraft.block.shrine.upgrade.ShrineUpgrade;
import sirttas.elementalcraft.block.shrine.upgrade.ShrineUpgrades;
import sirttas.elementalcraft.config.ECConfig;
import sirttas.elementalcraft.item.pureore.PureOreManager;
import sirttas.elementalcraft.loot.function.ECLootFunctions;
import sirttas.elementalcraft.network.message.MessageHandler;
import sirttas.elementalcraft.spell.properties.SpellProperties;
import sirttas.elementalcraft.world.feature.ECFeatures;

@Mod(ElementalCraftApi.MODID)
public class ElementalCraft {
	
	public static final PureOreManager PURE_ORE_MANAGER = new PureOreManager();
	public static final IDataManager<ShrineUpgrade> SHRINE_UPGRADE_MANAGER = IDataManager.builder(ShrineUpgrade.class, ShrineUpgrades.FOLDER).withIdSetter(ShrineUpgrade::setId).merged(ShrineUpgrade::merge).build();
	public static final IDataManager<SpellProperties> SPELL_PROPERTIES_MANAGER = IDataManager.builder(SpellProperties.class, SpellProperties.FOLDER).withDefault(SpellProperties.NONE).build();

	
	public ElementalCraft() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ECFeatures::onBiomeLoad);
		MinecraftForge.EVENT_BUS.addListener(PURE_ORE_MANAGER::reload);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ECConfig.COMMON_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ECConfig.CLIENT_SPEC);
	}

	public static ResourceLocation createRL(String name) {
		if (name.contains(":")) {
			return new ResourceLocation(name);
		}
		return new ResourceLocation(ElementalCraftApi.MODID, name);
	}

	private void setup(FMLCommonSetupEvent event) {
		MessageHandler.setup();
		ECLootFunctions.setup();
		CapabilityManager.INSTANCE.register(IElementStorage.class);
		CapabilityManager.INSTANCE.register(IRuneHandler.class);
	}


	private void enqueueIMC(InterModEnqueueEvent event) {
		DataManagerIMC.enqueue(() -> new DataManagerIMC<>(createRL(ShrineUpgrades.NAME), SHRINE_UPGRADE_MANAGER).withCodec(ShrineUpgrade.CODEC));
		DataManagerIMC.enqueue(() -> new DataManagerIMC<>(createRL(SpellProperties.NAME), SPELL_PROPERTIES_MANAGER).withCodec(SpellProperties.CODEC));
		DataManagerIMC.enqueue(() -> new DataManagerIMC<>(createRL(Rune.NAME), ElementalCraftApi.RUNE_MANAGER).withCodec(Rune.CODEC));
		DataManagerIMC.enqueue(() -> new DataManagerIMC<>(createRL(ToolInfusion.NAME), ElementalCraftApi.TOOL_INFUSION_MANAGER).withCodec(ToolInfusion.CODEC));
		DataTagIMC.enqueue(() -> new DataTagIMC<>(ElementalCraftApi.RUNE_MANAGER, ElementalCraftApi.RUNE_TAGS));
	}
}

