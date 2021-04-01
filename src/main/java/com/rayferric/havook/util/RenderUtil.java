package com.rayferric.havook.util;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.manager.FriendManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class RenderUtil {
	public static void drawOutlinedBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_LINES);
		{
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		}
		GL11.glEnd();
	}

	public static void drawSolidBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		}
		GL11.glEnd();
	}

	public static void drawESPBoxes(List<Entity> entities, int box, float partialTicks) {
		GL11.glLineWidth(2);
		for (Entity entity : entities) {
			GL11.glPushMatrix();
			Vec3d interpolated = EntityUtil.getInterpolatedPos(entity, partialTicks);
			GL11.glTranslated(interpolated.x, interpolated.y, interpolated.z);
			GL11.glScaled(entity.width + 0.1, entity.height + 0.1, entity.width + 0.1);

			if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
				GL11.glColor4f(0.9f, 0.2f, 1f, 0.5f);
			} else if (entity instanceof EntityItem) {
				GL11.glColor4f(0.5f, 0.5f, 1f, 0.5f);
			} else {
				float intensity = Minecraft.getMinecraft().player.getDistance(entity) / 20f;
				GL11.glColor4f(2f - intensity, intensity, 0f, 0.5f);
			}

			GL11.glCallList(box);

			GL11.glPopMatrix();
		}
	}

	public static void drawESPTracers(List<Entity> entities) {
		Vec3d start = new Vec3d(Minecraft.getMinecraft().getRenderManager().viewerPosX,
				Minecraft.getMinecraft().getRenderManager().viewerPosY + Minecraft.getMinecraft().player.getEyeHeight(),
				Minecraft.getMinecraft().getRenderManager().viewerPosZ)
						.add(Minecraft.getMinecraft().player.getLookVec());
		GL11.glLineWidth(2);
		GL11.glBegin(GL11.GL_LINES);
		for (Entity entity : entities) {
			Vec3d target = entity.getEntityBoundingBox().getCenter();

			if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
				GL11.glColor4f(0.9f, 0.2f, 1f, 0.5f);
			} else if (entity instanceof EntityItem) {
				GL11.glColor4f(0.5f, 0.5f, 1f, 0.5f);
			} else {
				float intensity = Minecraft.getMinecraft().player.getDistance(entity) / 20f;
				GL11.glColor4f(2f - intensity, intensity, 0f, 0.5f);
			}

			GL11.glVertex3d(start.x, start.y, start.z);
			GL11.glVertex3d(target.x, target.y, target.z);
		}
		GL11.glEnd();
	}
}
