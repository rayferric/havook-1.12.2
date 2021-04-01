package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import com.rayferric.havook.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class LoreCommand extends Command {
	public LoreCommand() {
		super("lore", ".lore <lore>", "Adds lore to item.");
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
		String lore = args[0];
		for (int i = 1; i < args.length; i++) {
			lore += " " + args[i];
		}
		lore = lore.replace('&', '\247').replace("\247\247", "&");
		if (!Minecraft.getMinecraft().player.isCreative()) {
			ChatUtil.warning("You must be in creative mode.");
		}
		if (stack.hasTagCompound() && stack.getTagCompound().getCompoundTag("display") != null) {
			NBTTagList lores = new NBTTagList();
			if (stack.getTagCompound().getCompoundTag("display").getTag("Lore") != null) {
				lores = (NBTTagList) stack.getTagCompound().getCompoundTag("display").getTag("Lore");
			}
			lores.appendTag(new NBTTagString(lore));
			NBTTagCompound display = new NBTTagCompound();
			display.setTag("Lore", lores);
			stack.getTagCompound().getCompoundTag("display").merge(display);
		} else {
			NBTTagList lores = new NBTTagList();
			lores.appendTag(new NBTTagString(lore));
			NBTTagCompound display = new NBTTagCompound();
			display.setTag("Lore", lores);
			stack.setTagInfo("display", display);
		}
		InventoryUtil.updateSlot(36 + Minecraft.getMinecraft().player.inventory.currentItem, stack);
		ChatUtil.info("Added lore \2477" + lore + "\247e to the item.");
	}
}
