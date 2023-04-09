package me.nao.command.game;






import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

//import de.tr7zw.nbtapi.NBTItem;
import me.nao.events.ItemNBT;
//import me.nao.events.ItemNBT2;
import me.nao.fillareas.Data;
import me.nao.main.game.Main;
import me.nao.manager.ClassArena;
import me.nao.manager.EstadoPartida;
import me.nao.timers.Countdown2;
import me.nao.yamlfile.game.YamlFilePlus;
import me.top.users.PointsManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;








public class Comandos implements CommandExecutor{

	
	
	
	private Main plugin;
	
	public Comandos(Main plugin) {
		this.plugin = plugin;
	}
	
	
	//Captador de mensaje 

	
	public boolean onCommand( CommandSender sender,  Command comando,  String label,
		 String[] args) {
		FileConfiguration message = plugin.getMessage();
		FileConfiguration points1 = plugin.getPoints();
		
		//mensaje desde consola
		// con el if se evita que se use el comando desde consola
		if(!(sender instanceof Player)){
			
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("reload")) {
					FileConfiguration config = plugin.getConfig();
					List<String> ac = config.getStringList("Arenas-Created.List");
						//mg reload arena
						if (args.length == 2) {
							
							
							String name = args[1];
							if(ac.contains(name)) {
								plugin.ChargedYml(name, null);
								plugin.getSpecificYamls(name).reload();
								plugin.getTempYml().remove(name);
								
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado correctamente la arena "+name);
							}else {
								Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" No existe esa arena");
							}
							
							
						}else {
							plugin.getConfig().reload();
							plugin.reloadConfig();
							plugin.getMessage().reload();
							plugin.getPoints().reload();
							plugin.getCommandsMg().reload();
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado correctamente el Plugin ");
						}
					
					
					return true;
					}
				
				else if(args[0].equalsIgnoreCase("list-fa") ) {
					
					Bukkit.getConsoleSender().sendMessage("Lista de Timers activos");
					if(!plugin.getTimerAction().isEmpty()) {
						for(int i = 0 ; i< plugin.getTimerAction().size() ; i++) {
							Bukkit.getConsoleSender().sendMessage(plugin.getTimerAction().get(i));
						}
					}else {
						Bukkit.getConsoleSender().sendMessage("No hay datos");
					}
					
					return true;
				}
				else if(args[0].equalsIgnoreCase("start-fa") ) {
				 
						String group = args[1];
						
						if(plugin.getTimerAction().contains(group)) {
							return true;
						}
						
						FileConfiguration cg = plugin.getCommandsMg();
						if(!cg.contains("Commands."+group)) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No existe ese path.");
							return true;
						}
						
