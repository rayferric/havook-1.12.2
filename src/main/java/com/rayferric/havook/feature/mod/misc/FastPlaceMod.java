package com.rayferric.havook.feature.mod.misc;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.util.ChatUtil;
import net.minecraft.client.Minecraft;

public class FastPlaceMod extends Mod {
	private transient int nativeDelay;
	
	public FastPlaceMod() {
		super("fastplace", "Fast Place", "Allows to place blocks faster.", ModCategoryEnum.MISC);
	}
	
	@Override
	public void onEnable() {
		nativeDelay = Minecraft.getMinecraft().rightClickDelayTimer;
	}
	
	@Override
	public void onDisable() {
		Minecraft.getMinecraft().rightClickDelayTimer = nativeDelay;
	}
	
	@Override
	public void onLocalPlayerUpdate() {
		Minecraft.getMinecraft().rightClickDelayTimer = 0;
	}
}
