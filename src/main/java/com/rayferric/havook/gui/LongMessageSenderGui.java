package com.rayferric.havook.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.rayferric.havook.Havook;
import com.rayferric.havook.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class LongMessageSenderGui extends GuiScreen {
	private GuiTextField messageField;

	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, 20, 20, 100, 20, "\247lBack"));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 2 + 35, 200, 20, "Send"));

		messageField = new GuiTextField(0, fontRenderer, width / 2 - 200, height / 2 - 50, 400, 20);
		messageField.setMaxStringLength(32768);
		messageField.setFocused(true);
		messageField.setText("");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		drawCenteredString(fontRenderer, "\247lLong Message Sender", width / 2, height / 2 - 90, 0x55ff55);
		drawString(fontRenderer, Havook.NAME + " " + Havook.VERSION, 2, height - 10, 0x808080);
		drawCenteredString(fontRenderer, "The message you type in here will be", width / 2, height / 2 - 10, 0xa0a0a0);
		drawCenteredString(fontRenderer, "sent to the chat.", width / 2, height / 2, 0xa0a0a0);

		messageField.drawTextBox();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (!button.enabled) {
			return;
		}

		switch (button.id) {
		case 0:
			Minecraft.getMinecraft().displayGuiScreen(new MainMenuGui());
			break;
		case 1:
			Minecraft.getMinecraft().displayGuiScreen(null);
			ChatUtil.sendChatMessage(messageField.getText(), false);
			break;
		default:
			break;
		}
	}

	@Override
	public void updateScreen() {
		messageField.updateCursorCounter();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		messageField.textboxKeyTyped(typedChar, keyCode);
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		} else if (keyCode == Keyboard.KEY_RETURN) {
			Minecraft.getMinecraft().displayGuiScreen(null);
			ChatUtil.sendChatMessage(messageField.getText(), false);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		messageField.mouseClicked(x, y, button);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
