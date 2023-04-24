package me.ferdithedev.magicwands.items;

import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import me.ferdithedev.overblock.util.Effects;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public class BrackiumEmendo extends OBItem {


    public BrackiumEmendo(JavaPlugin plugin) {
        super(plugin, Material.GOLDEN_APPLE, "Brackium Emendo", 10, OBItemType.TOOL, OBItemRarity.EPIC, "§7When used correctly it will refill your health","§725% chance to fail and damage you 3 hearts");
    }

    @Override
    public boolean function(Player player) {
        Random r = new Random();
        Effects.playSoundDistance(player.getLocation(),5, Sound.BLOCK_BEACON_ACTIVATE,1,2);
        Effects.ParticleEffectPart particle;
        if(r.nextFloat() <= .25) {
            player.damage(6,player);
            particle = new Effects.ParticleEffectPart(Particle.SNEEZE,3,.1,.1,.1,null,0);
        } else {
            player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

            particle = new Effects.ParticleEffectPart(Particle.HEART,3,.1,.1,.1,null,0);
        }
        particle.spawn(player.getEyeLocation());
        return true;
    }

    @Override
    public boolean click(PlayerInteractEvent e) {
        e.setCancelled(true);
        return function(e.getPlayer());
    }
}
