package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.manager.ModManager;
import com.rayferric.havook.util.ChatUtil;

public class PanicCommand extends Command {
	public PanicCommand() {
		super("panic", ".panic", "Disables all mods.");
	}

	@Override
	public void execute(String[] args) {
		for (Mod mod : ModManager.MODS) {
			if(mod.id == "activelist")continue;
			mod.setEnabled(false);
		}
	}
}
