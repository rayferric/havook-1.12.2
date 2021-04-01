package com.rayferric.havook.util;

import org.apache.commons.lang3.ArrayUtils;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Command;
import com.rayferric.havook.manager.CommandManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil {
	public static void info(String message) {
		Minecraft.getMinecraft().player
				.sendMessage(new TextComponentString("\2478\247l[\2473\247l" + Havook.NAME + "\2478\247l] \247e" + message));
	}

	public static void error(String message) {
		Minecraft.getMinecraft().player.sendMessage(
				new TextComponentString("\2478\247l[\2473\247l" + Havook.NAME + "\2478\247l] \247c\247lERROR: \247c" + message));
	}

	public static void syntax(String message) {
		Minecraft.getMinecraft().player.sendMessage(
				new TextComponentString("\2478\247l[\2473\247l" + Havook.NAME + "\2478\247l] \247a\247lSYNTAX: \247a" + message));
	}

	public static void warning(String message) {
		Minecraft.getMinecraft().player.sendMessage(
				new TextComponentString("\2478\247l[\2473\247l" + Havook.NAME + "\2478\247l] \247b\247lWARNING: \247b" + message));
	}

	public static void clear(int lines) {
		for (int i = 0; i < lines; i++) {
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(""));
		}
	}

	public static boolean sendChatMessage(String message, boolean isFromChat) {
		if(message.equalsIgnoreCase(""))return true;
		String[] components = message.split("\\s+");
		if (components.length > 0) {
			if (components[0].charAt(0) == '.') {
				components[0] = components[0].substring(1);
				executeCommand(components);
				return false;
			}
		}
		if (!isFromChat) {
			Minecraft.getMinecraft().player.sendChatMessage(message);
		}
		return true;
	}

	private static void executeCommand(String[] args) {
		for (Command command : CommandManager.COMMANDS) {
			if (args[0].equalsIgnoreCase(command.name)) {
				command.execute(ArrayUtils.remove(args, 0));
				return;
			}
		}
		ChatUtil.error("There's no such command with name \2477" + args[0]
				+ "\247c. Type .help to list all available commands.");
	}
}
