package me.dinosparkour.minefestival;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

	private Main plugin;

	public Events(Main instance) {
		plugin = instance;
	}
	
	private boolean autoJoin = false;

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String pName = p.getName();

		StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.GRAY).append("[").append(ChatColor.GREEN).append("+").append(ChatColor.GRAY).append("] ").append(pName);
		String joinMessage = sb.toString();

		if(autoJoin) plugin.addPlayer(p);
		e.setJoinMessage(joinMessage);

	}

	//@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String pName = p.getName();

		StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.GRAY).append("[").append(ChatColor.RED).append("-").append(ChatColor.GRAY).append("] ").append(pName);
		String quitMessage = sb.toString();

		if(plugin.getPlayers().contains(pName)) plugin.removePlayer(p);;
		e.setQuitMessage(quitMessage);
	}

	@EventHandler
	public void gameSigns(PlayerInteractEvent e) {
		if(!e.hasBlock()) return;
		Material mat = e.getClickedBlock().getType();
		if(mat == Material.SIGN_POST || mat == Material.WALL_SIGN) {
			Sign s = (Sign) e.getClickedBlock().getState();
			if(s.getLine(0).contains("MineFestival")) {
				Player p = e.getPlayer();
				String command = s.getLine(1).toLowerCase();
				
				if(command.contains("join")) {
					plugin.joinGame(p);
				} else if(command.contains("leave")) {
					plugin.leaveGame(p);
				}
			}
		}
	}
}