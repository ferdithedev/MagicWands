package me.ferdithedev.magicwands.items;

import me.ferdithedev.magicwands.MagicWands;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import me.ferdithedev.overblock.util.Effects;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Colloshoo extends OBItem {
    public Colloshoo(JavaPlugin plugin) {
        super(plugin, Material.SLIME_BALL, "Colloshoo", 10, OBItemType.WEAPON, OBItemRarity.UNIQUE, "§7Sticks the target to the ground");
    }

    @Override
    public boolean function(Player player) {
        Player target = MagicWands.entityLookingAt(player);
        if(target != null) {
            Effects.playSoundDistance(player.getLocation(),5, Sound.BLOCK_BEACON_ACTIVATE,1,2);
            Effects.drawParticleLine(player.getEyeLocation(),target.getLocation(),
                    new Effects.ParticleEffectPart(Particle.SLIME,5,.1,.1,.1,null,0), 0.2);
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,80,4,false,false,false));
            return true;
        }
        return false;
    }

    @Override
    public boolean click(PlayerInteractEvent e) {
        e.setCancelled(true);
        return function(e.getPlayer());
    }
}
