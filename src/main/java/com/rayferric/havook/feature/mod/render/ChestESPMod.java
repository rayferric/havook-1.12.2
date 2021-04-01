package com.rayferric.havook.feature.mod.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.util.EntityUtil;
import com.rayferric.havook.util.RenderUtil;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ChestESPMod extends Mod {
	private transient List<TileEntity> TILE_ENTITIES = new ArrayList<TileEntity>();
	private transient List<Entity> ENTITIES = new ArrayList<Entity>();
	private transient List<AxisAlignedBB> TILE_BOXES = new ArrayList<AxisAlignedBB>();

	public ChestESPMod() {
		super("chestesp", "Chest ESP", "Highlights nearby chests.", ModCategoryEnum.RENDER);
	}

	@Override
	public void onLocalPlayerUpdate() {
		TILE_BOXES.clear();
		TILE_ENTITIES.clear();
		// TILE ENTITIES
		for (TileEntity tileEntity : Minecraft.getMinecraft().world.loadedTileEntityList) {
			AxisAlignedBB bb;
			if (tileEntity instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest) tileEntity;

				if (chest.adjacentChestXPos != null || chest.adjacentChestZPos != null)
					continue;

				bb = Minecraft.getMinecraft().world.getBlockState(chest.getPos())
						.getBoundingBox(Minecraft.getMinecraft().world, chest.getPos()).offset(chest.getPos());
				if (bb == null)
					continue;

				if (chest.adjacentChestXNeg != null) {
					BlockPos pos = chest.adjacentChestXNeg.getPos();
					AxisAlignedBB newBb = Minecraft.getMinecraft().world.getBlockState(pos)
							.getBoundingBox(Minecraft.getMinecraft().world, pos).offset(pos);
					bb = bb.union(newBb);

				} else if (chest.adjacentChestZNeg != null) {
					BlockPos pos = chest.adjacentChestZNeg.getPos();
					AxisAlignedBB newBb = Minecraft.getMinecraft().world.getBlockState(pos)
							.getBoundingBox(Minecraft.getMinecraft().world, pos).offset(pos);
					bb = bb.union(newBb);
				}
			} else if (tileEntity instanceof TileEntityEnderChest) {
				TileEntityEnderChest chest = (TileEntityEnderChest) tileEntity;
				bb = Minecraft.getMinecraft().world.getBlockState(chest.getPos())
						.getBoundingBox(Minecraft.getMinecraft().world, chest.getPos()).offset(chest.getPos());
			} else
				continue;

			TILE_ENTITIES.add(tileEntity);
			TILE_BOXES.add(bb);
		}
		ENTITIES.clear();
		// ENTITIES
		for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
			if (entity instanceof EntityMinecartChest)
				ENTITIES.add(entity);
		}
	}

	@Override
	public void onRenderWorldLast(float partialTicks) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LINE_BIT);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(3);

		GL11.glPushMatrix();
		GL11.glTranslated(-Minecraft.getMinecraft().getRenderManager().renderPosX,
				-Minecraft.getMinecraft().getRenderManager().renderPosY,
				-Minecraft.getMinecraft().getRenderManager().renderPosZ);

		drawESPBoxes(partialTicks);

		GL11.glPopMatrix();

		GL11.glPopAttrib();
	}

	private void drawESPBoxes(float partialTicks) {
		GL11.glLineWidth(2);
		// TILE ENTITIES
		for (int i = 0; i < TILE_ENTITIES.size(); i++) {
			TileEntity tileEntity = TILE_ENTITIES.get(i);

			GL11.glPushMatrix();

			if (tileEntity instanceof TileEntityChest
					&& ((TileEntityChest) tileEntity).getChestType() == BlockChest.Type.TRAP)
				GL11.glColor4f(1f, 0.6f, 0f, 0.3f);
			else if (tileEntity instanceof TileEntityEnderChest)
				GL11.glColor4f(0f, 1f, 1f, 0.3f);
			else
				GL11.glColor4f(0f, 1f, 0f, 0.3f);

			RenderUtil.drawSolidBox(TILE_BOXES.get(i));
			RenderUtil.drawOutlinedBox(TILE_BOXES.get(i));

			GL11.glPopMatrix();
		}
		// ENTITIES
		AxisAlignedBB box = new AxisAlignedBB(-0.5, 0, -0.5, 0.5, 1, 0.5);
		for (Entity entity : ENTITIES) {
			GL11.glPushMatrix();
			Vec3d interpolated = EntityUtil.getInterpolatedPos(entity, partialTicks);
			GL11.glTranslated(interpolated.x, interpolated.y + 0.38, interpolated.z);
			GL11.glScaled(0.66, 0.66, 0.66);

			GL11.glColor4f(0f, 1f, 0f, 0.3f);
			RenderUtil.drawSolidBox(box);
			RenderUtil.drawOutlinedBox(box);

			GL11.glPopMatrix();
		}
	}
}
