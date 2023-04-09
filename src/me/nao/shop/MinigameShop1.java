package me.nao.shop;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Chest;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nao.main.game.Main;
import me.nao.manager.ClassIntoGame;





public class MinigameShop1 implements Listener{
	

	private Main plugin;

	public MinigameShop1(Main plugin) {
		this.plugin = plugin;
	}

	//TODO REVIVE METODO
	@SuppressWarnings("deprecation")
	public void reviveInv(Player player) {
		
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"REVIVIR");
		ItemStack item8 = new ItemStack(Material.BARRIER,1);
		int i = 0;
		if(plugin.PlayerisArena(player)) {
			String arena = plugin.getArenaPlayerInfo().get(player);
			
			
			List<String> deaths1 =  plugin.getDeaths().get(arena);
		//	player.setCustomName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"MUERTO"+ChatColor.DARK_GRAY+ChatColor.BOLD+"] "+ChatColor.WHITE+player.getName());
			 

			
			
				inv.setItem(49, item8);
				if(!deaths1.isEmpty()) {
					for(Player players: Bukkit.getOnlinePlayers()) {
						 if(players.getName().equals(player.getName())) continue;
						 
						 String arena2 = plugin.getArenaPlayerInfo().get(players);
						 if(arena2 != null && arena2.equals(arena)) {
								if(deaths1.contains(players.getName())) {
									ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
									SkullMeta meta = (SkullMeta) head.getItemMeta();
									meta.setOwner(players.getName());
									List<String> lore2 = new ArrayList<>();
								    lore2.add(ChatColor.GREEN+"Necesitas 35 diamantes para revivir a este jugador");
									meta.setLore(lore2);
									head.setItemMeta(meta);
									
									inv.setItem(i,head);
									i++;
									if(i == 49)continue;
									if(i == 54) {
										break;
									}
								}
								
					     	 }	
							
						}
					
					player.openInventory(inv);
				}else {
					player.sendMessage(ChatColor.RED+"No hay ningun jugador que revivir.");
				}
			
			
			
			
				
				
		}
		
		
	}
	
	
	public void StoreChest(Player player,ItemStack item) {
		
		
		
			if(item.isSimilar(Items.ESPADAENCAN1.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 30)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.ESPADAENCAN1P.getValue());
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Diamantes.");
				}
	
			}	
			if(item.isSimilar(new ItemStack(Material.DIAMOND))) {
					if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 5)) {
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
						player.getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
						player.getInventory().removeItem(new ItemStack(Material.DIAMOND,5));
					}else {
						player.sendMessage(ChatColor.RED+"Necesitas 5 Diamantes.");
					}
			}
			if(item.isSimilar(Items.STOREXPRESS.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 192)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.STOREXPRESSP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,192));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 192 de Lingotes de Hierro (3 Stacks de hierro)");
				}
			}
				
			if(item.isSimilar(Items.REFUERZOS.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 30)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.REFUERZOSP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 de Lingotes de Netherite ");
				}
			}
			if(item.isSimilar(Items.REFUERZOS2.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 64)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.REFUERZOS2P.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 de Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.JEDI.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.JEDIP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 de Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.PALO.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.PALOP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Hierro");
				}
			}
			if(item.isSimilar(new ItemStack(Material.GOLDEN_SWORD))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 7)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(new ItemStack(Material.GOLDEN_SWORD,1));
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,7));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 7 Lingotes de Hierro");
				}
			}
			
			if(item.isSimilar(new ItemStack(Material.WOODEN_SWORD))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)) {
					player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,1));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 1 Diamantes");
				}
			}
			if(item.isSimilar(new ItemStack(Material.STONE_SWORD))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 3)) {
					player.getInventory().addItem(new ItemStack(Material.STONE_SWORD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,3));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 3 Diamantes");
				}
			}
			if(item.isSimilar(new ItemStack(Material.NETHERITE_SWORD))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 12)) {
					player.getInventory().addItem(new ItemStack(Material.NETHERITE_SWORD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,12));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 12 Diamantes");
				}
			}
		
			if(item.isSimilar(Items.CHECKPOINT.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 30)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.CHECKPOINTP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.KATANA.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 62)) {
					player.getInventory().addItem(Items.KATANAP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,62));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 62 Lingotes de Neherite");
				}
			}
			
			if(item.isSimilar(new ItemStack(Material.TRIDENT))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD), 5)) {
					player.getInventory().addItem(new ItemStack(Material.TRIDENT,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.EMERALD,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 5 Esmeraldas");
				}
			}
			if(item.isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD), 30)) {
					player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.EMERALD,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Esmeraldas");
				}
			}
			if(item.isSimilar(new ItemStack(Material.SPECTRAL_ARROW))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 5)) {
					player.getInventory().addItem(new ItemStack(Material.SPECTRAL_ARROW,10));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 5 Lingotes de Hierro");
				}
			}
			if(item.isSimilar(new ItemStack(Material.SHIELD))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 20)) {
					player.getInventory().addItem(new ItemStack(Material.SHIELD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,20));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 20 Lingotes de Hierro");
				}
			}
			if(item.isSimilar(Items.ARCOENCAN1.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 30)) {
					player.getInventory().addItem(Items.ARCOENCAN1P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.DEADBOW.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.getInventory().addItem(Items.DEADBOWP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.ARCO.getValue())) {
					if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 20)) {
						player.getInventory().addItem(new ItemStack(Material.BOW,1));
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
						player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,20));
					}else {
						player.sendMessage(ChatColor.RED+"Necesitas 20 Lingotes de Hierro");
					}
				}
		
			if(item.isSimilar(Items.MEDICO.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 30)) {
					player.getInventory().addItem(Items.MEDICOP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.BALLESTA1.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.getInventory().addItem(Items.BALLESTA1P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Netherite");
				}
			}
			
			
			if(item.isSimilar(Items.BALLESTA2.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 64)) {
					player.getInventory().addItem(Items.BALLESTA2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 Lingotes de Netherite");
				}
			}
			
			
			if(item.isSimilar(Items.BALLESTA.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 12)) {
					player.getInventory().addItem(new ItemStack(Material.CROSSBOW,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT,12));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 12 Lingotes de Oro");
				}
			}
			if(item.isSimilar(new ItemStack(Material.ARROW))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 5)) {
					player.getInventory().addItem(new ItemStack(Material.ARROW,3));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 5 Lingotes de oro");
				}
			}
			
			if(item.isSimilar(new ItemStack(Material.APPLE))) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT),1)) {
					player.getInventory().addItem(new ItemStack(Material.APPLE,5));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,1));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 1 Lingotes de Hierro");
				}
			}
			
			if(item.isSimilar(Posion.HEALTH.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),3)) {
					player.getInventory().addItem(Posion.HEALTHP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 3 Diamantes");
				}
			}
		
			if(item.isSimilar(Posion.SPEED.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),20)) {
					player.getInventory().addItem(Posion.SPEEDP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,20));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 20 Diamantes");
				}
			}
			if(item.isSimilar(Posion.REGENER.getValue())) {
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),2)) {
					player.getInventory().addItem(Posion.REGENERP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,2));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 2 Diamantes");
				}
			}
			
			if(item.isSimilar(Items.ARMAS.getValue())) {
				createInvArmas(player);
			}
			
			if(item.isSimilar(Items.ARMAS2.getValue())) {
				createInvArmas2(player);
			}
			
			if(item.isSimilar(Items.DEFENSA.getValue())) {
				createInvDefensa(player);
			}
			
			if(item.isSimilar(Items.COMIDA.getValue())) {
				createInvComida(player);
			}
			
			if(item.isSimilar(Items.ESPECIALES.getValue())) {
				createInvEspeciales(player);
			}
			
			if(item.isSimilar(Items.VOLVER.getValue())) {
				createInv(player);
			}
			
			if(item.isSimilar(Items.CERRAR.getValue())) {
				player.closeInventory();
			}
			
	}
	
	public void CreateInvChest(Player player,Chest chest) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		chest.setCustomName(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"TIENDA TEST");
		Inventory inv = chest.getInventory();
		
		
		//ItemStack item9 = new ItemStack(Material.LIME_STAINED_GLASS,1);
	
		inv.setItem(0, Items.ESPADAMADERA.getValue());
		inv.setItem(1,  Items.ESPADAPIEDRA.getValue());
		inv.setItem(2,  Items.ESPADAHIERRO.getValue());
		inv.setItem(3,  Items.ESPADAORO.getValue());
		inv.setItem(4,  Items.ESPADADIAMANTE.getValue());
		inv.setItem(7, Items.ESPADANETHERITA.getValue());
		inv.setItem(8, Items.ARCO.getValue());
		inv.setItem(9, Items.BALLESTA.getValue());
		inv.setItem(10, Items.FLECHA.getValue());
		
	}
	
	public void createInv(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"TIENDA");
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);l.add(1);l.add(2);l.add(3);l.add(4);l.add(5);l.add(6);l.add(7);l.add(8);l.add(9);l.add(17);l.add(18);l.add(26);
		l.add(27);l.add(35);l.add(36);l.add(44);l.add(45);l.add(46);l.add(47);l.add(48);l.add(49);l.add(50);l.add(51);l.add(52);l.add(53);
		
		for(int i = 0 ; i < 54; i++) {
			if(l.contains(i)) {
				inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
			}
		}
		
		inv.setItem(11, Items.ARMAS.getValue());
		inv.setItem(13, Items.ARMAS2.getValue());
		inv.setItem(15, Items.DEFENSA.getValue());
		inv.setItem(21, Items.COMIDA.getValue());
		inv.setItem(23, Items.ESPECIALES.getValue());
		inv.setItem(40, Items.CERRAR.getValue());

		// slot 49 es el centro verticalmente 
		inv.setItem(49, Items.CERRAR.getValue());
		//tope es 53 no hay 54
		//inv.setItem(50, item9);
	
		
		player.openInventory(inv);
	}
	
	
	public void createInvArmas(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"ESPADAS");
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);l.add(1);l.add(2);l.add(3);l.add(4);l.add(5);l.add(6);l.add(7);l.add(8);l.add(9);l.add(17);l.add(18);l.add(26);
		l.add(27);l.add(35);l.add(36);l.add(44);l.add(45);l.add(46);l.add(47);l.add(48);l.add(49);l.add(50);l.add(51);l.add(52);l.add(53);
		
		for(int i = 0 ; i < 54; i++) {
			if(l.contains(i)) {
				inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
			}
		}
		
		inv.setItem(10, Items.ESPADAMADERA.getValue());
		inv.setItem(11,  Items.ESPADAPIEDRA.getValue());
		inv.setItem(12,  Items.ESPADAHIERRO.getValue());
		inv.setItem(13,  Items.ESPADAORO.getValue());
		inv.setItem(14,  Items.ESPADADIAMANTE.getValue());
		inv.setItem(15, Items.ESPADANETHERITA.getValue());
		inv.setItem(16, Items.ESPADAENCAN1.getValue());
		inv.setItem(19, Items.KATANA.getValue());
		inv.setItem(20, Items.TRIDENTE.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		player.openInventory(inv);
		
	}

	public void createInvArmas2(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"ARCOS Y BALLESTAS");
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);l.add(1);l.add(2);l.add(3);l.add(4);l.add(5);l.add(6);l.add(7);l.add(8);l.add(9);l.add(17);l.add(18);l.add(26);
		l.add(27);l.add(35);l.add(36);l.add(44);l.add(45);l.add(46);l.add(47);l.add(48);l.add(49);l.add(50);l.add(51);l.add(52);l.add(53);
		
		for(int i = 0 ; i < 54; i++) {
			if(l.contains(i)) {
				inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
			}
		}
		
		
		inv.setItem(10, Items.ARCO.getValue());
		inv.setItem(11, Items.BALLESTA.getValue());
		inv.setItem(12, Items.ARCOENCAN1.getValue());
		inv.setItem(13, Items.BALLESTA1.getValue());
		inv.setItem(14, Items.BALLESTA2.getValue());
		inv.setItem(15, Items.FLECHA.getValue());
		inv.setItem(16, Items.FLECHAESPECTRAL.getValue());
		inv.setItem(19, Items.DEADBOW.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		player.openInventory(inv);
		
	}
	
	public void createInvDefensa(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"DEFENSA");
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);l.add(1);l.add(2);l.add(3);l.add(4);l.add(5);l.add(6);l.add(7);l.add(8);l.add(9);l.add(17);l.add(18);l.add(26);
		l.add(27);l.add(35);l.add(36);l.add(44);l.add(45);l.add(46);l.add(47);l.add(48);l.add(49);l.add(50);l.add(51);l.add(52);l.add(53);
		
		for(int i = 0 ; i < 54; i++) {
			if(l.contains(i)) {
				inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
			}
		}
		
		inv.setItem(10, Items.ESCUDO.getValue());
		inv.setItem(11, Items.TOTEM.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		
		player.openInventory(inv);
	}
	
	public void createInvComida(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"COMIDA Y POSIONES");
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);l.add(1);l.add(2);l.add(3);l.add(4);l.add(5);l.add(6);l.add(7);l.add(8);l.add(9);l.add(17);l.add(18);l.add(26);
		l.add(27);l.add(35);l.add(36);l.add(44);l.add(45);l.add(46);l.add(47);l.add(48);l.add(49);l.add(50);l.add(51);l.add(52);l.add(53);
		
		for(int i = 0 ; i < 54; i++) {
			if(l.contains(i)) {
				inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
			}
		}
		
		inv.setItem(10, Items.MANZANAORO.getValue());
		inv.setItem(11, Items.MANZANA.getValue());
		inv.setItem(12, Posion.REGENER.getValue());
		inv.setItem(13, Posion.HEALTH.getValue());
		inv.setItem(14, Posion.SPEED.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		
		player.openInventory(inv);
		
	}
	
	public void createInvEspeciales(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"ESPECIALES");
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);l.add(1);l.add(2);l.add(3);l.add(4);l.add(5);l.add(6);l.add(7);l.add(8);l.add(9);l.add(17);l.add(18);l.add(26);
		l.add(27);l.add(35);l.add(36);l.add(44);l.add(45);l.add(46);l.add(47);l.add(48);l.add(49);l.add(50);l.add(51);l.add(52);l.add(53);
		
		for(int i = 0 ; i < 54; i++) {
			if(l.contains(i)) {
				inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
			}
		}
		
		inv.setItem(10, Items.PALO.getValue());
		
		inv.setItem(11, Items.REFUERZOS.getValue());
		inv.setItem(12, Items.REFUERZOS2.getValue());
		inv.setItem(13, Items.STOREXPRESS.getValue());
		inv.setItem(14, Items.JEDI.getValue());
		//inv.setItem(30, levitar);
		inv.setItem(15, Items.MEDICO.getValue());
		inv.setItem(16, Items.CHECKPOINT.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		
		player.openInventory(inv);
	}
	
	public void createInv2(Player player) {
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"2");
		player.openInventory(inv);
		ItemStack item8 = new ItemStack(Material.RED_STAINED_GLASS,1);
		ItemStack item1 = new ItemStack(Material.WOODEN_SWORD,1);
		
		
		ItemMeta meta3 = item8.getItemMeta();
		meta3.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"PAGINA ANTERIOR"+ChatColor.GOLD+ChatColor.BOLD+"]");
		item8.setItemMeta(meta3);
		
		ItemMeta meta4 = item1.getItemMeta();
		meta4.setDisplayName(""+ChatColor.GOLD+ChatColor.BOLD+"["+ChatColor.RED+ChatColor.BOLD+"CLICKEAME"+ChatColor.GOLD+ChatColor.BOLD+"]");
		item1.setItemMeta(meta4);
		
		inv.setItem(48, item8);
		inv.setItem(0, item1);
		player.openInventory(inv);
	}
	
	
	
	@EventHandler
	public void clickEvent(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		
		//String tittle = ChatColor.translateAlternateColorCodes("&", usa el fileconfiguartion);
		// String tittlec = ChatColor.stripColor(titulo);
		 
		if(plugin.PlayerisArena(player)) {
			List<String> l = new ArrayList<String>();
			l.add("TIENDA"); l.add("ESPADAS");l.add("ARCOS Y BALLESTAS");l.add("DEFENSA");l.add("COMIDA Y POSIONES");l.add("ESPECIALES");
			
			String namet = ChatColor.stripColor(e.getView().getTitle());
			if(l.contains(namet)) {
				if(e.getCurrentItem() != null) {
					
					 InventoryAction action = e.getAction();
					
					  switch(action) {
				      case MOVE_TO_OTHER_INVENTORY:
				          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
				            e.setCancelled(true);
				            return;
				        case HOTBAR_MOVE_AND_READD:
				          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
				            e.setCancelled(true);
				            return;
				        case HOTBAR_SWAP:
				          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
				            e.setCancelled(true);
				            return;
				            
				        case SWAP_WITH_CURSOR:
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
				        case COLLECT_TO_CURSOR:
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
				        default:
				            break;
					  }
					
					
					  
				   if(player.getOpenInventory().getBottomInventory().getType() == InventoryType.PLAYER && e.getClickedInventory().getType() == InventoryType.CHEST) {
					
					
					
						StoreChest(player, e.getCurrentItem());
						
					}
				 
				   
				   
				   
				}
			

			return;
			
		}
		
			if(e.getView().getTitle().contains(ChatColor.stripColor("2"))) {
			
					if(e.getCurrentItem() != null) {
						if(player.getOpenInventory().getBottomInventory().getType() == InventoryType.PLAYER && e.getClickedInventory().getType() == InventoryType.CHEST) {
						switch(e.getCurrentItem().getType())  {
						case WOODEN_SWORD:
							
							e.setCancelled(true);
							
							 Random r = new Random();
							 int n = r.nextInt(5);
							
							if(!player.getInventory().containsAtLeast(new ItemStack(Material.WOODEN_SWORD), 1)) {
								player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD,1));
								player.getInventory().addItem(new ItemStack(Material.DIAMOND,20));
								player.getInventory().addItem(new ItemStack(Material.EMERALD,25));
								player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT,40));
								player.getInventory().addItem(new ItemStack(Material.IRON_INGOT,50));
								player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING,1));
								player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
								
								if(n == 1) {
									LivingEntity entidad = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
									Zombie zombi = (Zombie) entidad;
									zombi.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
									zombi.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(50);
									zombi.setCustomName("Su ambicion fue su perdicion");
									
									PotionEffect rapido = new PotionEffect(PotionEffectType.SPEED,/*duration*/ 99999,/*amplifier:*/4, true ,true,true );
									PotionEffect salto= new PotionEffect(PotionEffectType.JUMP,/*duration*/ 99999,/*amplifier:*/5, true ,true,true );

									
								    zombi.addPotionEffect(rapido);
									zombi.addPotionEffect(salto);
								}
								
								if(n == 4) {
									LivingEntity entidad = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
									Creeper c = (Creeper) entidad;
									c.setExplosionRadius(5);
									c.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(150);
									c.setCustomName(ChatColor.RED+"Por afrentoso");
									
									
									
								}
								
								
							}else {
								player.sendMessage(ChatColor.RED+"Ya tienes una espada");
							}
							
						break;
						case RED_STAINED_GLASS:
							e.setCancelled(true);
							player.closeInventory();
							createInv(player);
						break;
						
						default:
							player.closeInventory();
						break;
						
						}
					}
				}
			}
		
		//TODO REVIVE
		if(e.getView().getTitle().contains(ChatColor.stripColor("REVIVIR"))) {
			
				if(e.getCurrentItem() != null) {
					if(player.getOpenInventory().getBottomInventory().getType() == InventoryType.PLAYER && e.getClickedInventory().getType() == InventoryType.CHEST) {
						
						InventoryAction action = e.getAction();
						  
						
						  switch(action) {
					      case MOVE_TO_OTHER_INVENTORY:
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
					        case HOTBAR_MOVE_AND_READD:
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
					        case HOTBAR_SWAP:
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
					            
					        case SWAP_WITH_CURSOR:
						          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
						            e.setCancelled(true);
						            return;
					        case COLLECT_TO_CURSOR:
						          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
						            e.setCancelled(true);
						            return;
					        default:
					            break;
						  }
						
					switch(e.getCurrentItem().getType())  {
					
					
					case PLAYER_HEAD:
						SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
						@SuppressWarnings("deprecation") String name = meta.getOwner();
						
					ClassIntoGame c = new ClassIntoGame(plugin);
					c.PlayerRevive(player, name);
						
								
								
						
					break;
					// esta opcion esta mostrando la posibilidad de usar un mismo material multiples veces pero con un lore distinto
					case BARRIER:
					
						player.closeInventory();
						
					
						
						break;
					default:
						player.closeInventory();
					break;
					
					}
				}
	          } 
			}
		}
		

		
		
	}
	
	
	
	
	
	
}
