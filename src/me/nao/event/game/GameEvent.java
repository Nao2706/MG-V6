package me.nao.event.game;

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
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.EnchantingTable;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nao.main.game.Main;
import me.nao.manager.ClassArena;
import me.nao.manager.ClassIntoGame;



public class GameEvent implements Listener{

	private Main plugin;
	
	public GameEvent(Main plugin) {
		this.plugin = plugin;
	}
	
	
	
	
	@EventHandler
	public void PlayerBreak(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		if(plugin.PlayerisArena(player)) {
			
		}
		
	}
	
	
	@EventHandler
	public void PlayerTelport(PlayerTeleportEvent e) {
		Player player = (Player) e.getPlayer();
		Location l = e.getTo();
		Block b = l.getBlock();
		Block b1 = b.getRelative(0, -2, 0);
		Block b2 = b.getRelative(0, -3, 0);

		if(b1.getType() == Material.BEDROCK && b2.getType() == Material.BARRIER) {
			if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.CREATIVE)) {
				player.setGameMode(GameMode.ADVENTURE);
			}
		}
		
		if(player.getGameMode().equals(GameMode.SPECTATOR)) {
			
			
			if(e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
				
				if(plugin.PlayerisArena(player)) {
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(l.equals(players.getLocation())) {
							if(plugin.PlayerisArena(players)) {
								 String tp = plugin.getArenaPlayerInfo().get(player);
								 String tp2 =  plugin.getArenaPlayerInfo().get(players);
									if(tp.equals(tp2) && players.getGameMode().equals(GameMode.ADVENTURE)) {
										player.sendMessage(ChatColor.GREEN+"Te teletransportaste a "+ChatColor.GOLD+players.getName());
										player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
									}else{
										player.sendMessage(ChatColor.RED+"Ese Jugador no esta en tu arena|");
										player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 20.0F, 1F);
										e.setCancelled(true);
									}
							}else{
								player.sendMessage(ChatColor.RED+"Ese Jugador no esta en tu arena");
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 20.0F, 1F);
								e.setCancelled(true);
							}
							
						  
						
						}
					}
				}
				
			}
		
		}


		
		
	}
	
	
	@EventHandler
	public void Command1(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String message = e.getMessage();
		if(plugin.PlayerisArena(player)) {
			if(!player.isOp()) {
				if(message.contains("mg")) {
					 
					e.setCancelled(false);
				}else {
					player.sendMessage(ChatColor.RED+"No puedes ejecutar comandos mientras estas en una Partida");
					player.sendMessage(ChatColor.AQUA+"Si deseas salir usa: "+ChatColor.DARK_RED+"/mg leave");
					e.setCancelled(true);
				}
			}
		}
			
		
		
	}
	
	@EventHandler
	public void inmob(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		Entity entidad = e.getRightClicked();
		
		if(entidad.getType() == EntityType.VILLAGER && entidad.getCustomName() != null && entidad.getCustomName().contains(""+ChatColor.GREEN+ChatColor.BOLD+"Medico "+ChatColor.RED+ChatColor.BOLD+"+")) {
			//player.setHealth(20);
			player.setFoodLevel(20);
			player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 0, 0),
					/* NUMERO DE PARTICULAS */10, 1, 0, 1, /* velocidad */0, null, true);
			PotionEffect inst = new PotionEffect(PotionEffectType.HEAL,/*duration*/ 5,/*amplifier:*/2, true ,true,true );

			player.addPotionEffect(inst);
			player.sendMessage(ChatColor.GREEN+"Has sido curado.");
		}
		
		
	}

	
	//TODO MOVE EVNT
	@EventHandler  //METODO
    public void moving(PlayerMoveEvent e){
		   
			Player player = e.getPlayer();
			
			Block block = player.getLocation().getBlock();
			Block b = block.getRelative(0, -2, 0);
			Block b1 = block.getRelative(0, -1, 0);
			Block b2 = block.getRelative(0, -3, 0);
			Block b3 = block.getRelative(0, -4, 0);
			Block bx = block.getRelative(0, 0, 0);
			
			
			if(plugin.PlayerisArena(player)) {
				
				DetectDoor(player);
				if(plugin.getSpectPlayers2().contains(player.getName())) {
					if(player.getGameMode() != GameMode.SPECTATOR) {
						player.setGameMode(GameMode.SPECTATOR);
					}
				}
				
				 
				if(b.getType() == Material.REDSTONE_BLOCK) {
					if(plugin.getCheckPoint().containsKey(player)) {
						plugin.getCheckPoint().remove(player);
						player.sendMessage(ChatColor.RED+"Tu Checkpoint a sido borrado.");
					}
				}
				
				if(bx.getType() == Material.BARRIER && player.getGameMode() == GameMode.SPECTATOR) {
				
							String arena = plugin.getArenaPlayerInfo().get(player);
							
							ClassArena c = new ClassArena(plugin);
							c.DeathTptoSpawn(player, arena);
							player.sendMessage(ChatColor.RED+"No puedes salir de la arena.");	
						
						
						 
					
				}
				
				if(b1.getType() == Material.SLIME_BLOCK) {
					PotionEffect salto = new PotionEffect(PotionEffectType.JUMP,/*duration*/ 2 * 20,/*amplifier:*/10, true ,true,true );
					if(!player.hasPotionEffect(PotionEffectType.JUMP)) {
						player.addPotionEffect(salto);
					}
					
				}
				
			}
			
			//DetectDispenser(player);
			
			if(b1.getType() == Material.LODESTONE && b.getType() == Material.BEDROCK) {
				if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.CREATIVE) {
					player.setGameMode(GameMode.ADVENTURE);
				}
			}
			
			if(b.getType() == Material.LODESTONE && b2.getType() == Material.BEDROCK) {
				if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.ADVENTURE)) {
					player.setGameMode(GameMode.SPECTATOR);
				}
			}
			
			if(b.getType() == Material.RED_CONCRETE && b2.getType() == Material.BARRIER) {
				if(plugin.PlayerisArena(player)) {
					player.setHealth(20);
					player.setFoodLevel(20);
				}
			}
			
			if(b3.getType() == Material.CHEST){
				  Chest chest = (Chest) b3.getState();
				  if(chest.getCustomName() != null) {
					  
					  FileConfiguration config = plugin.getConfig();
					  List<String> ac = config.getStringList("Arenas-Created.List");
					  
					  if(ac.contains(chest.getCustomName())) {
						  String arenaName = chest.getCustomName();
						  ClassArena c = new ClassArena(plugin);
						  if(!plugin.getPlayerGround().contains(player.getName())) {
						    c.JoinPlayerArena(player, arenaName);
						  }
						  
					  }
					  
			
					  
					  
					  
				  }
			} 
			
			if(b.getType() == Material.CHEST){
				  Chest chest = (Chest) b.getState();
				  if(chest.getCustomName() != null) {
					  
					  
					  FileConfiguration config = plugin.getConfig();
					  List<String> ac = config.getStringList("Arenas-Created.List");
					  
					  if(ac.contains(chest.getCustomName())) {
						  String arenaName = chest.getCustomName();
						  ClassArena c = new ClassArena(plugin);
						  //la lista esta para evitar spam de chat
						  if(!plugin.getPlayerGround().contains(player.getName())) {
							    c.JoinPlayerArena(player, arenaName);
						  }
						  
					  }
							  if(plugin.PlayerisArena(player)) {
								  
								  if(player.getGameMode() == GameMode.ADVENTURE) {
									   if(chest.getCustomName().equals("TELEPORT")) {
										     if(!chest.getInventory().isEmpty()) {
													for(ItemStack itemStack : chest.getInventory().getContents()) {
															if(itemStack == null) continue;
														
															     if(!itemStack.hasItemMeta()) {
																		return;
																	}
																	
																	ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
															
																	if(!bm.hasDisplayName()) {
																		return;
																	}
																	   String n = bm.getDisplayName();
																	   String[] nn = n.split(" ");
																	    //world 345 67 89 0 1
																		 
																		  String world = nn[0];
																		  double x = Double.valueOf( nn[1]);
																		  double y = Double.valueOf(nn[2]);
																		  double z = Double.valueOf(nn[3]);
																		  float pitch = Float.valueOf(nn[4]);
																		  float yaw = Float.valueOf(nn[5]);
																		  
																		  Location l = new Location(Bukkit.getWorld(world),x,y,z,pitch,yaw);
																		  player.teleport(l);	
																		  player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
										              }
										          }
								      }
								  }
								  
						
						
								   
								   
						    } 
					  
			   }
			}
			
			
			if(plugin.PlayerisArena(player)) {
				
				getNearbyBlocksParticle(player);
				
				for(Entity e1 : getNearbyEntites(player.getLocation(),150)) {
					
					Block blocke = e1.getLocation().getBlock();
					Block be = blocke.getRelative(0, -1, 0);
					if(be.getType() == Material.BARRIER) {
						if(e1.getType() == EntityType.DROPPED_ITEM) {
							e1.remove();
						}
					}
					
				}
			}
			ClassIntoGame ci = new ClassIntoGame(plugin);
			
			ci.PlayerFallMap(player);	
			ci.PlayerWin(player);
			
		
	}
	

	//TODO MUERTES DE MOBS
	@EventHandler  //METODO
    public void killmobs(EntityDeathEvent e){
		 
		
		Player player = (Player) e.getEntity().getKiller();
		EntityType mob = e.getEntityType();
		
		if(plugin.PlayerisArena(player)) {	
			if(player != null && player.getType() == EntityType.PLAYER && mob != EntityType.ITEM_FRAME && mob != EntityType.DROPPED_ITEM) {
				Entity m = e.getEntity();
				

				Random random = new Random();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20.0F, 1F);
				
				int n = random.nextInt(5);
				if(n == 1) {
					m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.DIAMOND,5)));
				}
				else if(n == 2) {
					m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.EMERALD,10)));
				}
				else if(n == 3) {
					m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.GOLD_INGOT,15)));		
								}
				else if(n == 4) {
					m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.IRON_INGOT,20)));
				}
				else if(n == 5) {
					m.getWorld().dropItem(m.getLocation(), (new ItemStack(Material.NETHERITE_INGOT,1)));
				}
				
				
				List<Entity> l = getNearbyEntites(player.getLocation(),30);
				
				for(Entity e1 : l) {
					// 
					if(e1.getType() == EntityType.ARMOR_STAND && e1.getCustomName() != null) {
						// FALTAN  10 / 30
						//ALMAS 10 / 10
						String name = ChatColor.stripColor(e1.getCustomName());
						
						if(name.contains("FALTAN")) {
							String[] sp = name.split(" ");
							int nm = Integer.valueOf(ChatColor.stripColor(sp[1]));
							int nm1 = Integer.valueOf(ChatColor.stripColor(sp[3]));
							nm = nm + 1;
							
							if(nm >= nm1) {
								
								DetectChestAndCommandArmorStand(player);
								
								List<Entity> l2 = getNearbyEntites(player.getLocation(),30);
								
								for(Entity e2 : l2) {
									if(e2.getType() == EntityType.ARMOR_STAND && e2.getCustomName() != null ) {
										
										String name2 = e2.getCustomName();
										if(name2.contains("RECIPIENTE DE ALMAS") || name2.contains("FALTAN")) {
											e2.getWorld().spawnParticle(Particle.CLOUD, e2.getLocation().add(0.5, 0, 0.5),	/* N DE PARTICULAS */10, 3, 1, 3, /* velocidad */0, null, true);
											e2.remove();
										}
									
										
									}
									
								
								}
								
								player.sendMessage(ChatColor.GREEN+"Se ha terminado de llenar las almas.");
								 String arena = plugin.getArenaPlayerInfo().get(player);
								 for(Player players : Bukkit.getOnlinePlayers()) {
									 if(players.getName().equals(player.getName())) continue;
									 String arena2 = plugin.getArenaPlayerInfo().get(players);
									 if(arena2 != null && arena2.equals(arena)) {
										
										 players.sendMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Termino de llenar las almas.");							 
										 
									 }
									 
								 }
								
							}else {
								
								e1.setCustomName(""+ChatColor.AQUA+ChatColor.BOLD+"FALTAN "+ChatColor.GREEN+nm+ChatColor.RED+" / "+ChatColor.GREEN+nm1);	
								e1.getWorld().spawnParticle(Particle.FLAME, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
								e.getEntity().getWorld().spawnParticle(Particle.FLAME, e1.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
							}
						}
						
						
						
					
					
						return;
					}
				}
					
				
				
				 ClassIntoGame c = new ClassIntoGame(plugin);
				 c.PlayerAddPoints(player);
			
			}
		}
		
	
		
		
	}
	
	
	
	
	//TODO COMANDOS DETECT
	@EventHandler 
	public void CommandEvent(ServerCommandEvent e) {
		
		
		
		if(e.getSender() instanceof BlockCommandSender) {
			
			String comando = e.getCommand();
			
			
			if(comando.startsWith("kill")) {
				String[] a = comando.split(" ");
				String name = a[1];
				if(name != null) {
					Player target = Bukkit.getServer().getPlayerExact(name);
					if(target != null && plugin.PlayerisArena(target)){
						e.setCancelled(true);
						 Dead(target);
						 target.setHealth(20);
						 target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"INSTAKILL DE AREA", 40, 80, 40);
						 target.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Instakill de Area.");
							
						 
						 String arena = plugin.getArenaPlayerInfo().get(target);
						 for(Player players : Bukkit.getOnlinePlayers()) {
							 if(players.getName().equals(target.getName())) continue;
							 String arena2 = plugin.getArenaPlayerInfo().get(players);
							 if(arena2 != null && arena2.equals(arena)) {
								
								 players.sendMessage(ChatColor.GOLD+target.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"InstaKill de Area");							 }
							 
						 }
						 
						 /*
						 List<Map.Entry<Player, String>> list2 = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
							for (Map.Entry<Player,String> datos : list2) {
								 for(Player players : Bukkit.getOnlinePlayers()) {
									 if(players.getName().equals(target.getName())) continue;
									 String ar = plugin.getArenaPlayerInfo().get(players);
										
									 if(ar != null && ar.equals(arena)) {
								     		 players.sendMessage(ChatColor.GOLD+target.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"InstaKill de Area");
								     	}
								     		 //esto lo veran los que estan en arena
								 }
							}*/
						
						 
					}
				}
				
			}
			
			
			//Bukkit.getConsoleSender().sendMessage("commandblocksay: "+comando);
			//Player target = Bukkit.getServer().getPlayerExact(args[0]);
		}
		if(e.getSender() instanceof CommandSender) {
			//Player target = Bukkit.getServer().getPlayerExact(args[0]);
			String comando = e.getCommand();
			//Bukkit.getConsoleSender().sendMessage("consolesay: "+comando);
			if(comando.startsWith("kill")) {
				String[] a = comando.split(" ");
				String name = a[1];
				if(name != null) {
					Player target = Bukkit.getServer().getPlayerExact(name);
					if(target != null && plugin.PlayerisArena(target)){
						e.setCancelled(true);
						 Dead(target);
						 target.setHealth(20);
						 target.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"INSTAKILL POR COMANDO", 40, 80, 40);
						 target.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Instakill por Comando.");
						 
						 
						 List<Map.Entry<Player, String>> list2 = new ArrayList<>(plugin.getArenaPlayerInfo().entrySet());
							for (Map.Entry<Player,String> datos : list2) {
								 for(Player players : Bukkit.getOnlinePlayers()) {
									 if(players.getName().equals(target.getName())) continue;
									 String ar = plugin.getArenaPlayerInfo().get(players);
										
									 if(ar != null && ar.equals(datos.getValue())) {
								     		 players.sendMessage(ChatColor.GOLD+target.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"InstaKill por Comando.");
								     	}
								     		 //esto lo veran los que estan en arena
								 }
							}
						
						 
					}
				}
				
			}
			
			
		}
		
	}
	
	
	@EventHandler  //TODO MUERTES METODO
    public void damageInGame(EntityDamageEvent e){
		
		if(e.getEntity().getType() == EntityType.PLAYER) {
			Player player = (Player) e.getEntity();
			Block block = e.getEntity().getLocation().getBlock();
			Block b = block.getRelative(0, -1, 0);
			
		
			
			if(b.getType().equals(Material.BARRIER) && e.getEntity().getType() == EntityType.PLAYER){
				if(plugin.PlayerisArena(player)) {
					ClassIntoGame c = new ClassIntoGame(plugin);
					c.PlayerDeadInArena(player);
					
				}
			}
			
			
	
		}
		
		
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			
			// TIPO DE DAÑO COMO PROJECTILE O DRAWNING  ENTITY ETC
			//player.sendMessage("Motivo de daño recibido de "+e.getCause().toString());
			
			
			if(e instanceof EntityDamageByEntityEvent) {
				
				Entity damager = ((EntityDamageByEntityEvent)e).getDamager();
				
				
				//if(damager.getType() != EntityType.ARMOR_STAND) {
					
				
					
					if(e.getEntity() instanceof Player) {
						if(e.getFinalDamage() >= ((Player)e.getEntity()).getHealth()) {
							
						
							if(plugin.PlayerisArena(player)) {
								//TODO TOCA CAMBIAR CREO
								
									String arena = plugin.getArenaPlayerInfo().get(player);
								
									
										if (player.getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING)) || player.getInventory().getItemInOffHand().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING))) {
											 
											return;
										}else {
											e.setCancelled(true);
											if(plugin.getCheckPoint().containsKey(player)) {
												 String[] coords = plugin.getCheckPoint().get(player).split("/");
												    String world2 = coords[0];
												    Double x2 = Double.valueOf(coords[1]);
												    Double y2 = Double.valueOf(coords[2]);
												    Double z2 = Double.valueOf(coords[3]);
												    Location l1 = new Location(Bukkit.getWorld(world2),x2,y2,z2);
												    
												    player.teleport(l1);
												    plugin.getCheckPoint().remove(player);
												    player.sendMessage(ChatColor.GREEN+"Has usado tu checkpoint. \n(coloca otro si puedes para evitar morir)");
											}else {
											
											
											
												
											if(damager.getCustomName() == null) {
												//PENDIENTE DE CHEQUEAR XD POR QUE ME OLVIDE
												if(damager.getType() == EntityType.ARROW || damager.getType() == EntityType.SPECTRAL_ARROW ||damager.getType() == EntityType.SMALL_FIREBALL) {
														Projectile pro = (Projectile) damager;
														Entity disp = (Entity) pro.getShooter();
														
														//si el que disparo no es un jugador
														if(disp.getType() != EntityType.PLAYER) {
															Dead(player);
															SpawnPlayerZombi(player);
															dropItemsPlayer(player);
															player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+disp.getType(), 40, 80, 40);
															player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+disp.getType());
															
															 for(Player players : Bukkit.getOnlinePlayers()) {
																 if(players.getName().equals(player.getName())) continue;
																 String ar = plugin.getArenaPlayerInfo().get(players);
																	
																 	if(ar != null && ar.equals(arena)) {
															     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+disp.getType());
															     		 
															     	}
															     		 //esto lo veran los que estan en arena
															 }
														}else {
															//SI EL QUE DISPARA ES UN JUGADOR
															Player killer = (Player) disp;
															Dead(player);
															dropItemsPlayer(player);
															player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+killer.getName(), 40, 80, 40);
															player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+killer.getName());
															
															 for(Player players : Bukkit.getOnlinePlayers()) {
																 if(players.getName().equals(player.getName())) continue;
																 String ar = plugin.getArenaPlayerInfo().get(players);
																	
																 	if(ar != null && ar.equals(arena)) {
															     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+killer.getName());
															     		 
															     	}
															     		 //esto lo veran los que estan en arena
															 }
														}
													
												}else {
													   Dead(player);
													   SpawnPlayerZombi(player);
													   dropItemsPlayer(player);
														player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto..",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+damager.getType(), 40, 80, 40);
														player.sendMessage(ChatColor.RED+"Moriste por un: "+ChatColor.YELLOW+damager.getType());
														
														 for(Player players : Bukkit.getOnlinePlayers()) {
															 if(players.getName().equals(player.getName())) continue;
															 String ar = plugin.getArenaPlayerInfo().get(players);
																
															 if(ar != null && ar.equals(arena)) {
														     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+damager.getType());
														     		
														     	}
														     		 //esto lo veran los que estan en arena
														 }
														
												}
												
												
											
												
											}else {
												  Dead(player);
										          SpawnPlayerZombi(player);
												  dropItemsPlayer(player);
													player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"por: "+ChatColor.YELLOW+damager.getCustomName(), 40, 80, 40);
													player.sendMessage(ChatColor.RED+"Moriste por :"+ChatColor.YELLOW+damager.getCustomName());
													
													 for(Player players : Bukkit.getOnlinePlayers()) {
														 if(players.getName().equals(player.getName())) continue;
														 String ar = plugin.getArenaPlayerInfo().get(players);
															
														 	if(ar != null && ar.equals(arena)) {
													     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+damager.getCustomName());
													     		 
													     	}
													     		 //esto lo veran los que estan en arena
													 }
												
												
												
											}
										//	DamageCause t2 = player.getLastDamageCause().getCause();
										
									//		Bukkit.broadcastMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Moriste por:" +e.getCause().toString());
											
											
											
											}
											
										}
									
						
								
							
							}
							
						}
					}
				
				
				
		
				
			 
				
			
			 
				
			}else {
				//bloque de daño por causas externas
				if(e.getEntity() instanceof Player) {
					if(e.getFinalDamage() >= ((Player)e.getEntity()).getHealth()) {
						
					
						if(plugin.PlayerisArena(player)) {
							
						String arena = plugin.getArenaPlayerInfo().get(player);
							
									if (player.getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING)) || player.getInventory().getItemInOffHand().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING))) {
										 
										return;
									}else {
										e.setCancelled(true);
										if(plugin.getCheckPoint().containsKey(player)) {
											PotionEffect vid = new PotionEffect(PotionEffectType.REGENERATION,/*duration*/ 10 * 20,/*amplifier:*/4, true ,true,true );
											PotionEffect fir = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,/*duration*/ 10 * 20,/*amplifier:*/4, true ,true,true );
											player.addPotionEffect(vid);
											player.addPotionEffect(fir);
											
											
											    String[] coords = plugin.getCheckPoint().get(player).split("/");
											    String world2 = coords[0];
											    Double x2 = Double.valueOf(coords[1]);
											    Double y2 = Double.valueOf(coords[2]);
											    Double z2 = Double.valueOf(coords[3]);
											    Location l1 = new Location(Bukkit.getWorld(world2),x2,y2,z2);
											    
											    player.teleport(l1);
											    plugin.getCheckPoint().remove(player);
											    player.sendMessage(ChatColor.GREEN+"Has usado tu checkpoint.");
											    player.sendMessage(ChatColor.YELLOW+"(coloca otro si puedes para evitar morir)");
											   
											    
										}else {
										 
										dropItemsPlayer(player);
										Dead(player);
										player.setHealth(20);
										
									
										
										if(e.getCause() == EntityDamageEvent.DamageCause.FALL) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"CAIDA", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por una "+ChatColor.YELLOW+"Caida");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por una "+ChatColor.YELLOW+"CAIDA");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.FIRE) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"FUEGO", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Quemado");

											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"FUEGO");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"LAVA", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por la "+ChatColor.YELLOW+"Lava");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por la "+ChatColor.YELLOW+"LAVA");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"PISO CALIENTE", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por"+ChatColor.YELLOW+"Piso Caliente");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"PISO CALIENTE");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"AHOGADO", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Ahogado");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio "+ChatColor.YELLOW+"AHOGADO");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"CONTACTO", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por un "+ChatColor.YELLOW+"Cactus");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por un "+ChatColor.YELLOW+"CACTUS");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"FUEGO", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Quemado hasta no poder mas");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio "+ChatColor.YELLOW+"QUEMADO");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.POISON) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"POSION", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste "+ChatColor.YELLOW+"Envenenado");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"ENVENENAMIENTO");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"SOFOCACION", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Sofocarte");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"SOFOCACION");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"SUICIDIO", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Suicidarte");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"SUICIDIO");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"HEROBRINE", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"Herobrine");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"HEROBRINE");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"EXPLOTO UN BLOQUE", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"UNA EXPLOSION DE UN BLOQUE");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"UNA EXPLOSION DE UN BLOQUE");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
										if(e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
											player.sendTitle(""+ChatColor.RED+ChatColor.BOLD+"Has Muerto",ChatColor.YELLOW+"motivo: "+ChatColor.YELLOW+"TE ESTRELLASTE", 40, 80, 40);
											player.sendMessage(ChatColor.RED+"Moriste por "+ChatColor.YELLOW+"TE ESTRELLASTE CONTRA UNA PARED");
											
											 for(Player players : Bukkit.getOnlinePlayers()) {
												 if(players.getName().equals(player.getName())) continue;
												 String ar = plugin.getArenaPlayerInfo().get(players);
													
												 if(ar != null && ar.equals(arena)) {
											     		 players.sendMessage(ChatColor.GOLD+player.getName()+ChatColor.RED+" murio por "+ChatColor.YELLOW+"ESTRELLARSE CONTRA UNA PARED");
											     	}
											     		 //esto lo veran los que estan en arena
											 }
											
										}
										
									
									//	DamageCause t2 = player.getLastDamageCause().getCause();
										
										player.setGameMode(GameMode.SPECTATOR);
										
								//		Bukkit.broadcastMessage(ChatColor.GREEN+player.getName()+ChatColor.YELLOW+" Moriste por:" +e.getCause().toString());
										
									 }
									}
								
					
							
						
						}
						
					}
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
	
	//TODO CHEST
	public void getNearbyBlocksParticle(Player player) {
		FileConfiguration config = plugin.getConfig();
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		int rango = config.getInt("Chest-Particle-Range") ;
		
		if (r.getType().equals(Material.AIR)) {
			for (int x = -rango; x < rango+1; x++) {
				for (int y = -rango; y < rango+1; y++) {
					for (int z = -rango; z < rango+1; z++) {

				
						Block a = r.getRelative(x, y, z);
						// setea bloques en esos puntos
						
						if(player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SPECTATOR ) {
								if(a.getType() == Material.CHEST) {
									  Chest chest = (Chest) a.getState();
									  if(chest.getCustomName() != null) {
										
										  if(chest.getCustomName().contains("TIENDA")) {
												chest.getWorld().spawnParticle(Particle.TOTEM, chest.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
												
										  }
										  
										  if(chest.getCustomName().contains("REVIVIR")) {
											  chest.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, chest.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
												
										  }
										  
								
									  }
								}
								if(a.getType() == Material.ENCHANTING_TABLE) {
									EnchantingTable t = (EnchantingTable) a.getState();
									t.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, t.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */1, 0.5, 1, 0.5, /* velocidad */0, null, true);
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
	

	
	public void dropItemsPlayer(Player player) {
		if(player.getInventory().getContents().length >= 1) {
			for (ItemStack itemStack : player.getInventory().getContents()) {
				if(itemStack == null) continue;
						
					player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                    player.getInventory().removeItem(itemStack);
              }
				player.getInventory().clear(); 
		}
		
	}
	
	
	
	public void Dead(Player player) {
		ClassIntoGame c = new ClassIntoGame(plugin);
		c.PlayerDeadInArena(player);
		player.setGameMode(GameMode.SPECTATOR);
		
	}
	
	@SuppressWarnings("deprecation")
	public void SpawnPlayerZombi(Player player) {
		PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );   
	    PotionEffect salto= new PotionEffect(PotionEffectType.JUMP,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );
		
		ItemStack item = new ItemStack(Material.PLAYER_HEAD,/*CANTIDAD*/1);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(player.getName());
		item.setItemMeta(meta);
		
	    
		LivingEntity entidad = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		Zombie zombi = (Zombie) entidad;
		zombi.getEquipment().setHelmet(item);
		zombi.setCanPickupItems(true);
		zombi.setCustomName(ChatColor.RED+player.getName());
		zombi.setCustomNameVisible(true);
		zombi.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
		zombi.addPotionEffect(rapido);
		zombi.addPotionEffect(salto);
		
		plugin.getDeadmob().put(player, player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ());
		
		LivingEntity entidad1 = (LivingEntity) player.getWorld().spawnEntity(player.getLocation().add(0,-1,0), EntityType.ARMOR_STAND);
		ArmorStand a = (ArmorStand) entidad1;
		a.setCustomName(ChatColor.YELLOW+player.getName()+ChatColor.RED+" Murio aqui.");
		a.setCustomNameVisible(true);
		a.setCollidable(false);
		a.setInvisible(true);
		a.setInvulnerable(true);
		a.setGravity(false);
		a.setTicksLived(2000);
	}
	
	public void DetectChestAndCommandArmorStand(Player player) {
		//FileConfiguration config = plugin.getConfig();
		
		
		
		
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		
	//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
			int rango = 10;
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
										  if(chest.getCustomName().contains("C-A")) {
											  if(!chest.getInventory().isEmpty()) {
													for (ItemStack itemStack : chest.getInventory().getContents()) {
															if(itemStack == null) continue;
														
																
																	if(!itemStack.hasItemMeta()) continue;
																	
																	ItemMeta bm = (ItemMeta) itemStack.getItemMeta();
																	
																	if(!bm.hasDisplayName()) continue;
																	
																	//EFFECT DURA POTENCIA
																	//
																	   String n = bm.getDisplayName();
																	  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																	  Bukkit.dispatchCommand(console, n);
																	   //player.performCommand(n);
															 
															
														
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
	
	public void DetectDoor(Player player) {
		//FileConfiguration config = plugin.getConfig();
		
		
		
		
		Block block = player.getLocation().getBlock();
		Block r = block.getRelative(0, 0, 0);
		
	//	if (r.getType().equals(Material.STONE_PRESSURE_PLATE)) {
			int rango = 5;
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
										  if(chest.getCustomName().contains("DOOR")) {
											  if(!chest.getInventory().isEmpty()) {
												  
												  /*if(chest.getInventory().getItem(2) != null) {
													  ItemStack it = chest.getInventory().getItem(2);
													  if(player.getInventory().containsAtLeast(it, it.getAmount())) {
														  if(player.getLocation().distance(chest.getLocation()) < 3) {
															  if(chest.getInventory().getItem(0) != null) {
																  ItemStack it2 = chest.getInventory().getItem(0);
																  
																  if(it2.hasItemMeta()) {
																	  ItemMeta bm = (ItemMeta) it2.getItemMeta();
																	  if(bm.hasDisplayName()) {
																		  String n = bm.getDisplayName().replace("%exe%", "execute at "+player.getName()+" run ");
																			  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																			  Bukkit.dispatchCommand(console, n);
																	  }
																  }
																  
															  }
														  }
														  if(player.getLocation().distance(chest.getLocation()) >= 4 || player.getLocation().distance(chest.getLocation()) <= 5) {
															  if(chest.getInventory().getItem(1) != null) {
																  ItemStack it2 = chest.getInventory().getItem(1);
																  if(it2.hasItemMeta()) {
																	  ItemMeta bm = (ItemMeta) it2.getItemMeta();
																	  if(bm.hasDisplayName()) {
																		     String n = bm.getDisplayName().replace("%exe%", "execute at "+player.getName()+" run ");
																			  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																			  Bukkit.dispatchCommand(console, n);
																	  }
																  }
																  
															  }
														  }
														  
													  }
													  
													  
												  }*/
													  if(player.getLocation().distance(chest.getLocation()) == 3) {
														  
														  if(!plugin.getDoorsChest().contains(chest.getLocation())) {
															  
															  plugin.getDoorsChest().add(chest.getLocation());
															  if(chest.getInventory().getItem(0) != null) {
																  ItemStack it2 = chest.getInventory().getItem(0);
																  
																  if(it2.hasItemMeta()) {
																	  ItemMeta bm = (ItemMeta) it2.getItemMeta();
																	  
																	  
																	  if(bm.hasDisplayName()) {
																		  String n = bm.getDisplayName().replace("%exe%", "execute at "+player.getName()+" run ");
																			  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																			  Bukkit.dispatchCommand(console, n);
																	  }
																  }
																  
															  }
															 return; 
														  }
														 
													  }
													  if(player.getLocation().distance(chest.getLocation()) == 4) {
														  if(plugin.getDoorsChest().contains(chest.getLocation())) {
															  plugin.getDoorsChest().remove(chest.getLocation());
															  
															  if(chest.getInventory().getItem(1) != null) {
																  ItemStack it2 = chest.getInventory().getItem(1);
																  if(it2.hasItemMeta()) {
																	  ItemMeta bm = (ItemMeta) it2.getItemMeta();
																	  if(bm.hasDisplayName()) {
																		  String n = bm.getDisplayName().replace("%exe%", "execute at "+player.getName()+" run ");
																			  ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
																			  Bukkit.dispatchCommand(console, n);
																	  }
																  }
																  
															  }
															  
																 return; 
														  }
														  
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
	
	
}
