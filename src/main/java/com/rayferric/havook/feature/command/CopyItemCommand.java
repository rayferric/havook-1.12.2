package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import com.rayferric.havook.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CopyItemCommand extends Command {
	public CopyItemCommand() {
		super("copyitem", ".copyitem <player> <hand|head|chest|legs|feet>",
				"Copies item from other player's armor slot or hand.");
	}

	@Override
	public void execute(String[] args) {
		if (!Minecraft.getMinecraft().player.isCreative()) {
			ChatUtil.warning("You must be in creative mode.");
		}
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
		ItemStack stack = null;
		// find player
		EntityPlayer target = Minecraft.getMinecraft().world.getPlayerEntityByName(args[0]);
		if (target == null) {
			ChatUtil.error("Player \2477" + args[0] + "\247c can not be found.");
			return;
		}
		if (args.length < 2) {
			ChatUtil.error("You must specify the slot you want to copy item from.");
			return;
		}
		if (args.length > 2) {
			ChatUtil.warning("Too many arguments.");
		}
		// find item
		switch (args[1].toLowerCase()) {
		case "hand":
			stack = target.inventory.getCurrentItem();
			break;
		case "head":
			stack = target.inventory.armorItemInSlot(3);
			break;
		case "chest":
			stack = target.inventory.armorItemInSlot(2);
			break;
		case "legs":
			stack = target.inventory.armorItemInSlot(1);
			break;
		case "feet":
			stack = target.inventory.armorItemInSlot(0);
			break;
		default:
			ChatUtil.error("Invalid slot specified.");
			return;
		}
		// give item stack
		InventoryUtil.updateFirstEmptySlot(stack.copy());
		ChatUtil.info("Item copied.");
	}
}
