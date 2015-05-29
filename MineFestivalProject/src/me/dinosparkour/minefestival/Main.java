package me.dinosparkour.minefestival;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private Events Events = new Events(this);
	private Commands Commands = new Commands(this);

	public boolean isActive;
	private List<UUID> Players = new ArrayList<UUID>();

	public List<UUID> getPlayers() {
		return Players;
	}

	public void addPlayer(Player p) {
		getPlayers().add(p.getUniqueId());
	}

	public void removePlayer(Player p) {
		getPlayers().remove(p.getUniqueId());
	}

	public void joinGame(Player p) {
		if(!(getPlayers().contains(p.getUniqueId()))) {
			addPlayer(p);
			p.sendMessage(ChatColor.GREEN + "You have been added to the queue!");
		} else p.sendMessage(ChatColor.RED + "You have already joined the queue!");
	}

	public void leaveGame(Player p) {
		if(getPlayers().contains(p.getUniqueId())) {
			removePlayer(p);
			p.sendMessage(ChatColor.GREEN + "You have been removed from the queue!");
		} else p.sendMessage(ChatColor.RED + "You are not in the queue!");

	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(Events, this);
		getCommand("MineFestival").setExecutor(Commands);

		isActive = false;
	}

	public void start() {
		isActive = true;
		for(UUID id : getPlayers()) {
			Player p = Bukkit.getPlayer(id);
			p.sendMessage(ChatColor.RED + "The Festival has begun!");
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 3F, 1F);
		}
		//DO STUFF
	}

	public void end() {
		isActive = false;
		Bukkit.getScheduler().cancelAllTasks();
		Players.clear();
	}
}
