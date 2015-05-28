package me.dinosparkour.minefestival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Commands implements CommandExecutor {

	private Main plugin;

	public Commands(Main instance) {
		plugin = instance;
	}

	private final int COUNTDOWN_TIME = 30;
	private final int BROADCAST_INTERVAL = 5; /* You want to broadcast each x seconds. That's now 5 */

	/**
	 * Start the countdown for ONE player, you can also start this for every person online
	 * @param p Player object
	 */
	public void startCountdown(final Player p){
		new BukkitRunnable(){
			int time = COUNTDOWN_TIME + 1; /* Set a variable 'time' */
			public void run(){
				time--; /* Remove time */
				if(time > 0){
					if(time % BROADCAST_INTERVAL == 0 || time <= 5){
						/* If it's divisible is 5, OR or the time is smaller than 6*/
						StringBuilder sb = new StringBuilder();
						sb.append(ChatColor.GREEN).append(time).append(" second(s) until the game begins!");
						String startMessage = sb.toString();
						Bukkit.broadcastMessage(startMessage);
						p.playSound(p.getEyeLocation(), Sound.NOTE_PIANO, 3F, 2.533F);
						/* In this case, 30, 25, 20, 15, 10, 5, 4, 3, 2 1*/
					}
				}else{
					/* Time over */
					this.cancel();
					plugin.start();
				}
			}
		}.runTaskTimer(plugin, 0, 20); /* 20 ticks = 1 second*/
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
						for(Player p : Bukkit.getOnlinePlayers()) {
							this.startCountdown(p);
						}

					} else if(args[0].equalsIgnoreCase("reset")) {
						sender.sendMessage(ChatColor.DARK_RED + "Reset the MineFestival!");
						plugin.end();
					}

				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Insufficient permissions.");
				}
			}
		}
		return true;
	}
}