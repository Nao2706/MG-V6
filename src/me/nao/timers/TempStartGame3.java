package me.nao.timers;





import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.main.game.Main;
import me.nao.manager.ClassArena;
import me.nao.manager.EstadoPartida;
import me.nao.yamlfile.game.YamlFilePlus;
import net.md_5.bungee.api.ChatColor;



public class TempStartGame3 {

	
	// private Player player;
	 private Main plugin;

	 int taskID;
	 
	
	  
	 public TempStartGame3(Main plugin) {
		 
		//  this.player = player;
		  this.plugin = plugin;
			 }
	
	
	public void StartInGame(String name) {
		FileConfiguration config = plugin.getConfig();
		
		
		
		
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			 
        int  segundo = config.getInt("CountDownPreLobby");
      
			
		@Override
		public void run() {
			
						
							
			YamlFilePlus u = new YamlFilePlus(plugin);
			FileConfiguration ym = u.getSpecificYamlFile("Arenas",name);
			List<String> join1 = plugin.getArenaAllPlayers().get(name);
			
			        
	        for(Player players : Bukkit.getOnlinePlayers()) {
	        	if(plugin.PlayerisArena(players)) {
		        	if(plugin.getArenaPlayerInfo().get(players).equals(name)) {
		        		
			       	     if(join1.contains(players.getName())) {
					    	 
			       	    	 
					       	  	if(segundo <= 5) {
					       	  		if(segundo != 0) {
					       	  		players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
						    		players.sendTitle(""+ChatColor.AQUA+ChatColor.BOLD+String.valueOf(segundo),""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"La partida empieza en ", 20, 20, 20);
					       	  		}
					    			
						    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
					    		}else {
					    			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
					    		    players.sendMessage(ChatColor.DARK_PURPLE+"La partida empieza en "+ChatColor.RED+segundo);
					    		}
			       	    	 
			       	    	 
			       	    	 
						    	if(segundo == 0) {
						    		  Bukkit.getScheduler().cancelTask(taskID);	
						    		 
						    		  EstadoPartida estadoPartida = plugin.getEstatusArena().get(name);
						    		if(estadoPartida == EstadoPartida.COMENZANDO) {
						    		  
						    			plugin.getEstatusArena().replace(name,EstadoPartida.JUGANDO);
						    			
						    			
						    			List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
		   								for (Map.Entry<Player,String> datos : list) {
		   									
		   									String arenaName = datos.getValue();
		   									
		   									if(arenaName.equals(name)) {
		   										 Player allPlayer = datos.getKey();
		   										 ClassArena c = new ClassArena(plugin);
											     c.TptoSpawnArena(allPlayer, name);
		   										 
		   										 //allPlayer.sendMessage(ChatColor.GREEN+"OBJETIVO BETA: Todos contra todos el ultimo en pie gana");
		   										
										     	 
		   									}
		   								}
						
		   							 
		   								 TempInGame3 t = new TempInGame3(plugin);
							     	     //t.Inicio(allPlayer,arenaName);
							     	     t.Inicio(name);
		   								
		   							   
		   							
										
						    			
						    		}
								  
							     }
						    	
						    	if(join1.size() == ym.getInt("Max-Player") && segundo > 10) {
						    		
						    		 segundo = 10;
						    		 players.sendMessage(ChatColor.YELLOW+"Se alcanzo la cantidad maxima de jugadores la partida empieza en "+segundo+"s");
						    		
						    	}
							        
						      if(join1.size() < ym.getInt("Min-Player")) {
					   			     Bukkit.getScheduler().cancelTask(taskID);	
					   			     Bukkit.getConsoleSender().sendMessage("se detuvo");
					   				 players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
					   		    }
							        
					    		
							 	
							   continue;
							 }
			        	}
	        	}

	        }
	        
	    	if(join1.size() == 0) {
  			     Bukkit.getScheduler().cancelTask(taskID);	
  			     Bukkit.getConsoleSender().sendMessage("se detuvo");
  				 
  		     	}
					        
				
   							    
							
							
							
						
			 segundo--;
		//   player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN+"Cronometro : "+hora+"h "+minuto+"m "+segundo+"s " ));
		 //  System.out.println("Cuenta atras : "+hora+"h "+minuto+"m "+segundo+"s " );
			
			
			}
		},0L,20);
		
	  }
	
	
	
	
	
	  
	
}
