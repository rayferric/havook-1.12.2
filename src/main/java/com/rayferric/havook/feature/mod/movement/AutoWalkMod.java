package com.rayferric.havook.feature.mod.movement;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class AutoWalkMod extends Mod {
	public AutoWalkMod() {
		super("autowalk", "Auto Walk", "Makes you walk automatically.", ModCategoryEnum.MOVEMENT);
	}

	@Override
	public void onDisable() {
		KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(),
				Minecraft.getMinecraft().gameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward));
	}

	@Override
	public void onLocalPlayerUpdate() {
		KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), true);
	}
}
