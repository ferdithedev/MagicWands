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
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class AvadaKedavra extends OBItem {

    static Effects.ParticleEffect particleEffect;
    static {
        List<Effects.ParticleEffectPart> parts = new ArrayList<>();
        parts.add(new Effects.ParticleEffectPart(Particle.SNEEZE, 5, 0.2, 0.2, 0.2, null,0));
        parts.add(new Effects.ParticleEffectPart(Particle.SCRAPE, 5, 0.1, 0.1, 0.1, null,0));
        parts.add(new Effects.ParticleEffectPart(Particle.DUST_COLOR_TRANSITION, 5, 0.1, 0.1, 0.1, new Particle.DustTransition(Color.fromRGB(102,204,0), Color.fromRGB(255, 255, 255), 1f),0));
        parts.add(new Effects.ParticleEffectPart(Particle.WAX_OFF, 3, 0, 0, 0, null, 0));
        particleEffect = new Effects.ParticleEffect(parts);
    }

    public AvadaKedavra(JavaPlugin plugin) {
        super(plugin, Material.STICK, "Avada Kedavra", /*10*60*20*/20, OBItemType.WEAPON, OBItemRarity.SPECIAL, "§r§7There was a flash of blinding green light and a rushing sound, as","§r§athough a vast, invisible something was soaring through the air —","§r§7instantaneously the spider rolled over onto its back, unmarked, but","§r§aunmistakably dead.");
    }

    @Override
    public boolean function(Player player) {
        Player lookingAt = MagicWands.entityLookingAt(player);
        Block blockLookingAt = MagicWands.blockLookingAt(player);

        Location eyes = player.getEyeLocation();
        Vector direction = eyes.getDirection().clone().normalize().multiply(10);
        Location lightningTarget = direction.toLocation(player.getWorld()).add(eyes);
        if(lookingAt != null) {
            lightningTarget = lookingAt.getLocation();
            lookingAt.damage(100000,player);
        } else if(blockLookingAt != null) {
            lightningTarget = blockLookingAt.getLocation();
        }

        MagicWands.spellSounds(player, eyes, lightningTarget, particleEffect);
        return true;
    }

}
