package me.nao.timers;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import me.nao.main.game.Main;
import me.nao.manager.ClassArena;
import me.nao.manager.ClassIntoGame;
import me.nao.manager.EstadoPartida;
import me.nao.yamlfile.game.YamlFilePlus;
//import net.md_5.bungee.api.ChatMessageType;
//import net.md_5.bungee.api.chat.TextComponent;


public class TempInGame2 {

	
	  
	
	   private Main plugin;
	   int taskID;
	  
	
	  
	 public TempInGame2(Main plugin) {
		 
		  this.plugin = plugin;
		  
		  
	 }
	
	
	public void Inicio(String name) {
		  
      
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			YamlFilePlus u = new YamlFilePlus(plugin);
			FileConfiguration ym = u.getSpecificYamlFile("Arenas",name);
		
			String timer[] = ym.getString("Timer-H-M-S").split(",");
			int hora = Integer.valueOf(timer[0]);
			int minuto = Integer.valueOf(timer[1]);
			int segundo = Integer.valueOf(timer[2]);
		
		    int  segundo2 = 0;
			int  minuto2 = 0 ;
			int  hora2 = 0 ;
																						//APARENTEMENTE ESTOS DOS SON BarFlag. unas flags
		
			//bossbar al maximo
		    double pro = 1.0;
		   
		    //Transformar minutos - hora a segundos
		 	double minuto3 = minuto * 60;
		 	double hora3 = hora * 3600;
		 	//todos los seg
		 	double total = hora3 + minuto3 + segundo;
		 	//calculo para hacer uba reduccion
		 	double time = 1.0 / total;
		@Override
		public void run() {
			BossBar boss = plugin.getBossBarTime().get(name);
			//HORA> MINUTO
			//MINUTO > SEGUND
			
			//CRONOMETRO
			 if (segundo2 != 60 ){
					 segundo2++; 
				}
			 if (minuto2 != 60 && segundo2 == 60) {
					segundo2 = 0;
					minuto2 ++;
				}
			 if (hora2 != 60 && minuto2 == 60) {
					minuto2 = 0;
					hora2 ++;
				}
						 //60:30:00
			 //String fs = String.format("%02d", numef);
			 String seg = String.format("%02d", segundo2);
			 String min = String.format("%02d", minuto2);
			 String hor = String.format("%02d", hora2);
			 
			 plugin.getArenaCronometer().put(name,hor+":"+min+":"+seg);
			
			 
			
			//TIMER
			 if (segundo != 0){
				 segundo--; 
			 }
			 if (minuto != 0 && segundo == 0) {
					segundo = 60;
					minuto --;
				}
			 if (hora != 0 && minuto == 0) {
					minuto = 60;
					hora --;
				}
			 	
			 
			 //boss = Bukkit.createBossBar("Hello",BarColor.GREEN, BarStyle.SOLID,  null ,null);
				List<String> join1 = plugin.getArenaAllPlayers().get(name);
				List<String> alive1 = plugin.getAlive().get(name);
				List<String> arrive1 = plugin.getArrive().get(name);
				List<String> s = plugin.getSpectators().get(name);
			 
		        for(Player players : Bukkit.getOnlinePlayers()) {
		        	if(plugin.PlayerisArena(players)) {
		        	  if(plugin.getArenaPlayerInfo().get(players).equals(name)) {
		        		  
		        		  
		        		 
		        		
					     if(join1.contains(players.getName()) || s.contains(players.getName())) {
					    	 
					    	 
					    	 
					    	if(segundo == 0 && minuto == 0) {
					    		
					    		 Bukkit.getScheduler().cancelTask(taskID);	
					    		 
					  		   if(!arrive1.contains(players.getName()) && alive1.contains(players.getName())) {
									  ClassIntoGame c = new ClassIntoGame(plugin);
									  c.PlayerLost(players);
							
									
									  players.sendMessage(ChatColor.YELLOW+"Tu tiempo fue: "+hora+"h "+minuto+"m "+segundo+"s ");
									  players.sendMessage(ChatColor.RED+"No llegaste a tiempo F ");
							   }
					    		 
					    		 
					  		 FileConfiguration config = plugin.getConfig();
								int rango = config.getInt("Remove-Armor-Stands");
								
								if(plugin.PlayerisArena(players)) {
									for(Entity e1 : getNearbyEntites(players.getLocation(),rango)) {
										
										if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
								    		String namea = ChatColor.stripColor(e1.getCustomName());
								    		if(namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
								    			e1.remove();
								    		}
												//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
										}
									
										
										if(e1.getType() == EntityType.DROPPED_ITEM) {
											e1.remove();
										}
										
										
										if(e1.getType() != EntityType.PLAYER && e1.getType() != EntityType.ARMOR_STAND && e1.getType() != EntityType.FIREWORK) {
											e1.remove();
										}
										
									}

								}
								
								List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getDeadmob().entrySet());
   								for (Map.Entry<Player,String> datos : list) {
   									if(!list.isEmpty()) {
   										String[] coords = datos.getValue().split("/");
									    String world = coords[0];
									    Double x = Double.valueOf(coords[1]);
									    Double y = Double.valueOf(coords[2]);
									    Double z = Double.valueOf(coords[3]);
									    
									    Location l = new Location(Bukkit.getWorld(world),x,y,z);
									    for(Entity e2 : getNearbyEntites(l,rango)) {
									    	
									    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null) {
									    		String namea = ChatColor.stripColor(e2.getCustomName());
									    		if(namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
									    			e2.remove();
									    		}
												
												//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
											}
									    	
									    	if(e2.getType() == EntityType.DROPPED_ITEM) {
												e2.remove();
											}
											
									    	if(e2.getType() != EntityType.PLAYER && e2.getType() != EntityType.ARMOR_STAND && e2.getType() != EntityType.FIREWORK) {
												e2.remove();
											}
									    	
										}
									    plugin.getDeadmob().remove(datos.getKey());
   									}
   									
								    
   								}
												
					  		   
   								EstadoPartida estadoPartida = plugin.getEstatusArena().get(name);
					    		if(estadoPartida == EstadoPartida.JUGANDO) {
									
									plugin.getEstatusArena().replace(name,EstadoPartida.TERMINANDO);
							
  								
  								     ClassArena c = new ClassArena(plugin);
								     c.Top(name);
									 
								 
									 TempEndGame2 t = new TempEndGame2(plugin);
						     	     //t.Inicio(allPlayer,arenaName);
						     	     t.Inicio(name);
  									
					    			
					    		}
							  
						     }  
						    
					    	//TODO FORZAR SALIDA
					    	EstadoPartida estadoPartida2 = plugin.getEstatusArena().get(name);
				    		if(estadoPartida2 == EstadoPartida.TERMINANDO && plugin.getStopArenas().contains(name)) {
				    			Bukkit.getScheduler().cancelTask(taskID);
								
				    			
								 TempEndGame2 t = new TempEndGame2(plugin);
					     	     //t.Inicio(allPlayer,arenaName);
					     	     t.Inicio(name);
					     	     if(plugin.getStopArenas().remove(name));
					     	    players.sendMessage(ChatColor.RED+"La partida a sido Forzada a Terminar no habran resultados.");	
				    			
				    		}
						        
					  	  if(alive1.isEmpty()) {
					  		 
							  Bukkit.getScheduler().cancelTask(taskID);
							     
							
							  FileConfiguration config = plugin.getConfig();
								int rango = config.getInt("Remove-Armor-Stands");
								
								if(plugin.PlayerisArena(players)) {
									for(Entity e1 : getNearbyEntites(players.getLocation(),rango)) {
										
										if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
								    		String namea = ChatColor.stripColor(e1.getCustomName());
								    		if(namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
								    			e1.remove();
								    		}
												//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
										}
										
										if(e1.getType() == EntityType.DROPPED_ITEM) {
											e1.remove();
										}
										
										if(e1.getType() != EntityType.PLAYER && e1.getType() != EntityType.ARMOR_STAND && e1.getType() != EntityType.FIREWORK) {
											e1.remove();
										}
										
									}

								}
												
								List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getDeadmob().entrySet());
   								for (Map.Entry<Player,String> datos : list) {
   									if(!list.isEmpty()) {
   										String[] coords = datos.getValue().split("/");
									    String world = coords[0];
									    Double x = Double.valueOf(coords[1]);
									    Double y = Double.valueOf(coords[2]);
									    Double z = Double.valueOf(coords[3]);
									    
									    Location l = new Location(Bukkit.getWorld(world),x,y,z);
									    for(Entity e2 : getNearbyEntites(l,rango)) {
									    	
									    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null) {
									    		String namea = ChatColor.stripColor(e2.getCustomName());
									    		if(namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
									    			e2.remove();
									    		}
												
												//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
											}
									    	
									    	if(e2.getType() == EntityType.DROPPED_ITEM) {
												e2.remove();
											}
											
											if(e2.getType() != EntityType.PLAYER && e2.getType() != EntityType.ARMOR_STAND && e2.getType() != EntityType.FIREWORK) {
												e2.remove();
											}
									    	
										}
									    plugin.getDeadmob().remove(datos.getKey());
   									}
   									
								    
   								}
				    		
							  
							  players.sendMessage(ChatColor.RED+"Todos los jugadores murieron ");
							  
							  EstadoPartida estadoPartida = plugin.getEstatusArena().get(name);
								
								if(estadoPartida == EstadoPartida.JUGANDO) {
													
									
									plugin.getEstatusArena().replace(name,EstadoPartida.TERMINANDO);
									
								
										
										
											 ClassArena c = new ClassArena(plugin);
										     c.Top(name);
											
											 TempEndGame2 t = new TempEndGame2(plugin);
											 t.Inicio(name);
											 
											 players.sendMessage(ChatColor.GREEN+"Todos los jugadores fueron eliminados F ");
											 
										
									
						     	}
							  
							  
						  }
						 	
					  	  
						  if(alive1.size() == arrive1.size()) {
							  
							  boss.setTitle(""+ChatColor.BOLD+ChatColor.WHITE+"FIN");
							  Bukkit.getScheduler().cancelTask(taskID);
							  if(arrive1.contains(players.getName())) {
								  players.sendMessage(ChatColor.GREEN+"Todos los jugadores con vida han llegado a la meta "+arrive1);
							  }
							
							  EstadoPartida estadoPartida = plugin.getEstatusArena().get(name);
								FileConfiguration config = plugin.getConfig();
								int rango = config.getInt("Remove-Armor-Stands");
								
								
									for(Entity e1 : getNearbyEntites(players.getLocation(),rango)) {
										
										if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
								    		String namea = ChatColor.stripColor(e1.getCustomName());
								    		if(namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
								    			e1.remove();
								    		}
												//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
										}
									
										
										if(e1.getType() == EntityType.DROPPED_ITEM) {
											e1.remove();
										}
										
										if(e1.getType() != EntityType.PLAYER && e1.getType() != EntityType.ARMOR_STAND && e1.getType() != EntityType.FIREWORK) {
											e1.remove();
										}
										
									
										
									}
									
									
									List<Map.Entry<Player, String>> list = new ArrayList<>(plugin.getDeadmob().entrySet());
	   								for (Map.Entry<Player,String> datos : list) {
	   									if(!list.isEmpty()) {
	   										String[] coords = datos.getValue().split("/");
										    String world = coords[0];
										    Double x = Double.valueOf(coords[1]);
										    Double y = Double.valueOf(coords[2]);
										    Double z = Double.valueOf(coords[3]);
										    
										    Location l = new Location(Bukkit.getWorld(world),x,y,z);
										    for(Entity e2 : getNearbyEntites(l,rango)) {
										    	
										    	
										    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null) {
										    		String namea = ChatColor.stripColor(e2.getCustomName());
										    		if(namea.contains("RECIPIENTE DE ALMAS") || namea.contains("FALTAN") || namea.contains("aqui.")) {
										    			e2.remove();
										    		}
													
													//e1.getWorld().spawnParticle(Particle.FLASH, e1.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
												}
										    	
										    	if(e2.getType() == EntityType.DROPPED_ITEM) {
													e2.remove();
												}
												
										    	if(e2.getType() != EntityType.PLAYER && e2.getType() != EntityType.ARMOR_STAND && e2.getType() != EntityType.FIREWORK) {
													e2.remove();
												}
										    	
											}
										    plugin.getDeadmob().remove(datos.getKey());
	   									}
	   									
									    
	   								}
									
	   								
								
									
									
								
												
									if(estadoPartida == EstadoPartida.JUGANDO) {
																
										plugin.getEstatusArena().replace(name,EstadoPartida.TERMINANDO);
										
											
					
								
								
									
									players.sendMessage(ChatColor.GREEN+"Todos los jugadores con vida llegaron ");
									 ClassArena c = new ClassArena(plugin);
								     c.Top(name);
									
								    TempEndGame2 t = new TempEndGame2(plugin);
								    t.Inicio(name);
									 
										
												 
											
										
							     	}
							  
						  }
						  
					
						 
						 
						  removeTrapArrows(players);
						  getNearbyBlocks2(players);
						  getNearbyBlocks3(players);
						
						  
						  String s1 = String.valueOf(segundo);
						  if(s1.endsWith("0")) {
							  getNearbyBlocks(players);
						  }
							
					    	
					    	 

						  boss.setVisible(true);
						  if(hora <= 0 && minuto >= 10) {
							  boss.setColor(BarColor.GREEN);
						  }

						  if(hora <= 0 && minuto <= 9) {
							  boss.setColor(BarColor.YELLOW);						  
							  }
						  
				          if(hora <= 0 && minuto <= 1 ) {
							  boss.setColor(BarColor.RED);
							
						  } 						 
				        //  players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_RED+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " ));

						   
				          boss.setProgress(pro);
				          //reduccion de tiempo en boss bar
						    pro = pro - time;
						//	if(pro <= 0) {
						//		pro = 1.0;
						//	}
						  
						 
						  boss.setTitle(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_RED+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " );
						
						
						  //boss.addPlayer(players);
						  
						 // boss.setTitle(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.DARK_RED+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " );
						  
						 }
		        	  }
		           }
		        	
		        }
			 
		    	if(segundo == 0 && minuto == 0) {
		    		 
		    		EstadoPartida estadoPartida = plugin.getEstatusArena().get(name);
		    		if(estadoPartida == EstadoPartida.JUGANDO) {
						
						plugin.getEstatusArena().replace(name,EstadoPartida.TERMINANDO);
	
						 Bukkit.getConsoleSender().sendMessage("se detuvo time 0");
						
						 TempEndGame2 t = new TempEndGame2(plugin);
			     	     //t.Inicio(allPlayer,arenaName);
			     	     t.Inicio(name);
							
		    			
		    		}
		    	}
			 
		    	if(join1.size() == 0) {
		    		
		     		EstadoPartida estadoPartida = plugin.getEstatusArena().get(name);
		    		if(estadoPartida == EstadoPartida.JUGANDO) {
		    			
		    			
						plugin.getEstatusArena().replace(name,EstadoPartida.TERMINANDO);
	
						
						 TempEndGame2 t = new TempEndGame2(plugin);
			     	     //t.Inicio(allPlayer,arenaName);
			     	     t.Inicio(name);
							
		    			
		    		}
	   			     Bukkit.getScheduler().cancelTask(taskID);	
	   			     Bukkit.getConsoleSender().sendMessage("se detuvo1-w2");
	   				 
	   		     	}
		

	
		  
			
		  
		 //  System.out.println("Cuenta atras : "+hora+"h "+minuto+"m "+segundo+"s " );
			
			
			}
		},0L,20);
		
	  }
	//TODO NERBYBLOCK
	public void getNearbyBlocks(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Mob-Spawn-Range") ;
		
		if (r.getType().equals(Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode().equals(GameMode.ADVENTURE) ) {
					
								if(a.getType() == Material.GREEN_CONCRETE && b.getType() == Material.BEDROCK) {
								
									zombi(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.BLUE_CONCRETE && b.getType() == Material.BEDROCK) {
									
									drowned(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.ORANGE_CONCRETE && b.getType() == Material.BEDROCK) {
									
									husk(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.LIGHT_GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
									
									villagerz(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.WHITE_CONCRETE && b.getType() == Material.BEDROCK) {
									
									skeleton(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.BLACK_CONCRETE && b.getType() == Material.BEDROCK) {
									skeletonDark(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.GRAY_CONCRETE && b.getType() == Material.BEDROCK) {
									vindicador(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.RED_CONCRETE && b.getType() == Material.BEDROCK) {
									Pillager(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.PURPLE_CONCRETE && b.getType() == Material.BEDROCK) {
									evoker(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.LIME_CONCRETE && b.getType() == Material.BEDROCK) {
									Creeper(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								
								if(a.getType() == Material.MAGENTA_CONCRETE && b.getType() == Material.BEDROCK) {
									Bruja(a.getWorld(),a.getLocation().getBlockX(),a.getLocation().getBlockY(),a.getLocation().getBlockZ());
								}
								//player.sendMessage("Hay un bloque de diamante en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
							}

						

					

					}
					;
				}
				;
			}
			;


		}
	}
	
	
	
	
	//TODO NERBYBLOCK 2
	public void getNearbyBlocks2(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Item-Spawn-Range") ;
		
		if (r.getType().equals(Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode().equals(GameMode.ADVENTURE) ) {
							
						
								if(a.getType() == Material.NETHERITE_BLOCK && b.getType() == Material.BEDROCK) {

									player.getWorld().playSound(player.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
			
								}
								
								if(a.getType() == Material.DIAMOND_BLOCK && b.getType() == Material.BEDROCK) {
									player.getWorld().playSound(player.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.EMERALD_BLOCK && b.getType() == Material.BEDROCK) {
									
									player.getWorld().playSound(player.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.IRON_BLOCK && b.getType() == Material.BEDROCK) {
									player.getWorld().playSound(player.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.GOLD_BLOCK && b.getType() == Material.BEDROCK) {
									
									player.getWorld().playSound(player.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.GOLD_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
							
							
						
								
								//player.sendMessage("Hay un bloque de diamante en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
							}

						

					

					}
					;
				}
				;
			}
			;


		}
	}	
	
	//TODO SPAWN GENERATOR 3
	public void getNearbyBlocks3(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Item-Spawn-Range") ;
		
		if (r.getType().equals(Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode().equals(GameMode.ADVENTURE) ) {
							
						
							if(a.getType() == Material.END_PORTAL_FRAME && b.getType() == Material.BEDROCK) {
								DetectChestAndGenerator(a);
							}
			
								//player.sendMessage("Hay un bloque de diamante en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
							}

						

					

					}
					;
				}
				;
			}
			;


		}
	}
	
	
	public void DetectChestAndGenerator(Block b) {
		//FileConfiguration config = plugin.getConfig();
		Block block = b.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		
	//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
			int rango = 30;
		//	Powerable pw = (Powerable) r.getBlockData();
		//	if(pw.isPowered()) {

				for (int x = -rango; x < rango+1; x++) {
					for (int y = -rango; y < rango+1; y++) {
						for (int z = -rango; z < rango+1; z++) {
	
							Block a = r.getRelative(x, y, z);
	
							// setea bloques en esos puntos
							
	
								if(a.getType() == Material.CHEST) {
									
									  Chest chest = (Chest) a.getState();
									  if(chest.getCustomName() != null) {
										  if(chest.getCustomName().contains("GENERATOR")) {
											  if(!chest.getInventory().isEmpty()) {
													for (ItemStack itemStack : chest.getInventory().getContents()) {
														if(itemStack == null) continue;
															b.getWorld().dropItem(b.getLocation().add(0.5,1,0.5), itemStack);
								
										              }
											  }
										  
										  }
							          }
								
									//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
								}
	
		
						};
					};
				};
		//	}	
			//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

		//}
	}
	
	//TODO Creeper
    public void Blaze(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.BLAZE);
		Blaze s = (Blaze) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	
	//TODO Creeper
    public void Creeper(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.CREEPER);
		Creeper s = (Creeper) entidad;
		s.setExplosionRadius(10);
		s.setMaxFuseTicks(3);
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }

	//TODO evoker
    public void villagerz(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE_VILLAGER);
		ZombieVillager s = (ZombieVillager) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void drowned(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.DROWNED);
		Drowned s = (Drowned) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void husk(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.HUSK);
		Husk s = (Husk) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO evoker
    public void evoker(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.EVOKER);
		Evoker s = (Evoker) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	
	//TODO pillager
    public void Pillager(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.PILLAGER);
		Pillager s = (Pillager) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		ItemStack b = new ItemStack(Material.CROSSBOW,1);
		ItemMeta meta = b.getItemMeta();
		meta.addEnchant(Enchantment.QUICK_CHARGE, 5, true);
		meta.addEnchant(Enchantment.MULTISHOT, 1, true);
		b.setItemMeta(meta);
		
		s.getEquipment().setItemInMainHand(b);
		
		
    }
	
	//TODO vindicator
    public void vindicador(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.VINDICATOR);
		Vindicator s = (Vindicator) entidad;
		
		Random random = new Random();

		int n = random.nextInt(50);
		
		if(n == 0) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		}
		
		if(n == 5) {
			s.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
		}
		
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		s.setCanPickupItems(true);
		
		
    }
	
	//TODO SkeletomDARK
    public void skeletonDark(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.WITHER_SKELETON);
		WitherSkeleton s = (WitherSkeleton) entidad;
		s.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		s.setCanPickupItems(true);
		
		
    }
	
	//TODO Skeletom
    public void skeleton(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
		Skeleton s = (Skeleton) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
  //TODO Bruja
    public void Bruja(World world , int x , int y , int z) {
    	Location l2 = new Location(world, x, y+2, z); 			
		
		LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.WITCH);
		Witch s = (Witch) entidad;
		s.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		
		
    }
	
	//TODO ZOMBI
    public void zombi(World world , int x , int y , int z) {
    	
    	
    		
				Random random = new Random();
			
				
				int n = random.nextInt(100);
				
				
			
				Location l2 = new Location(world, x, y+2, z); 			
			
				LivingEntity entidad = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
				Zombie zombi = (Zombie) entidad;
				zombi.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
				zombi.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(50);
				
				
				PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/4, true ,true,true );
				PotionEffect salto= new PotionEffect(PotionEffectType.JUMP,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );

				
			    zombi.addPotionEffect(rapido);
				zombi.addPotionEffect(salto);
				
	
				
			     	
				if(n == 0) {
					LivingEntity entidad10 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE_VILLAGER);
					ZombieVillager zv = (ZombieVillager) entidad10;
					zv.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
				
					
					
					LivingEntity entidad8 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.DROWNED);
					Drowned zombi8 = (Drowned) entidad8;
					
					
					zv.addPotionEffect(salto);
					zv.addPotionEffect(rapido);
					
					
				    zombi8.addPotionEffect(rapido);
				   
					zombi8.addPotionEffect(salto);
					zombi8.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					
					LivingEntity husk1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.HUSK);	
					Husk husk = (Husk) husk1;
					
					
					husk.addPotionEffect(rapido);
				
					husk.addPotionEffect(rapido);
				
					husk.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					
					

				}
				
				if(n == 1) {
				    LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					Zombie zombi1 = (Zombie) entidad1;
				
					zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					
	  				zombi1.addPotionEffect(rapido);
	  				zombi1.addPotionEffect(salto);
	  				
	  				
	  				
				}else if(n == 2) {
					LivingEntity entidad1 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
					Zombie zombi1 = (Zombie) entidad1;
					zombi1.setCustomName(""+ChatColor.DARK_RED+ChatColor.BOLD+"Tank");
					
					
					zombi1.addPotionEffect(rapido);
					
					zombi1.addPotionEffect(salto);
				
					zombi1.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
					zombi1.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					zombi1.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
					zombi1.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					zombi1.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					zombi1.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
					
				}else if(n == 3) {
				
					
	  				LivingEntity entidad4 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
	  				Zombie zombi4 = (Zombie) entidad4;

	  			
	  			    zombi4.addPotionEffect(rapido);
	  			    zombi4.addPotionEffect(salto);
	  				zombi4.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
	  			
	  				
				}else if(n == 4) {
					
					
		  		    
	  				LivingEntity entidad6 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.ZOMBIE);
	  				Zombie zombi6 = (Zombie) entidad6;
	  				
	  				zombi6.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
	  			    zombi6.addPotionEffect(rapido);
	  				zombi6.addPotionEffect(salto);
	  				zombi6.setBaby();
	  			
	  				LivingEntity entidad7 = (LivingEntity) world.spawnEntity(l2.add(0.5, 0, 0.5), EntityType.SKELETON);
	  				Skeleton zombi7 = (Skeleton) entidad7;
	  				
	  			    zombi7.addPotionEffect(rapido);
	  				zombi7.addPotionEffect(salto);
	  				entidad6.addPassenger(entidad7);
	  				zombi7.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
	  			
	  				
	  				
	  			
				}	
				/*
				 else if(n == 7) {
					
					
		  		    
					 WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
						CustomZombi giant = new CustomZombi(player.getLocation());
						
						world.addEntity(giant);
	  			
				}	
				*/
				
				
					
		
    	return;
    }
    
    
    public void removeTrapArrows(Player player) {
    	List<Entity> entities = getNearbyEntites(player.getLocation(), 50);
    	for(int i = 0;i< entities.size();i++) {
    		if(entities.get(i).getType() == EntityType.ARROW) {
    			Arrow f = (Arrow) entities.get(i);
    			if(f.isOnGround() && f.getCustomName() != null && f.getCustomName().equals("Flecha Trampa")) {
    				
    				f.remove();
    			}
    		}
    	}
    	
    	
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
