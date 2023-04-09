package me.nao.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nao.cosmetics.fireworks.Fireworks;
import me.nao.main.game.Main;
import me.nao.shop.MinigameShop1;
import me.top.users.PointsManager;

public class ClassIntoGame {
	
	private Main plugin;

	
	public ClassIntoGame(Main plugin) {
		this.plugin = plugin;
	}
	
	
	public void PlayerRevive(Player player ,String name) {
		
		if(plugin.PlayerisArena(player)) {
			String arena = plugin.getArenaPlayerInfo().get(player);
		
		
			 List<String> deaths1 =  plugin.getDeaths().get(arena);
			 player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 20.0F, 1F);
			if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 35)) {
				
				
				
			
					for(Player players: Bukkit.getOnlinePlayers()) {
						 if(players.getName().equals(player.getName())) continue;
							 if(plugin.PlayerisArena(players)) {
								 String arena2 = plugin.getArenaPlayerInfo().get(players);
								 if(arena2 != null && arena2.equals(arena)) {
										if(deaths1.contains(players.getName()) && players.getName().equals(name)) {
											
												String world = player.getWorld().getName();
												 int x = player.getLocation().getBlockX();
												 int y = player.getLocation().getBlockY();
												 int z = player.getLocation().getBlockZ();
												 
												 Location l = new Location(Bukkit.getWorld(world), x, y, z);
												 	
												 if(plugin.getDeadmob().containsKey(players)) {
													 String[] coords = plugin.getDeadmob().get(players).split("/");
													    String world2 = coords[0];
													    Double x2 = Double.valueOf(coords[1]);
													    Double y2 = Double.valueOf(coords[2]);
													    Double z2 = Double.valueOf(coords[3]);
													    
													    Location l1 = new Location(Bukkit.getWorld(world2),x2,y2,z2);
													    for(Entity e2 : getNearbyEntites(l1,10)) {
													    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null) {
													    		String name2 = ChatColor.stripColor(e2.getCustomName());
													    		if(name2.contains(name)) {
													    			e2.remove();
													    		}
															
												
															}
													    	
														}
													    plugin.getDeadmob().remove(players);
													    
													    
												 }
												 
												 player.getInventory().removeItem(new ItemStack(Material.DIAMOND,35));
												 
												plugin.revivePlayer(arena, players.getName());
												ClassArena c = new ClassArena(plugin);
												c.JoinTeamLife(players);
												 
												 players.teleport(l);
												 players.setGameMode(GameMode.ADVENTURE);
												 players.setHealth(20);
												 players.sendTitle(ChatColor.GREEN+"Fuiste Revivido",ChatColor.GREEN+"por: "+ChatColor.YELLOW+player.getName(),20,40,20);
												 players.sendMessage(ChatColor.WHITE+"Fuiste Revivido por: "+ChatColor.GREEN+player.getName());
												 player.sendMessage(ChatColor.GREEN+"Reviviste a: "+ChatColor.GOLD+players.getName());
												 
												 
												 player.closeInventory();
												 if(!deaths1.isEmpty()) {
												 		MinigameShop1 m = new MinigameShop1(plugin);
														 m.reviveInv(player); 
											 	}
												 
							
												 
												 for(Player players1 : Bukkit.getOnlinePlayers()) {
													 if(players1.getName().equals(player.getName())) continue;
													 String ar = plugin.getArenaPlayerInfo().get(players);
												
													 if(plugin.PlayerisArena(players1)) {
														 if(ar != null && ar.equals(arena)) {
															 
												     		 players1.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.GREEN+" Revivio a "+ChatColor.WHITE+players.getName());
												     	}
												     		 //esto lo veran los que estan en arena 
													 }
														
												 }
												 
												
											
												 

											break;
										}
									
									      }else {
									    	  player.sendMessage(ChatColor.RED+"Ese jugador salio de la partida o ya fue revivido");
									    	  break;
									      }	
							 }
					
							
						}
					
				
				
				
				
			}else {
				player.sendMessage(ChatColor.RED+"No tienes dinero suficiente para revivir a ese jugador");
			}
			
	
			
			
			
			

			
			
		}
		
		
	}	
	
	
	
	
	
	public void PlayerFallMap(Player player) {
		if(plugin.PlayerisArena(player)) {
			Block block = player.getLocation().getBlock();
			Block b = block.getRelative(0, -1, 0);
			if(b.getType().equals(Material.BARRIER) && player.getGameMode().equals(GameMode.ADVENTURE)) {
			
					if(plugin.getCheckPoint().containsKey(player)) {
						 String[] coords = plugin.getCheckPoint().get(player).split("/");
						    String world2 = coords[0];
						    Double x2 = Double.valueOf(coords[1]);
						    Double y2 = Double.valueOf(coords[2]);
						    Double z2 = Double.valueOf(coords[3]);
						    Location l1 = new Location(Bukkit.getWorld(world2),x2,y2,z2);
						    
						    player.teleport(l1);
						    player.sendMessage(ChatColor.GREEN+"Has usado tu checkpoint. (coloca otro si puedes para evitar morir)");
						    plugin.getCheckPoint().remove(player);
						   
					}
					else{
						ClassArena c = new ClassArena(plugin);
						String arena =plugin.getArenaPlayerInfo().get(player);
					
						
						 String time = plugin.getArenaCronometer().get(arena);
					     plugin.getPlayerCronomet().put(player.getName(),ChatColor.WHITE+time+" SE CAYO DEL MAPA");
						
						plugin.deathPlayer(arena, player.getName());
					//	player.setCustomName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getName());
						
						player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0, 0),
								/* NUMERO DE PARTICULAS */50, 1, 0, 1, /* velocidad */0, null, true);
						player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0),
								/* NUMERO DE PARTICULAS */50, 1, 0, 1, /* velocidad */0, null, true);
						
						if(player.getInventory().getContents().length >= 1) {
							for (ItemStack itemStack : player.getInventory().getContents()) {
								if(itemStack == null) continue;
										
									player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
				                    player.getInventory().removeItem(itemStack);
			                  }
								player.getInventory().clear(); 
						}
						
						
							

						 for(Player players : Bukkit.getOnlinePlayers()) {
							 if(players.getName().equals(player.getName())) continue;
							 String ar = plugin.getArenaPlayerInfo().get(players);
								
							 if(ar != null && ar.equals(arena)) {
						     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio porque "+ChatColor.YELLOW+"SE CAYO DEL MAPA");
						     	}
						     		 //esto lo veran los que estan en arena
						 }
					
					
							
							//c.LeaveTeamLife(player);
							c.JoinTeamDead(player);
						
						player.setGameMode(GameMode.SPECTATOR);
						c.DeathTptoSpawn(player, arena);
						player.setDisplayName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getDisplayName());
						player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"TE CAISTE DEL MAPA", 40, 80, 40);
						player.sendMessage(ChatColor.RED+"Moriste por que "+ChatColor.YELLOW+"Te caiste fuera del mapa");
						player.sendMessage(ChatColor.RED+"\nUsa la hotbar para ver a otros jugadores. "+ChatColor.YELLOW+"\n!!!Solo podras ver a los que estan en tu partida");
						player.sendMessage(ChatColor.GREEN+"Puedes ser revivido por tus compañeros.(Siempre que hayan cofres de Revivir)");
					
					
	
					}
		
					
				
			}
			
		}
	}
	
	
	//TODO Win
	public void PlayerWin(Player player) {
		
		
	
		if(plugin.PlayerisArena(player)) {
			Block block = player.getLocation().getBlock();
			Block b = block.getRelative(0, -1, 0);
			Block b2 = block.getRelative(0, -2, 0);
			if(player.getGameMode().equals(GameMode.ADVENTURE) && b.getType().equals(Material.LIME_STAINED_GLASS) && b2.getType().equals(Material.BEACON)) {
			
			
				int puntos = plugin.getEspecificPlayerPoints().get(player);
				
			
					
				
				String arena = plugin.getArenaPlayerInfo().get(player);
				
				//SI EL JUGADOR LLEGA A LA META SE LE SUMAN SUS PUNTOS RESPECTIVOS
				List<String> are = plugin.getConfig().getStringList("Arena-Points.List");
				if(are.contains(arena)) {
					PointsManager pm = new PointsManager(plugin);
					pm.addPoints(player, puntos);	
				}
				
				
				 plugin.arrivePlayer(arena, player.getName());
			
				 
				 String time = plugin.getArenaCronometer().get(arena);
			     plugin.getPlayerCronomet().put(player.getName(),ChatColor.GREEN+time+" GANO");
				
			     
			     
				player.setGameMode(GameMode.SPECTATOR);
				Fireworks f = new Fireworks(player);
				f.spawnFireballGreenLarge();
				player.sendMessage(ChatColor.GREEN+"Has llegado a la Meta."+ChatColor.YELLOW+ChatColor.BOLD+" Tu tiempo fue: "+ChatColor.GREEN+time);
				player.sendMessage(ChatColor.RED+"\nUsa la hotbar para ver a otros jugadores. "+ChatColor.YELLOW+"\n!!!Solo podras ver a los que estan en tu partida");
			
				
				 for(Player players : Bukkit.getOnlinePlayers()) {
					 if(players.getName().equals(player.getName())) continue;
					 String ar = plugin.getArenaPlayerInfo().get(players);
						
					 if(ar != null && ar.equals(arena)) {
				     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.GREEN+" llego a la Meta.");
				     	}
				     		 //esto lo veran los que estan en arena
				 }
				
				
					
					
					
					
				
			   
			}
		}
	}
	
	//TODO Win2 este es del otro modo
		public void PlayerWin2(Player player) {
			
			if(plugin.PlayerisArena(player)) {
				
				
				ClassArena c = new ClassArena(plugin);
			
				
				int puntos = plugin.getEspecificPlayerPoints().get(player);
				
			
					
				
				String arena = plugin.getArenaPlayerInfo().get(player);
				
				List<String> are = plugin.getConfig().getStringList("Arena-Points.List");
				if(are.contains(arena)) {
					PointsManager pm = new PointsManager(plugin);
					pm.addPoints(player, puntos);	
				}
				
				c.EndTptoSpawn(player, arena);
				
			}
			
			
		}
	
	
	//TODO PlayerLost
	public void PlayerLost(Player player) {
		

		if(plugin.PlayerisArena(player)) {
		
			
						String arena = plugin.getArenaPlayerInfo().get(player);
						
						
					//	player.setCustomName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getName());
						 
					//	YamlFilePlus u = new YamlFilePlus(plugin);
					//	FileConfiguration ym = u.getSpecificYamlFile("Arenas",arena);
						
						plugin.deathPlayer(arena, player.getName());
						
						 String time = plugin.getArenaCronometer().get(arena);
					     plugin.getPlayerCronomet().put(player.getName(),ChatColor.RED+time+" SIN TIEMPO");
						
						player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0, 0),
								/* NUMERO DE PARTICULAS */50, 0.5, 1, 0.5, /* velocidad */0, null, true);

						
						if(player.getInventory().getContents().length >= 1) {
							for (ItemStack itemStack : player.getInventory().getContents()) {
								if(itemStack == null) continue;
										
									player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
				                    player.getInventory().removeItem(itemStack);
			                  }
								player.getInventory().clear(); 
						}
						ClassArena c = new ClassArena(plugin);
						//c.LeaveTeamLife(player);
						c.JoinTeamDead(player);
					     player.setDisplayName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getDisplayName());
						 player.setGameMode(GameMode.SPECTATOR);
						 player.sendMessage(ChatColor.RED+"Se te acabo el tiempo has perdido.");
				
				
				
					
					
				
				 
			
		}
		
		
	}
	
	

	
	
	
	public void PlayerDeadInArena(Player player) {
		if(plugin.PlayerisArena(player)) {
			
				
				
					String arena = plugin.getArenaPlayerInfo().get(player);
					
				//	player.setCustomName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getName());
					 

					plugin.deathPlayer(arena, player.getName());
					 String time = plugin.getArenaCronometer().get(arena);
				     plugin.getPlayerCronomet().put(player.getName(),ChatColor.RED+time+" MURIO EN EL MAPA");
					Fireworks f = new Fireworks(player);
					f.spawnFireballRedLarge();
					player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0, 0),
							/* NUMERO DE PARTICULAS */50, 0, 0, 0, /* velocidad */0, null, true);
					player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0),
							/* NUMERO DE PARTICULAS */50, 0, 0, 0, /* velocidad */0, null, true);
					
					if(player.getInventory().getContents().length >= 1) {
						for (ItemStack itemStack : player.getInventory().getContents()) {
							if(itemStack == null) continue;
									
								player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
			                    player.getInventory().removeItem(itemStack);
		                  }
							player.getInventory().clear(); 
					}
					
					player.setDisplayName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getDisplayName());
					player.sendMessage(ChatColor.RED+"\nUsa la hotbar para ver a otros jugadores. "+ChatColor.YELLOW+"\n!!!Solo podras ver a los que estan en tu partida");
					player.sendMessage(ChatColor.GREEN+"Puedes ser revivido por tus compañeros.(Siempre que hayan cofres de Revivir)");
				//	player.setCustomName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getName());
					 
					ClassArena c = new ClassArena(plugin);
					//c.LeaveTeamLife(player);
					c.JoinTeamDead(player);
					
				
				
				
			
		}
		
	}
	
	public void PlayerAddPoints(Player player) {
		
		 HashMap <Player,Integer> h = plugin.getEspecificPlayerPoints();
		 int puntaje = h.get(player); 
		 h.replace(player, puntaje+1);	
		
		
	}
	
	public List<Entity> getNearbyEntites(Location l , int size){
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : l.getWorld().getEntities()) {
			if(l.distance(e.getLocation()) <= size) {
				entities.add(e);
			}
		}
		return entities;
		
		
	}
	
	

}
