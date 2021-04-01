package com.rayferric.havook.gui.list;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.rayferric.havook.feature.Keybind;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.manager.KeybindManager;
import com.rayferric.havook.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class KeybindList extends GuiScrollingList {
	private List<Keybind> ENTRIES = new ArrayList<Keybind>();
	public Keybind selection = null;
	public boolean hasSelectionUpdated = false;

	public KeybindList(int width, int height, int left, int top) {
		super(Minecraft.getMinecraft(), width, height, top, top + height, left, 50);
		updateEntries();
	}

	public void updateEntries() {
		selection = null;
		ENTRIES.clear();
		for (Keybind keybind : KeybindManager.KEYBINDS) {
			ENTRIES.add(keybind);
		}
	}

	@Override
	protected void drawSlot(int slotIndex, int x, int y, int arg3, Tessellator tesselator) {
		Keybind keybind = ENTRIES.get(slotIndex);

		Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer,
				"\247c\247l" + Keyboard.getKeyName(keybind.key) + " \2477[\2479" + keybind.id + "\2477]",
				left + listWidth / 2,
				slotHeight / 3 + y - (int) (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 1.5), 0xffffff);
		GuiUtil.drawLongText(Minecraft.getMinecraft().fontRenderer, "\2477" + keybind.message, left + 10,
				slotHeight / 3 * 2 + y - (int) (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 1.5),
				listWidth - 20, 1);
	}

	@Override
	protected void elementClicked(int slotIndex, boolean doubleClick) {
		selection = ENTRIES.get(slotIndex);
		hasSelectionUpdated = true;
	}

	@Override
	protected int getSize() {
		return ENTRIES.size();
	}

	@Override
	protected boolean isSelected(int slotIndex) {
		return ENTRIES.get(slotIndex) == selection;
	}

	@Override
	protected void drawBackground() {
	}
}
