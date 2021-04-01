package com.rayferric.havook.feature.mod.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.util.ChatUtil;
import com.rayferric.havook.util.EntityUtil;
import com.rayferric.havook.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class HitmanRadarMod extends Mod {
	public transient ModAttributeBoolean tracers = new ModAttributeBoolean("Tracers", true);
	public transient ModAttributeBoolean showInvisibleEntities = new ModAttributeBoolean("ShowInvisibleEntities",
			false);
	public transient ModAttributeBoolean cacheNames = new ModAttributeBoolean("CacheNames", true);
	
	private transient List<EntityPlayer> HITMEN = new ArrayList<EntityPlayer>();
	private transient List<String> CACHE = new ArrayList<String>();
	private transient int BOX = 0;
	private transient ItemStack WOODEN_SWORD = new ItemStack(Items.WOODEN_SWORD);
	private transient ItemStack STONE_SWORD = new ItemStack(Items.STONE_SWORD);
	private transient ItemStack IRON_SWORD = new ItemStack(Items.IRON_SWORD);
	private transient ItemStack GOLDEN_SWORD = new ItemStack(Items.GOLDEN_SWORD);
	private transient ItemStack DIAMOND_SWORD = new ItemStack(Items.DIAMOND_SWORD);

	public HitmanRadarMod() {
		super("hitmanradar", "Hitman Radar", "Highlights players with a sword. Useful when playing Murder Mystery minigame.", ModCategoryEnum.RENDER);

		addAttrib(tracers);
		addAttrib(showInvisibleEntities);
		addAttrib(cacheNames);
	}

	@Override
	public void onEnable() {
		BOX = GL11.glGenLists(1);
		GL11.glNewList(BOX, GL11.GL_COMPILE);
		RenderUtil.drawOutlinedBox(new AxisAlignedBB(-0.5, 0, -0.5, 0.5, 1, 0.5));
		GL11.glEndList();
		CACHE.clear();
	}

	@Override
	public void onDisable() {
		GL11.glDeleteLists(BOX, 1);
	}

	@Override
	public void onLocalPlayerUpdate() {
		HITMEN.clear();
		for (EntityPlayer entity : Minecraft.getMinecraft().world.playerEntities) {
			if (entity.isDead || entity.getHealth() < 0)
				continue;
			if (entity == Minecraft.getMinecraft().player)
				continue;
			if (entity.isInvisible() && !showInvisibleEntities.value)
				continue;
			if (!(entity.inventory.hasItemStack(WOODEN_SWORD) || entity.inventory.hasItemStack(STONE_SWORD)
					|| entity.inventory.hasItemStack(IRON_SWORD) || entity.inventory.hasItemStack(GOLDEN_SWORD)
					|| entity.inventory.hasItemStack(DIAMOND_SWORD)) && !CACHE.contains(entity.getName()))
				continue;
			HITMEN.add(entity);
			if(cacheNames.value)CACHE.add(entity.getName());
		}
	}

	@Override
	public void onRenderWorldLast(float partialTicks) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LINE_BIT | GL11.GL_CURRENT_BIT);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(2);

		GL11.glPushMatrix();
		GL11.glTranslated(-Minecraft.getMinecraft().getRenderManager().renderPosX,
				-Minecraft.getMinecraft().getRenderManager().renderPosY,
				-Minecraft.getMinecraft().getRenderManager().renderPosZ);

		drawBoxes(partialTicks);
		if (tracers.value)
			drawTracers();

		GL11.glPopMatrix();

		GL11.glPopAttrib();
	}

	private void drawBoxes(float partialTicks) {
		GL11.glLineWidth(4);
		for (EntityPlayer entity : HITMEN) {
			GL11.glPushMatrix();
			Vec3d interpolated = EntityUtil.getInterpolatedPos(entity, partialTicks);
			GL11.glTranslated(interpolated.x, interpolated.y, interpolated.z);
			GL11.glScaled(entity.width + 0.1, entity.height + 0.1, entity.width + 0.1);
			GL11.glColor4f(0, 0, 0, 1);
			GL11.glCallList(BOX);
			GL11.glPopMatrix();
		}
	}

	private void drawTracers() {
		GL11.glLineWidth(4);
		Vec3d start = new Vec3d(Minecraft.getMinecraft().getRenderManager().viewerPosX,
				Minecraft.getMinecraft().getRenderManager().viewerPosY + Minecraft.getMinecraft().player.getEyeHeight(),
				Minecraft.getMinecraft().getRenderManager().viewerPosZ)
						.add(Minecraft.getMinecraft().player.getLookVec());
		GL11.glBegin(GL11.GL_LINES);
		for (EntityPlayer entity : HITMEN) {
			Vec3d target = entity.getEntityBoundingBox().getCenter();
			GL11.glColor4f(0, 0, 0, 1);
			GL11.glVertex3d(start.x, start.y, start.z);
			GL11.glVertex3d(target.x, target.y, target.z);
		}
		GL11.glEnd();
	}
}
