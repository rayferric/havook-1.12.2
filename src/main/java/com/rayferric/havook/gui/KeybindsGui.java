package com.rayferric.havook.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Keybind;
import com.rayferric.havook.gui.list.KeybindList;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.manager.KeybindManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class KeybindsGui extends GuiScreen {
	private KeybindList keybindList;
	private GuiButton setKeyButton;
	private GuiButton removeKeybindButton;
	private GuiTextField setKeybindField;
	private int keybindId = 0;
	private int keybindKey = Keyboard.KEY_NONE;
	private boolean keyListenerEnabled = false;

	@Override
	public void initGui() {
		keybindList = new KeybindList(width / 2 - 30, height - 120, 20, 60);

		buttonList.add(new GuiButton(0, 20, 20, 100, 20, "\247lBack"));
		buttonList.add(new GuiButton(1, width - 120, 20, 100, 20, "Reset"));
		buttonList.add(new GuiButton(2, width / 4 * 3 - 10 - 90, height / 2 - 10 - 60, 20, 20, "-"));
		buttonList.add(new GuiButton(3, width / 4 * 3 - 10 - 10, height / 2 - 10 - 60, 20, 20, "+"));
		buttonList.add(new GuiButton(4, width / 4 * 3 + 3, height / 2 - 10 + 45, 97, 20, "Set"));
		setKeyButton = new GuiButton(5, width / 4 * 3 - 100, height / 2 - 10 - 30, 100, 20, "KEY");
		removeKeybindButton = new GuiButton(6, width / 4 * 3 - 100, height / 2 - 10 + 45, 97, 20, "Remove");

		setKeybindField = new GuiTextField(0, fontRenderer, width / 4 * 3 - 100, height / 2 - 10, 200, 20);
		setKeybindField.setMaxStringLength(32768);
		setKeybindField.setText("");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// LOGIC
		if (keybindList.hasSelectionUpdated) {
			keybindId = keybindList.selection.id;
			keybindKey = keybindList.selection.key;
			setKeybindField.setText(keybindList.selection.message);

			keybindList.hasSelectionUpdated = false;
		}

		if (keyListenerEnabled) {
			if (((int) System.currentTimeMillis() / 500 % 2) == 0)
				setKeyButton.displayString = "";
			else
				setKeyButton.displayString = "\247a\247l> <";
		} else
			setKeyButton.displayString = "\247a\247l" + Keyboard.getKeyName(keybindKey);
		// RENDER
		drawDefaultBackground();

		keybindList.drawScreen(mouseX, mouseY, partialTicks);

		setKeyButton.drawButton(mc, mouseX, mouseY, partialTicks);
		if (keybindList.selection != null)
			removeKeybindButton.drawButton(mc, mouseX, mouseY, partialTicks);

		setKeybindField.drawTextBox();

		super.drawScreen(mouseX, mouseY, partialTicks);

		drawCenteredString(fontRenderer, "\247lKeybinds", width / 2, 20, 0xffff55);
		drawString(fontRenderer, Havook.NAME + " " + Havook.VERSION, 2, height - 10, 0x808080);

		drawString(fontRenderer, "\247c\247lID", width / 4 * 3 - 110 - fontRenderer.getStringWidth("\247c\247lID"),
				height / 2 - 60 - (fontRenderer.FONT_HEIGHT / 2), 0xffffff);
		drawString(fontRenderer, "\247c\247lKEY", width / 4 * 3 - 110 - fontRenderer.getStringWidth("\247c\247lKEY"),
				height / 2 - 30 - (fontRenderer.FONT_HEIGHT / 2), 0xffffff);
		drawString(fontRenderer, "\247c\247lMSG", width / 4 * 3 - 110 - fontRenderer.getStringWidth("\247c\247lMSG"),
				height / 2 - (fontRenderer.FONT_HEIGHT / 2), 0xffffff);
		drawCenteredString(fontRenderer, Integer.toString(keybindId), width / 4 * 3 - 50,
				height / 2 - 60 - (fontRenderer.FONT_HEIGHT / 2), 0x5555ff);
	}

	@Override
	public void updateScreen() {
		setKeybindField.updateCursorCounter();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		keybindList.actionPerformed(button);

		if (!button.enabled) {
			return;
		}

		switch (button.id) {
		case 0:
			Minecraft.getMinecraft().displayGuiScreen(new MainMenuGui());
			break;
		case 1:
			KeybindManager.resetKeybinds();
			keybindList.updateEntries();
			break;
		case 2:
			keybindId--;
			break;
		case 3:
			keybindId++;
			break;
		case 4:
			Keybind existingKeybind = KeybindManager.getKeybindById(keybindId);
			if (existingKeybind != null) {
				existingKeybind.id = keybindId;
				existingKeybind.key = keybindKey;
				existingKeybind.message = setKeybindField.getText();
				KeybindManager.saveKeybinds();
			} else {
				KeybindManager.addKeybind(keybindId, keybindKey, setKeybindField.getText());
			}
			setKeybindField.setText("");
			keybindList.updateEntries();
			break;
		default:
			break;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (keyListenerEnabled) {
			if (keyCode == Keyboard.KEY_LCONTROL && Keyboard.isKeyDown(Keyboard.KEY_RMENU))
				keybindKey = Keyboard.KEY_RMENU;
			else
				keybindKey = keyCode;
			keyListenerEnabled = false;
			return;
		}
		setKeybindField.textboxKeyTyped(typedChar, keyCode);
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		setKeybindField.mouseClicked(x, y, button);
		if (setKeyButton.mousePressed(mc, x, y)) {
			keybindKey = Keyboard.KEY_NONE;
			keyListenerEnabled = true;
		}
		if (keybindList.selection != null && removeKeybindButton.mousePressed(mc, x, y)) {
			KeybindManager.removeKeybind(keybindList.selection.id);
			keybindList.updateEntries();
		}
	}

	@Override
	public void mouseReleased(int x, int y, int button) {
		super.mouseReleased(x, y, button);
		setKeyButton.mouseReleased(x, y);
		if (keybindList.selection != null)
			removeKeybindButton.mouseReleased(x, y);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
