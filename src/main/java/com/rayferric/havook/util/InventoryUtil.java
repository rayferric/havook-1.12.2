package com.rayferric.havook.util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;

public class InventoryUtil {
	public static void updateFirstEmptySlot(ItemStack stack) {
		int slot = 0;
		boolean slotFound = false;
		for (int i = 0; i < 36; i++) {
			if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).isEmpty()) {
				slot = i;
				slotFound = true;
				break;
			}
		}
		if (!slotFound) {
			ChatUtil.warning("Could not find empty slot. Operation has been aborted.");
			return;
		}

		int convertedSlot = slot;
		if (slot < 9)
			convertedSlot += 36;

		if (stack.getCount() > 64) {
			ItemStack passStack = stack.copy();
			stack.setCount(64);
			passStack.setCount(passStack.getCount() - 64);
			Minecraft.getMinecraft().player.inventory.setInventorySlotContents(slot, stack);
			Minecraft.getMinecraft().getConnection()
					.sendPacket(new CPacketCreativeInventoryAction(convertedSlot, stack));
			updateFirstEmptySlot(passStack);
			return;
		}

		Minecraft.getMinecraft().getConnection().sendPacket(new CPacketCreativeInventoryAction(convertedSlot, stack));
	}

	public static void updateSlot(int slot, ItemStack stack) {
		Minecraft.getMinecraft().getConnection().sendPacket(new CPacketCreativeInventoryAction(slot, stack));
	}
}
