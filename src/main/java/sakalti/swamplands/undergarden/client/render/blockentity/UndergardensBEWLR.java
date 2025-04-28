package sakalti.swamplands.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import sakalti.swamplands.block.GrongletBlock;
import sakalti.swamplands.block.entity.DepthrockBedBlockEntity;
import sakalti.swamplands.block.entity.GrongletBlockEntity;
import sakalti.swamplands.registry.UGBlocks;

public class UndergardensBEWLR extends BlockEntityWithoutLevelRenderer {

	public UndergardensBEWLR() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		Item item = stack.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem) item).getBlock();
			if (block instanceof GrongletBlock) {
				Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(new GrongletBlockEntity(BlockPos.ZERO, UGBlocks.GRONGLET.get().defaultBlockState()), ms, buffer, light, overlay);
			} else {
				Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(new DepthrockBedBlockEntity(BlockPos.ZERO, UGBlocks.DEPTHROCK_BED.get().defaultBlockState()), ms, buffer, light, overlay);
			}
		}
	}
}
