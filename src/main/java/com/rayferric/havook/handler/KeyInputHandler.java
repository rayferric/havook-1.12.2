package com.rayferric.havook.handler;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Keybind;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.manager.KeybindManager;
import com.rayferric.havook.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyInputHandler {
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			for (Keybind keybind : KeybindManager.KEYBINDS) {
				keybind.process();
			}
		}
	}
}
