package me.dinosparkour.minefestival;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private Events Events = new Events(this);
	private Commands Commands = new Commands(this);
	
	public boolean isActive;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(Events, this);
		getCommand("MineFestival").setExecutor(Commands);
		
		isActive = false;
	}
	
	public void start() {
		isActive = true;
		//DO STUFF
	}
	
	public void end() {
		isActive = false;
	}
}
