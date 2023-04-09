package me.nao.shop;
 
import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
 
 
        public enum Items{
        	
            ESPADAMADERA(""+ChatColor.GOLD+ChatColor.BOLD+"ESPADA DE MADERA", Material.WOODEN_SWORD,null,1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 1 Diamante"),
            ESPADAPIEDRA(""+ChatColor.GOLD+ChatColor.BOLD+"ESPADA DE PIEDRA", Material.STONE_SWORD,null, 1,ChatColor.GREEN+"Precio:"+ChatColor.RED+" 3 Diamante"),
            ESPADAHIERRO(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE HIERRO", Material.IRON_SWORD,null, 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 5 Diamante"),
            ESPADAORO(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE ORO", Material.GOLDEN_SWORD,null,1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 7 Diamante"),
            ESPADADIAMANTE(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE DIAMANTE", Material.DIAMOND_SWORD,null,1,ChatColor.GREEN+"Precio:"+ChatColor.RED+" 9 Diamante"),
            ESPADANETHERITA(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE NETHERITA", Material.NETHERITE_SWORD,null, 1,ChatColor.GREEN+"Precio:"+ChatColor.RED+" 12 Diamante"),
            
            FLECHA(""+ChatColor.GREEN+ChatColor.BOLD+"FLECHA", Material.ARROW,null, 1,ChatColor.GREEN+"Precio: "+ChatColor.RED+"3 de Oro"),
            FLECHAESPECTRAL(""+ChatColor.GREEN+ChatColor.BOLD+"FLECHA ESPECTRAL", Material.SPECTRAL_ARROW,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"5 de Oro",ChatColor.YELLOW+ "Marca a tus enemigos para que tus aliados lo vean"),
            ARCO(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO", Material.BOW,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"20 de Hierro"),
            BALLESTA(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA", Material.CROSSBOW,null, 1,ChatColor.GREEN+"Precio: "+ChatColor.RED+"12 de Oro",ChatColor.YELLOW+ "Tiene mas alcance que un arco"),
            TRIDENTE(""+ChatColor.GREEN+ChatColor.BOLD+"TRIDENTE", Material.TRIDENT,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"5 Esmeraldas",ChatColor.YELLOW+ "Tiene un poco mas de da�o y puedes lanzarlo en el agua"),
            ESCUDO(""+ChatColor.GREEN+ChatColor.BOLD+"ESCUDO", Material.SHIELD,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"20 de Hierro",ChatColor.YELLOW+ "Te servira para cubrirte"),
            TOTEM(""+ChatColor.GREEN+ChatColor.BOLD+"TOTEM DE INMORTALIDAD", Material.TOTEM_OF_UNDYING, null,1, ChatColor.GREEN+"Precio:  "+ChatColor.RED+"30 de Esmeralda",ChatColor.YELLOW+ "Te dara otra oportunidad (pero no si te caes del mapa)"),
            MANZANAORO(""+ChatColor.GREEN+ChatColor.BOLD+"MANZANA DE ORO", Material.GOLDEN_APPLE,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"10 Esmeraldas",ChatColor.YELLOW+ "Te curara al comerla"),
            MANZANA(""+ChatColor.GREEN+ChatColor.BOLD+"MANZANA", Material.APPLE,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"1 Lingote de Hierro",ChatColor.YELLOW+ "Evitara que mueras de Hambre"),
            
            
            CERRAR(""+ChatColor.RED+ChatColor.BOLD+"CERRAR", Material.BARRIER,null, 1, ChatColor.GREEN+"Salir de la Tienda"),
            VOLVER(""+ChatColor.WHITE+ChatColor.BOLD+"VOLVER", Material.LIME_STAINED_GLASS,null, 1, ChatColor.GREEN+"Regresa al Menu Principal de la Tienda."),
            
            ARMAS(""+ChatColor.GOLD+ChatColor.BOLD+"ESPADAS", Material.IRON_SWORD,null, 1, ChatColor.GREEN+"Compra una para Defenderte."),
            ARMAS2(""+ChatColor.GOLD+ChatColor.BOLD+"ARCOS", Material.BOW,null, 1, ChatColor.GREEN+"Arcos y Ballestas a la Venta."),
            DEFENSA(""+ChatColor.GOLD+ChatColor.BOLD+"DEFENSA", Material.SHIELD,null, 1, ChatColor.GREEN+"Items que podrian salvarte la vida si los usas bien."),
            COMIDA(""+ChatColor.GOLD+ChatColor.BOLD+"COMIDA Y POSIONES", Material.GOLDEN_APPLE,null, 1, ChatColor.GREEN+"Compra comida o Posiones para regenera la vida."),
            ESPECIALES(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"ESPECIALES", Material.LIME_STAINED_GLASS,null, 1, ChatColor.GREEN+"Items con acciones Especiales."),


            
            MEDICO(""+ChatColor.RED+ChatColor.BOLD+"MEDICO", Material.EMERALD,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "Llama a un Aldeano medico"),
            MEDICOP(""+ChatColor.RED+ChatColor.BOLD+"MEDICO", Material.EMERALD,null, 1),
            
            REFUERZOS(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS", Material.NETHER_STAR,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "Llama a un Iron Golem"),
            REFUERZOSP(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS", Material.NETHER_STAR,null, 1),
            
            REFUERZOS2(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS 2", Material.END_CRYSTAL,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"64 Netherite",ChatColor.YELLOW+ "Llama a una escuadra de Golems de hierro"),
            REFUERZOS2P(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS 2", Material.END_CRYSTAL,null, 1),
            
            STOREXPRESS(""+ChatColor.BLUE+ChatColor.BOLD+"STORE EXPRESS", Material.ENCHANTED_BOOK,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"3 Stacks Hierro",ChatColor.YELLOW+ "Compra desde donde sea que estes."),
            STOREXPRESSP(""+ChatColor.BLUE+ChatColor.BOLD+"STORE EXPRESS", Material.ENCHANTED_BOOK,null, 1,ChatColor.YELLOW+ "Compra desde donde sea que estes."),
            
            CHECKPOINT(""+ChatColor.GREEN+ChatColor.BOLD+"CHECKPOINT", Material.BEACON,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "En caso de estar cerca de morir ",ChatColor.YELLOW+ "Tepeara al sitio donde pusiste el marcador"),
            CHECKPOINTP(""+ChatColor.GREEN+ChatColor.BOLD+"CHECKPOINT", Material.BEACON,null, 1,ChatColor.YELLOW+ "En caso de estar cerca de morir ",ChatColor.YELLOW+ "Tepeara al sitio donde pusiste el marcador"),
            
            BALLESTA1(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA", Material.CROSSBOW,"quick_charge,5/piercing,1", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            BALLESTA1P(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA", Material.CROSSBOW,"quick_charge,5/piercing,1", 1),
            
            
            BALLESTA2(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA 2", Material.CROSSBOW,"quick_charge,5/multishot,1", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"64 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            BALLESTA2P(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA 2", Material.CROSSBOW,"quick_charge,5/multishot,1", 1),
            
            
            ARCOENCAN1(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO ENCANTADO", Material.BOW,"arrow_fire,3/arrow_knockback,5", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            ARCOENCAN1P(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO ENCANTADO", Material.BOW,"arrow_fire,3/arrow_knockback,5", 1),
            
            //
            DEADBOW(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO DE MUERTE", Material.BOW,"arrow_damage,10/arrow_fire,3/arrow_knockback,4", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            DEADBOWP(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO DE MUERTE", Material.BOW,"arrow_damage,10/arrow_fire,3/arrow_knockback,4", 1),
            
            
            JEDI(""+ChatColor.GREEN+ChatColor.BOLD+"STAR WARS", Material.END_CRYSTAL,"fire_aspect,3/knockback,4", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Empuja a tus enemigos cuando te rodeen"),
            JEDIP(""+ChatColor.GREEN+ChatColor.BOLD+"STAR WARS", Material.END_CRYSTAL,"fire_aspect,3/knockback,4", 1),
            
            //
            KATANA(""+ChatColor.RED+ChatColor.BOLD+"KATANA", Material.NETHERITE_SWORD,"damage_all,10/knockback,2", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"62 Netherite",ChatColor.YELLOW+ "Esta encantada"),
            KATANAP(""+ChatColor.RED+ChatColor.BOLD+"KATANA", Material.NETHERITE_SWORD,"damage_all,10/knockback,2", 1),
            
            PALO(""+ChatColor.RED+ChatColor.BOLD+"KICK ALL", Material.STICK,"fire_aspect,3/knockback,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "No dejes que se te acerquen"),
            PALOP(""+ChatColor.RED+ChatColor.BOLD+"KICK ALL", Material.STICK,"fire_aspect,3/knockback,10", 1),
            
            ESPADAENCAN1(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA ENCANTADA", Material.IRON_SWORD,"fire_aspect,3/knockback,4", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Diamantes",ChatColor.YELLOW+ "Esta encantada"),
        	ESPADAENCAN1P(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA ENCANTADA", Material.IRON_SWORD,"fire_aspect,3/knockback,4", 1);
        	
        	
        	
        	
            
            public ItemStack item;
         
            @SuppressWarnings("deprecation")
			private Items(String nombre, Material material,String encant , int amount, String ...lore){
                ItemStack item = new ItemStack(material,amount);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(nombre);
                List<String> lore2 = new ArrayList<>();
                
              // luck,1;fire,5;
                
              
          
               
              
             // Material.END_CRYSTAL;
                
                if(lore != null) {
                	 for(String linea : lore){
                         lore2.add(linea);
                     }
                     //lore.add(""+ChatColor.GOLD+ChatColor.BOLD+"Valor:"+ChatColor.GREEN+ChatColor.BOLD+"+ 3");|
                     meta.setLore(lore2);
                }
               
                if(encant != null) {
                	 String[] seccion = encant.split("/");
                  	for(int i =0;i<seccion.length;i++) {
                    		String seccion2 = seccion[i];
                    		
                    		String[] f = seccion2.split(",");
                    		String encan = f[0];
         				int encanlvl = Integer.valueOf(f[1]);
                    	
         				meta.addEnchant(Enchantment.getByName(encan.toUpperCase()), encanlvl, true);
                    	}
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