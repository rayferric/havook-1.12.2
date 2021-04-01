package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;

public class ClearChatCommand extends Command {
	public ClearChatCommand() {
		super("clearchat", ".clearchat", "Clears the chat completely.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length > 0) {
			ChatUtil.warning("Too many arguments.");
		}
		ChatUtil.clear(256);
	}
}
