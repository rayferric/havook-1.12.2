package com.rayferric.havook.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModAttributeDouble;
import com.rayferric.havook.feature.mod.ModAttributeString;
import com.rayferric.havook.gui.list.FriendList;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.manager.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class FriendsGui extends GuiScreen {
	FriendList friendList;
	GuiButton removeFriendButton;
	GuiTextField addFriendField;

	@Override
	public void initGui() {
		friendList = new FriendList(width / 2, height - 150, width / 4, 60);

		buttonList.add(new GuiButton(0, 20, 20, 100, 20, "\247lBack"));
		buttonList.add(new GuiButton(1, width - 120, 20, 100, 20, "Clear"));
		buttonList.add(new GuiButton(2, width / 2 + 3, height - 10 - 30, 97, 20, "Add"));
		removeFriendButton = new GuiButton(2, width / 2 - 100, height - 10 - 30, 97, 20, "Remove");

		addFriendField = new GuiTextField(0, fontRenderer, width / 2 - 100, height - 10 - 60, 200, 20);
		addFriendField.setMaxStringLength(128);
		addFriendField.setText("");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		friendList.drawScreen(mouseX, mouseY, partialTicks);

		if (friendList.selection != null)
			removeFriendButton.drawButton(mc, mouseX, mouseY, partialTicks);

		addFriendField.drawTextBox();

		drawCenteredString(fontRenderer, "\247lFriends", width / 2, 20, 0xff55ff);
		drawString(fontRenderer, Havook.NAME + " " + Havook.VERSION, 2, height - 10, 0x808080);
	}

	@Override
	public void updateScreen() {
		addFriendField.updateCursorCounter();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		friendList.actionPerformed(button);

		if (!button.enabled) {
			return;
		}

		switch (button.id) {
		case 0:
			Minecraft.getMinecraft().displayGuiScreen(new MainMenuGui());
			break;
		case 1:
			FriendManager.clearFriends();
			friendList.updateEntries();
			break;
		case 2:
			String text = addFriendField.getText().replaceAll("\\s+", "");
			if (text.isEmpty() || FriendManager.isFriend(text))
				break;
			FriendManager.addFriend(text);
			friendList.updateEntries();
			addFriendField.setText("");
			break;
		default:
			break;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		addFriendField.textboxKeyTyped(typedChar, keyCode);
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		addFriendField.mouseClicked(x, y, button);
		if (friendList.selection != null && removeFriendButton.mousePressed(mc, x, y)) {
			FriendManager.removeFriend(friendList.selection);
			friendList.updateEntries();
		}
	}

	@Override
	public void mouseReleased(int x, int y, int button) {
		super.mouseReleased(x, y, button);
		if (friendList.selection != null)
			removeFriendButton.mouseReleased(x, y);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
