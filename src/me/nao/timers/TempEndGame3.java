package me.nao.timers;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
//import org.bukkit.command.ConsoleCommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.main.game.Main;
import me.nao.manager.ClassArena;
import me.nao.manager.EstadoPartida;
//import me.nao.yamlfile.game.YamlFilePlus;



public class TempEndGame3 {

	
	 
	  private Main plugin;
	  int taskID;
	  private  int  segundo = 10;
	
	
	  
	 public TempEndGame3(Main plugin) {
		 
		
		  this.plugin = plugin;
			 }
	
	
	public void Inicio(String name) {
		  
		
	 BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
     taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){

			
			
		@Override
		public void run() {
			
			
		 //	YamlFilePlus u = new YamlFilePlus(plugin);
		//	FileConfiguration ym = u.getSpecificYamlFile("Arenas",name);
		  	List<String> join1 = plugin.getArenaAllPlayers().get(name);
		  	List<String> s = plugin.getSpectators().get(name);
			
			  for(Player players : Bukkit.getOnlinePlayers()) {
				  
				  if(plugin.PlayerisArena(players)) {
			        	if(plugin.getArenaPlayerInfo().get(players).equals(name)) {
			        		
				       	     if(join1.contains(players.getName()) || s.contains(players.getName())) {
				 	       	  	if(segundo <= 5) {
 					    			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 2F);
 						    		players.sendTitle(""+ChatColor.RED+ChatColor.BOLD+String.valueOf(segundo),""+ChatColor.YELLOW+ChatColor.BOLD+"La partida termina en ", 20, 20, 20);
 						    	//	players.sendMessage(ChatColor.RED+"No hay jugadores suficientes para empezar la partida :(");
 					    		}else {
 					    			players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
 					    		    players.sendMessage(ChatColor.GREEN+"La partida termina en "+ChatColor.RED+segundo);
 					    		}
							    	if(segundo == 0) {
							    		
							    		 
							    		EstadoPartida estadoPartida = plugin.getEstatusArena().get(name);
							    		if(estadoPartida == EstadoPartida.TERMINANDO) {
											
											plugin.getEstatusArena().replace(name,EstadoPartida.ESPERANDO);
											  Bukkit.getScheduler().cancelTask(taskID);	
								   			  Bukkit.getConsoleSender().sendMessage("se detuvo2-- ");
							   	
							    		    List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
											for (Map.Entry<Player,String> datos : list) {
												
												String arenaName = datos.getValue();
												
												if(arenaName.equals(name)) {
													 Player allPlayer = datos.getKey();
												     ClassArena c = new ClassArena(plugin);
												     Bukkit.getScheduler().cancelTask(taskID);	
													 c.EndGame2(allPlayer);
													 
												}
											}
							    		    
							    		    
	
											 
							    			
							    		}
									  
								     }
							    	
								 	
								 	
								   continue;
								 }
				       	 
				        	}
				  }

		        }
			  
			  /*
			  if(segundo == 0 ) {
				  
				  List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
					for (Map.Entry<Player,String> datos : list) {
						
						String arenaName = datos.getValue();
						
						if(arenaName.equals(name)) {
							 Player allPlayer = datos.getKey();
						     ClassArena c = new ClassArena(plugin);
						     Bukkit.getScheduler().cancelTask(taskID);	
							 c.EndGame2(allPlayer);
							 
							 
							 List<String> end = ym.getStringList("End.Commands");
								ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
								for(int i = 0 ; i < end.size(); i++) {
									String texto = end.get(i);
								
									Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",allPlayer.getName())));
									
								}
							 
						}
					}
				 
				  String estadoPartida = plugin.getEstatusArena().get(name);
		    		if(estadoPartida.equals(EstadoPartida.TERMINANDO.toString())) {
						
						plugin.getEstatusArena().replace(name,EstadoPartida.ESPERANDO.toString());
						}
		   			     Bukkit.getScheduler().cancelTask(taskID);	
		   			     Bukkit.getConsoleSender().sendMessage("se detuvo2 1"); 
			  }
			  
			  
			  if(join1.size() == 0) {
					
				  
				  
				  List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
					for (Map.Entry<Player,String> datos : list) {
						
						String arenaName = datos.getValue();
						
						if(arenaName.equals(name)) {
							 Player allPlayer = datos.getKey();
						     ClassArena c = new ClassArena(plugin);
						     Bukkit.getScheduler().cancelTask(taskID);	
							 c.EndGame2(allPlayer);
							 
							 List<String> end = ym.getStringList("End.Commands");
								ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
								for(int i = 0 ; i < end.size(); i++) {
									String texto = end.get(i);
								
									Bukkit.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",allPlayer.getName())));
									
								}
							 
						}
					}
				  
				  String estadoPartida = plugin.getEstatusArena().get(name);
	    		if(estadoPartida.equals(EstadoPartida.TERMINANDO.toString())) {
					
					plugin.getEstatusArena().replace(name,EstadoPartida.ESPERANDO.toString());
					}
	   			     Bukkit.getScheduler().cancelTask(taskID);	
	   			     Bukkit.getConsoleSender().sendMessage("se detuvo2 2");
	   				 
	   		    }

	    		
	    		*/
	    		
			  segundo--;
		
		}
	 },0L,20);
		
	  }
	
	
	
	
	
	  
	
}
