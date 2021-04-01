package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.feature.Keybind;
import com.rayferric.havook.manager.KeybindManager;
import com.rayferric.havook.util.ChatUtil;

public class BindCommand extends Command {
	public BindCommand() {
		super("bind", ".bind <set <id> <key> [message]|remove <id>|list|reset>",
				"Manages havook keybindings. You can find all available key codes at https://minecraft.gamepedia.com/Key_codes.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
		if (args[0].equals("set")) {
			if (args.length < 3) {
				ChatUtil.error("No key specified.");
				return;
			}
			if (args.length < 2) {
				ChatUtil.error("No id specified.");
				return;
			}
			int id = 0;
			int key = 0;
			String message = "";
			if (args.length > 3) {
				message = args[3];
				for (int i = 4; i < args.length; i++) {
					message += " " + args[i];
				}
			}
			try {
				id = Integer.parseInt(args[1]);
			} catch (NullPointerException | NumberFormatException e) {
				ChatUtil.error("\2477" + args[1] + "\247e is not a valid number.");
				return;
			}
			try {
				key = Integer.parseInt(args[2]);
			} catch (NullPointerException | NumberFormatException e) {
				ChatUtil.error("\2477" + args[2] + "\247e is not a valid number.");
				return;
			}
			Keybind target = KeybindManager.getKeybindById(id);
			if (target != null) {
				target.key = key;
				if (message != "") {
					target.message = message;
				}
				ChatUtil.info("Updated existing keybind with id \2479" + id + "\247e.");
			} else {
				if (message == "") {
					ChatUtil.error("You must provide a message to create new keybind.");
					return;
				}
				KeybindManager.addKeybind(id, key, message);
				ChatUtil.info("Created new keybind with id \2479" + id + "\247e.");
			}
			KeybindManager.saveKeybinds();
		} else if (args[0].equals("remove")) {
			if (args.length < 2) {
				ChatUtil.error("No id specified.");
				return;
			}
			if (args.length > 2) {
				ChatUtil.warning("Too many arguments.");
			}
			int id = 0;
			try {
				id = Integer.parseInt(args[1]);
			} catch (NullPointerException | NumberFormatException e) {
				ChatUtil.error("\2477" + args[1] + "\247e is not a valid number.");
				return;
			}
			if (KeybindManager.removeKeybind(id)) {
				ChatUtil.info("Removed keybind with id \2479" + id + "\247e.");
			} else {
				ChatUtil.error("There's no such keybind with id \2479" + id + "\247e.");
				return;
			}
		} else if (args[0].equals("list")) {
			if (args.length > 1) {
				ChatUtil.warning("Too many arguments.");
			}
			ChatUtil.info("\2473\247l--------------------------------");
			ChatUtil.info("\247lRegistered keybinds:");
			ChatUtil.info("");
			if (KeybindManager.KEYBINDS.size() < 1) {
				ChatUtil.info("\247c\247l<NONE>");
			} else
				for (Keybind keybind : KeybindManager.KEYBINDS) {
					ChatUtil.info("\247c\247lID\247e\247l:\2479" + keybind.id + " \247c\247lKEY\247e\247l:\2479"
							+ keybind.key + " \247a\247lMSG\247e\247l:\2477" + keybind.message);
				}
			ChatUtil.info("\2473\247l--------------------------------");
		} else if (args[0].equals("reset")) {
			if (args.length > 1) {
				ChatUtil.warning("Too many arguments.");
			}
			KeybindManager.resetKeybinds();
			ChatUtil.info("All keybinds have been reset.");
		} else {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
	}
}
