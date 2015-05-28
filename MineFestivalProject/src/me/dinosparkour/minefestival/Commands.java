package me.dinosparkour.minefestival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	private Main plugin;

	public Commands(Main instance) {
		plugin = instance;
	}

	int left = 30;
	
	public void startTimer() {
		new Runnable() {
			public int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);

			float tone = 1.033F;
			
			public void run() {
				if(left == 0) tone = 2.033F;
				
				if(left <= 3) {	
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3F, tone);
					}
				}
				
				if (left > 0) {
					switch (left) {
					case 30:
						Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in 30 seconds!");
						break;
					case 15:
						Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in 15 seconds!");
						break;
					case 10:
						Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in 10 seconds!");
						break;
					case 5:
						Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in 5 seconds!");
						break;
					case 3:
						Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in 3 seconds!");
						break;
					case 2:
						Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in 2 seconds!");
						break;
					case 1:
						Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in 1 second!");
						break;
					}
					left--;

				} else {
					Bukkit.getScheduler().cancelTask(taskID);
					Bukkit.broadcastMessage(ChatColor.RED + "The game has started!");
					plugin.hasStarted = true;
				}
			}
		};
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(lbl.equalsIgnoreCase("MineFestival")) {
			if(args.length != 1) {
				sender.sendMessage(ChatColor.RED + "Incorrect usage!");
				sender.sendMessage(ChatColor.RED + "Available subcommands: countdown, reset");

			} else {
				if(sender.hasPermission("minefestival." + args[0]) || sender.isOp()) {
					if(args[0].equalsIgnoreCase("countdown")) {
						startTimer();

					} else if(args[0].equalsIgnoreCase("reset")) {
						if(left != 30){
							Bukkit.getScheduler().cancelAllTasks();
							sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Cancelled countdown.");
						}
						left = 30;
					}

				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Insufficient permissions.");
				}
			}
		}
		return true;
	}
}