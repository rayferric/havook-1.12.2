package com.rayferric.havook.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModAttributeDouble;
import com.rayferric.havook.feature.mod.ModAttributeString;
import com.rayferric.havook.gui.list.ModAttributeList;
import com.rayferric.havook.gui.list.ModList;
import com.rayferric.havook.manager.ModManager;
import com.rayferric.havook.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ModsGui extends GuiScreen {
	private ModList modList;
	private ModAttributeList attributeList;
	private GuiButton toggleButton;
	private GuiTextField searchField;
	private GuiButton booleanToggleButton;
	private GuiButton doubleDecrementButton;
	private GuiButton doubleIncrementButton;
	private GuiButton stringSetButton;
	private GuiTextField stringSetField;

	@Override
	public void initGui() {
		modList = new ModList(10, 70, width / 2 - 20, (int) (height / 1.5 - 60));
		attributeList = new ModAttributeList(width / 2 + 10, 70, width / 2 - 20, height / 2 - 60);

		buttonList.add(new GuiButton(0, 20, 20, 100, 20, "\247lBack"));
		toggleButton = new GuiButton(1, width / 2 - 75, 45, 150, 20, "Toggle");

		searchField = new GuiTextField(0, fontRenderer, width / 2 - 75, 20, 150, 20);
		searchField.setMaxStringLength(8);
		searchField.setText("");

		booleanToggleButton = new GuiButton(2, (int) (width * 0.75) - 50, (int) (height * 0.75 - 10 + 30), 100, 20,
				"Toggle");
		doubleDecrementButton = new GuiButton(3, (int) (width * 0.75) - 10 - 40, (int) (height * 0.75 - 10 + 30), 20,
				20, "-");
		doubleIncrementButton = new GuiButton(4, (int) (width * 0.75) - 10 + 40, (int) (height * 0.75 - 10 + 30), 20,
				20, "+");
		stringSetButton = new GuiButton(5, (int) (width * 0.75) - 75, (int) (height * 0.75) - 10 + 30, 150, 20, "Set");

		stringSetField = new GuiTextField(0, fontRenderer, (int) (width * 0.75) - 100,
				(int) (height * 0.75) - 10 - 30 + 30, 200, 20);
		stringSetField.setMaxStringLength(16);
		stringSetField.setText("");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		modList.drawScreen(mouseX, mouseY, partialTicks);
		if (modList.hasSelectionUpdated && modList.selection != null) {
			attributeList.updateEntries(modList.selection);
			modList.hasSelectionUpdated = false;
		}
		attributeList.drawScreen(mouseX, mouseY, partialTicks);

		searchField.drawTextBox();

		drawString(fontRenderer, Havook.NAME + " " + Havook.VERSION, 2, height - 10, 0x808080);
		drawCenteredString(fontRenderer, "\247lMods", (int) (width * 0.25), 45, 0xffaa00);
		drawCenteredString(fontRenderer, "\247lSettings", (int) (width * 0.75), 45, 0xaa0000);
		if (modList.selection != null) {
			toggleButton.displayString = "Toggle " + modList.selection.name;
			toggleButton.drawButton(mc, mouseX, mouseY, partialTicks);
			drawCenteredString(fontRenderer, "\2476\247l" + modList.selection.name, (int) (width * 0.25),
					(int) (height / 1.5 + 20), 0xffaa00);
			GuiUtil.drawLongText(fontRenderer, modList.selection.description, 20, (int) (height / 1.5 + 40),
					width / 2 - 20, 3);
		}
		if (attributeList.selection != null) {
			String typeColor = "";
			String typeName = "";
			String valueColor = "";
			String valueString = "";
			if (attributeList.selection instanceof ModAttributeBoolean) {
				typeColor = "\247d";
				typeName = "BOOLEAN";
				if (((ModAttributeBoolean) attributeList.selection).value) {
					valueColor = "\247a";
					booleanToggleButton.displayString = "\247atrue";
				} else {
					valueColor = "\247c";
					booleanToggleButton.displayString = "\247cfalse";
				}
				valueString = Boolean.toString(((ModAttributeBoolean) attributeList.selection).value);

				booleanToggleButton.drawButton(mc, mouseX, mouseY, partialTicks);
			} else if (attributeList.selection instanceof ModAttributeDouble) {
				typeColor = "\2479";
				typeName = "DOUBLE";
				valueColor = "\2479";
				valueString = Double.toString(Math.round(((ModAttributeDouble) attributeList.selection).value * 100) / 100f);

				doubleDecrementButton.drawButton(mc, mouseX, mouseY, partialTicks);
				doubleIncrementButton.drawButton(mc, mouseX, mouseY, partialTicks);
				float displayNumber = Math.round(((ModAttributeDouble) attributeList.selection).value * 100) / 100f;
				drawCenteredString(fontRenderer, Float.toString(displayNumber), (int) (width * 0.75),
						(int) (height * 0.75) + 30 - (fontRenderer.FONT_HEIGHT / 2), 0x5555ff);
			} else if (attributeList.selection instanceof ModAttributeString) {
				typeColor = "\247a";
				typeName = "STRING";
				valueColor = "\2477";
				valueString = ((ModAttributeString) attributeList.selection).value;

				stringSetButton.drawButton(mc, mouseX, mouseY, partialTicks);
				stringSetField.drawTextBox();
			}

			drawCenteredString(fontRenderer, "\2474\247l" + attributeList.selection.name, (int) (width * 0.75),
					height / 2 + 20, 0xffaa00);
			drawString(fontRenderer, "\2477Type: " + typeColor + "\247l" + typeName, width / 2 + 20, height / 2 + 40,
					0xaaaaaa);
			drawString(fontRenderer, "\2477Value: " + valueColor + valueString, width / 2 + 20, height / 2 + 50,
					0xaaaaaa);
		}
	}

	@Override
	public void updateScreen() {
		searchField.updateCursorCounter();
		stringSetField.updateCursorCounter();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		modList.actionPerformed(button);
		attributeList.actionPerformed(button);

		if (!button.enabled) {
			return;
		}

		switch (button.id) {
		case 0:
			Minecraft.getMinecraft().displayGuiScreen(new MainMenuGui());
			break;
		default:
			break;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (searchField.textboxKeyTyped(typedChar, keyCode))
			modList.updateEntries(searchField.getText());
		if (attributeList.selection != null && attributeList.selection instanceof ModAttributeString)
			stringSetField.textboxKeyTyped(typedChar, keyCode);
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		searchField.mouseClicked(x, y, button);
		if (modList.selection != null && toggleButton.mousePressed(mc, x, y)) {
			modList.selection.setEnabled(!modList.selection.isEnabled());
		}
		if (attributeList.selection != null) {
			if (attributeList.selection instanceof ModAttributeBoolean) {
				if (booleanToggleButton.mousePressed(mc, x, y)) {
					((ModAttributeBoolean) attributeList.selection).value = !((ModAttributeBoolean) attributeList.selection).value;
					ModManager.saveMods();
				}
			} else if (attributeList.selection instanceof ModAttributeDouble) {
				if (doubleDecrementButton.mousePressed(mc, x, y)) {
					((ModAttributeDouble)attributeList.selection).value -= 0.1;
					ModManager.saveMods();
				}
				if (doubleIncrementButton.mousePressed(mc, x, y)) {
					((ModAttributeDouble)attributeList.selection).value += 0.1;
					ModManager.saveMods();
				}
			} else if (attributeList.selection instanceof ModAttributeString) {
				stringSetField.mouseClicked(x, y, button);
				if (stringSetButton.mousePressed(mc, x, y)) {
					((ModAttributeString) attributeList.selection).value = stringSetField.getText();
					ModManager.saveMods();
					stringSetField.setText("");
				}
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y, int button) {
		super.mouseReleased(x, y, button);
		if (modList.selection != null)
			toggleButton.mouseReleased(x, y);
		if (attributeList.selection != null) {
			if (attributeList.selection instanceof ModAttributeBoolean) {
				booleanToggleButton.mouseReleased(x, y);
			} else if (attributeList.selection instanceof ModAttributeDouble) {
				doubleDecrementButton.mouseReleased(x, y);
				doubleIncrementButton.mouseReleased(x, y);
			} else if (attributeList.selection instanceof ModAttributeString) {
				stringSetButton.mouseReleased(x, y);
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
