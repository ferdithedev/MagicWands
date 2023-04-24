package me.ferdithedev.magicwands.items;

import me.ferdithedev.magicwands.MagicWands;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import me.ferdithedev.overblock.util.Effects;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
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
            Effects.drawParticleLine(player.getEyeLocation(),block.getLocation(),
                    new Effects.ParticleEffectPart(Particle.WAX_OFF,5,0,0,0,null,0),
                    0.2);
            Effects.playSoundDistance(player.getLocation(),5, Sound.ENTITY_ENDER_DRAGON_HURT,100,2);
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
    public boolean click(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);
            return function(e.getPlayer());
        }
        return false;
    }
}
