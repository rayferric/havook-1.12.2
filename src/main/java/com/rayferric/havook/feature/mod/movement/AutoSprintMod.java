package com.rayferric.havook.feature.mod.movement;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttributeString;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import net.minecraft.client.Minecraft;

public class AutoSprintMod extends Mod {
	public AutoSprintMod() {
		super("autosprint", "Auto Sprint", "Makes you sprint automatically.", ModCategoryEnum.MOVEMENT);
	}

	@Override
	public void onLocalPlayerUpdate() {
		if (Minecraft.getMinecraft().player.collidedHorizontally || Minecraft.getMinecraft().player.isSneaking())
			return;
		if (Minecraft.getMinecraft().player.moveForward > 0)
			Minecraft.getMinecraft().player.setSprinting(true);
	}
}
