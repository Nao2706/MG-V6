package me.nao.main.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.nao.command.game.Comandos;
import me.nao.command.game.TabComplete1;
import me.nao.event.game.GameEvent;
import me.nao.events.EventRandoms;
import me.nao.manager.ClassArena;
import me.nao.manager.EstadoPartida;
import me.nao.shop.MinigameShop1;
import me.nao.yamlfile.game.YamlFile;
import me.nao.yamlfile.game.YamlFilePlus;
import me.top.users.PHMiniGame;







public class Main extends JavaPlugin{
	
	
	public String carpeta = "/Arenas/";

    
    private YamlFile general;
  
    ///
    //TODO MINI DB
  
    private YamlFile config;
    private YamlFile messages;
    private YamlFile points;
    private YamlFile cooldown;
    private YamlFile levels;
    private YamlFile commands;
    
    
   //==================================== 
   
    private Map<UUID, GameMode> gmode ;//guarda y devuelve el inv
    private Map<UUID, Collection<PotionEffect>>potion ;//guarda y devuelve el inv
	private Map<UUID, ItemStack[]>inventoryCache ;//guarda y devuelve el inv
	public Map<String,YamlFile>getAllYmls  ; // YML Manager
	
	HashMap <Player,Boolean> Fly ;
	HashMap <Player,Double> vida ;  
	HashMap <Player,Double> maxvida ;  
	HashMap <Player,Integer> comida ;
	HashMap <Player,Float> exp ;
	HashMap <Player,Integer> texp ;
	HashMap <Player,String> checkpoint ; 
    HashMap <Player,String> arena ;    //hashmap creado para almacenar al jugador y al nombre de la arena
    HashMap <String,EstadoPartida> statusArena ;
        
   
    HashMap <Player,Player> tp ;
   	//
    //DATOS X
  
    HashMap <String,String> totalp ;
    HashMap <String,String> cronomet ;
    HashMap <String,String> aretime ;
    HashMap <Player,Integer> pointsgame ;
    HashMap <Player,String> location ;	//guarda coords o ubicacion de jugador
    HashMap <Player,String> deadmob ;
    HashMap <String,YamlFile> yml ;
    
    //Listas Total de jugadores , total de vivos , muertos spectadores etc
    HashMap <String,List<String>> arenajoin ;
    HashMap <String,List<String>> arrive ;
    HashMap <String,List<String>> alive ;
    HashMap <String,List<String>> deaths ;
    HashMap <String,List<String>> spectators ;
   
    
    List<String> stop;
    List<String> air;
    List<String> timeract;
    List<String> spect2;
    List<Location> doors;
    
    //Esto fue un test
    HashMap <Player,Double> vida1 ;  
	HashMap <Player,Double> maxvida1 ;  
	
	HashMap <String,Boolean> cg ;
    
   // HashMap <String,YamlFile> t2 ;
    
     //creada para agregar a al nombre de la arena 1 vez para iniciar timer o un cronometro
 
	

	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String nombre = ""+ChatColor.AQUA+ChatColor.BOLD+"["+ChatColor.GREEN+ChatColor.BOLD+pdffile.getName()+ChatColor.AQUA+ChatColor.BOLD+"]";
	
	
	
	private Team green ;
	private Team red ;
	private Team white ;
	

    HashMap <String,BossBar> boss ;
	///================================================0

	
	

	
	/////////////////////////////////////////////////////////////7===============================================================
	public void addPlayerArena(Player player,String NameArena) {
		arena.put(player, NameArena);
		
	}
	//antes tenia player, arena
	public void removePlayerArena(Player player) {
		arena.remove(player);
	}
	
	public void JoinPlayerPoints(String nameArena ,Player player) {
		
		pointsgame.put(player, 0);
		
		
	}
	
	public void LeavePlayerPoint(String nameArena ,Player player) {
		
		pointsgame.remove(player);
		
	} 
	
	public void resetPlayerPoints(String nameArena ,Player player) {
		pointsgame.remove(player);
		
		//points.remove(player);
	}
	
