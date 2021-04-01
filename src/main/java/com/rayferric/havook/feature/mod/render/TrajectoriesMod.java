package com.rayferric.havook.feature.mod.render;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.util.BlockUtil;
import com.rayferric.havook.util.EntityUtil;
import com.rayferric.havook.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class TrajectoriesMod extends Mod {
	private transient int BOX = 0;

	public TrajectoriesMod() {
		super("trajectories", "Trajectories", "Predicts the flight path of arrows and throwable items.",
				ModCategoryEnum.RENDER);
	}

	@Override
	public void onEnable() {
		BOX = GL11.glGenLists(1);
		GL11.glNewList(BOX, GL11.GL_COMPILE);
		RenderUtil.drawSolidBox(new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5));
		GL11.glEndList();
	}

	@Override
	public void onDisable() {
		GL11.glDeleteLists(BOX, 1);
	}

	public void onRenderWorldLast(float partialTicks) {
		EntityPlayer player = Minecraft.getMinecraft().player;

		ItemStack stack = player.inventory.getCurrentItem();
		if (stack == null)
			return;

		Item item = stack.getItem();
		if (!(item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg
				|| item instanceof ItemEnderPearl || item instanceof ItemSplashPotion
				|| item instanceof ItemLingeringPotion || item instanceof ItemFishingRod))
			return;

		boolean bow = stack.getItem() instanceof ItemBow;

		double arrowPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks
				- Math.cos((float) Math.toRadians(player.rotationYaw)) * 0.08f;
		double arrowPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks
				+ player.getEyeHeight() - 0.04;
		double arrowPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks
				- Math.sin((float) Math.toRadians(player.rotationYaw)) * 0.08f;

		float arrowMotionFactor = bow ? 1f : 0.4f;
		float yaw = (float) Math.toRadians(player.rotationYaw);
		float pitch = (float) Math.toRadians(player.rotationPitch);
		double arrowMotionX = -Math.sin(yaw) * Math.cos(pitch) * arrowMotionFactor;
		double arrowMotionY = -Math.sin(pitch) * arrowMotionFactor;
		double arrowMotionZ = Math.cos(yaw) * Math.cos(pitch) * arrowMotionFactor;
		double arrowMotion = Math
				.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
		arrowMotionX /= arrowMotion;
		arrowMotionY /= arrowMotion;
		arrowMotionZ /= arrowMotion;
		if (bow) {
			float bowPower = (72000 - player.getItemInUseCount()) / 20f;
			bowPower = (bowPower * bowPower + bowPower * 2f) / 3f;

			if (bowPower > 1f || bowPower <= 0.1f)
				bowPower = 1f;

			bowPower *= 3f;
			arrowMotionX *= bowPower;
			arrowMotionY *= bowPower;
			arrowMotionZ *= bowPower;

		} else {
			arrowMotionX *= 1.5;
			arrowMotionY *= 1.5;
			arrowMotionZ *= 1.5;
		}
		
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LINE_BIT | GL11.GL_CURRENT_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(2);

		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

		double gravity = bow ? 0.005
				: item instanceof ItemPotion ? 0.04 : item instanceof ItemFishingRod ? 0.015 : 0.003;
		Vec3d eyePos = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		boolean hit = false;
		boolean predictedHit = predictHit(eyePos, new Vec3d(arrowPosX, arrowPosY, arrowPosZ),
				new Vec3d(arrowMotionX, arrowMotionY, arrowMotionZ), gravity);
		if (predictedHit)
			GL11.glColor4f(0.9f, 0.2f, 0.1f, 0.5f);
		else
			GL11.glColor4f(0, 0.9f, 0.8f, 0.5f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for (int i = 0; i < 1000; i++) {
			if (Minecraft.getMinecraft().world.rayTraceBlocks(eyePos,
					new Vec3d(arrowPosX, arrowPosY, arrowPosZ)) != null) {
				if (predictedHit)
					GL11.glColor4f(0.3f, 0.1f, 0.1f, 0.5f);
				else
					GL11.glColor4f(0.1f, 0.3f, 0.3f, 0.5f);
			} else {
				if (predictedHit)
					GL11.glColor4f(0.9f, 0.2f, 0.1f, 0.5f);
				else
					GL11.glColor4f(0, 0.9f, 0.8f, 0.5f);
			}
			GL11.glVertex3d(arrowPosX - renderManager.renderPosX, arrowPosY - renderManager.renderPosY,
					arrowPosZ - renderManager.renderPosZ);

			arrowPosX += arrowMotionX * 0.1;
			arrowPosY += arrowMotionY * 0.1;
			arrowPosZ += arrowMotionZ * 0.1;
			arrowMotionX *= 0.999;
			arrowMotionY *= 0.999;
			arrowMotionZ *= 0.999;
			arrowMotionY -= gravity;

			for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
				if (entity instanceof EntityLiving && entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35)
						.contains(new Vec3d(arrowPosX, arrowPosY, arrowPosZ))) {
					hit = true;
					break;
				}
			}
			if (hit)
				break;
			for (EntityPlayer entity : Minecraft.getMinecraft().world.playerEntities) {
				if (entity != Minecraft.getMinecraft().player && entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35)
						.contains(new Vec3d(arrowPosX, arrowPosY, arrowPosZ))) {
					hit = true;
					break;
				}
			}
			if (hit)
				break;
			Block block = Minecraft.getMinecraft().world
					.getBlockState(new BlockPos(new Vec3d(arrowPosX, arrowPosY, arrowPosZ))).getBlock();
			if (BlockUtil.isCollidable(block))
				break;
		}
		GL11.glEnd();

		GL11.glPushMatrix();
		GL11.glTranslated(arrowPosX - renderManager.renderPosX, arrowPosY - renderManager.renderPosY,
				arrowPosZ - renderManager.renderPosZ);
		GL11.glCallList(BOX);
		GL11.glPopMatrix();
		
		GL11.glPopAttrib();
	}

	boolean predictHit(Vec3d eyePos, Vec3d arrowPos, Vec3d arrowMotion, double gravity) {
		boolean hit = false;
		for (int i = 0; i < 250; i++) {
			arrowPos = arrowPos.add(arrowMotion.scale(0.4));
			arrowMotion = new Vec3d(arrowMotion.x * 0.996, arrowMotion.y * 0.996 - gravity * 4.0,
					arrowMotion.z * 0.996);

			for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
				if (entity instanceof EntityLiving
						&& entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35).contains(arrowPos)) {
					hit = true;
					break;
				}
			}
			if (hit)
				break;
			for (EntityPlayer entity : Minecraft.getMinecraft().world.playerEntities) {
				if (entity != Minecraft.getMinecraft().player
						&& entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35).contains(arrowPos)) {
					hit = true;
					break;
				}
			}
			if (hit)
				break;

			Block block = Minecraft.getMinecraft().world.getBlockState(new BlockPos(arrowPos)).getBlock();
			if (BlockUtil.isCollidable(block))
				break;
		}
		return hit;
	}
}
