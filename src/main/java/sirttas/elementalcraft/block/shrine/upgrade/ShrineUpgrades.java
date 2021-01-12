package sirttas.elementalcraft.block.shrine.upgrade;

import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import sirttas.dpanvil.api.annotation.DataHolder;
import sirttas.dpanvil.api.event.DataManagerReloadEvent;
import sirttas.elementalcraft.ElementalCraft;
import sirttas.elementalcraft.block.shrine.upgrade.horizontal.BlockFortuneShrineUpgrade;
import sirttas.elementalcraft.block.shrine.upgrade.horizontal.BlockNectarShrineUpgrade;
import sirttas.elementalcraft.block.shrine.upgrade.horizontal.BlockSilkTouchShrineUpgrade;
import sirttas.elementalcraft.block.shrine.upgrade.unidirectional.BlockBonelessGrowthShrineUpgrade;
import sirttas.elementalcraft.block.shrine.upgrade.unidirectional.BlockPickupShrineUpgrade;
import sirttas.elementalcraft.block.shrine.upgrade.unidirectional.BlockPlantingShrineUpgrade;

@Mod.EventBusSubscriber(modid = ElementalCraft.MODID)
public class ShrineUpgrades {

	public static final String NAME = "shrine_upgrades";
	public static final String FOLDER = ElementalCraft.MODID + '_' + NAME;

	@DataHolder(ElementalCraft.MODID + ":" + BlockFortuneShrineUpgrade.NAME) public static ShrineUpgrade fortune;
	@DataHolder(ElementalCraft.MODID + ":" + BlockSilkTouchShrineUpgrade.NAME) public static ShrineUpgrade silkTouch;
	@DataHolder(ElementalCraft.MODID + ":" + BlockPlantingShrineUpgrade.NAME) public static ShrineUpgrade planting;
	@DataHolder(ElementalCraft.MODID + ":" + BlockBonelessGrowthShrineUpgrade.NAME) public static ShrineUpgrade bonelessGrowth;
	@DataHolder(ElementalCraft.MODID + ":" + BlockPickupShrineUpgrade.NAME) public static ShrineUpgrade pickup;
	@DataHolder(ElementalCraft.MODID + ":" + BlockNectarShrineUpgrade.NAME) public static ShrineUpgrade nectar;

	@SubscribeEvent
	public static void onReload(DataManagerReloadEvent<ShrineUpgrade> event) {
		event.getDataManager().getData().forEach((id, upgrade) -> {
			upgrade.setId(id);
			Block block = ForgeRegistries.BLOCKS.getValue(id);

			if (block instanceof BlockShrineUpgrade) {
				((BlockShrineUpgrade) block).setUpgrade(upgrade);
			}
		});
	}

}