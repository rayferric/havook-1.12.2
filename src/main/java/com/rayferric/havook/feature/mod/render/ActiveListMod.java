package com.rayferric.havook.feature.mod.render;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttributeDouble;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.manager.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ActiveListMod extends Mod {
	public transient ModAttributeDouble topMargin = new ModAttributeDouble("TopMargin", 0);

	public ActiveListMod() {
		super("activelist", "Active List", "Shows other active mods in upper-left corner of the screen.", ModCategoryEnum.RENDER);
		enabled = true; // overwritten when loading config (this enables the mod on first start)
		addAttrib(topMargin);
	}

	@Override
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
		if(event.getType() != ElementType.TEXT || Minecraft.getMinecraft().gameSettings.showDebugInfo)return;
		int i = 0;
		for(Mod mod : ModManager.MODS) {
			if(!mod.isEnabled() || mod.id == "activelist")continue;
			Minecraft.getMinecraft().fontRenderer.drawString(mod.name, 2, 2 + i++ * 10 + (int)(topMargin.value * 10), 0xEEEEEE);
		}
	}
}
