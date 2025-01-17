package sirttas.elementalcraft.api.rune.handler;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import sirttas.elementalcraft.api.ElementalCraftCapabilities;

import javax.annotation.Nonnull;

public class RuneHandlerHelper {

	private RuneHandlerHelper() {}

	@Nonnull
	public static LazyOptional<IRuneHandler> get(ICapabilityProvider provider) {
		return get(provider, null);
	}
	
	@Nonnull
	public static LazyOptional<IRuneHandler> get(ICapabilityProvider provider, Direction side) {
		return ElementalCraftCapabilities.RUNE_HANDLE != null ? provider.getCapability(ElementalCraftCapabilities.RUNE_HANDLE, side) : LazyOptional.empty();
	}
}
