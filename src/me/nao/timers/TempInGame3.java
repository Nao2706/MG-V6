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
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class TempInGame3 {

	
	  
	
	   private Main plugin;
	   int taskID;
	  
	
	  
	 public TempInGame3(Main plugin) {
		 
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

		
		@Override
		public void run() {
		
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
			
				
				List<String> join1 = plugin.getArenaAllPlayers().get(name);
				List<String> alive1 = plugin.getAlive().get(name);
				List<String> s = plugin.getSpectators().get(name);
			 
		        for(Player players : Bukkit.getOnlinePlayers()) {
		        	if(plugin.PlayerisArena(players)) {
		        	  if(plugin.getArenaPlayerInfo().get(players).equals(name)) {
		        		
					     if(join1.contains(players.getName()) || s.contains(players.getName())) {
					    	 
					    	if(segundo == 0 && minuto == 0) {
					    		 Bukkit.getScheduler().cancelTask(taskID);	
					    		 
					  		   if(alive1.contains(players.getName())) {
									  ClassIntoGame c = new ClassIntoGame(plugin);
									  c.PlayerWin2(players);
							
									
									  //players.sendMessage(ChatColor.YELLOW+"Tu tiempo fue: "+hora+"h "+minuto+"m "+segundo+"s ");
									  players.sendMessage(ChatColor.GREEN+"Has Sobrevivido hasta el Final ");
							   }
					    		 
					    		 
					  		 FileConfiguration config = plugin.getConfig();
								int rango = config.getInt("Remove-Armor-Stands");
								
								if(plugin.PlayerisArena(players)) {
									for(Entity e1 : getNearbyEntites(players.getLocation(),rango)) {
										
										if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null && e1.getCustomName().endsWith("aqui.")) {
											e1.remove();
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
									    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null && e2.getCustomName().endsWith("aqui.")) {
												e2.remove();
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
									 
								 
									 TempEndGame3 t = new TempEndGame3(plugin);
						     	     //t.Inicio(allPlayer,arenaName);
						     	     t.Inicio(name);
  									
					    			
					    		}
							  
						     }  
						        
						        
					  	  if(alive1.isEmpty()) {
							  Bukkit.getScheduler().cancelTask(taskID);
							  
							
							  FileConfiguration config = plugin.getConfig();
								int rango = config.getInt("Remove-Armor-Stands");
								
								if(plugin.PlayerisArena(players)) {
									for(Entity e1 : getNearbyEntites(players.getLocation(),rango)) {
										
										if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null && e1.getCustomName().endsWith("aqui.")) {
											e1.remove();
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
									    	if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null && e2.getCustomName().endsWith("aqui.")) {
												e2.remove();
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
											
											 TempEndGame3 t = new TempEndGame3(plugin);
											 t.Inicio(name);
											 
											 players.sendMessage(ChatColor.GREEN+"Todos los jugadores fueron eliminados F ");
											 
										
									
						     	}
							  
							  
						  }
						 	
					  	  
						
						
						  getNearbyBlocks2(players);
						  
						  String s1 = String.valueOf(segundo);
						  if(s1.endsWith("0")) {
							  getNearbyBlocks(players);
						  }
							
							
						  
						
						  players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""+ChatColor.GOLD+ChatColor.BOLD+"Tiempo Remanente:"+ChatColor.AQUA+ChatColor.BOLD+" "+hora+"h "+minuto+"m "+segundo+"s " ));
						  
						 }
		        	  }
		           }
		        	
		        }
		/*	 
		    	if(segundo == 0 && minuto == 0) {
		    		 
		    		String estadoPartida = plugin.getEstatusArena().get(name);
		    		if(estadoPartida.equals(EstadoPartida.JUGANDO.toString())) {
						
						plugin.getEstatusArena().replace(name,EstadoPartida.TERMINANDO.toString());
	
						
						
						 TempEndGame3 t = new TempEndGame3(plugin);
			     	     //t.Inicio(allPlayer,arenaName);
			     	     t.Inicio(name);
							
		    			
		    		}
		    	}
			 
		    	if(join1.size() == 0) {
		    		
		     		String estadoPartida = plugin.getEstatusArena().get(name);
		    		if(estadoPartida.equals(EstadoPartida.JUGANDO.toString())) {
		    			
		    			
						plugin.getEstatusArena().replace(name,EstadoPartida.TERMINANDO.toString());
	
						
						 TempEndGame3 t = new TempEndGame3(plugin);
			     	     //t.Inicio(allPlayer,arenaName);
			     	     t.Inicio(name);
							
		    			
		    		}
	   			     Bukkit.getScheduler().cancelTask(taskID);	
	   			     Bukkit.getConsoleSender().sendMessage("se detuvo");
	   				 
	   		     	}
		
*/
	
		  
			
		  
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
			for (int x = -rango; x < rango; x++) {
				for (int y = -rango; y < rango; y++) {
					for (int z = -rango; z < rango; z++) {

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
			for (int x = -rango; x < rango; x++) {
				for (int y = -rango; y < rango; y++) {
					for (int z = -rango; z < rango; z++) {

						Block a = r.getRelative(x, y, z);
						Block b = r.getRelative(x, y-1, z);

						// setea bloques en esos puntos
						
						if(player.getGameMode().equals(GameMode.ADVENTURE) ) {
							
						
								if(a.getType() == Material.NETHERITE_BLOCK && b.getType() == Material.BEDROCK) {
								
									List <Entity> e2 = getNearbyEntites(a.getLocation(),rango);
									if(!e2.isEmpty()) {
										for(int i = 0 ; i < e2.size() ; i++) {
											if(e2.get(i).getType() == EntityType.PLAYER) {
												Player p = (Player) e2.get(i);
												if(p.getLocation().distance(a.getLocation()) <= rango) {
													p.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
												}
											}}}
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.NETHERITE_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
									
									
									 
									
									
								}
								
								if(a.getType() == Material.DIAMOND_BLOCK && b.getType() == Material.BEDROCK) {
									List <Entity> e2 = getNearbyEntites(a.getLocation(),rango);
									if(!e2.isEmpty()) {
										for(int i = 0 ; i < e2.size() ; i++) {
											if(e2.get(i).getType() == EntityType.PLAYER) {
												Player p = (Player) e2.get(i);
												if(p.getLocation().distance(a.getLocation()) <= rango) {
													p.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
												}
											}}}
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.EMERALD_BLOCK && b.getType() == Material.BEDROCK) {
									
									List <Entity> e2 = getNearbyEntites(a.getLocation(),rango);
									if(!e2.isEmpty()) {
										for(int i = 0 ; i < e2.size() ; i++) {
											if(e2.get(i).getType() == EntityType.PLAYER) {
												Player p = (Player) e2.get(i);
												
												if(p.getLocation().distance(a.getLocation()) <= rango) {
													p.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
												}
											}}}
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.EMERALD));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.IRON_BLOCK && b.getType() == Material.BEDROCK) {
									List <Entity> e2 = getNearbyEntites(a.getLocation(),rango);
									if(!e2.isEmpty()) {
										for(int i = 0 ; i < e2.size() ; i++) {
											if(e2.get(i).getType() == EntityType.PLAYER) {
												Player p = (Player) e2.get(i);
												if(p.getLocation().distance(a.getLocation()) <= rango) {
													p.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
												}
											}}}
									a.getWorld().dropItem(a.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.IRON_INGOT));
									 a.getWorld().spawnParticle(Particle.TOTEM,a.getLocation().add(0.5, 1.5, 0.5), 1);
								}
								
								if(a.getType() == Material.GOLD_BLOCK && b.getType() == Material.BEDROCK) {
									
									List <Entity> e2 = getNearbyEntites(a.getLocation(),rango);
									if(!e2.isEmpty()) {
										for(int i = 0 ; i < e2.size() ; i++) {
											if(e2.get(i).getType() == EntityType.PLAYER) {
												Player p = (Player) e2.get(i);
												if(p.getLocation().distance(a.getLocation()) <= rango) {
													p.getWorld().playSound(a.getLocation(),Sound.ENTITY_ITEM_PICKUP ,20.0F , 1F  );
												}
											
											
											}}}
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
