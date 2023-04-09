package me.nao.shop;
 
import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
 
 
        public enum Posion{
            HEALTH(""+ChatColor.RED+ChatColor.BOLD+"INSTA HEALTH", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.HEAL,5 * 20,2, true ,true,true ), 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 3 Diamantes",ChatColor.GREEN+"Te curara instantaneamente al usarlo."),
            HEALTHP(""+ChatColor.RED+ChatColor.BOLD+"INSTA HEALTH", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.HEAL,5 * 20,2, true ,true,true ), 1,ChatColor.GREEN+"Te curara al usarlo."),
            
            SPEED(""+ChatColor.AQUA+ChatColor.BOLD+"SPEED", Material.SPLASH_POTION,Color.AQUA,new PotionEffect(PotionEffectType.SPEED,20 * 20,2, true ,true,true ), 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 20 Diamantes",ChatColor.GREEN+"Te dara velocidad Temporal."),
            SPEEDP(""+ChatColor.AQUA+ChatColor.BOLD+"SPEED", Material.SPLASH_POTION,Color.AQUA,new PotionEffect(PotionEffectType.SPEED,20 * 20,2, true ,true,true ), 1,ChatColor.GREEN+"Te dara velocidad Temporal."),

            
            REGENER(""+ChatColor.RED+ChatColor.BOLD+"REGENERACION", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.REGENERATION,5 * 20,2, true ,true,true ),1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 2 Diamantes",ChatColor.GREEN+ "Te Regenerara la vida al usarlo."),
            REGENERP(""+ChatColor.RED+ChatColor.BOLD+"REGENERACION", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.REGENERATION,5 * 20,2, true ,true,true ),1,ChatColor.GREEN+ "Te Regenerara la vida al usarlo.");
            
            public ItemStack item;
         
        
			private Posion(String nombre, Material material,Color encant,PotionEffect efc ,int amount, String ...lore){
                ItemStack item = new ItemStack(material,amount);
                PotionMeta meta = (PotionMeta) item.getItemMeta();
                meta.setDisplayName(nombre);
                List<String> lore2 = new ArrayList<>();
                
              // luck,1;fire,5;
                
              
          
               if(efc != null) {
            	   meta.addCustomEffect(efc, true);
               }
              
             // Material.END_CRYSTAL;
                
                if(lore != null) {
                	 for(String linea : lore){
                         lore2.add(linea);
                     }
                     //lore.add(""+ChatColor.GOLD+ChatColor.BOLD+"Valor:"+ChatColor.GREEN+ChatColor.BOLD+"+ 3");|
                     meta.setLore(lore2);
                }
               
                if(encant != null) {
                
                	meta.setColor(encant);
                }
               
          // meta.addEnchant(Enchantment.DAMAGE_ALL,1, true);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                item.setItemMeta(meta);
                this.item = item;
            }
         
            public ItemStack getValue(){
                return this.item;
            }
        }