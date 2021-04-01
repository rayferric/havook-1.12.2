package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import net.minecraft.client.Minecraft;

public class ForwardCommand extends Command {
	public ForwardCommand() {
		super("forward", ".forward <distance>", "Teleports you forward by given distance.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
		if (args.length > 1) {
			ChatUtil.warning("Too many arguments.");
		}

		double distance = 0;
		try {
			distance = Double.parseDouble(args[0]);
		} catch (NullPointerException | NumberFormatException e) {
			ChatUtil.error("\2477" + args[0] + " \247cis not a valid number.");
			return;
		}

		double y = Minecraft.getMinecraft().player.posY;
		float yaw = Minecraft.getMinecraft().player.rotationYaw;

		double newX = -Math.sin(Math.toRadians(yaw)) * distance + Minecraft.getMinecraft().player.posX;
		double newZ = Math.cos(Math.toRadians(yaw)) * distance + Minecraft.getMinecraft().player.posZ;

		Minecraft.getMinecraft().player.setPosition(newX, y, newZ);

		ChatUtil.info("Teleported \2477" + Minecraft.getMinecraft().player.getName() + "\247e to \2479" + newX
				+ "\247e, \2479" + y + "\247e, \2479" + newZ + "\247e.");
	}
}
