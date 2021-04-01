package com.rayferric.havook.gui.list;

import java.util.ArrayList;
import java.util.List;

import com.rayferric.havook.manager.FriendManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class FriendList extends GuiScrollingList {
	private List<String> ENTRIES = new ArrayList<String>();
	public String selection = null;

	public FriendList(int width, int height, int left, int top) {
		super(Minecraft.getMinecraft(), width, height, top, top + height, left, 25);
		updateEntries();
	}

	public void updateEntries() {
		selection = null;
		ENTRIES.clear();
		for (String friend : FriendManager.FRIENDS) {
			ENTRIES.add(friend);
		}
	}

	@Override
	protected void drawSlot(int slotIndex, int x, int y, int arg3, Tessellator tesselator) {
		String friend = ENTRIES.get(slotIndex);

		Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer, friend,
				left + listWidth / 2,
				slotHeight / 2 + y - (int) (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 1.5), 0xffffff);
	}

	@Override
	protected void elementClicked(int slotIndex, boolean doubleClick) {
		selection = ENTRIES.get(slotIndex);
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
