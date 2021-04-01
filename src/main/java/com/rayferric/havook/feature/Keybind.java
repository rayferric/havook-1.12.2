package com.rayferric.havook.feature;

import org.lwjgl.input.Keyboard;

import com.rayferric.havook.util.ChatUtil;

public class Keybind {
	public int id;
	public int key;
	public String message;
	private transient Boolean active;

	public Keybind(int id, int key, String message) {
		this.id = id;
		this.key = key;
		this.message = message;
		active = false;
	}

	public void process() {
		if (active == false && Keyboard.isKeyDown(key)) {
			active = true;
			ChatUtil.sendChatMessage(message, false);
		} else if (!Keyboard.isKeyDown(key)) {
			active = false;
		}
	}
}
