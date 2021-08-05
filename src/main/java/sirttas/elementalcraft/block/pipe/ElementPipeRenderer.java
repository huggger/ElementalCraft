package sirttas.elementalcraft.block.pipe;

import java.util.stream.Stream;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.ModelDataManager;
import sirttas.elementalcraft.ElementalCraft;
import sirttas.elementalcraft.block.entity.renderer.IECRenderer;
import sirttas.elementalcraft.block.pipe.ElementPipeBlock.CoverType;
import sirttas.elementalcraft.block.pipe.IElementPipe.ConnectionType;

public class ElementPipeRenderer implements IECRenderer<ElementPipeBlockEntity> {

	public static final ResourceLocation SIDE_LOCATION = ElementalCraft.createRL("block/elementpipe_side");
	public static final ResourceLocation EXTRACT_LOCATION = ElementalCraft.createRL("block/elementpipe_extract");
	public static final ResourceLocation PRIORITY_LOCATION = ElementalCraft.createRL("block/elementpipe_priority");
	
	private static final AABB BOX = new AABB(0, 0, 0, 1, 1, 1);
	
	private BakedModel sideModel;
	private BakedModel extractModel;
	private BakedModel prioritytModel;
	
	public ElementPipeRenderer(Context context) {}

	@Override
	public void render(ElementPipeBlockEntity te, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
		BlockState coverState = te.getCoverState();

		if (sideModel == null || extractModel == null || prioritytModel == null) {
			ModelManager modelManager = Minecraft.getInstance().getModelManager();
			
			sideModel = modelManager.getModel(SIDE_LOCATION);
			extractModel = modelManager.getModel(EXTRACT_LOCATION);
			prioritytModel = modelManager.getModel(PRIORITY_LOCATION);
		}
		if (coverState != null && ElementPipeBlock.showCover(te.getBlockState(), Minecraft.getInstance().player)) {
			renderBlock(coverState, matrixStack, buffer, combinedLightIn, combinedOverlayIn, ModelDataManager.getModelData(te.getLevel(), te.getBlockPos()));
		} else {
			renderPipes(te, matrixStack, buffer, combinedLightIn, combinedOverlayIn);
			if (coverState != null) {
				renderBlock(te.getBlockState().setValue(ElementPipeBlock.COVER, CoverType.NONE), matrixStack, buffer, combinedLightIn, combinedOverlayIn,
						ModelDataManager.getModelData(te.getLevel(), te.getBlockPos()));
				LevelRenderer.renderLineBox(matrixStack, buffer.getBuffer(RenderType.lines()), BOX, 0F, 0F, 0F, 1);
			}
		}
	}
	
	private void renderPipes(ElementPipeBlockEntity te, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
		matrixStack.pushPose();
		matrixStack.translate(0.5, 0.5, 0.5);
		Stream.of(Direction.values()).forEach(d -> renderSide(te, d, matrixStack, buffer, light, overlay));
		matrixStack.popPose();
	}
	
	private void renderSide(ElementPipeBlockEntity te, Direction side, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
		ConnectionType connection = te.getConection(side);
		
		if (connection.isConnected()) {
			matrixStack.pushPose();
			matrixStack.mulPose(side.getRotation());
			matrixStack.translate(-0.5, -0.5, -0.5);
			this.renderModel(sideModel, matrixStack, buffer, te, light, overlay);
			if (connection == ConnectionType.EXTRACT) {
				this.renderModel(extractModel, matrixStack, buffer, te, light, overlay);
			}
			if (te.isPriority(side)) {
				this.renderModel(prioritytModel, matrixStack, buffer, te, light, overlay);
			}
			matrixStack.popPose();
		}
	}
}