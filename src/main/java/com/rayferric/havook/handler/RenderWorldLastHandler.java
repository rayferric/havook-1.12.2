package com.rayferric.havook.handler;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.manager.ModManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderWorldLastHandler {
	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		for (Mod mod : ModManager.MODS) {
			if (mod.isEnabled()) {
				mod.onRenderWorldLast(event.getPartialTicks());
			}
		}
	}
}
