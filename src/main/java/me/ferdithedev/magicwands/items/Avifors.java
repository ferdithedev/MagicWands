package me.ferdithedev.magicwands.items;

import me.ferdithedev.overblock.games.ItemSpawner;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import me.ferdithedev.overblock.util.Effects;
import me.ferdithedev.overblock.util.invs.InventoryItemCreator;
import org.bukkit.*;
import org.bukkit.Color;
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
            Effects.ParticleEffectPart particle = new Effects.ParticleEffectPart(Particle.REDSTONE, 20, .1,.75,.1,new Particle.DustOptions(Color.WHITE,3),0.1);
            particle.spawn(player.getLocation().add(0,1,0));
            Effects.playSoundDistance(player.getLocation(),5, Sound.ENTITY_ENDER_DRAGON_FLAP,1,2);
        }
        if(!checkFly) checkFly();
        return true;
    }

    @Override
    public boolean click(PlayerInteractEvent e) {
        e.setCancelled(true);
        return function(e.getPlayer());
    }

    boolean checkFly = false;
    private void checkFly() {
        checkFly = true;
        for(Player p : birdTime.keySet()) {
            if(birdTime.get(p) <= 0) {
                birdTime.remove(p);
                if(p.getGameMode() != GameMode.CREATIVE) {
                    p.setAllowFlight(false);
                }

                p.getInventory().setHelmet(new ItemStack(Material.AIR));
            } else {
                birdTime.put(p,birdTime.get(p)-1);

                p.setAllowFlight(true);
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
