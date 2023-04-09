package me.nao.timers;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitScheduler;

import me.nao.main.game.Main;



public class TpALL {

	
	 private Player player;

	private Main plugin;
	 int taskID;
	 int segundo;
	
	  
	 public TpALL(Main plugin, Player player) {
		 
		  this.player = player;
		  this.plugin = plugin;
			 }
	
	public void Start() {
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){

			@Override
			public void run() {
				
				if(segundo == 0) {
					 List<Map.Entry<Player, Player>> list2 = new ArrayList<>(plugin.TPall().entrySet());
				     for (Map.Entry<Player,Player> e : list2) {
				    	 
				    	 
				    	 Player p = e.getKey();
				    	 
				    	 Location l =player.getLocation();
						 p.teleport(l);
				     }
				}
			
			     
			     
			     player.sendMessage("Tus panas estaran contigo en: "+segundo);
			     
				
			
				segundo--;
			}
			
		},0L,20);
		
	}

	
	  
	
}
