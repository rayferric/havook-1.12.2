package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class TpCommand extends Command {
	public TpCommand() {
		super("tp", ".tp <<x> <y> <z> [yaw] [pitch]|<player>>", "Teleports you to specified player or position.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		} else if (args.length < 2) {
			EntityPlayer target = Minecraft.getMinecraft().world.getPlayerEntityByName(args[0]);
			if (target == null) {
				ChatUtil.error("Player \2477" + args[0] + " \247ccan not be found.");
				return;
			}
			double x = target.posX;
			double y = target.posY;
			double z = target.posZ;
			float pitch = target.rotationPitch;
			float yaw = target.rotationYaw;
			Minecraft.getMinecraft().player.setPositionAndRotation(x, y, z, yaw, pitch);
			ChatUtil.info("Teleported \2477" + Minecraft.getMinecraft().player.getName() + "\247e to \2479" + x
					+ "\247e, \2479" + y + "\247e, \2479" + z + "\247e.");
			return;
		} else if (args.length < 3) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		} else {
			double x = Minecraft.getMinecraft().player.posX;
			double y = Minecraft.getMinecraft().player.posY;
			double z = Minecraft.getMinecraft().player.posZ;
			float pitch = Minecraft.getMinecraft().player.rotationPitch;
			float yaw = Minecraft.getMinecraft().player.rotationYaw;
			// CALC X
			try {
				x = parseMath(args[0], x);
			} catch (NullPointerException | NumberFormatException e) {
				ChatUtil.error("\2477" + args[0] + " \247cis not a valid number.");
				return;
			}
			// CALC Y
			try {
				y = parseMath(args[1], y);
			} catch (NullPointerException | NumberFormatException e) {
				ChatUtil.error("\2477" + args[1] + " \247cis not a valid number.");
				return;
			}
			// CALC Z
			try {
				z = parseMath(args[2], z);
			} catch (NullPointerException | NumberFormatException e) {
				ChatUtil.error("\2477" + args[2] + " \247cis not a valid number.");
				return;
			}
			if (args.length > 3) {
				// CALC YAW
				try {
					yaw = (float) parseMath(args[3], yaw);
				} catch (NullPointerException | NumberFormatException e) {
					ChatUtil.error("\2477" + args[3] + " \247cis not a valid number.");
					return;
				}
			}
			if (args.length > 4) {
				// CALC PITCH
				try {
					pitch = (float) parseMath(args[4], pitch);
				} catch (NullPointerException | NumberFormatException e) {
					ChatUtil.error("\2477" + args[4] + " \247cis not a valid number.");
					return;
				}
			}
			if (args.length > 5) {
				ChatUtil.warning("Too many arguments.");
			}
			Minecraft.getMinecraft().player.setPositionAndRotation(x, y, z, yaw, pitch);
			ChatUtil.info("Teleported \2477" + Minecraft.getMinecraft().player.getName() + "\247e to \2479" + x
					+ "\247e, \2479" + y + "\247e, \2479" + z + "\247e.");
			return;
		}
	}

	private static double parseMath(String input, double old) {
		if (input.length() < 1) {
			throw new NumberFormatException();
		}
		if (input.charAt(0) == '~') {
			if (input.length() > 2 && input.charAt(1) == '+') {
				String coord = input.substring(2);

				try {
					return old + Double.parseDouble(coord);
				} catch (NullPointerException | NumberFormatException e) {
					throw e;
				}
			} else if (input.length() > 2 && input.charAt(1) == '-') {
				String coord = input.substring(2);

				try {
					return old - Double.parseDouble(coord);
				} catch (NullPointerException | NumberFormatException e) {
					throw e;
				}
			} else if (input.length() != 1) {
				throw new NumberFormatException();
			}
			return old;
		} else {
			try {
				return Double.parseDouble(input);
			} catch (NullPointerException | NumberFormatException e) {
				throw e;
			}
		}
	}
}
