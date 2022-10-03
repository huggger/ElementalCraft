package sirttas.elementalcraft.block.instrument.io.firefurnace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sirttas.elementalcraft.block.entity.renderer.IECRenderer;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class FireFurnaceRenderer implements IECRenderer<AbstractFireFurnaceBlockEntity<?>> {

	@Override
	public void render(AbstractFireFurnaceBlockEntity<?> te, float partialTicks, PoseStack poseStack, @Nonnull MultiBufferSource buffer, int light, int overlay) {
		Container inv = te.getInventory();
		ItemStack stack = inv.getItem(0);
		ItemStack stack2 = inv.getItem(1);
		float tick = getClientTicks(partialTicks);
		
		renderRunes(poseStack, buffer, te.getRuneHandler(), tick, light, overlay);
		poseStack.translate(0.5F, 0.3F, 0.5F);
		if (!stack.isEmpty() || !stack2.isEmpty()) {
			poseStack.mulPose(Vector3f.YP.rotationDegrees(tick));
			if (!stack.isEmpty()) {
				renderItem(stack, poseStack, buffer, light, overlay);
			}
			if (!stack2.isEmpty()) {
				poseStack.translate(0, 0.5F, 0);
				renderItem(stack2, poseStack, buffer, light, overlay);
			}
		}
	}
}
