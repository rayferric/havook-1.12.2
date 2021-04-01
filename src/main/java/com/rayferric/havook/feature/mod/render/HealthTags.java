package com.rayferric.havook.feature.mod.render;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;

public class HealthTags extends Mod {
	public transient ModAttributeBoolean showSneakingPlayers = new ModAttributeBoolean("ShowSneakingPlayers", true);
	public transient ModAttributeBoolean upscaleDistantTags = new ModAttributeBoolean("UpscaleDistantTags", true);
	
	public HealthTags() {
		super("healthtags", "Health Tags", "Shows the health of players in their nametags.", ModCategoryEnum.RENDER);
		addAttrib(showSneakingPlayers);
		addAttrib(upscaleDistantTags);
	}

	@Override
	public void onRenderLivingSpecialsPre(RenderLivingEvent.Specials.Pre event) {
		EntityLivingBase entity = event.getEntity();
		if(!(entity instanceof EntityPlayer) || entity == Minecraft.getMinecraft().player)
			return;
		if (entity.isDead || entity.getHealth() < 0 || entity.isInvisible())
			return;
		GL11.glPushMatrix();
		Vec3d pos = new Vec3d(event.getX(), event.getY() + entity.height / 1.5, event.getZ());
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		GL11.glTranslated(pos.x, pos.y + 1, pos.z);
		if(upscaleDistantTags.value) {
			double scale = Math.max(1, pos.distanceTo(new Vec3d(0, 0, 0)) / 6);
			GL11.glScaled(scale, scale, scale);
		}
		int health = (int)Math.ceil(entity.getHealth());
		EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer,
				"\2477" + entity.getDisplayName().getFormattedText() + (health > 12 ? " \247a" : (health > 6 ? " \247e" : " \247c")) + health, 0, 0, 0, 0,
				Minecraft.getMinecraft().getRenderManager().playerViewY,
				Minecraft.getMinecraft().getRenderManager().playerViewX,
				Minecraft.getMinecraft().gameSettings.thirdPersonView == 2, showSneakingPlayers.value ? false : entity.isSneaking());
		GL11.glPopMatrix();
		event.setCanceled(true);
	}
}
