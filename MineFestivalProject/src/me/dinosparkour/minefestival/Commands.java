package me.dinosparkour.minefestival;

import java.util.UUID;

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

	private String incorrectUsage = ChatColor.RED + "Incorrect usage!";

	private final int COUNTDOWN_TIME = 30;
	private final int BROADCAST_INTERVAL = 5;

	public void startCountdown(final Player p) {
		new BukkitRunnable(){
			int time = COUNTDOWN_TIME + 1;
			public void run(){
				time--;
				if(time > 0){
					if(time % BROADCAST_INTERVAL == 0 || time <= 5){
						StringBuilder sb = new StringBuilder();
						sb.append(ChatColor.GREEN).append(time).append(" second(s) until the game begins!");
						String startMessage = sb.toString();
						Bukkit.broadcastMessage(startMessage);
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3F, 2.533F);
					}
				}else{
					this.cancel();
					plugin.start();
				}
			}
		}.runTaskTimer(plugin, 0, 20);
	}

	public void getParticipants(CommandSender p) {
		p.sendMessage("Active players:");
		for(UUID id : plugin.getPlayers()) {
			String s = Bukkit.getPlayer(id).toString();
			p.sendMessage(s);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(args.length != 1) {
			sender.sendMessage(incorrectUsage);
			return true;

		}

		if(!(sender.hasPermission("minefestival." + args[0]) || sender.isOp())) {
			sender.sendMessage(ChatColor.RED + "Insufficient permissions.");
			return true;
		}

		switch(args[0].toLowerCase()) {
		case "start": 
			sender.sendMessage(ChatColor.DARK_GREEN + "Started the countdown!");
			for(Player p : Bukkit.getOnlinePlayers()) {
				this.startCountdown(p);
			}
			break;
		case "reset": 
			sender.sendMessage(ChatColor.DARK_GREEN + "Started the countdown!");
			for(Player p : Bukkit.getOnlinePlayers()) {
				this.startCountdown(p);
			}
			break;
		case "stop": 
			if(!Bukkit.getScheduler().getPendingTasks().isEmpty()){
				sender.sendMessage(ChatColor.DARK_RED + "Stopped the countdown!");
				plugin.end();
			} else sender.sendMessage(ChatColor.RED + "The countdown isn't running!");
			break;
		case "force": 
			sender.sendMessage(ChatColor.DARK_GREEN + "Forcing start!");
			plugin.start();
			break;
		case "list":
			this.getParticipants(sender);
			break;
		case "join" :
			if(sender instanceof Player) {
				plugin.joinGame((Player) sender);
			} else sender.sendMessage("You can only execute this command ingame!");
			break;
		case "leave" :
			if(sender instanceof Player) {
				plugin.leaveGame((Player) sender);
			} else sender.sendMessage("You can only execute this command ingame!");
			break;
		default:
			sender.sendMessage(incorrectUsage);
			break;
		}
		return true;
	}
}