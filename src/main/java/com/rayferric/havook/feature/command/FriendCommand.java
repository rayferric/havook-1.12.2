package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.util.ChatUtil;

public class FriendCommand extends Command {
	public FriendCommand() {
		super("friend", ".friend <add <player>|remove <player>|list|clear>", "Manages friend list.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
		if (args[0].equals("add")) {
			if (args.length < 2) {
				ChatUtil.error("Invalid syntax.");
				ChatUtil.syntax(syntax);
				return;
			}
			if (args.length > 2) {
				ChatUtil.warning("Too many arguments.");
			}
			if (FriendManager.addFriend(args[1])) {
				ChatUtil.info("Added \2477" + args[1] + "\247e to friends.");
			} else {
				ChatUtil.error("Player \2477" + args[1] + " \247cis already in friends.");
				return;
			}
		} else if (args[0].equals("remove")) {
			if (args.length < 2) {
				ChatUtil.error("Invalid syntax.");
				ChatUtil.syntax(syntax);
				return;
			}
			if (args.length > 2) {
				ChatUtil.warning("Too many arguments.");
			}
			if (FriendManager.removeFriend(args[1])) {
				ChatUtil.info("Removed \2477" + args[1] + " \247efrom friends.");
			} else {
				ChatUtil.error("There is no player \2477" + args[1] + " \247cin friends.");
				return;
			}
		} else if (args[0].equals("list")) {
			if (args.length > 1) {
				ChatUtil.warning("Too many arguments.");
			}
			ChatUtil.info("\2473\247l--------------------------------");
			ChatUtil.info("\247lFriends:");
			ChatUtil.info("");
			if (FriendManager.FRIENDS.size() < 1) {
				ChatUtil.info("\247c\247l<NONE>");
			} else
				for (String friend : FriendManager.FRIENDS) {
					ChatUtil.info("\247c" + friend);
				}
			ChatUtil.info("\2473\247l--------------------------------");
		} else if (args[0].equals("clear")) {
			if (args.length > 1) {
				ChatUtil.warning("Too many arguments.");
			}
			FriendManager.clearFriends();
			ChatUtil.info("All friends have been removed.");
		} else {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
	}
}
