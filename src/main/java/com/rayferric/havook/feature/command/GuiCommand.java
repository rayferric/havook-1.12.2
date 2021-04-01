package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.gui.LongMessageSenderGui;
import com.rayferric.havook.gui.MainMenuGui;
import com.rayferric.havook.handler.ClientTickHandler;
import com.rayferric.havook.util.ChatUtil;

public class GuiCommand extends Command {

	public GuiCommand() {
		super("gui", ".gui", "Opens graphical interface. This command is binded to RSHIFT key by default.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length > 0) {
			ChatUtil.warning("Too many arguments.");
		}
		ClientTickHandler.queueGui(new MainMenuGui());
	}

}
