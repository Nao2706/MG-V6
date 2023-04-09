package me.nao.command.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import me.nao.main.game.Main;



public class TabComplete2 implements TabCompleter{
	

	private Main plugin;
	
	public TabComplete2(Main plugin) {
		this.plugin = plugin;
	}

	List<String> arguments = new ArrayList<String>();
	
	public List <String> onTabComplete(CommandSender sender, Command cmd,String label,String[]args){
		FileConfiguration config = plugin.getConfig();
		List<String> arguments2 = config.getStringList("Arenas-Created.List");
		if(arguments.isEmpty()) {
			if(!arguments2.isEmpty()) {
				for(int i = 0; i < arguments2.size();i++) {
					arguments.add(arguments2.get(i));
				}
			}
			
		
			
			
		}
		List<String> result = new ArrayList<String>();
		if(args.length == 2) {
			for(String a : arguments ) {
				//report NAO
				//inicio de autocompletado args[0,1,2]
				if(a.toLowerCase().startsWith(args[1].toLowerCase())) 
					result.add(a);
					
				
				
			}
			return result;
		}
		return null;
	}	
	
	
	
	

}
