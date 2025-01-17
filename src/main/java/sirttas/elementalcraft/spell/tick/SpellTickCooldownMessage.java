package sirttas.elementalcraft.spell.tick;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import sirttas.elementalcraft.spell.Spell;
import sirttas.elementalcraft.spell.Spells;

import java.util.function.Supplier;

public class SpellTickCooldownMessage {

	private final Spell spell;

	public SpellTickCooldownMessage(Spell spell) {
		this.spell = spell;
	}

	// message handling

	public static SpellTickCooldownMessage decode(FriendlyByteBuf buf) {
		return new SpellTickCooldownMessage(Spells.REGISTRY.get().getValue(buf.readResourceLocation()));
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeResourceLocation(spell.getKey());
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (ctx.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
				startCooldown();
			}
		});
		ctx.get().setPacketHandled(true);
	}

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	private void startCooldown() {
		SpellTickHelper.startCooldown(Minecraft.getInstance().player, this.spell);
	}

}
