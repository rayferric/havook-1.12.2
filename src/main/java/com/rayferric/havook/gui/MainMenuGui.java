package com.rayferric.havook.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.rayferric.havook.Havook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MainMenuGui extends GuiScreen {
	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 2 - 10 - 45, 200, 20, "Mods..."));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 2 - 10 - 20, 200, 20, "Keybinds..."));
		buttonList.add(new GuiButton(2, width / 2 - 100, height / 2 - 10 + 5, 200, 20, "Long Message Sender..."));
		buttonList.add(new GuiButton(3, width / 2 - 100, height / 2 - 10 + 45, 97, 20, "Friends..."));
		buttonList.add(new GuiButton(4, width / 2 + 3, height / 2 - 10 + 45, 97, 20, "\247lExit"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		drawCenteredString(fontRenderer, "\247lMain Menu", width / 2, height / 2 - 4 - 100, 0x5555ff);
		drawString(fontRenderer, Havook.NAME + " " + Havook.VERSION, 2, height - 10, 0x808080);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (!button.enabled) {
			return;
		}

		switch (button.id) {
		case 0:
			Minecraft.getMinecraft().displayGuiScreen(new ModsGui());
			break;
		case 1:
			Minecraft.getMinecraft().displayGuiScreen(new KeybindsGui());
			break;
		case 2:
			Minecraft.getMinecraft().displayGuiScreen(new LongMessageSenderGui());
			break;
		case 3:
			Minecraft.getMinecraft().displayGuiScreen(new FriendsGui());
			break;
		case 4:
			Minecraft.getMinecraft().displayGuiScreen(null);
			break;
		default:
			break;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
