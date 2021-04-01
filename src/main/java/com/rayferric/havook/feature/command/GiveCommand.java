package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import com.rayferric.havook.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public class GiveCommand extends Command {
	public GiveCommand() {
		super("give", ".give <item> [amount] [data] [dataTag]", "Gives you an item.");
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
		Item item = null;
		int amount = 1;
		int metadata = 0;
		String nbt = null;
		// item
		item = Item.getByNameOrId(args[0]);
		if (item == null) {
			ChatUtil.error("There's no such item with name \2477" + args[0] + "\247c.");
			return;
		}
		// amount
		if (args.length > 1) {
			try {
				amount = Integer.parseInt(args[1]);
			} catch (NullPointerException | NumberFormatException e) {
				ChatUtil.error("\2477" + args[1] + "\247c is not a valid number.");
				return;
			}
			// metadata
			if (args.length > 2) {
				try {
					metadata = Integer.parseInt(args[2]);
				} catch (NullPointerException | NumberFormatException e) {
					ChatUtil.error("\2477" + args[2] + "\247c is not a valid number.");
					return;
				}
				// nbt
				if (args.length > 3) {
					nbt = args[3];
					for (int i = 4; i < args.length; i++)
						nbt += " " + args[i];
					nbt = nbt.replace('&', '\247').replace("\247\247", "&");
				}
			}
		}
		// generate item stack
		ItemStack stack = new ItemStack(item, amount, metadata);
		// apply nbt data
		if (nbt != null) {
			try {
				stack.setTagCompound(JsonToNBT.getTagFromJson(nbt));
			} catch (NBTException e) {
				ChatUtil.error("Data tag parsing failed: " + e.getMessage());
				return;
			}
		}
		// give item stack
		InventoryUtil.updateFirstEmptySlot(stack);
		ChatUtil.info("Given \2477" + stack.getDisplayName() + "\247e * \2479" + amount + "\247e to \2477"
				+ Minecraft.getMinecraft().player.getName() + "\247e.");
	}
}
