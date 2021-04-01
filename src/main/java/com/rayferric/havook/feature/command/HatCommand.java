package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import com.rayferric.havook.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class HatCommand extends Command {
	public HatCommand() {
		super("hat", ".hat", "Moves item from your hand to head slot.");
	}

	public void execute(String[] args) {
		if (!Minecraft.getMinecraft().player.isCreative()) {
			ChatUtil.warning("You must be in creative mode.");
		}
		if (args.length > 0) {
			ChatUtil.warning("Too many arguments.");
		}
		ItemStack stack = Minecraft.getMinecraft().player.inventory.getCurrentItem();
		if (stack.isEmpty()) {
			ChatUtil.error("You must hold an item in your hand.");
			return;
		}

		ItemStack head = Minecraft.getMinecraft().player.inventory.armorItemInSlot(3);

		Minecraft.getMinecraft().player.inventory.armorInventory.set(3, stack);
		InventoryUtil.updateSlot(5, stack);
		InventoryUtil.updateSlot(36 + Minecraft.getMinecraft().player.inventory.currentItem, head);

		ChatUtil.info("Item \2477" + stack.getDisplayName() + " \247ehas been moved to your head slot.");
	}
}
