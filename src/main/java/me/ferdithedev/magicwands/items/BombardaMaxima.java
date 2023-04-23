package me.ferdithedev.magicwands.items;

import me.ferdithedev.magicwands.MagicWands;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BombardaMaxima extends OBItem {

    public BombardaMaxima(JavaPlugin plugin) {
        super(plugin, Material.TNT_MINECART, "Bombarda Maxima", 20, OBItemType.WEAPON, OBItemRarity.EPIC, "§7Creates a huge explosion where you looking at","§7Like Bombarda but more powerful");
    }

    @Override
    public boolean function(Player player) {
        Block block = MagicWands.blockLookingAt(player);
        if(block != null) {
            player.getWorld().createExplosion(block.getX(),block.getY(),block.getZ(),5f,false,false,player);
            for(Entity entity : player.getWorld().getNearbyEntities(block.getLocation(),4,4,4, e->e instanceof Player)) {
                if(entity instanceof Player) {
                    if(entity != player) {
                        ((Player) entity).damage(10f,player);
                    }
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public void click(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(function(e.getPlayer())) {
                e.getPlayer().getInventory().removeItem(getItemStack());
            }
            e.setCancelled(true);
        }
    }
}
