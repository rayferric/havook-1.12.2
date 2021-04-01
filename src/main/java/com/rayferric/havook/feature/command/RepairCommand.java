package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import com.rayferric.havook.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class RepairCommand extends Command {
	public RepairCommand() {
		super("repair", ".repair", "Repairs the item in your hand.");
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
		if (!stack.isItemStackDamageable()) {
			ChatUtil.error("This item cannot take any damage.");
			return;
		}
		if (!stack.isItemDamaged()) {
			ChatUtil.error("This item is not damaged.");
			return;
		}

		stack.setItemDamage(0);
		InventoryUtil.updateSlot(36 + Minecraft.getMinecraft().player.inventory.currentItem, stack);
		ChatUtil.info("Item \2477" + stack.getDisplayName() + " \247ehas been repaired.");
	}
}
