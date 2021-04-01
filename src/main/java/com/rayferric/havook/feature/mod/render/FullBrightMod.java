package com.rayferric.havook.feature.mod.render;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import net.minecraft.client.Minecraft;

public class FullBrightMod extends Mod {
	private transient float nativeGamma;

	public FullBrightMod() {
		super("fullbright", "Full Bright", "Allows you to see in the dark.", ModCategoryEnum.RENDER);
	}

	@Override
	public void onEnable() {
		nativeGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
		Minecraft.getMinecraft().gameSettings.gammaSetting = 8f;
	}

	@Override
	public void onDisable() {
		if (nativeGamma < 1.0) // if game has closed with Full Bright enabled, the gamma will be ducked up
			Minecraft.getMinecraft().gameSettings.gammaSetting = nativeGamma;
		else
			Minecraft.getMinecraft().gameSettings.gammaSetting = 0.5f;
	}
}
