package me.dinosparkour.minefestival;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private Events Events = new Events(this);
	private Commands Commands = new Commands(this);
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(Events, this);
		this.getCommand("FireTools").setExecutor(Commands);
		// DO STUFF
	}
}
