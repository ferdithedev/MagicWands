package me.ferdithedev.magicwands.items;

import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public class BrackiumEmendo extends OBItem {


    public BrackiumEmendo(JavaPlugin plugin) {
        super(plugin, Material.GOLDEN_APPLE, "Brackium Emendo", 10, OBItemType.TOOL, OBItemRarity.EPIC, "§7When used correctly it will refill your health","§71/4 chance to fail and damage you 3 hearts");
    }

    @Override
    public boolean function(Player player) {
        Random r = new Random();
        if(r.nextFloat() <= .25) {
            player.damage(6,player);
        } else player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        return true;
    }

    @Override
    public void click(PlayerInteractEvent e) {
        if(function(e.getPlayer())) {
            e.getPlayer().getInventory().removeItem(getItemStack());
        }
        e.setCancelled(true);
    }
}
