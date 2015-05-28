package me.dinosparkour.minefestival;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;

	public Commands(Main instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		//DO STUFF
		return true;
	}
}