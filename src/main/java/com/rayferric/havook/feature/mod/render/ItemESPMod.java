package com.rayferric.havook.feature.mod.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.util.EntityUtil;
import com.rayferric.havook.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class ItemESPMod extends Mod {
	public transient ModAttributeBoolean tracers = new ModAttributeBoolean("Tracers", false);
	public transient ModAttributeBoolean showItemNames = new ModAttributeBoolean("ShowItemNames", true);

	private transient List<Entity> ENTITIES = new ArrayList<Entity>();
	private transient int BOX = 0;

	public ItemESPMod() {
		super("itemesp", "Item ESP", "Highlights nearby items.", ModCategoryEnum.RENDER);

		addAttrib(tracers);
		addAttrib(showItemNames);
	}

	@Override
	public void onEnable() {
		BOX = GL11.glGenLists(1);
		GL11.glNewList(BOX, GL11.GL_COMPILE);
		RenderUtil.drawOutlinedBox(new AxisAlignedBB(-0.5, 0, -0.5, 0.5, 1, 0.5));
		GL11.glEndList();
	}

	@Override
	public void onDisable() {
		GL11.glDeleteLists(BOX, 1);
	}

	@Override
	public void onLocalPlayerUpdate() {
		ENTITIES.clear();
		for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
			if (entity instanceof EntityItem)
				ENTITIES.add(entity);
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

		RenderUtil.drawESPBoxes(ENTITIES, BOX, partialTicks);
		if (tracers.value)
			RenderUtil.drawESPTracers(ENTITIES);
		GL11.glPopAttrib();
		if (showItemNames.value)
			drawStackNames(partialTicks);

		GL11.glPopMatrix();
	}

	private void drawStackNames(float partialTicks) {
		for (Entity entity : ENTITIES) {
			GL11.glPushMatrix();
			Vec3d interpolated = EntityUtil.getInterpolatedPos(entity, partialTicks);
			GL11.glTranslated(interpolated.x, interpolated.y, interpolated.z);
			ItemStack stack = ((EntityItem) entity).getItem();
			EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer,
					stack.getCount() + "x " + stack.getDisplayName(), 0, 1, 0, 0,
					Minecraft.getMinecraft().getRenderManager().playerViewY,
					Minecraft.getMinecraft().getRenderManager().playerViewX,
					Minecraft.getMinecraft().gameSettings.thirdPersonView == 2, false);
			GL11.glPopMatrix();
		}
	}
}
