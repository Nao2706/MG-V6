package me.top.users;


import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nao.main.game.Main;
import me.nao.manager.ClassArena;


public class PHMiniGame extends PlaceholderExpansion{

	
	
	
	 // We get an instance of the plugin later.
    private Main plugin;
 
    public PHMiniGame(Main plugin) {
    	this.plugin = plugin;
    }
 
    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }
    /**
     * Since this expansion requires api access to the plugin "SomePlugin" 
     * we must check if said plugin is on the server or not.
     *
     * @return true or false depending on if the required plugin is installed.
     */
    @Override
    public boolean canRegister(){
        return true;
    }
 
    /**
     * The name of the person who created this expansion should go here.
     * 
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "NAO2706";
    }
 
    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest 
     * method to obtain a value if a placeholder starts with our 
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "mg";
    }
 
    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }
 
    /**
     * This is the method called when a placeholder with our identifier 
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.Player Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
 
    // %mipluginvidas_vidas%
    // %mg_top%
 
    @Override
    public String onPlaceholderRequest(Player player, String identifier){
 
        if(player == null){
            return "";
        }
 
        if(identifier.equals("top")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.TopArmorStand();
        	
        }
        
        if(identifier.equals("top1")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(0);
        	
        }
        if(identifier.equals("top2")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(1);
        	
        }
        if(identifier.equals("top3")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(2);
        	
        }
        if(identifier.equals("top4")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(3);
        	
        }
        if(identifier.equals("top5")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(4);
        	
        }
        if(identifier.equals("top6")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(5);
        	
        }
        if(identifier.equals("top7")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(6);
        	
        }
        if(identifier.equals("top8")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(7);
        	
        }
        if(identifier.equals("top9")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(8);
        	
        }
        if(identifier.equals("top10")){
        	PointsManager p = new PointsManager(plugin);
        	
        	
        	return p.PositionArmorStand(9);
        	
        }
        
        if(identifier.equals("%dific1%")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific1%");
        }
        if(identifier.equals("%dific2%")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific2%");
        }
        if(identifier.equals("%dific3%")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific3%");
        }
        if(identifier.equals("%dific3%")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific3%");
        }
        if(identifier.equals("%dific4%")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific4%");
        }
        if(identifier.equals("%dific5%")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific5%");
        }
        if(identifier.equals("%dific6%")){
        	ClassArena c = new ClassArena(plugin);
        	return c.DifficultyMission("%dific6%");
        }
 
        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%) 
        // was provided
        return null;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
