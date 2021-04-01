package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.util.ChatUtil;
import net.minecraft.client.Minecraft;

public class SayCommand extends Command {
	public SayCommand() {
		super("say", ".say <message>", "Sends given message to chat.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
		String message = args[0];
		for (int i = 1; i < args.length; i++) {
			message += " " + args[i];
		}
		Minecraft.getMinecraft().player.sendChatMessage(message);
	}
}
