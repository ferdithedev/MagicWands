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
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Bombarda extends OBItem {
    public Bombarda(JavaPlugin plugin) {
        super(plugin, Material.TNT, "Bombarda", 10, OBItemType.WEAPON, OBItemRarity.UNIQUE, "§7Summons an explosion at","§7the block you're looking at");
    }

    @Override
    public boolean function(Player player) {
        Block block = MagicWands.blockLookingAt(player);
        if(block != null) {
            player.getWorld().createExplosion(block.getX(),block.getY(),block.getZ(),2f,false,false,player);
            Effects.drawParticleLine(player.getEyeLocation(),block.getLocation(),
                    new Effects.ParticleEffectPart(Particle.WAX_OFF,2,0,0,0,null,0),
                    0.2);
            Effects.playSoundDistance(player.getLocation(),5, Sound.ENTITY_ENDER_DRAGON_HURT,100,2);
            for(Entity entity : player.getWorld().getNearbyEntities(block.getLocation(),4,4,4, e->e instanceof Player)) {
                if(entity instanceof Player) {
                    if(entity != player) {
                        ((Player) entity).damage(5f,player);
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