	 public boolean PlayerisArena(Player player) {
		 if(arena.containsKey(player)){
				return true;
			}
			else {
				return false;
			}
	 }
	 
	
	 public void locationPlayer(Player player) {
			location.put(player,player.getLocation().getWorld().getName()+"/"+player.getLocation().getX()+"/"+player.getLocation().getY()+"/"+player.getLocation().getZ()+"/"+player.getLocation().getYaw()+"/"+player.getLocation().getPitch());
	 }
	 
	 
	 public void locationReturnPlayer(Player player) {
			
		   String[] coords1 = location.get(player).split("/");
		   String world2 = coords1[0];
		   Double x2 = Double.valueOf(coords1[1]);
		   Double y2 = Double.valueOf(coords1[2]);
		   Double z2 = Double.valueOf(coords1[3]);
		   Float yaw2 = Float.valueOf(coords1[4]);
		   Float pitch2 = Float.valueOf(coords1[5]);

	   	
		Location l1 = new Location(Bukkit.getWorld(world2), x2, y2, z2, yaw2, pitch2);
		player.teleport(l1);
		location.remove(player);
				
				return;
		 
	 }
	
	public void locationReturnLobbyPlayer(Player player) {
			for (String key : config.getConfigurationSection("Lobby-Spawn").getKeys(false)) {
				
				String world1 = key;
				double x = Double.valueOf(config.getString("Lobby-Spawn." + key + ".X"));
				double y = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Y"));
				double z = Double.valueOf(config.getString("Lobby-Spawn." + key + ".Z"));
				float yaw = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Yaw"));
				float pitch = Float.valueOf(config.getString("Lobby-Spawn." + key + ".Pitch"));
	
				Location l = new Location(Bukkit.getWorld(world1), x, y, z, yaw, pitch);
				player.teleport(l);
	
				player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0, 1, 0),
						/* NUMERO DE PARTICULAS */20, 10, 10, 10, /* velocidad */0, null, true);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20.0F, 1F);
			
				return;
			
	
		}
	}
	
	//TODO save inventory
	public void saveInventoryPlayerArena(Player player) {
		
		
		gmode.put(player.getUniqueId(),player.getGameMode());
		inventoryCache.put(player.getUniqueId(), player.getInventory().getContents());
		Fly.put(player, player.isFlying());
		potion.put(player.getUniqueId(), player.getActivePotionEffects());
		maxvida.put(player, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		vida.put(player, player.getHealth());
		comida.put(player,player.getFoodLevel());
		exp.put(player, player.getExp());
		texp.put(player, player.getLevel());
		//displayn.put(player.getUniqueId(), player.getDisplayName());
		
	
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		player.setExp(0);
		player.setLevel(0);
		player.getInventory().clear();
		player.setFoodLevel(20);
		player.setFlying(false);
		player.setHealth(20);
		player.setGameMode(GameMode.ADVENTURE);
		
		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		
	}
	
	public void saveGFPlayerArena(Player player) {
		
		gmode.put(player.getUniqueId(),player.getGameMode());
		Fly.put(player, player.isFlying());
		//displayn.put(player.getUniqueId(), player.getDisplayName());
		
		player.setFlying(false);
		player.setGameMode(GameMode.ADVENTURE);
		
	}
	
	//===================================================
	
	public void restoreInventoryPlayerArena(Player player) {
		
		
		 player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		 player.setGameMode(gmode.get(player.getUniqueId()));
		 player.setFlying(Fly.get(player));
		 player.getInventory().setContents(inventoryCache.get(player.getUniqueId()));
		 player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxvida.get(player));
		 player.setFoodLevel(comida.get(player));
		 player.addPotionEffects(potion.get(player.getUniqueId()));
		 player.setExp(exp.get(player));
		 player.setLevel(texp.get(player));
		 player.setHealth(vida.get(player));
		// player.setDisplayName(displayn.get(player.getUniqueId()));
		 
		 
	}
	
	public void restoreGFPlayerArena(Player player) {
		
		 player.setGameMode(gmode.get(player.getUniqueId()));
		 if(Fly.containsKey(player)) {
			 player.setFlying(Fly.get(player));
		 }
		// player.setDisplayName(displayn.get(player.getUniqueId()));
	}  
	
	//====================================================================================================================
	// metodo que guarda y te retorna a tu locacion esto debera ser usado sino hay spawn seteado o lobby
	//salva info del jugador
	
	//se buego el salvado de items probablemente por el if getConfig que fue agregado
	
	public boolean joinExistLobby(Player player) {
		if(this.config.getBoolean("Lobby-Active")) {
			return true;
		}else {
			locationPlayer(player);
			return false;
		}
	}
	
	public boolean leaveExistLobby(Player player) {
		if(this.config.getBoolean("Lobby-Active")) {
			locationReturnLobbyPlayer(player);
			return true;
		}else {
			locationReturnPlayer(player);
			return false;
		}
	}

	 public boolean isAllowedJoinInventory(Player player) {
		 String name = getArenaPlayerInfo().get(player);
		 	YamlFilePlus u = new YamlFilePlus(this);
			FileConfiguration ym = u.getSpecificYamlFile("Arenas",name);
			 if(ym.getBoolean("Allow-Inventory")) {
				 saveGFPlayerArena(player);
				 
				 return true;
			 }else{
				 
				 saveInventoryPlayerArena(player);
				 
				 return false;
			 }
	 }
	 
	 public boolean isAllowedLeaveInventory(Player player) {
		 String name = getArenaPlayerInfo().get(player);
		 YamlFilePlus u = new YamlFilePlus(this);
		 FileConfiguration ym = u.getSpecificYamlFile("Arenas",name);
			 if(ym.getBoolean("Allow-Inventory")) {
				 restoreGFPlayerArena(player);
				
				 return true;
			 }else{
				 restoreInventoryPlayerArena(player);
				 
				 return false;
			 }
	 }
	 
	 
	 

	public void Join(Player player) {
		joinExistLobby(player);
		isAllowedJoinInventory(player);
	}
	
	public void Leave(Player player) {
		leaveExistLobby(player);
		isAllowedLeaveInventory(player);
	}
	

	//====================================================================================================================

	// metodo que se ejecuta  al acabar una partida

	 //para comprobar si un jugador esta en una arena
	
	
	 
	
	//====================================================================================================================
	 //este metodo es usado para comprobar si existe hecho para no resetear la config de los ymls
	 public boolean ExistArena(String name) {
		 List<String> ac = config.getStringList("Arenas-Created.List");
		 if(ac.contains(name)){
				return true;
			}
			else {
				return false;
			}
	 }
	//====================================================================================================================
	 
	 
	//====================================================================================================================	
	//TODO LOAD YMLS
		public void LoadYml() {
			
			List<String> ac = this.config.getStringList("Arenas-Created.List");
			if(!ac.isEmpty()) {
				this.general = new YamlFile(this,ac.get(0), new File(this.getDataFolder().getAbsolutePath()+carpeta));
				for(int i = 0 ; i < ac.size();i++) {
				
					getAllYmls.put(ac.get(i),general);
					statusArena.put(ac.get(i),EstadoPartida.ESPERANDO);
				}
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" LAS ARENAS HAN SIDO CARGADAS");
				
			}else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" NO HAY ARENAS ");
			}
			
		}

	 //TODO YML
	 
	 
	 public void ChargedYml(String name, Player player) {
		
		 List<String> ac = config.getStringList("Arenas-Created.List");
			if(ac.contains(name)) {
				this.general = new YamlFile(this,name, new File(this.getDataFolder().getAbsolutePath()+carpeta));
				getAllYmls.put(name,general);
				if(player != null) {
					 
					//player.sendMessage(nombre+ChatColor.GREEN+" La arena "+name+" a sido cargada");
				}
				Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GREEN+" La arena "+name+" a sido cargada");
			}
			else {
		    	Bukkit.getConsoleSender().sendMessage("La Arena "+name+" no existe.");
		    	if(player != null) {
		    	player.sendMessage(nombre+ChatColor.GREEN+" La arena "+name+" no existe");
		    	}
		       }
	 }
	 
	 //TODO [NEW YML]
	public void NewYml(String name, Player player) {
		
	    
		List<String> ac = config.getStringList("Arenas-Created.List");
	
		 
		 
		if(!ac.contains(name)) {
			this.general = new YamlFile(this,name, new File(this.getDataFolder().getAbsolutePath()+carpeta));
			config.set("Arenas-Created.List",ac);
			ac.add(name);
			this.config.save();
			
			
			   getAllYmls.put(name,this.general);
			   statusArena.put(name,EstadoPartida.ESPERANDO);
			if(player != null) {
				player.sendMessage(nombre+ChatColor.GREEN+" La arena "+name+" a sido creada");
			}
		
			Bukkit.getConsoleSender().sendMessage("Nuevo yml creado: "+name);
		}else {
	    	Bukkit.getConsoleSender().sendMessage("La Arena "+name+" ya existe.");
	    	if(player != null) {
	    	player.sendMessage(nombre+ChatColor.GREEN+" La arena "+name+" ya existe");
	    	}
	       }
		
		
	}

	public void addPlayerArenaList(String arena,String player) {
		
		if(arenajoin.containsKey(arena)) {
			List<String> join1 = arenajoin.get(arena);
			List<String> alive1 = alive.get(arena);
			if(!join1.contains(player)) {
				join1.add(player);
				alive1.add(player);
			}
		}else {
			List<String> join1 = new ArrayList<>();
			List<String> alive1 = new ArrayList<>();
			List<String> death1 = new ArrayList<>();
			List<String> spect = new ArrayList<>();
			List<String> arrive1 = new ArrayList<>();
			
			join1.add(player);
			alive1.add(player);
			arenajoin.put(arena, join1);
			alive.put(arena, alive1);
			deaths.put(arena, death1);
			arrive.put(arena,arrive1);
			spectators.put(arena, spect);
		}
		
		
	//	List<String> arrive1 = new ArrayList<>();
	//		List<String> deads1 = new ArrayList<>();

		//List<String> spectator1 = new ArrayList<>();
	}
	
	public void removePlayerArenaList(String arena ,String player) {
		List<String> join1 = arenajoin.get(arena);
		List<String> alive1 = alive.get(arena);
		List<String> death1 = deaths.get(arena);
		List<String> spect = spectators.get(arena);
		List<String> arrive1 = arrive.get(arena);
		if(join1.remove(player));
		if(alive1.remove(player));
		if(death1.remove(player));
		if(spect.remove(player));
		if(arrive1.remove(player));
		
		
	}
	
	public void deathPlayer(String arena,String player) {
		List<String> alive1 = alive.get(arena);
		List<String> death1 = deaths.get(arena);
		if(!death1.contains(player)) {
			 death1.add(player);
		}
		if(alive1.remove(player));
	   
	}
	
	public void revivePlayer(String arena,String player) {
		List<String> alive1 = alive.get(arena);
		List<String> death1 = deaths.get(arena);
		if(!alive1.contains(player)) {
			alive1.add(player);
		}
		
		if(death1.remove(player));
	}
	
	public void arrivePlayer(String arena,String player) {
		List<String> arrive1 = arrive.get(arena);
		if(!arrive1.contains(player)) {
			arrive1.add(player);
		}
		
	}
	
	public void spectatorPlayer(String arena,String player) {
		List<String> spect = spectators.get(arena);
		if(!spect.contains(player)) {
			spect.add(player);
		}
		
	}
	
	public void OffOrReload(){
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(PlayerisArena(player)) {
				
				List<Map.Entry<Player, String>> list = new ArrayList<>(getArenaPlayerInfo().entrySet());
				for (Map.Entry<Player,String> e : list) {
					
					Player p2 = e.getKey();
					if(p2.getName().equals(player.getName())) {
						String name = e.getValue();
						removePlayerArena(player);
						
						//sino esta permitido entrar con el inventario
						  
						Leave(player);
						
						
						List<String> joinl = arenajoin.get(name);
						List<String> alive1 = alive.get(name);
						List<String> deaths1 = deaths.get(name);
						List<String> arrive1 = arrive.get(name);
						List<String> spectator1 = spectators.get(name);
					
						if(joinl.remove(player.getName()));
						if(alive1.remove(player.getName()));
						if(deaths1.remove(player.getName()));
						if(spectator1.remove(player.getName()));
						if(arrive1.remove(player.getName()));
						 player.setInvulnerable(false);
					
					
						 if(player.isOp()) {
								player.getInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK,10));
								player.sendMessage(ChatColor.GREEN+"TEST:recibiste 10 bloques de diamante :)");
						 }
						
							

						
						if(getEstatusArena().get(name) == EstadoPartida.TERMINANDO) {
							getEstatusArena().replace(name,EstadoPartida.ESPERANDO);
						}
						
						

						
						
						
					}
					
					
				}
			
				
			}
			 
		}
	}
