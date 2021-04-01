package com.rayferric.havook.feature.mod.misc;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.GL11;

import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class AutoFishMod extends Mod {
	private transient Timer timer = new Timer();

	public AutoFishMod() {
		super("autofish", "Auto Fish", "Catches fish automatically with fishing rod.", ModCategoryEnum.MISC);
	}

	@Override
	public void onPlaySound(PlaySoundEvent event) {
		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.fishEntity != null
				&& event.getName().equals("entity.bobber.splash")) {
			sheduleUse(500);
			sheduleUse(1000);
		}
	}

	private void sheduleUse(int delay) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode());
			}
		}, delay);
	}
}
