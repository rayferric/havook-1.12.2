package com.rayferric.havook.handler;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.manager.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlaySoundHandler {
	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent event) {
		for (Mod mod : ModManager.MODS) {
			if (mod.isEnabled()) {
				mod.onPlaySound(event);
			}
		}
	}
}
