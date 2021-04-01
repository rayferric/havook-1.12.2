package com.rayferric.havook.feature.mod.movement;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import net.minecraft.client.Minecraft;

public class ParkourMod extends Mod {
	public ParkourMod() {
		super("parkour", "Parkour", "Makes you jump automatically when reaching the edge of a block.",
				ModCategoryEnum.MOVEMENT);
	}

	@Override
	public void onLocalPlayerUpdate() {
		if (Minecraft.getMinecraft().player.onGround && !Minecraft.getMinecraft().player.isSneaking()
				&& !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()
				&& Minecraft.getMinecraft().world
						.getCollisionBoxes(Minecraft.getMinecraft().player, Minecraft.getMinecraft().player
								.getEntityBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001))
						.isEmpty()) {
			Minecraft.getMinecraft().player.jump();
		}
	}
}
