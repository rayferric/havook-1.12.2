package com.rayferric.havook.feature.command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import com.rayferric.havook.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public class NbtCommand extends Command {
	public NbtCommand() {
		super("nbt", ".nbt <add <dataTag>|set <dataTag>|remove <tagName>|clear|copy>",
				"Modifies held item's NBT data.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
		ItemStack stack = Minecraft.getMinecraft().player.inventory.getCurrentItem();
		if (stack.isEmpty()) {
			ChatUtil.error("You must hold an item in your hand.");
			return;
		}
		if (args[0].equals("add")) {
			if (!Minecraft.getMinecraft().player.isCreative()) {
				ChatUtil.warning("You must be in creative mode.");
			}
			if (args.length < 2) {
				ChatUtil.error("No NBT data provided.");
				return;
			}
			String nbt = args[1];
			for (int i = 2; i < args.length; i++) {
				nbt += " " + args[i];
			}
			nbt = nbt.replace('&', '\247').replace("\247\247", "&");
			try {
				if (!stack.hasTagCompound()) {
					stack.setTagCompound(JsonToNBT.getTagFromJson(nbt));
				} else {
					stack.getTagCompound().merge(JsonToNBT.getTagFromJson(nbt));
				}
				InventoryUtil.updateSlot(36 + Minecraft.getMinecraft().player.inventory.currentItem, stack);
				ChatUtil.info("Item modified.");
			} catch (NBTException e) {
				ChatUtil.error("Data tag parsing failed: " + e.getMessage());
				return;
			}
		} else if (args[0].equals("set")) {
			if (!Minecraft.getMinecraft().player.isCreative()) {
				ChatUtil.warning("You must be in creative mode.");
			}
			if (args.length < 2) {
				ChatUtil.error("No NBT data provided.");
				return;
			}
			String nbt = args[1];
			for (int i = 2; i < args.length; i++) {
				nbt += " " + args[i];
			}
			nbt = nbt.replace('&', '\247').replace("\247\247", "&");
			try {
				stack.setTagCompound(JsonToNBT.getTagFromJson(nbt));
			} catch (NBTException e) {
				ChatUtil.error("Data tag parsing failed: " + e.getMessage());
				return;
			}
			InventoryUtil.updateSlot(36 + Minecraft.getMinecraft().player.inventory.currentItem, stack);
			ChatUtil.info("Item modified.");
		} else if (args[0].equals("remove")) {
			if (!Minecraft.getMinecraft().player.isCreative()) {
				ChatUtil.warning("You must be in creative mode.");
			}
			if (args.length < 2) {
				ChatUtil.error("No NBT tag specified.");
				return;
			}
			if (args.length > 2) {
				ChatUtil.warning("Too many arguments.");
			}
			String tag = args[1];
			if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey(tag)) {
				ChatUtil.error("Item has no NBT tag with name \2477" + args[1] + "\247c.");
				return;
			}
			stack.getTagCompound().removeTag(tag);
			if (stack.getTagCompound().hasNoTags()) {
				stack.setTagCompound(null);
			}
			InventoryUtil.updateSlot(36 + Minecraft.getMinecraft().player.inventory.currentItem, stack);
			ChatUtil.info("Item modified.");
		} else if (args[0].equals("clear")) {
			if (!Minecraft.getMinecraft().player.isCreative()) {
				ChatUtil.warning("You must be in creative mode.");
			}
			if (args.length > 1) {
				ChatUtil.warning("Too many arguments.");
			}
			if (!stack.hasTagCompound()) {
				ChatUtil.error("Item has no NBT data.");
				return;
			}
			stack.setTagCompound(null);
			InventoryUtil.updateSlot(36 + Minecraft.getMinecraft().player.inventory.currentItem, stack);
			ChatUtil.info("Cleared item's NBT data.");

		} else if (args[0].equals("copy")) {
			if (args.length > 1) {
				ChatUtil.warning("Too many arguments.");
			}
			if (!stack.hasTagCompound()) {
				ChatUtil.error("Item has no NBT data.");
				return;
			}
			StringSelection selection = new StringSelection(stack.getTagCompound().toString());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			ChatUtil.info("Copied item's NBT data to clipboard.");
		} else {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
	}
}
