package me.ferdithedev.magicwands.items;

import me.ferdithedev.magicwands.MagicWands;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import me.ferdithedev.overblock.util.Effects;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CruciatusCurse extends OBItem {

    static Effects.ParticleEffect particleEffect;
    static {
        List<Effects.ParticleEffectPart> parts = new ArrayList<>();
        parts.add(new Effects.ParticleEffectPart(Particle.LAVA, 5, 0.1, 0.1, 0.1, null,0));
        parts.add(new Effects.ParticleEffectPart(Particle.DUST_COLOR_TRANSITION, 5, 0.1, 0.1, 0.1, new Particle.DustTransition(Color.fromRGB(255,50,20), Color.fromRGB(255, 255, 255), 1f),0));
        parts.add(new Effects.ParticleEffectPart(Particle.WAX_OFF, 3, 0, 0, 0, null, 0));
        particleEffect = new Effects.ParticleEffect(parts);
    }

    public CruciatusCurse(JavaPlugin plugin) {
        super(plugin, Material.STICK, "Cruciatus Curse", 20, OBItemType.WEAPON, OBItemRarity.UNIQUE, "§7A tool of the Dark Arts, created to" , "§7torture the victims with intense pain");
    }

    @Override
    public boolean function(Player player) {
        Player lookingAt = MagicWands.entityLookingAt(player);
        Block blockLookingAt = MagicWands.blockLookingAt(player);

        Location eyes = player.getEyeLocation();
        Vector direction = eyes.getDirection().clone().normalize().multiply(10);
        Location lightningTarget = direction.toLocation(player.getWorld()).add(eyes);
        if(lookingAt != null) {
            lookingAt.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,6*20,5,false,false,false));
            lookingAt.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,6*20,5,false,false,false));
            lookingAt.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,6*20,5,false,false,false));
            System.out.println(lookingAt.getName());
            lookingAt.damage(2.5d,player);
            lightningTarget = lookingAt.getLocation();
        } else if(blockLookingAt != null) {
            lightningTarget = blockLookingAt.getLocation();
        }

        MagicWands.spellSounds(player, eyes, lightningTarget, particleEffect);
        return true;
    }

}
