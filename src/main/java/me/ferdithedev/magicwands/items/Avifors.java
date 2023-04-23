package me.ferdithedev.magicwands.items;

import me.ferdithedev.overblock.games.ItemSpawner;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Avifors extends OBItem {

    public Avifors(JavaPlugin plugin) {
        super(plugin, Material.FEATHER, "Avifors", 0, OBItemType.TOOL, OBItemRarity.UNIQUE, "§7Transform yourself into a bird for 3 seconds");
    }

    @Override
    public boolean function(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,60,5,false,false,false));
        player.getInventory().setHelmet(ItemSpawner.getSkull("https://textures.minecraft.net/texture/ebee642cb4b4fb6671dec9ab9349f0c1ca83bc152738956c1efa2ff2c1030960"));

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.getInventory().setHelmet(new ItemStack(Material.AIR));
            }
        }.runTaskLaterAsynchronously(getPlugin(),60);

        return true;
    }

    @Override
    public void click(PlayerInteractEvent e) {
        function(e.getPlayer());
        e.getPlayer().getInventory().removeItem(getItemStack());
        e.setCancelled(true);
    }
}