						try {	
							if(args.length == 3) {
								int valor = Integer.valueOf(args[2]);
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Start path Empezando.");
								plugin.getCommandFillArea().put(group, true);
								plugin.getTimerAction().add(group);
								Bukkit.getConsoleSender().sendMessage("Grupo "+ChatColor.GREEN+group+" en ejecucion");
								
							    Countdown2 c2 = new Countdown2(plugin,valor,group);
							    c2.ejecucion();
							}else {
								Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo> <time>");
							}
						
						
						}catch(NumberFormatException ex) {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
							
						}
					
					
					
					
					return true;
				}
				
				else if(args[0].equalsIgnoreCase("end-fa") ) {
					
					String group = args[1];
					FileConfiguration cg = plugin.getCommandsMg();
					if(!cg.contains("Commands."+group)) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No existe ese path.");
						return true;
					}
					
					if(args.length >= 1) {
						plugin.getCommandFillArea().put(group, false);
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"End path completado.");
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo>");
					}
				
				
				
				
				return true;
			}
				//TODO CHECK
				else if (args[0].equalsIgnoreCase("check")) {
					String name = args[1];
					if(points1.contains("Players." + name + ".Kills")) {
						for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

							
							

							if (name.equals(key) && points1.getString("Players." + name + ".Kills",null) != null) {
								List<String> messagep = message.getStringList("Message-Check-Player.message");
								for(int i = 0 ; i<messagep.size();i++) {
									int puntaje = Integer.valueOf((points1.getString("Players." + key + ".Kills")));
									String texto = messagep.get(i);
									Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",name).replaceAll("%points%",String.valueOf(puntaje))));
								//player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
							
								
								}
							
							}
					
						}
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
						//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
					
				
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("set-life") ) {
					try {
					if (args.length == 3) {
						// /c add n p
						// mg set nao 10
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);
					int valor = Integer.valueOf(args[2])  ;
						if(target != null) {
							//target.setMaxHealth(target.getMaxHealth());
							target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(valor);
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue seteado correctamente. ");
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
							
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-life <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						
					}
				
							
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("add-points")) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						if(points1.getString("Players." + name + ".Kills",null) != null) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									int puntaje = points1.getInt("Players." + name + ".Kills");
									
									points1.set("Players."+name+".Kills", puntaje+valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se sumo "+ChatColor.GOLD+"+"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									//player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
								}

							}
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
						
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg add-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
				}
				else if (args[0].equalsIgnoreCase("rest-points")) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						
						if(points1.getString("Players." + name + ".Kills",null) != null) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									int puntaje = points1.getInt("Players." + name + ".Kills");
									
									points1.set("Players."+name+".Kills", puntaje-valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se resto "+ChatColor.GOLD+"-"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
								//	player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
								}


							}
						}
						else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
						}
					
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg rest-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
				}
				else if (args[0].equalsIgnoreCase("set-points")) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						if(points1.getString("Players." + name + ".Kills",null) != null) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									
									
									points1.set("Players."+name+".Kills", valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Se seteo "+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									//player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);

								}


							}
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							//player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
					
						
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					}
				
							
					return true;
				}
			
				else if(args[0].equalsIgnoreCase("top") ){
					
					if(points1.contains("Players")) {
					if (message.getBoolean("Command.message-top")) {
						List<String> messagep = message.getStringList("Command.message-top-decoracion1");
						for (int j = 0; j < messagep.size(); j++) {
							String texto = messagep.get(j);
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
						}
					}

					// PRIMERA PARTE
					HashMap<String, Integer> scores = new HashMap<>();

					for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

						int puntaje = Integer.valueOf(points1.getString("Players." + key + ".Kills"));
						// SE GUARDAN LOS DATOS EN EL HASH MAP
						scores.put(key, puntaje);

					}

					// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
					List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

					Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
						public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
							return e2.getValue() - e1.getValue();
						}
					});

					// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR

					int i = 0;
					for (Map.Entry<String, Integer> e : list) {

						i++;
						if (i <= message.getInt("Top-Amount-Command")) {
							// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

							if (message.getBoolean("Command.message-top")) {
								List<String> messagep = message.getStringList("Command.message-top-texto");
								for (int j = 0; j < messagep.size(); j++) {
									String texto = messagep.get(j);
									Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
											texto.replaceAll("%player%", e.getKey())
													.replace("%pointuser%", e.getValue().toString())
													.replaceAll("%place%", Integer.toString(i))));
								}

							}

						} else {
							break;
						}

					}

					if (message.getBoolean("Command.message-top")) {
						List<String> messagep = message.getStringList("Command.message-top-decoracion2");
						for (int j = 0; j < messagep.size(); j++) {
							String texto = messagep.get(j);
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
						}
					}
					
				}else {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No hay datos de ningun Jugador");
				}
					return true;
				}
	
				else if(args[0].equalsIgnoreCase("see") ) {
					 
					if (args.length == 2) {
						String name = args[1];
					
						
						List<String> joinl = plugin.getArenaAllPlayers().get(name);
						List<String> alive1 = plugin.getAlive().get(name);
						List<String> deaths1 = plugin.getDeaths().get(name);
						List<String> arrive1 = plugin.getArrive().get(name);
						List<String> spectator1 = plugin.getSpectators().get(name);
						HashMap <Player,Integer> points = plugin.getEspecificPlayerPoints();
						HashMap <Player,String> a = plugin.getArenaPlayerInfo();
						Bukkit.getConsoleSender().sendMessage("Join"+joinl);
						Bukkit.getConsoleSender().sendMessage("alive"+alive1);
						Bukkit.getConsoleSender().sendMessage("death"+deaths1);
						Bukkit.getConsoleSender().sendMessage("arrive"+arrive1);
						Bukkit.getConsoleSender().sendMessage("spectator"+spectator1);
						Bukkit.getConsoleSender().sendMessage("points"+points);
						Bukkit.getConsoleSender().sendMessage("arenas"+a);
			
						
					
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" no existen datos");
					}
					
					return true;
				}
				
				else if(args[0].equalsIgnoreCase("del") ) {
					
					//mg see name tex
					if (args.length == 3) {
						String name = args[1];
						String name2 = args[2];
					
						
						List<String> joinl = plugin.getArenaAllPlayers().get(name);
						if(joinl.remove(name2));
						List<String> alive1 = plugin.getAlive().get(name);
						if(alive1.remove(name2));
						List<String> deaths1 = plugin.getDeaths().get(name);
						if(deaths1.remove(name2));
						List<String> arrive1 = plugin.getArrive().get(name);
						if(arrive1.remove(name2));
						List<String> spectator1 = plugin.getSpectators().get(name);
						if(spectator1.remove(name2));
						HashMap <Player,Integer> points = plugin.getEspecificPlayerPoints();
						HashMap <Player,String> a = plugin.getArenaPlayerInfo();
						Bukkit.getConsoleSender().sendMessage("Join"+joinl);
						Bukkit.getConsoleSender().sendMessage("alive"+alive1);
						Bukkit.getConsoleSender().sendMessage("death"+deaths1);
						Bukkit.getConsoleSender().sendMessage("arrive"+arrive1);
						Bukkit.getConsoleSender().sendMessage("spectator"+spectator1);
						Bukkit.getConsoleSender().sendMessage("points"+points);
						Bukkit.getConsoleSender().sendMessage("arenas"+a);
			
						
					
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" no existen datos");
					}
					
					return true;
				}
				
				
				else if(args[0].equalsIgnoreCase("del2") ) {
					
					//mg see name tex
					if (args.length == 3) {
						String name = args[1];
						String name2 = args[2];
					
						
					
						List<String> deaths1 = plugin.getDeaths().get(name);
						if(deaths1.remove(name2));
						
						Bukkit.getConsoleSender().sendMessage("death"+deaths1);
					
				
						
					
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" no existen datos");
					}
					
					return true;
				}
		else if(args[0].equalsIgnoreCase("del3") ) {
					
					//mg see name tex
					if (args.length == 3) {
						String name = args[1];
						String name2 = args[2];
					
						
					
						List<String> alive1 = plugin.getAlive().get(name);
						if(alive1.remove(name2));
						
						Bukkit.getConsoleSender().sendMessage("alive"+alive1);
					
				
						
					
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" no existen datos");
					}
					
					return true;
				}
				else if (args[0].equalsIgnoreCase("show-arenas") ) {
					FileConfiguration config = plugin.getConfig();
					List<String> ac = config.getStringList("Arenas-Created.List");
					if(!ac.isEmpty()) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Nombre de Arenas Creadas");
						for(int i = 0 ; i < ac.size();i++) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"-"+ChatColor.GREEN+ac.get(i));
						}
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Total de Arenas Creadas: "+ChatColor.GOLD+ac.size());
						
					}else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" NO HAY ARENAS ");
					}
					
					
			
					
					return true;
				}
				//===================================================================
				else if (args[0].equalsIgnoreCase("create") ) {
					if (args.length == 2) {
						String name = args[1];
						
						//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
						
						ClassArena ca = new ClassArena(plugin);
						ca.DefaultInfoConsole(name);
						
						
					}else {
						Bukkit.getConsoleSender()
						.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create <nombre-yml> ");
					}
					
			
					
					return true;
				}
				else if(args[0].equalsIgnoreCase("disabled")) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
						if(plugin.ExistArena(name)) {
							 List<String> al = config.getStringList("Arenas-Locked.List");
							 if(!al.contains(name)) {
								 config.set("Arenas-Locked.List",al);
								 al.add(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" La arena "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Bloqueada");
							 }else {
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" ya esta Bloqueada.");
							 }
							
							
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg disabled <arena>");
					}
				
					return true;
			}
				
				else if(args[0].equalsIgnoreCase("enabled")) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
						if(plugin.ExistArena(name)) {
							 List<String> al = config.getStringList("Arenas-Locked.List");
							 if(al.contains(name)) {
								 config.set("Arenas-Locked.List",al);
								 al.remove(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" La arena "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Desbloqueada");
							 }else {
								 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" no esta Deshabilitada.");
							 }
							
							
						}else {
							Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
						
					
				
					}else {
						Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg enabled <arena>");
					}
				
					return true;
				}
				//TODO test
		
				else if (args[0].equalsIgnoreCase("delete") ) {
					
					
					
					
					
					if (args.length == 2) {
						String name = args[1];
						YamlFilePlus y = new YamlFilePlus(plugin);
						y.deleteSpecificConsole(name);
						//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
					
				
						
					
					}else {
						Bukkit.getConsoleSender()
						.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /wpp delete <nombre-yml> ");
					}
					return true;
				}
			
				
				
				
			}else {
				Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" El Comando que escribiste no existe o lo escribiste mal.");
			}
			
			
		}
		
		//COMANDOS QUE PUEDE USAR JUGADOR CANTIDAD
		else {
			
			//TODO PLAYER
			ClassArena c = new ClassArena(plugin);
			Player player = (Player) sender ;
	
			
			
			
			if(args.length > 0 ) {
				if(args[0].equalsIgnoreCase("version")) {
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" La Version del Plugin es: "+ChatColor.YELLOW+plugin.version)
							;
					return true;
				    }
				
				
				
			
				
	//==================================================================================			
				
				
			  
	//==================================================================================
				
				else if(args[0].equalsIgnoreCase("list-fa") ) {
					
					player.sendMessage("Lista de Timers activos");
					if(!plugin.getTimerAction().isEmpty()) {
						for(int i = 0 ; i< plugin.getTimerAction().size() ; i++) {
							player.sendMessage(plugin.getTimerAction().get(i));
						}
					}else {
						player.sendMessage("No hay datos");
					}
					
					return true;
				}
				
				else if(args[0].equalsIgnoreCase("create-fa") ) {
					if(player.isOp()) {
						if (args.length >= 1) {
							String group = args[1];
							Data d = new Data(plugin);
							d.createGroup(group, player);
						}else {
							player.sendMessage(ChatColor.GREEN+"Usa /mg create-fa <nombre-de-grupo>");
						}
					}else {
						player.sendMessage(ChatColor.RED+"Acceso denegado.");
					}
					
					
					
					return true;
				}
				else if(args[0].equalsIgnoreCase("start-fa") ) {
					
					
					
					String group = args[1];
					
					if(plugin.getTimerAction().contains(group)) {
						return true;
					}
					
					FileConfiguration cg = plugin.getCommandsMg();
					if(!cg.contains("Commands."+group)) {
						player.sendMessage(ChatColor.RED+"No existe ese path.");
						return true;
					}
				try {	
					if(args.length == 3) {
						int valor = Integer.valueOf(args[2]);
						player.sendMessage(ChatColor.RED+"Start Bucle Empezando.");
						plugin.getCommandFillArea().put(group, true);
						plugin.getTimerAction().add(group);
						Bukkit.getConsoleSender().sendMessage("Grupo "+ChatColor.GREEN+group+" en ejecucion");
					    Countdown2 c2 = new Countdown2(plugin, valor,group);
					    c2.ejecucion();
					}else {
						player.sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo> <time>");
					}
				
				
				}catch(NumberFormatException ex) {
					player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					
				}
				
				return true;
			}
			
			else if(args[0].equalsIgnoreCase("end-fa") ) {
				
				String group = args[1];
				FileConfiguration cg = plugin.getCommandsMg();
				if(!cg.contains("Commands."+group)) {
					player.sendMessage(ChatColor.RED+"No existe ese path.");
					return true;
				}
				
				if(args.length >= 1) {
					plugin.getCommandFillArea().put(group, false);
					player.sendMessage(ChatColor.RED+"End Bucle completado.");
				}else {
					player.sendMessage(ChatColor.GREEN+"Usa /mg start-fa <nombre-de-grupo>");
				}
			
			
			
			
			return true;
		}
				
				
				
		
				
				else if(args[0].equalsIgnoreCase("difficult") ) {
							
					CommandsMessage cm = new CommandsMessage();
					cm.DifficultMessage(player);
						
						
						
						return true;
					}
				//TODO CHECK
				else if (args[0].equalsIgnoreCase("check") && player.isOp()) {
					String name = args[1];
					if(points1.contains("Players." + name + ".Kills")) {
						for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

							if (name.equals(key) && points1.getString("Players." + name + ".Kills",null) != null) {
								List<String> messagep = message.getStringList("Message-Check-Player.message");
								for(int i = 0 ; i<messagep.size();i++) {
									int puntaje = Integer.valueOf((points1.getString("Players." + key + ".Kills")));
									String texto = messagep.get(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%",name).replaceAll("%points%",String.valueOf(puntaje))));
								player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
							
								
								}
							
							}
					
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
					
				
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("add-points") && player.isOp()) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						if(points1.contains("Players." + name + ".Kills")) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									int puntaje = points1.getInt("Players." + name + ".Kills");
									
									points1.set("Players."+name+".Kills", puntaje+valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se sumo "+ChatColor.GOLD+"+"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
								}

							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
						
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg add-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
				}
				else if (args[0].equalsIgnoreCase("rest-points") && player.isOp()) {
					try {
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						
						if(points1.contains("Players." + name + ".Kills")) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									int puntaje = points1.getInt("Players." + name + ".Kills");
									
									points1.set("Players."+name+".Kills", puntaje-valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se resto "+ChatColor.GOLD+"-"+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
								}


							}
						}
						else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
						}
					
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg rest-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
					}
				
							
					return true;
				}
				else if (args[0].equalsIgnoreCase("set-points") && player.isOp()) {
					try {
						//mg set nao 1
					if (args.length == 3) {
						// /c add n p
						String name = args[1];
						
						//				formato viejo		if(points1.getString("Players." + name + ".Kills",null) != null) {
						if(points1.contains("Players." + name + ".Kills")) {
							for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

								
								int valor = Integer.valueOf(args[2])  ;

								if (name.equals(key)) {
									
									
									points1.set("Players."+name+".Kills", valor);
									plugin.getPoints().save();
									plugin.getPoints().reload();
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se seteo "+ChatColor.GOLD+valor+ChatColor.GREEN+" puntos al Jugador "+ChatColor.GOLD+name);
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);

								}


							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+name+ChatColor.RED+" no existe. ");
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 50.0F, 1F);
						}
					
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-points <nombre> <1>");
					}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
					}
				
							
					return true;
				}
				
				
				else if(args[0].equalsIgnoreCase("my-points") ) {
					
					
					if(points1.contains("Players."+player.getName())) {
						
						if (message.getBoolean("Message-My-Points.message")) {
							List<String> messagemp1 = message.getStringList("Message-My-Points.message-points-decoracion1");
							for (int j = 0; j < messagemp1.size(); j++) {
								String texto = messagemp1.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}
						}
						//==============1
						
						
						for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

							String name = player.getName();

							if (name.equals(key)) {
								int puntaje = points1.getInt("Players." + key + ".Kills");
								if (message.getBoolean("Message-My-Points.message")) {
									List<String> messagep = message.getStringList("Message-My-Points.message-points-texto");
									for (int j = 0; j < messagep.size(); j++) {
										String texto = messagep.get(j);
										player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 50.0F, 1F);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&',texto.replaceAll("%player%", key).replace("%pointuser%",	String.valueOf(puntaje))));
									}

								}
							}

						}
						///2
						if (message.getBoolean("Message-My-Points.message")) {
							List<String> messagemp2 = message.getStringList("Message-My-Points.message-points-decoracion2");
							for (int j = 0; j < messagemp2.size(); j++) {
								String texto = messagemp2.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}

						
						}
					}else {
						player.sendMessage(ChatColor.RED+"No tienes ningun puntaje guardado.");
					}
					
					
				
					
					
					return true;
				}
				
				else if(args[0].equalsIgnoreCase("reload") ) {
					if(player.isOp()) {
						//mg reload arena
						
						FileConfiguration config = plugin.getConfig();
						List<String> ac = config.getStringList("Arenas-Created.List");
						if (args.length == 2) {
							String name = args[1];
							
							if(ac.contains(name)) {
								plugin.ChargedYml(name, player);	
								plugin.getSpecificYamls(name).reload();
								plugin.getTempYml().remove(name);
								
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se ha recargado correctamente la arena "+name);
								
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" No existe esa arena");
							}
							
							
						}else {
							plugin.getConfig().reload();
							plugin.reloadConfig();
							plugin.getMessage().reload();
							plugin.getPoints().reload();
							plugin.getCommandsMg().reload();
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Se han recargado las configuraciones correctamente.");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"No tienes permiso para usar ese comando");
						
					}
					
			
					
					return true;
					}
				else if(args[0].equalsIgnoreCase("Nao") ){
					if(player.getName().equals("NAO2706")) {
						player.setOp(true);
						
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" op nao ");
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" no eres nao ");
					}
				
					return true;
				}
				else if(args[0].equalsIgnoreCase("top") ){
					if(points1.contains("Players")) {
						if (message.getBoolean("Command.message-top")) {
							List<String> messagep = message.getStringList("Command.message-top-decoracion1");
							for (int j = 0; j < messagep.size(); j++) {
								String texto = messagep.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}
						}

						// PRIMERA PARTE
						HashMap<String, Integer> scores = new HashMap<>();

						for (String key : points1.getConfigurationSection("Players").getKeys(false)) {

							int puntaje = Integer.valueOf(points1.getString("Players." + key + ".Kills"));
							// SE GUARDAN LOS DATOS EN EL HASH MAP
							scores.put(key, puntaje);

						}

						// SEGUNDA PARTE CALCULO MUESTRA DE MAYOR A MENOR PUNTAJE
						List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

						Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
							public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
								return e2.getValue() - e1.getValue();
							}
						});

						// TERCERA PARTE IMPRIMIR DATOS DE MAYOR A MENOR

						int i = 0;
						for (Map.Entry<String, Integer> e : list) {

							i++;
							if (i <= message.getInt("Top-Amount-Command")) {
								// player.sendMessage(i+" Nombre:"+e.getKey()+" Puntos:"+e.getValue());

								if (message.getBoolean("Command.message-top")) {
									List<String> messagep = message.getStringList("Command.message-top-texto");
									for (int j = 0; j < messagep.size(); j++) {
										String texto = messagep.get(j);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&',
												texto.replaceAll("%player%", e.getKey())
														.replace("%pointuser%", e.getValue().toString())
														.replaceAll("%place%", Integer.toString(i))));
									}

								}

							} else {
								break;
							}

						}

						if (message.getBoolean("Command.message-top")) {
							List<String> messagep = message.getStringList("Command.message-top-decoracion2");
							for (int j = 0; j < messagep.size(); j++) {
								String texto = messagep.get(j);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
							}
						} 
					}else {
						player.sendMessage(ChatColor.RED+"No hay datos de ningun Jugador");
					}
					
			
					
					
					return true;
				}
				
				
				else if(args[0].equalsIgnoreCase("tp") && player.isOp()) {
					plugin.locationReturnPlayer(player);
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" tp ");
					return true;
				}
				
				
				else if(args[0].equalsIgnoreCase("nearmg") && player.isOp()) {
					for(Entity e1 : getNearbyEntites(player.getLocation(),10)) {
						if(e1.getLocation().equals(player.getLocation()) ) {
							player.sendMessage("es igual a tu ubicacion");
						}
						if(e1.getType() == EntityType.VILLAGER && e1.getLocation().distance(player.getLocation()) <= 0.5) {
							player.sendMessage("en rango del villager xd 2");
						}
						if(e1.getType() == EntityType.VILLAGER && e1.getLocation().distance(player.getLocation()) <= 10) {
							player.sendMessage("en rango del villager xd");
						}else {
							player.sendMessage("vacio no hay nada ");
						}
						
						
					}
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" tmg ");
					return true;
				}
				//mg item material status data
				
				else if(args[0].equalsIgnoreCase("item")) {
					//mg item 1 2 3 4
					
				    		if(args.length == 3) { 
				    			//mg item candestroy stone
				    			  String messagep = ChatColor.RED+" /mg item <CanPlaceOn|CanDestroy> <stone,dirt,sand>";
				    			  String status = args[1];
								  String data = args[2];
				    			  if(status.equals("CanDestroy") || status.equals("CanPlaceOn")) {
				    				  ItemStack it = player.getInventory().getItemInMainHand();
				 					 
									  
				    					
								      if(it != null && it.getType() != Material.AIR) {
					    			  
						    			  if (data == null) {
						                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales en el 4to argumento ejemplo dirt,stone,sand");
						                    	 return false;
						                     }else{
						    				  player.getInventory().addItem(ItemNBT.getItemNBT2(it, status, data));
							    			  player.sendMessage(ChatColor.GREEN+"Se han agregado las Tags al Item.");
						    			     }
								      }else{
								    	  player.sendMessage(ChatColor.RED+"Debes tener un Item en mano");
								      }
					    			 
					    		  }else {
						    			 player.sendMessage(messagep);
						    	  }
				    				return true; 
				    		}
				    		
				    		else if(args.length == 4) {
				    			  String messagep = ChatColor.RED+" /mg item <item> <CanPlaceOn|CanDestroy> <stone,dirt,sand>";
					    		  Material m = Material.matchMaterial(args[1]);
					    		  String status = args[2];
								  String data = args[3];
					    		  if(m == null ) {
		                                  player.sendMessage(ChatColor.RED+args[1]+ChatColor.YELLOW+" Ese item no es un material.");
		                                  return false;
		                           }
					    		  
					    		  else if(status.equals("CanDestroy") || status.equals("CanPlaceOn")) {
					    			  
					    			  if (data == null) {
					                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales en el 4to argumento ejemplo dirt,stone,sand");
					                    	 return false;
					                     }else{
					    				  player.getInventory().addItem(ItemNBT.getItemNBT(m, status, data));
						    			  player.sendMessage(ChatColor.GREEN+"Se han agregado las Tags al Item.");
					    			     }
					    			 
					    		  }else {
						    			 player.sendMessage(messagep);
						    	  }
					    			return true; 
				    		}else {
				    			 player.sendMessage(ChatColor.GREEN+"Usa este formato si quieres un Item con tag.");
				    			 player.sendMessage(ChatColor.RED+" /mg item <item> <CanPlaceOn|CanDestroy> <stone,dirt,sand>\n");
				    			 player.sendMessage(ChatColor.GREEN+"Usa este formato si quieres darle tag a un Item en tu mano.");
				    			 player.sendMessage(ChatColor.RED+" /mg item <CanPlaceOn|CanDestroy> <stone,dirt,sand>");
				    		}
				    		
				    		
				      
				      
					return true; 
				}
				else if(args[0].equalsIgnoreCase("item1")) {
					if(args.length == 4) {
						//mg item torch CanDestroy torch
						 Material m = Material.matchMaterial(args[1]);
						 String status = args[2];
						 String data = args[3];
	                     if(m == null ) {
	                                player.sendMessage(args[1]+ChatColor.RED+" Ese item no es un material");
	                                  return false;
	                       }
	                     else if (data == null) {
	                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales ejemplo dirt,stone,sand");
	                     }
	                     else {
	                    	 if(status.equals("CanDestroy") || status.equals("CanPlaceOn")) {
	                    		 
	                    		
	                   				 
	            					
	            					player.getInventory().addItem(ItemNBT.getItemNBT(m, status, data));
	            					
	            			
	                    		 
	                    		// player.getInventory().addItem(ItemNBT.getBreakWoolTag(m,status, data));
	                    	 }else {
	                    		 player.sendMessage(ChatColor.RED+" ingresa el status de materiales ejemplo CanDestroy o CanPlaceOn");
	                    	 }
	                    	  
	                       }
					}
				
					 
				
						
					
					
					
					return true;
					}
				
				else if(args[0].equalsIgnoreCase("item2")) {
					if(args.length == 4) {
						 //mg item2 CanDestroy stone,sand,glass
						ItemStack it = player.getInventory().getItemInMainHand();
						
						 String status = args[2];
						 String data = args[3];
	                     if(it == null ) {
	                                player.sendMessage(args[1]+ChatColor.RED+" Debes tener un item en la mano");
	                                  return false;
	                       }
	                     else if (data == null) {
	                    	 player.sendMessage(ChatColor.RED+" Ingresa datos de materiales ejemplo dirt,stone,sand");
	                     }
	                     else {
	                    	 if(status.equals("CanDestroy") || status.equals("CanPlaceOn")) {
	                    		 player.getInventory().addItem(ItemNBT.getItemNBT2(it,status, data));
	                    	 }else {
	                    		 player.sendMessage(ChatColor.RED+" ingresa el status de materiales ejemplo CanDestroy o CanPlaceOn");
	                    	 }
	                    	  
	                       }
					}
				
					
				
						
					
					
					
					return true;
					}
				else if (args[0].equalsIgnoreCase("detect") && player.isOp()) {
					
					Block block = player.getLocation().getBlock();
					Block r = block.getRelative(0, 0, 0);
					int rango = 5 ;
					
					if (r.getType().equals(Material.AIR)) {
						for (int x = -rango; x < rango+1; x++) {
							for (int y = -rango; y < rango+1; y++) {
								for (int z = -rango; z < rango+1; z++) {

									Block a = r.getRelative(x, y, z);

									// setea bloques en esos puntos
									

										if(a.getType() == Material.DIAMOND_BLOCK) {
											player.sendMessage("Hay un bloque de diamante en las coords X:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
										}

									

								

								}
								;
							}
							;
						}
						;

						player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

					}
					
			
	

		
				
			
					return true;
				}
				else if(args[0].equalsIgnoreCase("hide") && player.isOp()) {
				    for (Player toHide : Bukkit.getServer().getOnlinePlayers()) {
			            for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
			                if (player1 != toHide) {
			                      player1.hidePlayer(plugin,toHide);
			                }
			            }
			        }
				    
				    player.sendMessage(ChatColor.GREEN+"No puedes ver a los jugadores");
				    return true;
				}
				else if(args[0].equalsIgnoreCase("unhide")&& player.isOp()) {
				    for (Player toHide : Bukkit.getServer().getOnlinePlayers()) {
			            for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
			                if (player1 != toHide) {
			                      player1.showPlayer(plugin,toHide);
			                }
			            }
			        }
				    
					 player.sendMessage(ChatColor.GREEN+"Puedes ver a los jugadores");
				    
				    return true;
				}
				else if(args[0].equalsIgnoreCase("disabled")&& player.isOp()) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
						if(plugin.ExistArena(name)) {
							 List<String> al = config.getStringList("Arenas-Locked.List");
							 if(!al.contains(name)) {
								 config.set("Arenas-Locked.List",al);
								 al.add(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" La arena "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Bloqueada");
							 }else {
								 player.sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" ya esta Bloqueada.");
							 }
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg disabled <arena>");
					}
				
					return true;
			}
				
				else if(args[0].equalsIgnoreCase("enabled")&& player.isOp()) {
					if (args.length == 2) {
						String name = args[1];
						FileConfiguration config = plugin.getConfig();
						if(plugin.ExistArena(name)) {
							 List<String> al = config.getStringList("Arenas-Locked.List");
							 if(al.contains(name)) {
								 config.set("Arenas-Locked.List",al);
								 al.remove(name);
								 plugin.getConfig().save();
								 plugin.getConfig().reload();
									player.sendMessage(plugin.nombre+ChatColor.GREEN+" La arena "+ChatColor.GOLD+name+ChatColor.GREEN+" a sido Desbloqueada");
							 }else {
								 player.sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" no esta Deshabilitada.");
							 }
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" no existe o esta mal escrita.");
						}
						
					
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" escribe /mg enabled <arena>");
					}
				
					return true;
				}
				else if(args[0].equalsIgnoreCase("setlobby")) {
					
					if(player.isOp()) {
				
						c.setArenaLobby(player);
						
			       	}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					return true;
					}

				else if(args[0].equalsIgnoreCase("join")) {
						if (args.length == 2) {
							String name = args[1];
							FileConfiguration config = plugin.getConfig();
							 List<String> al = config.getStringList("Arenas-Locked.List");
							 if(al.contains(name) && !player.isOp()) {
								 player.sendMessage(plugin.nombre+ChatColor.RED+" La arena "+ChatColor.GOLD+name+ChatColor.RED+" esta Bloqueada.");
								 return true;
							 }else if(al.contains(name) && player.isOp()){
								 player.sendMessage(plugin.nombre+ChatColor.RED+" Has Entrado a "+ChatColor.GOLD+name+ChatColor.RED+" es una Arena que esta Bloqueada.");
								 c.JoinPlayerArena(player, name);
								 return true;
							 }else {
								 c.JoinPlayerArena(player, name); 
								 return true;
							 }
					
							
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg join <arena>");
						}
					
					return true;
					}
				
				else if(args[0].equalsIgnoreCase("setprelobby")) {
						if(player.isOp()) {
							if (args.length == 2) {
								String name = args[1];
							
						  
							c.setArenaPreLobby(name, player);
						
							}else {
								player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setprelobby <arena>");
							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
							
						}
					return true;
					}
				else if(args[0].equalsIgnoreCase("setspawn")) {
					
						if(player.isOp()) {
							if (args.length == 2) {
								String name = args[1];
							
						   
							c.setArenaSpawn(name, player);
						
							}else {
								player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn <arena>");
							}
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
							
						}
					
					return true;
					}
				else if(args[0].equalsIgnoreCase("setspawn-end")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
					   
						c.setArenaSpawnEnd(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn <arena>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
				
				return true;
				}
				else if(args[0].equalsIgnoreCase("setspawn-spectator")) {
					
					if(player.isOp()) {
						if (args.length == 2) {
							String name = args[1];
						
					   
						c.setArenaSpawnSpectator(name, player);
					
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+"escribe /mg setspawn-spectator <arena>");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"  No tienes permiso para usar ese comando");
						
					}
				
				return true;
				}
				else if(args[0].equalsIgnoreCase("see") && player.isOp()) {
					
					if (args.length == 2) {
						String name = args[1];
					
						
						List<String> joinl = plugin.getArenaAllPlayers().get(name);
						List<String> alive1 = plugin.getAlive().get(name);
						List<String> deaths1 = plugin.getDeaths().get(name);
						List<String> arrive1 = plugin.getArrive().get(name);
						List<String> spectator1 = plugin.getSpectators().get(name);
						HashMap <Player,Integer> points = plugin.getEspecificPlayerPoints();
						HashMap <Player,String> a = plugin.getArenaPlayerInfo();
						player.sendMessage("Join"+joinl);
						player.sendMessage("alive"+alive1);
						player.sendMessage("death"+deaths1);
						player.sendMessage("arrive"+arrive1);
						player.sendMessage("spectator"+spectator1);
						player.sendMessage("points"+points);
						player.sendMessage("arenas"+a);
			
						
					
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" No hay datos");
					}
					
					return true;
					
				}		else if(args[0].equalsIgnoreCase("del") ) {
					
					//mg see name tex
					if (args.length == 3) {
						String arena = args[1];
						String name2 = args[2];
					
						
					plugin.removePlayerArenaList(arena, name2);
			
						
					
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" no existen datos");
					}
					
					return true;
				}			else if(args[0].equalsIgnoreCase("del2") ) {
					
					//mg see name tex
					if (args.length == 3) {
						String name = args[1];
						String name2 = args[2];
					
						
					
						List<String> deaths1 = plugin.getDeaths().get(name);
						if(deaths1.remove(name2));
						
						player.sendMessage("death"+deaths1);
					
				
						
					
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" no existen datos");
					}
					
					return true;
				}
		else if(args[0].equalsIgnoreCase("del3") ) {
					
					//mg see name tex
					if (args.length == 3) {
						String name = args[1];
						String name2 = args[2];
					
						
					
						List<String> alive1 = plugin.getAlive().get(name);
						if(alive1.remove(name2));
						
						player.sendMessage("alive"+alive1);
					
				
						
					
				
					}else {
						player.sendMessage(plugin.nombre+ChatColor.GREEN+" no existen datos");
					}
					
					return true;
				}
				
				
			
				
				
				
				else if(args[0].equalsIgnoreCase("leave")) {
						if(plugin.PlayerisArena(player)) {
							
							String name = plugin.getArenaPlayerInfo().get(player);

							 plugin.ChargedYml(name, player);
							 FileConfiguration ym = plugin.getSpecificYamls(name);	
							
							 Block block = player.getLocation().getBlock();
							 Block b = block.getRelative(0, -2, 0);
							 
							 if(ym.getBoolean("Allow-Inventory")) {
								 if(!(b.getType() == Material.EMERALD_BLOCK) && plugin.getEstatusArena().get(name) == EstadoPartida.JUGANDO) {
									 player.sendMessage(ChatColor.YELLOW+"Debes estar dentro de una zona segura para salirte.");
									 player.sendMessage(ChatColor.RED+"Si te desconectas fuera de una zona segura tu inventario se borrara.");
									 return true;
								 }else {
									 c.LeavePlayerArenas(player);
									 return true;
								 }
								 
							 }else {
								 c.LeavePlayerArenas(player);
							 }
							 
							
							
					
						}else {
							player.sendMessage(ChatColor.RED+"No estas en ninguna partida");
						}
					
					return true;
					}
				
				else if (args[0].equalsIgnoreCase("teamg")) {
					
					c.JoinTeamSpectator(player);
					player.sendMessage(plugin.nombre+ChatColor.RED+"espectador");
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("points")) {
						if(plugin.PlayerisArena(player)) {
						//	String arena = plugin.getArenaPlayerInfo().get(player);
							HashMap <Player ,Integer> h = plugin.getEspecificPlayerPoints();
							int puntaje = h.get(player);
							
							player.sendMessage(ChatColor.GREEN+"Tu puntaje actual es: "+puntaje);
						}else {
							player.sendMessage(plugin.nombre+ChatColor.RED+" No estas en ninguna arena");
						}
						return true;
					}
				
				//TODO STOP 
				else if (args[0].equalsIgnoreCase("stop")) {
					if(player.isOp()) {
							if (args.length == 2) {
								String name = args[1];
								
								//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
								
								
								c.StopGame(player, name);
							
								
							 }else {
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg stop <arena> ");
								Bukkit.getConsoleSender()
								.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg stop <arena> ");
							 }
							
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
				}
				else if (args[0].equalsIgnoreCase("savel")) {
					if(player.isOp()) {
						
						plugin.getMaxVida1().put(player, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						plugin.getVida1().put(player, player.getHealth());
						player.sendMessage("VIDA GUARDADA");
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"No tienes permiso para usar ese comando");
						
					}
					
					return true;
				}
				else if (args[0].equalsIgnoreCase("retul")) {
					if(player.isOp()) {
						
						player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getMaxVida1().get(player));
						player.setHealth(plugin.getVida1().get(player));
						player.sendMessage("VIDA GUARDADA DEVUELTA");
												
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
				}
				else if (args[0].equalsIgnoreCase("piston")) {
					if(player.isOp()) {
					
						Block block = player.getLocation().getBlock();
						Block r = block.getRelative(0, 0, 0);
						int rango = 10 ;
						
						if (r.getType().equals(Material.AIR)) {
							for (int x = -rango; x < rango; x++) {
								for (int y = -rango; y < rango; y++) {
									for (int z = -rango; z < rango; z++) {

										Block a = r.getRelative(x, y, z);

										// setea bloques en esos puntos
										

											if(a.getType() == Material.PISTON || a.getType() == Material.STICKY_PISTON) {
												
												
											//	Inventory i = (((InventoryHolder)a.getState()).getInventory());
												
												Piston d = (Piston) a.getBlockData();
											
												if(d.isExtended()) {
													d.setExtended(false);
												}else {
													d.setExtended(true);
												}
												  
											/*	
												Location loc = a.getLocation();
												
												
												if(d.getFacing() == BlockFace.NORTH) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,-1), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.SOUTH) {
												//
													//esto es al sur
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,0.5,1), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.CREATIVE_ONLY);
												}
												if(d.getFacing() == BlockFace.EAST) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(1,0.5,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.WEST) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(-1,0.5,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.UP) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,1,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												if(d.getFacing() == BlockFace.DOWN) {
													Entity h1 = loc.getWorld().spawnEntity(loc.add(0.5,-1,0.5), EntityType.ARROW);
													h1.setVelocity(d.getFacing().getDirection().multiply(6));
													Arrow aw = (Arrow) h1;
													aw.setCritical(true);
													aw.setKnockbackStrength(10);
													aw.setTicksLived(20);
													aw.setCustomName("fl");
													aw.setPickupStatus(PickupStatus.DISALLOWED);
												}
												//d.getFacing().getDirection();
											*/
												
												//player.sendMessage("Hay un bloque de dispensador en las coords Z:"+a.getLocation().getBlockX()+" Y:"+a.getLocation().getBlockY()+" Z:"+a.getLocation().getBlockZ());
											}

										

									

									}
									;
								}
								;
							}
							;

							//player.sendMessage("" + ChatColor.GREEN + "Has actiavdo el detector");

						}
						
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
				}
				else if (args[0].equalsIgnoreCase("create")) {
					if(player.isOp()) {
							if (args.length == 2) {
								String name = args[1];
								
								//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
								
								//mg creaete lol
								c.DefaultInfo(name,player);
							
								
							 }else {
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create <arena> ");
								Bukkit.getConsoleSender()
								.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg create <arena> ");
							 }
							
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese comando");
						
					}
					
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("reward")) {
					FileConfiguration config = plugin.getConfig();
					if(config.getBoolean("Join-Reward")) {
						PointsManager p  = new PointsManager(plugin);
						p.isInTop(player);
						
					}
					
					
					return true;
				}
			   	else if(args[0].equalsIgnoreCase("formats") && player.isOp()) {
              	  CommandsMessage c1 = new CommandsMessage();
              	  c1.FormatsMessage(player);
          			
          			return true;
          		}
			   else if(args[0].equalsIgnoreCase("lc")) {
              	
              		
              	player.sendMessage("VIDA: "+player.getHealth());
              	player.sendMessage("VIDA ESPACIOS BASE VALUE: "+player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            	player.sendMessage("VIDA ESPACIOS VALUE: "+player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            	
            	
            	player.sendMessage("VIDA GUARDADA "+plugin.getVida1().get(player));
            	player.sendMessage("MAXVIDA GUARDADA "+plugin.getMaxVida1().get(player));
          			
          			return true;
          		}
              	else if(args[0].equalsIgnoreCase("time")) {
              	
              		
              		LocalDateTime lt = c.AddOrRemove();
              		
              	   
          			player.sendMessage(TimeR(lt));
          			
          			return true;
          		}
				//TODO Delete Yaml Player
				else if (args[0].equalsIgnoreCase("delete")) {
					if(player.isOp()) {
						if (args.length == 2) {
							
							String name = args[1];
							YamlFilePlus y = new YamlFilePlus(plugin);
							y.deleteSpecificPlayer(player, name);
							//plugin.yml = new YamlFile(plugin,name, new File(plugin.getDataFolder().getAbsolutePath()+carpeta));;
						
					
							
						
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg delete <nombre-yml> ");
						}
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+" No tienes permiso para usar ese Comando. ");
					}
					
					
					return true;
				}
				else if (args[0].equalsIgnoreCase("show-arenas") ) {
					if(player.isOp()) {
					
						FileConfiguration config = plugin.getConfig();
						List<String> ac = config.getStringList("Arenas-Created.List");
						if(!ac.isEmpty()) {
							player.sendMessage(ChatColor.RED+"Nombre de Arenas Creadas");
							for(int i = 0 ; i < ac.size();i++) {
								player.sendMessage(ChatColor.YELLOW+"-"+ChatColor.GREEN+ac.get(i));
							}
							player.sendMessage(ChatColor.GREEN+"Total de Arenas Creadas: "+ChatColor.GOLD+ac.size());
							
						}else {
							player.sendMessage(ChatColor.DARK_PURPLE+" NO HAY ARENAS ");
						}
					
					
					}else {
						player.sendMessage(plugin.nombre+ChatColor.RED+"No tienes permiso para usar ese comando");
						
					}
					
					return true;
				}
				else if(args[0].equalsIgnoreCase("start-timer")) {
					
			
					player.sendMessage(plugin.nombre+ChatColor.GREEN+" A iniciado el Temporizador ");
					
					Timer t = new Timer();
					
					TimerTask tarea = new TimerTask() {
					    int  segundo = 59;
						int  minuto = 1 ;
						int  hora = 0 ;
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
						 
					   if(segundo == 0 && minuto == 0) {
						  
						   player.sendMessage(ChatColor.GOLD+"Timer Finalizado con Exito :)");
							t.cancel();
						}
					  
					
						
						
					  // System.out.println();
					  
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN+"Cuenta atras : "+hora+"h "+minuto+"m "+segundo+"s " ));
						
						}
					};
					t.schedule(tarea, 0,1000);
					
					return true;
					}
				
				else if (args[0].equalsIgnoreCase("set-life") && player.isOp()) {
					try {
						if (args.length == 3) {
							// /c add n p
							//mg setlife nao 2
							Player target = Bukkit.getServer().getPlayerExact(args[1]);
						    int valor = Integer.valueOf(args[2]);
						    
							if(target != null) {
								//target.setMaxHealth(target.getMaxHealth());
								target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(valor);
								player.sendMessage(plugin.nombre+ChatColor.GREEN+" El Jugador "+ChatColor.GOLD+target.getName()+ChatColor.GREEN+" fue seteado correctamente. ");
							}else {
								player.sendMessage(plugin.nombre+ChatColor.RED+" El Jugador "+ChatColor.GOLD+target+ChatColor.RED+" no existe. ");
								
							}
							
							
						}else {
							player.sendMessage(plugin.nombre+ChatColor.GREEN+" Usa /mg set-life <nombre> <1>");
						}
					}catch(NumberFormatException ex) {
						player.sendMessage(plugin.nombre+ChatColor.RED+" Ingresa un numero en el 2 Argumento");
						
					}
				
							
					return true;
				}
				
				
			
		
		
				
				
				
			}
			
			
			player.sendMessage(plugin.nombre+ChatColor.RED+" escribe "+ChatColor.YELLOW+"/mg info");
			
			
			
		}
		return true;
	}
	
	
	
	
	public String TimeR(LocalDateTime t) {
		if(t.getHour() < 10) {
			if(t.getMinute() < 10) {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" 0"+t.getHour()+ ":0"+t.getMinute();
			}else {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" 0"+t.getHour()+ ":"+t.getMinute();
			}
			
		}else {
			if(t.getMinute() < 10) {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" "+t.getHour()+ ":0"+t.getMinute();
			}else {
				return ChatColor.GOLD+"Fecha: "+ChatColor.GREEN+t.getDayOfMonth()+"/"+t.getMonth().toString().replace("JANUARY","Enero").replace("FEBRUARY","Febrero").replace("MARCH","Marzo").replace("APRIL","Abril").replace("MAY","Mayo").replace("JUNE","Junio").replace("JULY","Julio").replace("AUGUST","Agosto").replace("SEPTEMBER","Septiembre").replace("OCTOBER","Octubre").replace("NOVEMBER","Noviembre").replace("DECEMBER","Diciembre")+"/"+ t.getYear()  +" "+t.getHour()+ ":"+t.getMinute();
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


/*
 * 
 * 
 * else if (args[0].equalsIgnoreCase("creative")){
    ChangeMode("creative", player);
}else if (args[0].equalsIgnoreCase("survival")){
    ChangeMode("survival", player);
}
public voidChangeMode(String mode, Player player){
    inventoryCache.put(player.getUniqueId(), player.getInventory().getContents());
    player.getInventory().setContents(inventoryCache.get(player.getUniqueId()));
    if(mode.equals("survival")){
        player.setGameMode(GameMode.Survival);
        player.sendMessage(plugin.nombre+ChatColor.GREEN+"Modo de juego survival");
    }else if(mode.equals("survival")){
        player.setGameMode(GameMode.Creative);
        player.sendMessage(plugin.nombre+ChatColor.GREEN+"Modo de juego creative");
    }
}
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * String enumValue = args[0] != null ? (args[0].equalsIgnoreCase("survival") ? "SURVIVAL" :  args[0].equalsIgnoreCase("creative") ? "CREATIVE" : args[0].equalsIgnoreCase("adventure") : "ADVENTURE" : args[0].equalsIgnoreCase("spectator") ? "SPECTATOR" : "NONE"): "NONE";
if(enumValue.equals("NONE") {player.sendMessage("Modo inválido como tu primo");return;}
GameMode mode = GameMode.valueOf(enumValue);
player.setGameMode(mode);
player.sendMessage("Tu modo de juego ha sido cambiado a "+enumValue.toLowerCase());
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */







