package com.rayferric.havook.feature.mod.movement;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttributeDouble;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;

public class ElytraBoostMod extends Mod {
	public ElytraBoostMod() {
		super("elytraboost", "Elytra Boost", "Allows you to gain extra speed when flying with elytra.", ModCategoryEnum.MOVEMENT);
	}

	@Override
	public void onLocalPlayerUpdate() {
		if(!Minecraft.getMinecraft().player.isElytraFlying())return;
		float yaw = Minecraft.getMinecraft().player.rotationYaw;
		float pitch = Minecraft.getMinecraft().player.rotationPitch;
		if (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()) {
			Minecraft.getMinecraft().player.motionX -= Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 0.05;
			Minecraft.getMinecraft().player.motionZ += Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 0.05;
			Minecraft.getMinecraft().player.motionY += Math.sin(Math.toRadians(pitch)) * 0.05;
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())Minecraft.getMinecraft().player.motionY += 0.05;
		if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown())Minecraft.getMinecraft().player.motionY -= 0.05;
	}
}
