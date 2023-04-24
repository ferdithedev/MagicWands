package me.ferdithedev.magicwands.items;

import me.ferdithedev.overblock.games.ItemSpawner;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import me.ferdithedev.overblock.util.invs.InventoryItemCreator;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Avifors extends OBItem {

    private static final Map<Player, Integer> birdTime = new HashMap<>();

    public Avifors(JavaPlugin plugin) {
        super(plugin, Material.FEATHER, "Avifors", 0, OBItemType.TOOL, OBItemRarity.UNIQUE, "§7Transform yourself into a bird for 3 seconds");
    }

    @Override
    public boolean function(Player player) {
        if(birdTime.containsKey(player)) {
            birdTime.put(player,birdTime.get(player)+3);
        } else {
            birdTime.put(player,3);
        }
        if(!checkFly) checkFly();
        return true;
    }

    @Override
    public void click(PlayerInteractEvent e) {
        function(e.getPlayer());
        e.getPlayer().getInventory().removeItem(getItemStack());
        e.setCancelled(true);
    }

    boolean checkFly = false;
    private void checkFly() {
        checkFly = true;
        for(Player p : birdTime.keySet()) {
            if(birdTime.get(p) <= 0) {
                birdTime.remove(p);
                if(p.getGameMode() != GameMode.CREATIVE) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                }

                p.getInventory().setHelmet(new ItemStack(Material.AIR));
            } else {
                birdTime.put(p,birdTime.get(p)-1);

                p.setAllowFlight(true);
                p.setFlying(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,22,5,false,false,false));
                p.getInventory().setHelmet(new InventoryItemCreator(ItemSpawner.getSkull("https://textures.minecraft.net/texture/ebee642cb4b4fb6671dec9ab9349f0c1ca83bc152738956c1efa2ff2c1030960")).setDisplayName("§dBirdie").get());
            }
        }
        if(!birdTime.isEmpty()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    checkFly();
                }
            }.runTaskLater(getPlugin(),20);
        } else {
            checkFly = false;
        }
    }
}
