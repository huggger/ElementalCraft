package sirttas.elementalcraft.network.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sirttas.elementalcraft.block.instrument.mill.RendererAirMill;
import sirttas.elementalcraft.block.solarsynthesizer.RendererSolarSynthesizer;
import sirttas.elementalcraft.block.tile.renderer.ECRenderers;
import sirttas.elementalcraft.entity.ECEntities;
import sirttas.elementalcraft.inventory.container.screen.ECScreens;
import sirttas.elementalcraft.item.ECItems;
import sirttas.elementalcraft.particle.ECParticles;
import sirttas.elementalcraft.rune.Runes;
import sirttas.elementalcraft.spell.SpellTickManager;

@SuppressWarnings("resource")
public class ClientProxy implements IProxy {

	private final Minecraft minecraft;
	
	public ClientProxy() {
		minecraft = Minecraft.getInstance();
	}
	
	@Override
	public void registerHandlers() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		modBus.addListener(this::setupClient);
		modBus.addListener(ECItems::replaceModels);
		modBus.addListener(this::registerModels);
		modBus.addListener(this::stitchTextures);
		modBus.addListener(ECItems::registerItemColors);
		modBus.addListener(ECParticles::registerFactories);

		MinecraftForge.EVENT_BUS.addListener(SpellTickManager::clientTick);
	}


	@Override
	public World getDefaultWorld() {
		return minecraft.world;
	}
	
	@Override
	public PlayerEntity getDefaultPlayer() {
		return minecraft.player;
	}
	
	private void setupClient(FMLClientSetupEvent event) {
		ECRenderers.initRenderLayouts();
		ECEntities.registerRenderers();
		ECScreens.initScreenFactories();
	}

	public void registerModels(ModelRegistryEvent event) {
		Runes.registerModels();
		ModelLoader.addSpecialModel(RendererSolarSynthesizer.LENSE_LOCATION);
		ModelLoader.addSpecialModel(RendererAirMill.BLADES_LOCATION);
	}
	
	public void stitchTextures(TextureStitchEvent.Pre event) {
		addSprite(event, RendererSolarSynthesizer.BEAM);
	}
	
	private void addSprite(TextureStitchEvent.Pre event, RenderMaterial sprite) {
		if (event.getMap().getTextureLocation().equals(sprite.getAtlasLocation())) {
			event.addSprite(sprite.getTextureLocation());
		}
	}
}