//====================================================================================================================	
	//TODO [ON ENABLE]
	@Override
	public void onEnable() {
		//getServer().getPluginManager().registerEvents(new Codigo(this),this);
		
		
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PHMiniGame(this).register();
		}else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" PlaceholderAPI no encontrado para MG");
		}
		this.messages = new YamlFile(this, "messages");
		this.config = new YamlFile(this, "config");
		this.points = new YamlFile(this, "points");
		this.cooldown = new YamlFile(this, "cooldowns");
		this.levels = new YamlFile(this, "levels");
		this.commands = new YamlFile(this, "commands");
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" ===============================================");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+" Ha sido activado (version:"+ChatColor.DARK_GREEN+version+")");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" Disfruta el Plugin :D");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" ===============================================");
		registrarcomando();
		registerEvents();
	
		
		
		
		
		inventoryCache  = new HashMap<>();
		gmode  = new HashMap<>();
		potion  = new HashMap<>();
		
	
		arena = new HashMap<Player,String>();
		checkpoint = new HashMap<Player,String>();
		getAllYmls = new HashMap<>();
		
		Fly = new HashMap <Player,Boolean>();
		vida = new HashMap <Player,Double>();
		maxvida = new HashMap <Player,Double>();
		comida = new HashMap <Player,Integer>();
		exp = new HashMap <Player,Float>();
		texp = new HashMap <Player,Integer>();
		statusArena = new HashMap <String,EstadoPartida>();
		tp = new HashMap<Player,Player>();
	
		deadmob = new HashMap<Player,String>();
		
		
		location = new HashMap<Player,String>();
		
		
		
		 yml  = new HashMap <String,YamlFile>();
		
		arenajoin = new HashMap <String,List<String>>();
		arrive = new HashMap <String,List<String>>();
		alive = new HashMap <String,List<String>>();
		deaths = new HashMap <String,List<String>>();
		spectators = new HashMap <String,List<String>>();
		
		
		totalp = new HashMap <String,String>();
		pointsgame = new HashMap <Player,Integer>();
		cronomet = new HashMap <String,String>();
		aretime = new HashMap <String,String>();
		stop = new ArrayList<String>();
		air = new ArrayList<String>();
		timeract = new ArrayList<String>();
		spect2 = new ArrayList<String>();
		doors = new ArrayList<Location>();
		
		
		boss = new HashMap <String,BossBar>();
		
		//es test estos dos
		vida1 = new HashMap <Player,Double>();
		maxvida1 = new HashMap <Player,Double>();
		
		cg = new HashMap <String,Boolean>();
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		
	   green = board.getTeam("lifemg");
	   red = board.getTeam("deadmg");
	   white = board.getTeam("spectatormg");
		
		if(green == null) {
			green = board.registerNewTeam("lifemg");
		}
		
		if(red == null) {
			red = board.registerNewTeam("deadmg");
		}
		
		if(white == null) {
			white = board.registerNewTeam("spectatormg");
		}
		
	
		  LoadYml();
		  
	
	}
	
   public void onDisable() {
	   
	    ClassArena c = new ClassArena(this);
	    for(Player players : Bukkit.getOnlinePlayers()) {
	    	    c.ForceLeave(players);
				c.LeaveTeamLife(players);
				c.LeaveTeamDead(players);
				c.LeaveTeamSpectator(players);
	    }
	   
	   
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" ===============================================");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+" Ha sido Desactivado (version:"+ChatColor.DARK_GREEN+version+")");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED+" Disfruta el Plugin :D");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+" ===============================================");
		
	}	
 
   public void registrarcomando() {
		
		getCommand("mg").setExecutor(new Comandos(this));
		this.getCommand("mg").setTabCompleter(new TabComplete1(this));
		//this.getCommand("mg").setTabCompleter(new TabComplete2(this));
		
		
		
	}
   
   public void registerEvents() {
		
	   PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventRandoms(this),this );
		pm.registerEvents(new GameEvent(this),this );
		pm.registerEvents(new MinigameShop1(this),this );
		
	}

   
   
   
   
   //TODO TEAMS
   
   public Team LifePlayers() {
	  return green;
   }
   
   public Team DeadPlayers() {
		  return red;
   }
   
   public Team SpectatorPlayers() {
		  return white;
   }
 
 //TODO  GETTERS
 

   
   public YamlFile getSpecificYamls(String name) {
	   if(getAllYmls.containsKey(name)) {
		   return getAllYmls.get(name);
	   }
	return null;
	 
   }
    
   public Map <String,YamlFile> getTempYml(){
	   return getAllYmls;
   }
   
   public YamlFile getConfig() {
       return config; 
   }
   
   public YamlFile getMessage() {
       return messages; 
   }
   public YamlFile getPoints() {
       return points; 
   }
   public YamlFile getCooldown() {
       return cooldown; 
   }
   //sin uso aparente el lvls
   public YamlFile getLevels() {
       return levels; 
   }
   public YamlFile getCommandsMg() {
       return commands; 
   }
 
   public List<String> getSpectPlayers2(){
	   return spect2;
   }
   
   public List<String> getStopArenas(){
	   return stop;
   }
   
   public List<String> getPlayerGround(){
	   return air;
   }
   
   public List<String> getTimerAction(){
	   return timeract;
   }
   
   public List<Location> getDoorsChest(){
	   return doors;
   }
   
   //obtiene jugador y arena en la que esta
   public HashMap <Player,String> getArenaPlayerInfo(){
	   return arena;
   }
   
   public HashMap <String,String> getPlayersTotalInGame(){
	   return totalp; 
   }
   
   //obtiene el status de la arena
   public HashMap <String,EstadoPartida> getEstatusArena(){
	   return statusArena; 
   }
   
   public HashMap <Player,Player> TPall(){
	   return tp;
   }
   
   public HashMap <Player,String> getDeadmob(){
	   return deadmob;
   }
   public HashMap <Player,String> getCheckPoint(){
	   return checkpoint;
   }
   
   public HashMap <String,String> getPlayerCronomet(){
	   return cronomet;
   }
   
   public HashMap <String,String> getArenaCronometer(){
	   return aretime; 
   }
   
   public HashMap <Player,Integer> getEspecificPlayerPoints(){
	   return pointsgame;
   }

   
   ///TODO LISTAS ARENA TEMP
 
   public HashMap <String,List<String>> getArenaAllPlayers(){
	   return arenajoin;
   }
   
   public HashMap <String,List<String>> getArrive(){
	   return arrive;
   }
   
   public HashMap <String,List<String>> getAlive(){
	   return alive;
   }
   
   public HashMap <String,List<String>> getDeaths(){
	   return deaths;
   }
   
   public HashMap <String,List<String>> getSpectators(){
	   return spectators;
   }

   public HashMap <Player,Double> getVida(){
	   return vida;
   }
   
   public HashMap <Player,Double> getMaxVida(){
	   return maxvida;
   }
   
   public HashMap <Player,Double> getVida1(){
	   return vida1;
   }
   
   public HashMap <Player,Double> getMaxVida1(){
	   return maxvida1;
   }
   
   public HashMap <String,Boolean> getCommandFillArea(){
	   return cg;
   }
   
   public HashMap <String,BossBar> getBossBarTime() {
	   return boss;
   }
   
	
}
