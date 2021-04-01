package com.rayferric.havook.manager;

import java.util.ArrayList;
import java.util.List;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.feature.command.AuthorCommand;
import com.rayferric.havook.feature.command.BindCommand;
import com.rayferric.havook.feature.command.ClearChatCommand;
import com.rayferric.havook.feature.command.CopyDataCommand;
import com.rayferric.havook.feature.command.CopyItemCommand;
import com.rayferric.havook.feature.command.ForwardCommand;
import com.rayferric.havook.feature.command.FriendCommand;
import com.rayferric.havook.feature.command.GiveCommand;
import com.rayferric.havook.feature.command.GuiCommand;
import com.rayferric.havook.feature.command.HatCommand;
import com.rayferric.havook.feature.command.HeadCommand;
import com.rayferric.havook.feature.command.HelpCommand;
import com.rayferric.havook.feature.command.LoreCommand;
import com.rayferric.havook.feature.command.ModCommand;
import com.rayferric.havook.feature.command.NbtCommand;
import com.rayferric.havook.feature.command.PanicCommand;
import com.rayferric.havook.feature.command.RenameCommand;
import com.rayferric.havook.feature.command.RepairCommand;
import com.rayferric.havook.feature.command.SayCommand;
import com.rayferric.havook.feature.command.TpCommand;

public class CommandManager {
	public static List<Command> COMMANDS = new ArrayList<Command>();

	public static void loadCommands() {
		COMMANDS.add(new AuthorCommand());
		COMMANDS.add(new BindCommand());
		COMMANDS.add(new ClearChatCommand());
		COMMANDS.add(new CopyDataCommand());
		COMMANDS.add(new CopyItemCommand());
		COMMANDS.add(new ForwardCommand());
		COMMANDS.add(new FriendCommand());
		COMMANDS.add(new GiveCommand());
		COMMANDS.add(new GuiCommand());
		COMMANDS.add(new HatCommand());
		COMMANDS.add(new HeadCommand());
		COMMANDS.add(new HelpCommand());
		COMMANDS.add(new LoreCommand());
		COMMANDS.add(new ModCommand());
		COMMANDS.add(new NbtCommand());
		COMMANDS.add(new PanicCommand());
		COMMANDS.add(new RenameCommand());
		COMMANDS.add(new RepairCommand());
		COMMANDS.add(new SayCommand());
		COMMANDS.add(new TpCommand());
	}
}
